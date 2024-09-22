package com.learning.journalApplication.service;

import com.learning.journalApplication.entity.User;
import com.learning.journalApplication.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository _userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers(){
        List<User> users = _userRepository.findAll();
        return users;
    }

    public User findUserByUserName(String userName){
        return _userRepository.findByUserName(userName);
    }

    public void saveUser(User user){
        _userRepository.save(user);
    }

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        _userRepository.save(user);
    }

    public void saveAdminUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER", "ADMIN"));
        _userRepository.save(user);
    }

    public void deleteUser(String userName){
        _userRepository.deleteByUserName(userName);
    }
}
