package com.example.bankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankingsystem.entity.Client;
import com.example.bankingsystem.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Operation(summary = "Cadastra um novo cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso"),
    })
    @PostMapping("/new")
    public ResponseEntity<Client> CreateClient(@RequestBody Client client){
        Client newClient = clientService.CreateClient(client);
        return new ResponseEntity<>(newClient, HttpStatus.OK);
    }

    @Operation(summary = "Edita um cliente já cadastrado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "O id informado não foi encontrado."),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Client> UpdateClient(@RequestParam long id, @RequestBody Client client){
        Client updatedClient = clientService.UpdateClient(id, client);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @Operation(summary = "Retorna um cliente já cadastrado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exibe o cliente cadastrado"),
        @ApiResponse(responseCode = "404", description = "O id informado não foi encontrado."),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Client> GetClient(@RequestParam long id){
        Client foundClient = clientService.GetClientById(id);
        return new ResponseEntity<>(foundClient, HttpStatus.OK);
    }

    @Operation(summary = "Deleta um cliente já cadastrado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "O id informado não foi encontrado."),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Client> DeleteClient(@RequestBody Client client){
        Client deletedClient = clientService.DeleteClient(client);
        return new ResponseEntity<>(deletedClient, HttpStatus.OK);
    }




}
