package com.lucascode.gvendas.gestaovendas.services;

import com.lucascode.gvendas.gestaovendas.entidade.Categoria;
import com.lucascode.gvendas.gestaovendas.entidade.Cliente;
import com.lucascode.gvendas.gestaovendas.exception.RegraNegocioException;
import com.lucascode.gvendas.gestaovendas.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public Cliente salvar(Cliente cliente){
        validarClienteDuplicado(cliente);
        return repository.save(cliente);
    }

    public Cliente atualizar(Long codigo, Cliente cliente) {
        Cliente clienteAtualizar = validarClienteExiste(codigo);
        validarClienteDuplicado(cliente);
        BeanUtils.copyProperties(cliente, clienteAtualizar, "codigo");
        return repository.save(clienteAtualizar);
    }

    public void deletar(Long codigo){
        repository.deleteById(codigo);
    }

    private Cliente validarClienteExiste(Long codigo){
        Optional<Cliente> cliente = buscarPorCodigo(codigo);
        if(cliente.isEmpty()){
            throw new EmptyResultDataAccessException(1);
        }
        return cliente.get();
    }

    private void validarClienteDuplicado(Cliente cliente){
        Cliente clientePorNome = repository.findByNome(cliente.getNome());
        if(clientePorNome != null && clientePorNome.getCodigo() != cliente.getCodigo()) {
            throw new RegraNegocioException(String.format("O cliente %s já está cadastrado", cliente.getNome().toUpperCase()));
        }

    }



}
