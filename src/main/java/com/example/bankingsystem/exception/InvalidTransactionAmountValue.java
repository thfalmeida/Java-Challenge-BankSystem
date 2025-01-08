package com.example.bankingsystem.exception;

public class InvalidTransactionAmountValue extends RuntimeException {

    public InvalidTransactionAmountValue(String message) {
        super(message);
    }
}