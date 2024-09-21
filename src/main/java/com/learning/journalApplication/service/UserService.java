package com.learning.journalApplication.service;

import com.learning.journalApplication.entity.User;
import com.learning.journalApplication.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepository _userRepository;

    public List<User> getAllUsers(){
        List<User> users = _userRepository.findAll();
        return users;
    }

    public User findUserByUserName(String userName){
        return _userRepository.findByUserName(userName);
    }

    public void saveEntry(User user){
        try{
            _userRepository.save(user);
        }
        catch(Exception exception){
            log.error("Exception", exception);
        }

    }

    public User deleteEntry(ObjectId id){
        Optional<User> user = _userRepository.findById(id);
        _userRepository.deleteById(id);
        return user.orElse(null);
    }
}
