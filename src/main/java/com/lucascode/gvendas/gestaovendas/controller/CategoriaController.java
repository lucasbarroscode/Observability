package com.lucascode.gvendas.gestaovendas.controller;

import com.lucascode.gvendas.gestaovendas.entidade.Categoria;
import com.lucascode.gvendas.gestaovendas.services.CategoriaService;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(tags = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;


    @ApiOperation(value = "Listar", nickname = "listarTodas")
    @GetMapping
    public List<Categoria> listarTodas(){

        return service.listarTodas();
    }
    @ApiOperation(value = "Listar por código", nickname = "buscarPorId")
    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Categoria>> buscarPorId (@PathVariable Long codigo){
        Optional<Categoria> categoria = service.buscarPorCodigo(codigo);
        return categoria.isPresent() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Salvar", nickname = "salvarCategoria")
    @PostMapping
    public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria){
        Categoria categoriaSalvar = service.salvar(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalvar);

    }

    @ApiOperation(value = "Atualizar", nickname = "atualizarCategoria")
    @PutMapping("/{codigo}")
    public ResponseEntity<Categoria> atualizar(@Valid @PathVariable Long codigo, @RequestBody Categoria categoria){
        return ResponseEntity.ok(service.atualizar(codigo, categoria));
    }
    @ApiOperation(value = "Deletar", nickname = "deletarCategoria")
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable Long codigo){
        service.deletar(codigo);
    }

}
