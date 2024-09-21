package com.learning.journalApplication.controller;

import com.learning.journalApplication.entity.User;
import com.learning.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    private UserService _userService;

    @PostMapping("/signup")
    public void signup(@RequestBody User user) {
        _userService.saveNewUser(user);
    }
}
