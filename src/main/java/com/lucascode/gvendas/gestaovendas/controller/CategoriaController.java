package com.lucascode.gvendas.gestaovendas.controller;

import com.lucascode.gvendas.gestaovendas.entidade.Categoria;
import com.lucascode.gvendas.gestaovendas.repository.CategoriaRepository;
import com.lucascode.gvendas.gestaovendas.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;


    @GetMapping
    public List<Categoria> listarTodas(){
        return service.listarTodas();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Categoria>> buscarPorId (@PathVariable Long codigo){
        Optional<Categoria> categoria = service.buscarPorId(codigo);
        return categoria.isPresent() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

}
