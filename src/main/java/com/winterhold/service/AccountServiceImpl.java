package com.winterhold.service;

import com.winterhold.ApplicationUserDetails;
import com.winterhold.dto.account.RegisterDTO;
import com.winterhold.entity.Account;
import com.winterhold.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public void registerAccount(RegisterDTO dto) {
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        Account account = new Account(
                dto.getUsername(),
                hashPassword);
        accountRepository.save(account);
    }

    @Override
    public Boolean checkExistingAccount(String username) {
        Long countAccount = accountRepository.count(username);
        return countAccount > 0;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> nullableEntity = accountRepository.findById(username);
        Account tempAccount = null;
        if (nullableEntity.isPresent()){
            tempAccount = nullableEntity.get();
        }
        return new ApplicationUserDetails(tempAccount);
    }
}
