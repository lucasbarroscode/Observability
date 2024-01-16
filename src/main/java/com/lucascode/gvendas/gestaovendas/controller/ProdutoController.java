package com.lucascode.gvendas.gestaovendas.controller;


import com.lucascode.gvendas.gestaovendas.entidade.Produto;
import com.lucascode.gvendas.gestaovendas.services.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Api(tags = "Produto")
@RestController
@RequestMapping("/categoria{codigoCategoria}/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @ApiOperation(value = "Listar", nickname = "listarTodos")
    @GetMapping
    public List<Produto> listarTodos(@PathVariable Long codigoCategoria) {
        return produtoService.listarTodos(codigoCategoria) ;
    }

    @ApiOperation(value = "Listar por código", nickname = "buscarPorCodigo")
    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Produto>> buscarPorCodigo(@PathVariable Long codigoCategoria,
                                                   @PathVariable Long codigo) {
        Optional<Produto> produto = produtoService.buscarPorCodigo(codigo, codigoCategoria);
        return produto.isPresent() ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
    }
}
