package com.example.customerservice.service.impl;

import com.example.customerservice.dto.APIResponseDto;
import com.example.customerservice.dto.AccountDto;
import com.example.customerservice.dto.CustomerAccountDto;
import com.example.customerservice.entity.CustomerAccount;
import com.example.customerservice.repository.CustomerAccountRepository;
import com.example.customerservice.service.impl.CustomerAccountServiceImpl;
import com.example.customerservice.validation.CustomerAccountValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.customerservice.entity.AccountType.SAVINGS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerAccountServiceImplTest {

    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private CustomerAccountServiceImpl customerAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveCustomer() {
        // Mock data
        CustomerAccountDto inputDto = new CustomerAccountDto(
                null, "John Doe", "1234567890", "john@example.com",
                "Address1", "Address2", 12345678L
        );

        CustomerAccount savedCustomer = new CustomerAccount(
                12345678L, "John Doe", "1234567890", "john@example.com",
                "Address1", "Address2", 12345678L
        );

        when(customerAccountRepository.save(any())).thenReturn(savedCustomer);

        // Perform the service method
        CustomerAccountDto resultDto = customerAccountService.saveCustomer(inputDto);

        // Verify the result
        assertNotNull(resultDto);
        assertNotNull(resultDto.getCustomerNumber());
        assertEquals("John Doe", resultDto.getCustomerName());
        assertEquals("1234567890", resultDto.getCustomerMobile());
        assertEquals("john@example.com", resultDto.getCustomerEmail());
        assertEquals("Address1", resultDto.getAddress1());
        assertEquals("Address2", resultDto.getAddress2());
        assertEquals(12345678L, resultDto.getAccountNumber());

        // Verify that the repository save method was called
        verify(customerAccountRepository, times(1)).save(any());
    }

    @Test
    void testGetCustomerByCustomerNumber() {
        // Mock data
        Long customerNumber = 12345678L;

        CustomerAccount customerAccount = new CustomerAccount(
                customerNumber, "John Doe", "1234567890", "john@example.com",
                "Address1", "Address2", 2345678L
        );

        when(customerAccountRepository.findById(customerNumber)).thenReturn(Optional.of(customerAccount));

        AccountDto accountDto = new AccountDto(12345L, SAVINGS, 1000);
        List<AccountDto> savingsList = Arrays.asList(accountDto);

        // Mock the behavior of WebClient methods
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec responseSpecMock = mock(WebClient.ResponseSpec.class);

        Stream<AccountDto> accountDtoStream = Arrays.asList(accountDto).stream();
        Flux<AccountDto> accountDtoFlux = Flux.fromStream(accountDtoStream);

        when(webClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToFlux(AccountDto.class)).thenReturn(accountDtoFlux);


        APIResponseDto apiResponseDto = customerAccountService.getCustomerByCustomerNumber(customerNumber);

        assertNotNull(apiResponseDto);
        assertNotNull(apiResponseDto.getCustomerAccount());
        assertNotNull(apiResponseDto.getSavings());
        assertEquals(1, apiResponseDto.getSavings().size());

        verify(customerAccountRepository, times(1)).findById(customerNumber);
    }

    @Test
    void testGetCustomerByCustomerNumberCustomerNotFound() {
        // Mock data
        Long customerNumber = 12345678L;

        when(customerAccountRepository.findById(customerNumber)).thenReturn(Optional.empty());

        // Perform the service method and expect an exception
        assertThrows(CustomerAccountValidationException.class, () -> {
            customerAccountService.getCustomerByCustomerNumber(customerNumber);
        });

        // Verify that the repository findById method was called
        verify(customerAccountRepository, times(1)).findById(customerNumber);
    }
}


