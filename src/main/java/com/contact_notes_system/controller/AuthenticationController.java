package com.contact_notes_system.controller;

import com.contact_notes_system.dto.LoginRequest;
import com.contact_notes_system.entity.UserInfo;
import com.contact_notes_system.repository.UserRepository;
import com.contact_notes_system.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Login api which will authenticate the user details and will provide the access accordingly.
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            log.info("User is in login api and the credentials is being validated.");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            return new ResponseEntity<>(jwtUtil
                    .generateToken(userDetailsService
                            .loadUserByUsername(request.getUsername())), HttpStatus.ACCEPTED);
        } catch (BadCredentialsException e) {
            log.info("User entered invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception exception) {
            log.info("Authentication has been failed.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    //Register api user can register and can access the contact and notes apis
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        log.info("User is currently being registered.");
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
        }
        log.info("Creating new user details.");
        UserInfo newUser = UserInfo.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        newUser.setCreatedBy(request.getUsername());
        userRepository.save(newUser);

        return new ResponseEntity<>("User " + request.getUsername() + " registered successfully", HttpStatus.CREATED);
    }

}
