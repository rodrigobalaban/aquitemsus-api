package br.ufpr.aquitemsus.service;

import br.ufpr.aquitemsus.model.User;
import br.ufpr.aquitemsus.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository _userRepository;

    public UserService(UserRepository userRepository) {
        _userRepository = userRepository;
    }

    public User findUserByEmail(String email) {
        return _userRepository.findUserByEmail(email);
    }

    public User createUser(User user) {
        return _userRepository.save(user);
    }
}
