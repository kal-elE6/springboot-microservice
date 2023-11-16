package com.example.accountservice.service.impl;

import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.entity.Account;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    @Override
    public AccountDto saveAccount(AccountDto accountDto) {
        Account account = new Account(
//                accountDto.getId(),
                accountDto.getAccountNumber(),
                accountDto.getAccountType(),
                accountDto.getAvailableBalance()
        );

        Account savedAccount = accountRepository.save(account);

        AccountDto savedAccountDto = new AccountDto(
//                savedAccount.getId(),
                savedAccount.getAccountNumber(),
                savedAccount.getAccountType(),
                savedAccount.getAvailableBalance()
        );

        return savedAccountDto;
    }

    @Override
    public AccountDto getAccountByAccountNumber(Long accountNumber) {

        Account savings = accountRepository.findByAccountNumber(accountNumber);

        AccountDto accountDto = new AccountDto(
//                account.getId(),
                savings.getAccountNumber(),
                savings.getAccountType(),
                savings.getAvailableBalance()
        );
        return accountDto;
    }
}
