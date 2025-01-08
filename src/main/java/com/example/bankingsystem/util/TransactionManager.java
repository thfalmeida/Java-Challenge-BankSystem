package com.example.bankingsystem.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.bankingsystem.DTO.TransactionOperation;
import com.example.bankingsystem.DTO.TransactionRequest;
import com.example.bankingsystem.entity.ClientAccount;
import com.example.bankingsystem.enums.TransactionType;
import com.example.bankingsystem.exception.InsufficientBalanceException;
import com.example.bankingsystem.exception.InvalidAmountValue;

@Component
public class TransactionManager {

    @Autowired
    LockManager lockManager;
    
    public ClientAccount ProcessTransaction(ClientAccount clientAccount, TransactionRequest transactionsRequest){
        List<TransactionOperation> transactions = transactionsRequest.getOperations();

        for (TransactionOperation transaction : transactions) {
            
            float transactionAmount = transaction.getAmount();
            if(transactionAmount <= 0)
                throw new InvalidAmountValue("Valor não pode ser menor ou igual a zero");
        
            long clientAccountId = clientAccount.getId();
            lockManager.lock(clientAccountId);

            float currentBalance = clientAccount.getBalance();
            TransactionType type = transaction.getType();
            
            switch (type) {
                case DEPOSIT:
                    float afterDepositBalance = currentBalance + transactionAmount;
                    clientAccount.setBalance(afterDepositBalance);
                    
                    break;
                case WITHDRAW:
                    if(transactionAmount > currentBalance){
                        throw new InsufficientBalanceException("Saldo indisponível para o saque");
                    }

                    float afterWithdrawBalance = currentBalance - transactionAmount;
                    clientAccount.setBalance(afterWithdrawBalance);
                    break;
                default:
                    break;
            }
            lockManager.unlock(clientAccountId);
        }
        
        return clientAccount;
    }
}
