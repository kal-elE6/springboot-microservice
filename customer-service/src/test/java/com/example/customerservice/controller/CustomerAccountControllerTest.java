package com.example.customerservice.controller;

import com.example.customerservice.dto.APIResponseDto;
import com.example.customerservice.dto.CustomerAccountDto;
import com.example.customerservice.service.CustomerAccountService;
import com.example.customerservice.validation.CustomerAccountValidationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerAccountController.class)
public class CustomerAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerAccountService customerAccountService;

    @InjectMocks
    private CustomerAccountController customerAccountController;

    @Test
    void testCreateAccount() throws Exception {
        // Mock service behavior
        when(customerAccountService.saveCustomer(any(CustomerAccountDto.class)))
                .thenReturn(new CustomerAccountDto(1L, "John Doe", "1234567890", "john@example.com", "Address1", "Address2", 12345678L));

        // Perform the request
        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customerName\": \"John Doe\", \"customerMobile\": \"1234567890\", \"customerEmail\": \"john@example.com\", \"address1\": \"Address1\", \"address2\": \"Address2\", \"accountNumber\": \"12345678\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerNumber").value(1))
                .andExpect(jsonPath("$.transactionStatusCode").value(201))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Customer account created"));
    }

    @Test
    void testGetCustomer() throws Exception {
        // Mock service behavior
        when(customerAccountService.getCustomerByCustomerNumber(anyLong()))
                .thenReturn(new APIResponseDto());

        // Perform the request
        mockMvc.perform(get("/api/v1/account/{customer-number}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionStatusCode").value(301))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Customer Account found"));


    }

    @Test
    void testGetCustomerNotFound() throws Exception {
        // Mock service behavior for the case when CustomerAccountValidationException is thrown
        when(customerAccountService.getCustomerByCustomerNumber(anyLong()))
                .thenThrow(new CustomerAccountValidationException("Customer not found"));

        // Perform the request
        mockMvc.perform(get("/api/v1/account/{customer-number}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.transactionStatusCode").value(401))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Customer not found"));
    }
}

