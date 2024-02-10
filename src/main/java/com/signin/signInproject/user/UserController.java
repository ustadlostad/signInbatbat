package com.signin.signInproject.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/blogger")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/{userId}")
    public Optional<User> getUser(@PathVariable long userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/user")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/user/{userId}")
    public Optional<User> editUser(@PathVariable("userId") Long userId, @RequestBody User user) {

        Optional<User> theResultUser = userService.updateUser(userId, user);

        return theResultUser;
    }

    @DeleteMapping("/user/{userId}")
    public int deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return -1;
    }

    @PostMapping(path = "/user/login", consumes = {"application/json"})
    public void loginUser(@RequestBody User user) {

        userService.loginUser(user.getEmail(), user.getPassword());
    }

    @PostMapping(path = "user/register", consumes = {"application/json"})
    public String registerUser(@RequestBody User user) {
        userService.registerUser(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth(),
                user.getPassword());

        return "you are in the main page!";
    }


}
