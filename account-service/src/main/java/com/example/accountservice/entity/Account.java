package com.example.accountservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @NotNull
    private Long accountNumber;

    @NotNull(message = "Account type cannot be null")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private int availableBalance;
}
