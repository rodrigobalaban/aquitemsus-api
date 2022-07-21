package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.User;
import br.ufpr.aquitemsus.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService _userService;

    public UserController(UserService userService) {
        _userService = userService;
    }

    @GetMapping
    public List<User> findAllUsers(@RequestParam String search,
                                   @RequestParam int page,
                                   @RequestParam int pagesize,
                                   HttpServletResponse response) {
        Page<User> pageUser = this._userService.findAllUsersByName(search, page, pagesize);
        response.setHeader("X-Total-Count", String.valueOf(pageUser.getTotalElements()));
        return pageUser.toList();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id) {
        return this._userService.findUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
        return _userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser( @PathVariable Long id, @RequestBody User updatedProduct) {
        return _userService.updateUser(id, updatedProduct);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        _userService.delete(id);
    }

    @PostMapping
    @RequestMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@RequestBody String email) {
        _userService.resetPassword(email);
    }
}
