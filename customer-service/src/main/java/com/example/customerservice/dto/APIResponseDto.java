package com.example.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponseDto {
    private CustomerAccountDto customerAccount;
    private List<AccountDto> savings;
    private int transactionStatusCode;
    private String transactionStatusDescription;
}
