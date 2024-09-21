package com.learning.journalApplication.controller;

import com.learning.journalApplication.entity.User;
import com.learning.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserService _userService;

    @GetMapping
    public List<User> getallUsers(){
        return _userService.getAllUsers();
    }

    @GetMapping("/details/{userName}")
    public User getUserDetails(@PathVariable String userName){
        return _userService.findUserByUserName(userName);
    }

    @PostMapping("/createUser")
    public void createUser(@RequestBody User user){
        _userService.saveEntry(user);
    }

    @PutMapping("/updateUser/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        User userInDb = _userService.findUserByUserName(userName);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            _userService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
