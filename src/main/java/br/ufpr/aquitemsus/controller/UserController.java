package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.User;
import br.ufpr.aquitemsus.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService _userService;

    public UserController(UserService userService) {
        _userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return _userService.createUser(user);
    }
}
