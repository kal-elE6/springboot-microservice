package com.example.customerservice.controller;

import com.example.customerservice.dto.APIResponseDto;
import com.example.customerservice.dto.CustomerAccountDto;
import com.example.customerservice.dto.ErrorResponse;
import com.example.customerservice.dto.TransactionResponse;
import com.example.customerservice.service.CustomerAccountService;
import com.example.customerservice.validation.CustomerAccountValidationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/account")
@AllArgsConstructor
public class CustomerAccountController {
    private CustomerAccountService customerAccountService;

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CustomerAccountDto customerAccountDto) {
        try {
            CustomerAccountDto savedEmployee = customerAccountService.saveCustomer(customerAccountDto);

            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setCustomerNumber(savedEmployee.getCustomerNumber());
            transactionResponse.setTransactionStatusCode(HttpStatus.CREATED.value());
            transactionResponse.setTransactionStatusDescription("Customer account created");

            return new ResponseEntity<>(transactionResponse, HttpStatus.CREATED);
        } catch (CustomerAccountValidationException e) {
            // Handle validation errors
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{customer-number}")
    public ResponseEntity<?> getCustomer(@PathVariable("customer-number") Long customerNumber) {
        try {
            APIResponseDto apiResponseDto = customerAccountService.getCustomerByCustomerNumber(customerNumber);

            TransactionResponse transactionResponse = getTransactionDetails(customerNumber);

            if (transactionResponse != null) {
                apiResponseDto.setTransactionStatusCode(transactionResponse.getTransactionStatusCode());
                apiResponseDto.setTransactionStatusDescription(transactionResponse.getTransactionStatusDescription());
            }

            return ResponseEntity.ok(apiResponseDto);
        } catch (CustomerAccountValidationException e) {
            // CustomerNumber is not found
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    e.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    private TransactionResponse getTransactionDetails(Long customerNumber) {
        return new TransactionResponse(
                customerNumber,
                301,
                "Customer Account found");
    }
}
