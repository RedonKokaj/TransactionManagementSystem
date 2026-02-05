package com.banking.transactionmanagementsystem.service;

import com.banking.transactionmanagementsystem.dto.userDTO.UserRegisterRequestDTO;
import com.banking.transactionmanagementsystem.model.User;
import com.banking.transactionmanagementsystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for managing bank users business logic.
 * Handles user registration, user login and user retrieval by id.
 */

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserRegisterRequestDTO dto){

        if(userRepository.existsByEmail(dto.getEmail())){
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        user.setPasswordHash(
                passwordEncoder.encode(dto.getPassword())
        );

        return userRepository.save(user);
    }

    public User login(String email, String rawPassword){

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }

        User user = userOpt.get();

        if(!passwordEncoder.matches(rawPassword, user.getPasswordHash())){
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    public User getById(Long id){

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid id");
        }

        return userOpt.get();
    }
}
