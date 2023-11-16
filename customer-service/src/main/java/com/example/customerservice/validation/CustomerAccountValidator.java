// CustomerAccountValidator.java
package com.example.customerservice.validation;

import com.example.customerservice.dto.CustomerAccountDto;
import org.springframework.util.StringUtils;

public class CustomerAccountValidator {

    public static void validateCustomerAccountDto(CustomerAccountDto customerAccountDto) {
        validateEmail(customerAccountDto.getCustomerEmail());
    }

    private static void validateEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new CustomerAccountValidationException("Email is a required field");
        }
    }
}
