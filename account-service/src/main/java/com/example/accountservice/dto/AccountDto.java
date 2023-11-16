package com.example.accountservice.dto;

import com.example.accountservice.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
//    private Long id;
    private Long accountNumber;
    private AccountType accountType;
    private int availableBalance;
}
