package com.example.customerservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerAccount {
    @Id
    private Long customerNumber;

    @NotEmpty(message = "Customer name is required")
    @Size(max = 50, message = "Customer name must not exceed 50 characters")
    private String customerName;

    @NotEmpty(message = "Customer mobile is required")
    @Size(max = 20, message = "Customer mobile must not exceed 20 characters")
    private String customerMobile;

    @NotEmpty(message = "Customer email is required")
    @Size(max = 50, message = "Customer email must not exceed 50 characters")
    private String customerEmail;

    @NotEmpty(message = "Address1 is required")
    @Size(max = 100, message = "Address1 must not exceed 100 characters")
    private String address1;

    @Size(max = 100, message = "Address2 must not exceed 100 characters")
    private String address2;

    private Long accountNumber;

}
