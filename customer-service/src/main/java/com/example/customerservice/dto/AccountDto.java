package com.example.customerservice.dto;

import com.example.customerservice.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long accountNumber;
    private AccountType accountType;
    private int availableBalance;
}
