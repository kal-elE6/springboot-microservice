package com.example.accountservice.service;

import com.example.accountservice.dto.AccountDto;

public interface AccountService {
    AccountDto saveAccount(AccountDto accountDto);

    AccountDto getAccountByAccountNumber(Long accountNumber);
}
