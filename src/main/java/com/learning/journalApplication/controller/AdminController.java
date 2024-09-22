package com.learning.journalApplication.controller;

import com.learning.journalApplication.entity.User;
import com.learning.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService _userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> allAvailableUsers = _userService.getAllUsers();
        if(!allAvailableUsers.isEmpty()){
            return new ResponseEntity<>(allAvailableUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdmin(@RequestBody User user){
        try{
            _userService.saveAdminUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
