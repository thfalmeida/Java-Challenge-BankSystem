package com.example.bankingsystem.DTO;

import java.util.List;

import lombok.Data;

@Data
public class TransactionRequest {
    private List<TransactionOperation> operations;
}