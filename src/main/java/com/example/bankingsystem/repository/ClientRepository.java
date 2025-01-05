package com.example.bankingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bankingsystem.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    public Optional<Client> findByCpf(String cpf);
    
}
