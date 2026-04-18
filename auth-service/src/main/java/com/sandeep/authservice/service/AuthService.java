package com.sandeep.authservice.service;

import com.sandeep.authservice.dto.LoginRequestDto;
import com.sandeep.authservice.model.User;
import com.sandeep.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Optional<String> authenticate(LoginRequestDto loginRequestDto){
        Optional<String> token=userService.findByEmail(loginRequestDto.getEmail())
                .filter(u->passwordEncoder.matches(loginRequestDto.getPassword(),u.getPassword()
                )).map(u->jwtUtil.generateToken(u.getEmail(),u.getRole()));
        return token;
    }


    public boolean validateToken(String token){
        try {
            jwtUtil.validateToken(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }
}
