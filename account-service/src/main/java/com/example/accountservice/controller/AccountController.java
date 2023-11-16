package com.example.accountservice.controller;

import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/accounts")
@AllArgsConstructor
public class AccountController {

    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> saveAccount(@RequestBody AccountDto accountDto){
       AccountDto savedAccount = accountService.saveAccount(accountDto);
       return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    @GetMapping("{account-number}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("account-number") Long accountNumber){
        AccountDto accountDto = accountService.getAccountByAccountNumber(accountNumber);
//        AccountDto responseDto = new AccountDto(
//                null,
//                accountDto.getAccountNumber(),
//                accountDto.getAccountType(),
//                accountDto.getAvailableBalance()
//        );
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

}
