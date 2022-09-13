package br.ufpr.aquitemsus.controller;

import br.ufpr.aquitemsus.model.UserAdmin;
import br.ufpr.aquitemsus.model.UserSus;
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
    public List<UserAdmin> findAllUsers(@RequestParam String search,
                                   @RequestParam int page,
                                   @RequestParam int pagesize,
                                   HttpServletResponse response) {
        Page<UserAdmin> pageUser = this._userService.findAllUsersByName(search, page, pagesize);
        response.setHeader("X-Total-Count", String.valueOf(pageUser.getTotalElements()));
        return pageUser.toList();
    }

    @GetMapping("/{id}")
    public UserAdmin findUserById(@PathVariable Long id) {
        return this._userService.findUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserAdmin saveUser(@RequestBody UserAdmin user) {
        return _userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public UserAdmin updateUser(@PathVariable Long id, @RequestBody UserAdmin updatedProduct) {
        return _userService.updateUser(id, updatedProduct);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        _userService.delete(id);
    }

    @GetMapping("/sus/{id}")
    public UserSus findUserSusById(@PathVariable Long id) {
        return _userService.findUserSusById(id);
    }

    @PostMapping("/sus")
    @ResponseStatus(HttpStatus.CREATED)
    public UserSus saveUserSus(@RequestBody UserSus userSus) {
        return _userService.saveUserSus(userSus);
    }

    @PutMapping("/sus/{id}")
    public UserSus updateUserSus(@PathVariable Long id, @RequestBody UserSus userSus) {
        return _userService.updateUserSus(id, userSus);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody String email) {
        _userService.resetPassword(email);
    }
}
