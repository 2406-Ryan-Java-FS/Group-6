package com.revature.controller;

import com.revature.dto.AuthResponseDTO;
import com.revature.model.User;
import com.revature.repository.UserRepository;
import com.revature.security.JwtGenerator;
import com.revature.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    UserService userService;

    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    JwtGenerator jwtGenerator;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginValidate(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);

    }

}
