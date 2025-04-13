package com.contact_notes_system.service;

import com.contact_notes_system.entity.UserInfo;
import com.contact_notes_system.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    //This method is used to filter and provide authentication for the user and can be used to validate the token as well.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("User is being loaded for the session "+username);
        return User
                .withUsername(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }
}
