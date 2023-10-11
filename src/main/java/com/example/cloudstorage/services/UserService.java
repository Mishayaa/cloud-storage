package com.example.cloudstorage.services;

import com.example.cloudstorage.dtos.UserDto;
import com.example.cloudstorage.entities.Role;
import com.example.cloudstorage.entities.User;
import com.example.cloudstorage.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public boolean createUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return false;
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword((passwordEncoder.encode(userDto.getPassword())));
        user.setRoles(Collections.singleton(Role.USER));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while saving user", e);
        }
        return true;


    }
}
