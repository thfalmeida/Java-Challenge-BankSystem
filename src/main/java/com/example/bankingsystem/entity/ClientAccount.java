package com.example.bankingsystem.entity;

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
}
