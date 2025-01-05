package com.example.bankingsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankingsystem.entity.Client;
import com.example.bankingsystem.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Client CreateClient(Client client){
        Client newClient = clientRepository.save(client);
        return newClient;
    }

    public Client GetClientById(long id){
        Optional<Client> foundClient = clientRepository.findById(id);
        return foundClient.get();
    }
    
    public Client UpdateClient(long id, Client client){
        Client foundClient = GetClientById(client.getId());
        foundClient.setCpf(client.getCpf());
        foundClient.setName(client.getName());
        Client updatedClient = clientRepository.save(foundClient);
        return updatedClient;
    }

    public Client DeleteClient(Client client){
        Client foundClient = GetClientById(client.getId());
        clientRepository.delete(foundClient);
        return foundClient;
    }


}

