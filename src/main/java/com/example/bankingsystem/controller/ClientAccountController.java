package com.example.bankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bankingsystem.DTO.TransactionRequest;
import com.example.bankingsystem.entity.Client;
import com.example.bankingsystem.entity.ClientAccount;
import com.example.bankingsystem.service.ClientAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Controller
@RequestMapping("/account")
public class ClientAccountController {

    @Autowired
    ClientAccountService clientAccountService;

    @Operation(summary = "Cria uma nova conta para um cliente já cadastrado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conta cadastrada com sucesso"),
        @ApiResponse(responseCode = "409", description = "CPF informado já possui uma conta cadastrado")
    })
    @PostMapping("/")
    public ResponseEntity<ClientAccount> CreateAccount(@RequestBody Client client){
        ClientAccount newAccount = clientAccountService.CreateNewAccount(client);
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }

    @Operation(summary = "Retorna uma conta de um cliente já cadastrado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exibe a conta do cliente cadastrado"),
        @ApiResponse(responseCode = "404", description = "Nenhuma conta para o CPF informado foi encontrada."),
    })
    @GetMapping("/")
    public ResponseEntity<ClientAccount> CheckBalance(@RequestBody Client client){
        ClientAccount AccountFound = clientAccountService.GetClientAccount(client);
        return new ResponseEntity<>(AccountFound, HttpStatus.OK);
    }

    @Operation(summary = "Realiza operações de depositos e saques na conta de um cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exibe a conta do cliente cadastrado"),
        @ApiResponse(responseCode = "404", description = "Nenhuma conta para o CPF informado foi encontrada."),
        @ApiResponse(responseCode = "400", description = "Saldo insuficiente para o saque informado."),
    })
    @PutMapping("/")
    public ResponseEntity<ClientAccount> ProcessTransactions(@RequestBody Client client, @RequestBody TransactionRequest transactionRequest){
        ClientAccount accountProccessed = clientAccountService.ProcessTransactions(client, transactionRequest);
        return new ResponseEntity<>(accountProccessed, HttpStatus.OK);
    }



    
}
