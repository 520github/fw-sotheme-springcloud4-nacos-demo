package org.sunso.sotheme.springcloud4.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sunso.sotheme.springcloud4.common.dto.user.UserDTO;
import org.sunso.sotheme.springcloud4.user.entity.User;
import org.sunso.sotheme.springcloud4.user.service.UserService;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get/{userId}")
    public User findOneByUserId(@PathVariable Long userId, HttpServletRequest request) {
        log.info("findOneByUserId header [User-Agent] value is [{}]", request.getHeader("User-Agent"));
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

    @DeleteMapping("/delete/{userId}")
    public int deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
