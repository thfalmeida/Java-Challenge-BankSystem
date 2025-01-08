package com.example.bankingsystem.entity;

import com.example.bankingsystem.exception.InsufficientBalanceException;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "account")
public class ClientAccount {
    @Id
    long id;

    private String clientCPF;
    public float balance;

    public ClientAccount(String clientCPF){
        this.clientCPF = clientCPF;
        this.balance = 0;
    } 

    public void DepositValue(float depositValue){
        balance += depositValue;
    }

    public void WithdrawalValue(float withdrawalValue){
        if(balance >= withdrawalValue){
            balance -= withdrawalValue;
        }else{
            throw new InsufficientBalanceException("Saldo insuficiente");
        }
    }


}
