package com.sandeep.authservice.controller;

import com.sandeep.authservice.dto.LoginRequestDto;
import com.sandeep.authservice.dto.LoginResponseDto;
import com.sandeep.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
//        System.out.println(passwordEncoder.matches(
//                "password123",
//                "$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu"
//        ));


        Optional<String> tokenOptional=authService.authenticate(loginRequestDto);
        if(tokenOptional.isEmpty()){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token=tokenOptional.get();
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @Operation(summary = "validate Token")
    @GetMapping("/validate")
    public ResponseEntity<Void> valdiateToken(@RequestHeader("Authorization")String authHeader){
        if(authHeader==null|| !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return authService.validateToken(authHeader.substring(7))?ResponseEntity.ok().build():ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
