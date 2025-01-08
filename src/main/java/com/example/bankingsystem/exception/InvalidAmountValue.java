package com.example.bankingsystem.exception;

public class InvalidAmountValue extends RuntimeException {

    public InvalidAmountValue(String message) {
        super(message);
    }
}