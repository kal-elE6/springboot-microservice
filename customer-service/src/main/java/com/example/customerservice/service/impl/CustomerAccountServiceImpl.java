package com.example.customerservice.service.impl;

import com.example.customerservice.dto.APIResponseDto;
import com.example.customerservice.dto.AccountDto;
import com.example.customerservice.dto.CustomerAccountDto;
import com.example.customerservice.entity.CustomerAccount;
import com.example.customerservice.repository.CustomerAccountRepository;
import com.example.customerservice.service.CustomerAccountService;
import com.example.customerservice.validation.CustomerAccountValidationException;
import com.example.customerservice.validation.CustomerAccountValidator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CustomerAccountServiceImpl  implements CustomerAccountService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerAccountServiceImpl.class);

    private CustomerAccountRepository customerAccountRepository;

    public WebClient webClient;

    @Override
    public CustomerAccountDto saveCustomer(CustomerAccountDto customerAccountDto) {
        CustomerAccountValidator.validateCustomerAccountDto(customerAccountDto);

        Long generatedCustomerNumber = generate8DigitCustomerNumber();

        CustomerAccount customerAccount = new CustomerAccount(
                generatedCustomerNumber,
                customerAccountDto.getCustomerName(),
                customerAccountDto.getCustomerMobile(),
                customerAccountDto.getCustomerEmail(),
                customerAccountDto.getAddress1(),
                customerAccountDto.getAddress2(),
                customerAccountDto.getAccountNumber()
        );

        logger.info("Saving CustomerAccount: {}", customerAccount);

        CustomerAccount savedCustomer = customerAccountRepository.save(customerAccount);

        CustomerAccountDto savedCustomerAccountDto = new CustomerAccountDto(
                savedCustomer.getCustomerNumber(),
                savedCustomer.getCustomerName(),
                savedCustomer.getCustomerMobile(),
                savedCustomer.getCustomerEmail(),
                savedCustomer.getAddress1(),
                savedCustomer.getAddress2(),
                savedCustomer.getAccountNumber()
        );

        logger.info("Saved CustomerAccountDto: {}", savedCustomerAccountDto);

        return savedCustomerAccountDto;
    }

    @Override
    public APIResponseDto getCustomerByCustomerNumber(Long customerNumber) {

        Optional<CustomerAccount> optionalCustomerAccount = customerAccountRepository.findById(customerNumber);

        if (optionalCustomerAccount.isPresent()) {
            CustomerAccount customerAccount = optionalCustomerAccount.get();

            List<AccountDto> savings = webClient.get()
                    .uri("http://localhost:8080/api/accounts/" + customerAccount.getAccountNumber())
                    .retrieve()
                    .bodyToFlux(AccountDto.class)
                    .collectList()
                    .block();

            CustomerAccountDto customerAccountDto = new CustomerAccountDto(
                    customerAccount.getCustomerNumber(),
                    customerAccount.getCustomerName(),
                    customerAccount.getCustomerMobile(),
                    customerAccount.getCustomerEmail(),
                    customerAccount.getAddress1(),
                    customerAccount.getAddress2(),
                    customerAccount.getAccountNumber()
            );

            CustomerAccountValidator.validateCustomerAccountDto(customerAccountDto);

            APIResponseDto apiResponseDto = new APIResponseDto();
            apiResponseDto.setCustomerAccount(customerAccountDto);
            apiResponseDto.setSavings(savings);

            return apiResponseDto;
        } else {
            throw new CustomerAccountValidationException("Customer not found");
        }
    }

    private Long generate8DigitCustomerNumber() {
        Random random = new Random();
        return (long) (10000000 + random.nextInt(90000000));
    }

}
