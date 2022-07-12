package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AuthService implements UserDetailsService {

    // EXPIRATION_TIME = 10 dias
    static final long EXPIRATION_TIME = 860_000_000;
    static final String SECRET = "MySecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    private final UserService _userService;

    public AuthService(UserService userService) {
        _userService = userService;
    }

    public static void addAuthentication(HttpServletResponse response, String email) throws IOException {
        Date expirationTime = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String jwt = Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        var sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String expirationTimeISO = sdf.format(expirationTime);

        String jsonMask = "{ \"email\": \"%s\", \"token\": \"%s\", \"expirationTime\": \"%s\" }";
        String userAuth = String.format(jsonMask, email, jwt, expirationTimeISO);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(userAuth);
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            }
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email)	throws UsernameNotFoundException, DataAccessException {
        List<GrantedAuthority> listGrantAuthority = new ArrayList<>();
        User user = _userService.findUserByEmail(email);
        // TODO: checkGrantAuthorities(user, listGrantAuthority);
        return validateUser(email, listGrantAuthority, user);
    }

    private UserDetails validateUser(String email, List<GrantedAuthority> listGrantAuthority, User user) {
        UserDetails userDetails = null;
        if (user != null) {
            boolean accountNonLocked = true;
            boolean enabledUser = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            userDetails = new org.springframework.security.core.userdetails.User(email, user.getPassword(), enabledUser, accountNonExpired, credentialsNonExpired, accountNonLocked, listGrantAuthority);
        }
        return userDetails;
    }
}
