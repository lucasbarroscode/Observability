package com.lucascode.gvendas.gestaovendas.services;

import com.lucascode.gvendas.gestaovendas.entidade.Categoria;
import com.lucascode.gvendas.gestaovendas.entidade.Cliente;
import com.lucascode.gvendas.gestaovendas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;


    public List<Cliente> listarTodos(){
        return repository.findAll();
    }

    public Optional<Cliente> buscarPorCodigo(Long codigo){
        return repository.findById(codigo);
    }



}
