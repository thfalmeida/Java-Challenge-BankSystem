package com.example.bankingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.bankingsystem.DTO.TransactionOperation;
import com.example.bankingsystem.DTO.TransactionRequest;
import com.example.bankingsystem.entity.Client;
import com.example.bankingsystem.entity.ClientAccount;
import com.example.bankingsystem.enums.TransactionType;
import com.example.bankingsystem.exception.DuplicateCpfException;
import com.example.bankingsystem.exception.ResourceNotFoundException;
import com.example.bankingsystem.repository.ClientAccountRepository;
import com.example.bankingsystem.util.LockManager;

@Service
public class ClientAccountService {

    @Autowired
    ClientAccountRepository clientAccountRepository;

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
        List<TransactionOperation> transactions = transactionsRequest.getOperations();
        for (TransactionOperation transaction : transactions) {
            TransactionType type = transaction.getType();
            float amount = transaction.getAmount();
            switch (type) {
                case DEPOSIT:
                    Deposit(client, amount);
                    break;
                case WITHDRAW:
                    Withdrawl(client, amount);
                default:
                    break;
            }
        }
        return null;
    }

    private ClientAccount Deposit(Client client, float value){
        ClientAccount clientAccount = GetClientAccount(client);
        long clientAccountId = clientAccount.getId();

        lockManager.lock(clientAccountId);

        clientAccount.DepositValue(value);
        clientAccountRepository.save(clientAccount);

        lockManager.unlock(clientAccountId);

        return clientAccount;
    }

    private ClientAccount Withdrawl(Client client, float value){
        ClientAccount clientAccount = GetClientAccount(client);
        long clientAccountId = clientAccount.getId();

        lockManager.lock(clientAccountId);

        clientAccount.WithdrawalValue(value);
        clientAccountRepository.save(clientAccount);

        lockManager.unlock(clientAccountId);

        return clientAccount;
    }
    
}
