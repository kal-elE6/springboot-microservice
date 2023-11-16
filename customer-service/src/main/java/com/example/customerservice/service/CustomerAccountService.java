package com.example.customerservice.service;

import com.example.customerservice.dto.APIResponseDto;
import com.example.customerservice.dto.CustomerAccountDto;

public interface CustomerAccountService {
   CustomerAccountDto saveCustomer(CustomerAccountDto customerAccountDto);

   APIResponseDto getCustomerByCustomerNumber(Long customerNumber);
}
