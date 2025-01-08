package com.example.bankingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.bankingsystem.DTO.TransactionRequest;
import com.example.bankingsystem.entity.Client;
import com.example.bankingsystem.entity.ClientAccount;
import com.example.bankingsystem.exception.DuplicateCpfException;
import com.example.bankingsystem.exception.ResourceNotFoundException;
import com.example.bankingsystem.repository.ClientAccountRepository;
import com.example.bankingsystem.util.LockManager;
import com.example.bankingsystem.util.TransactionManager;

@Service
public class ClientAccountService {

    @Autowired
    ClientAccountRepository clientAccountRepository;

    @Autowired
    TransactionManager transactionManager;

    @Autowired
    LockManager lockManager;

    public ClientAccount CreateNewAccount(Client client){
        String clientCPF = client.getCpf();
        boolean clientAlreadyRegistered = clientAccountRepository.existsByClientCPF(clientCPF);
        if(clientAlreadyRegistered){
            throw new DuplicateCpfException("O CPF já possui uma conta cadastrada");
        }
        
        ClientAccount clientAccount = new ClientAccount(clientCPF);
        clientAccountRepository.save(clientAccount);
        return clientAccount;
    }

    public ClientAccount GetClientAccountByCPF(String cpf){
        Optional<ClientAccount> clientAccount = clientAccountRepository.findByClientCPF(cpf);

        return clientAccount.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhuma conta para o CPF informado"));
    }

    public ClientAccount GetClientAccount(Client client){
        String clientCPF = client.getCpf();
        return GetClientAccountByCPF(clientCPF);
    }


    public ClientAccount ProcessTransactions(Client client, TransactionRequest transactionsRequest){
        ClientAccount clientAccount = GetClientAccount(client); 

        ClientAccount processedClientAccount = transactionManager.ProcessTransaction(clientAccount, transactionsRequest);
        clientAccountRepository.save(processedClientAccount);
        return processedClientAccount;
    }
}
