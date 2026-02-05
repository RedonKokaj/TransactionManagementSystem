package com.banking.transactionmanagementsystem.service;

import com.banking.transactionmanagementsystem.model.Account;
import com.banking.transactionmanagementsystem.model.User;
import com.banking.transactionmanagementsystem.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Service class for managing bank account business logic.
 * Handles account creation and account retrieval.
 */

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account createAccount(User user){

        String iban;

        do{
            iban = generateIban();
        }while(accountRepository.existsByIban(iban));

        Account account = new Account();
        account.setIban(iban);
        account.setBalance(BigDecimal.ZERO);
        account.setUser(user);

        return accountRepository.save(account);
    }

    //For this project we will generate ITALIAN ibans only
    //IT + CC + CIN + ABI + CAB + NUMERO_CONTO
    public String generateIban() {

        //IT for Italy
        String countryCode = "IT";

        //2 international check digits (10-99)
        int checkDigits = ThreadLocalRandom.current().nextInt(10, 100);

        //CIN (Control Internal Number): a random letter A-Z
        char cin = (char) ('A' + ThreadLocalRandom.current().nextInt(26));

        //ABI (Bank Code): 5 random digits (ex.: 00123)
        String abi = String.format("%05d", ThreadLocalRandom.current().nextInt(100000));

        //CAB (Branch Code): 5 random digits (ex.: 00045)
        String cab = String.format("%05d", ThreadLocalRandom.current().nextInt(100000));

        //Account Number: 12 random digits
        String accountNumber = String.format("%012d",
                ThreadLocalRandom.current().nextLong(0, 1_000_000_000_000L));

        return countryCode + checkDigits + cin + abi + cab + accountNumber;
    }

    public List<Account> getUserAccounts(Long userId) {
        return accountRepository.findByUserId(userId);
    }
}
