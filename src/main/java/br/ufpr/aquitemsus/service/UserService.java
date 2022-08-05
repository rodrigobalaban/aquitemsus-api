package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.exception.NotFoundException;
import br.ufpr.aquitemsus.model.User;
import br.ufpr.aquitemsus.model.UserAdmin;
import br.ufpr.aquitemsus.model.UserSus;
import br.ufpr.aquitemsus.model.enums.UserRole;
import br.ufpr.aquitemsus.repository.UserAdminRepository;
import br.ufpr.aquitemsus.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class UserService {

    private final UserRepository _userRepository;
    private final UserAdminRepository _userAdminRepository;
    private final JavaMailSender _mailSender;

    public UserService(UserRepository userRepository,
                       UserAdminRepository userAdminRepository,
                       JavaMailSender mailSender) {
        _userRepository = userRepository;
        _userAdminRepository = userAdminRepository;
        _mailSender = mailSender;
    }

    public Page<UserAdmin> findAllUsersByName(String name, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this._userAdminRepository.findAllByNameContainingIgnoreCase(name, pageable);
    }

    public UserAdmin findUserById(Long id) {
        return this._userAdminRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public User findUserByEmail(String email) {
        return _userRepository.findUserByEmail(email);
    }

    public UserAdmin saveUser(UserAdmin user) {
        return _userAdminRepository.save(user);
    }

    public UserAdmin updateUser(Long idUser, UserAdmin updatedUser) {
        UserAdmin user = findUserById(idUser);

        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());
        user.setRole(updatedUser.getRole());
        user.setAllowedEstablishments(updatedUser.getAllowedEstablishments());

        return saveUser(user);
    }

    public void delete(Long idUser) {
        User user = findUserById(idUser);

        _userAdminRepository.deleteById(user.getId());
    }

    public UserSus registerUserSus(UserSus userSus) {
        userSus.setRole(UserRole.User);

        return _userRepository.save(userSus);
    }

    public void resetPassword(String email) {
        User user = _userRepository.findUserByEmail(email);

        if (user == null) {
            return;
        }

        try {
            MimeMessage mail = _mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper( mail );
            helper.setTo(user.getEmail());
            helper.setFrom("rodrigo.balaban@ufpr.br", "Aqui Tem SUS");
            helper.setSubject("Recuperação de Senha");

            String message = "<p>Olá, sua senha para acesso ao Aqui Tem SUS é: " + user.getPassword() + "</p>";
            helper.setText(message, true);

            _mailSender.send(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
