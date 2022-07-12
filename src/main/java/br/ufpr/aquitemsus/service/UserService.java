package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.model.User;
import br.ufpr.aquitemsus.repository.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class UserService {

    private final UserRepository _userRepository;
    private final JavaMailSender _mailSender;

    public UserService(UserRepository userRepository, JavaMailSender mailSender) {
        _userRepository = userRepository;
        _mailSender = mailSender;
    }

    public User findUserByEmail(String email) {
        return _userRepository.findUserByEmail(email);
    }

    public User createUser(User user) {
        return _userRepository.save(user);
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
