package com.example.bankingsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "client", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf")
})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 11)
    @NotNull
    private String cpf;

    @Column(nullable = false, length = 60)
    @NotNull
    private String name;

    public Client(String name, String cpf){
        this.name = name;
        this.cpf = cpf;
    }

}
