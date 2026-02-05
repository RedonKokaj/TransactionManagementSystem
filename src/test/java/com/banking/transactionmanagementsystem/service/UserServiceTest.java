package com.banking.transactionmanagementsystem.service;

import com.banking.transactionmanagementsystem.dto.userDTO.UserRegisterRequestDTO;
import com.banking.transactionmanagementsystem.model.User;
import com.banking.transactionmanagementsystem.repository.AccountRepository;
import com.banking.transactionmanagementsystem.repository.TransactionRepository;
import com.banking.transactionmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for UserService.
 * Verifies user registration, duplicate email prevention, and
 * authentication logic (login) against the database.
 */

@SpringBootTest
@ActiveProfiles("test")
@Rollback
@Transactional
public class UserServiceTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    void cleanDB() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void registerUser_success(){
        UserRegisterRequestDTO dto = new UserRegisterRequestDTO();

        dto.setName("Pierino");
        dto.setSurname("NonSoIlCognome");
        dto.setEmail("pierino@test.com");
        dto.setPassword("password123");
        dto.setBirthDate(LocalDate.of(2000, 1, 1));

        User user = userService.register(dto);

        assertNotNull(user.getId());
        assertEquals("pierino@test.com", user.getEmail());
    }

    @Test
    void registerUser_duplicateEmail_throwsException(){
        User user = new User();
        user.setName("Pierino");
        user.setSurname("NonSoIlCognome");
        user.setEmail("pierino@test.com");
        user.setPasswordHash("password123");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        userRepository.save(user);

        UserRegisterRequestDTO dto = new UserRegisterRequestDTO();
        dto.setName("Pierino");
        dto.setSurname("NonSoIlCognome");
        dto.setEmail("pierino@test.com");
        dto.setPassword("password123");
        dto.setBirthDate(LocalDate.of(2000, 1, 1));

        //I expect an exception, if no exception there is an error
        assertThrows(RuntimeException.class, () -> userService.register(dto));
    }

    @Test
    void login_success() {
        UserRegisterRequestDTO dto = new UserRegisterRequestDTO();
        dto.setName("Pierino");
        dto.setSurname("NonSoIlCognome");
        dto.setEmail("pierino@test.com");
        dto.setPassword("password123");
        dto.setBirthDate(LocalDate.of(2000, 1, 1));

        userService.register(dto);

        assertDoesNotThrow(() ->
                userService.login("pierino@test.com", "password123")
        );
    }


    @Test
    void login_wrongPassword_throwsException(){
        User user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("pierino@test.com");
        user.setPasswordHash("password123");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        userRepository.save(user);

        assertThrows(RuntimeException.class, () ->
                userService.login("pierino@test.com", "wrongPassword")
        );
    }
}
