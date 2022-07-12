package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.exception.NotFoundException;
import br.ufpr.aquitemsus.model.User;
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
    private final JavaMailSender _mailSender;

    public UserService(UserRepository userRepository, JavaMailSender mailSender) {
        _userRepository = userRepository;
        _mailSender = mailSender;
    }

    public Page<User> findAllUsersByName(String name, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this._userRepository.findAllByNameContainingIgnoreCase(name, pageable);
    }

    public User findUserById(Long id) {
        return this._userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public User findUserByEmail(String email) {
        return _userRepository.findUserByEmail(email);
    }

    public User saveUser(User user) {
        return _userRepository.save(user);
    }

    public User updateUser(Long idUser, User updatedUser) {
        User user = findUserById(idUser);

        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());

        return _userRepository.save(user);
    }

    public void delete(Long idUser) {
        User product = findUserById(idUser);

        _userRepository.deleteById(product.getId());
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
