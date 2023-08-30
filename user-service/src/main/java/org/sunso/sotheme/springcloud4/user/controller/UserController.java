package org.sunso.sotheme.springcloud4.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sunso.sotheme.springcloud4.user.entity.User;
import org.sunso.sotheme.springcloud4.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get/{userId}")
    public User findOneByUserId(@PathVariable Long userId) {
        User user = userService.findOneByUserId(userId);
        if (user == null) {
            user = User.emptyUser();
        }
        return user;
    }

    @GetMapping("/save/{userId}")
    public User saveUser(@PathVariable Long userId) {
        return userService.save(userId);
    }
}
