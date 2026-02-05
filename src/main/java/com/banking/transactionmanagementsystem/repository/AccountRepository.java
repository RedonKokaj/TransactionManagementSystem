package com.banking.transactionmanagementsystem.repository;

import com.banking.transactionmanagementsystem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Account entity.
 * Provides standard CRUD operations and custom query methods using Spring Data JPA.
 */

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByIban(String iban); //auto creates sql script to select * from accounts where iban = ?

    List<Account> findByUserId(Long userId);

    boolean existsByIban(String iban);
}