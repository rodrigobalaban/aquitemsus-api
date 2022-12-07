package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.model.User;
import br.ufpr.aquitemsus.model.UserToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.*;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {

    // EXPIRATION_TIME = 10 dias
    static final long EXPIRATION_TIME = 860_000_000;
    static final String SECRET = "MySecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";
    static final String AUTHORITIES_KEY = "Role";

    private final UserService _userService;

    public AuthService(UserService userService) {
        _userService = userService;
    }

    public void addAuthentication(HttpServletResponse response, Authentication auth) throws IOException {
        User user = this._userService.findUserByEmail(auth.getName());

        UserToken userToken = new UserToken();
        userToken.setId(user.getId());
        userToken.setName(user.getName());
        userToken.setEmail(user.getEmail());
        userToken.setRole(user.getRole());
        userToken.setExpirationTime(new Date(System.currentTimeMillis() + EXPIRATION_TIME));

        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwt = Jwts.builder()
                .setSubject(userToken.getEmail())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(userToken.getExpirationTime())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        userToken.setToken(jwt);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();

        response.getWriter().write(gson.toJson(userToken));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            final JwtParser jwtParser = Jwts.parser().setSigningKey(SECRET);
            final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
            final Claims claims = claimsJws.getBody();

            final String user = claims.getSubject();

            final Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email)	throws UsernameNotFoundException, DataAccessException {
        User user = _userService.findUserByEmail(email);

        List<GrantedAuthority> listGrantAuthority = new ArrayList<>();
        checkGrantAuthorities(user, listGrantAuthority);

        return validateUser(listGrantAuthority, user);
    }

    private void checkGrantAuthorities(User user, List<GrantedAuthority> listGrantAuthority) {
        String role = String.valueOf(user.getRole());
        listGrantAuthority.add(new SimpleGrantedAuthority(role));
    }

    private UserDetails validateUser(List<GrantedAuthority> listGrantAuthority, User user) {
        UserDetails userDetails = null;

        if (user != null) {
            boolean accountNonLocked = true;
            boolean enabledUser = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), enabledUser, accountNonExpired, credentialsNonExpired, accountNonLocked, listGrantAuthority);
        }

        return userDetails;
    }
}
