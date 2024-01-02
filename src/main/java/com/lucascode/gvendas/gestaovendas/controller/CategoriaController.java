package com.lucascode.gvendas.gestaovendas.controller;

import com.lucascode.gvendas.gestaovendas.entidade.Categoria;
import com.lucascode.gvendas.gestaovendas.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Optional<Categoria> categoria = service.buscarPorCodigo(codigo);
        return categoria.isPresent() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria){
        Categoria categoriaSalvar = service.salvar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalvar);

    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Categoria> atualizar(@Valid @PathVariable Long codigo, @RequestBody Categoria categoria){
        return ResponseEntity.ok(service.atualizar(codigo, categoria));
    }

}
