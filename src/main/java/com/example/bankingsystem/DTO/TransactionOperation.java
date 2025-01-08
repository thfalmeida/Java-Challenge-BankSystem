package com.example.bankingsystem.DTO;

import com.example.bankingsystem.enums.TransactionType;

import lombok.Data;

@Data
public class TransactionOperation {
    private TransactionType type; // "DEPOSIT" ou "WITHDRAW"
    private float amount;
}
