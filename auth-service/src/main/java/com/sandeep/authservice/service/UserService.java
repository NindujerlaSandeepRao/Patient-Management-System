package com.sandeep.authservice.service;

import com.sandeep.authservice.model.User;
import com.sandeep.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByEmail(String email){
       return userRepository.findByEmail(email);
    }
}
