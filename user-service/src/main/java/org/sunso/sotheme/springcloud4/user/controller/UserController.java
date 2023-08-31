package org.sunso.sotheme.springcloud4.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sunso.sotheme.springcloud4.common.dto.user.UserDTO;
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

    @PostMapping("/save")
    public User saveUser(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }
}
