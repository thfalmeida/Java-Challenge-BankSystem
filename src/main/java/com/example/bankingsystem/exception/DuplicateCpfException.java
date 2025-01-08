package com.example.bankingsystem.exception;

public class DuplicateCpfException extends RuntimeException {

    public DuplicateCpfException(String message) {
        super(message);
    }
}