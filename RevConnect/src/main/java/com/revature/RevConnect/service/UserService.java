package com.revature.RevConnect.service;

import com.revature.RevConnect.models.User;
import com.revature.RevConnect.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User u) {

        u.setPassword(PasswordEncrypt.encrypt(u.getPassword()));

        return userRepository.save(u);
    }

    public void deleteUser(User u) {

        userRepository.delete(u);
    }

    //TODO: update user
    public void updateUser(User user) {

    }

    public User getUser(int userID) {

        return userRepository.findByUserID(userID);
    }

    public User getUser(String username) {

        return userRepository.findByUsername(username);
    }

    @Transactional
    public void updateBio(Integer userID,String bio){
        userRepository.updateBio(userID,bio);
    }
}
