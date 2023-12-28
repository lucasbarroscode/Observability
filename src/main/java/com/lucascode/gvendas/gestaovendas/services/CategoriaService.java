package com.lucascode.gvendas.gestaovendas.services;

import com.lucascode.gvendas.gestaovendas.entidade.Categoria;
import com.lucascode.gvendas.gestaovendas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> listarTodas(){
        return repository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long codigo){
        return repository.findById(codigo);
    }

    public Categoria salvar(Categoria categoria) {
        return repository.save(categoria);
    }

}
