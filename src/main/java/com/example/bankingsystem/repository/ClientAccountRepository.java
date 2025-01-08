package com.example.bankingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.bankingsystem.entity.ClientAccount;

public interface ClientAccountRepository extends JpaRepository<ClientAccount, Long>{
    
    public Optional<ClientAccount> findByClientCPF(String cpf);

    public boolean existsByClientCPF(String cpf);
}
