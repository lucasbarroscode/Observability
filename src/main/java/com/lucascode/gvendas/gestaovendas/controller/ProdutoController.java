package com.lucascode.gvendas.gestaovendas.controller;


import com.lucascode.gvendas.gestaovendas.entidade.Produto;
import com.lucascode.gvendas.gestaovendas.services.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
                                                   @PathVariable Long codigoProduto) {
        Optional<Produto> produto = produtoService.buscarPorCodigo(codigoProduto, codigoCategoria);
        return produto.isPresent() ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Salvar", nickname = "salvarProduto")
    @PostMapping
    public ResponseEntity<Produto> salvar(@PathVariable Long codigoCategoria, @Valid @RequestBody Produto produto){
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvar(codigoCategoria, produto));
    }

    @ApiOperation(value = "Atualizar", nickname = "atualizarProduto")
    @PutMapping("/{codigoProduto}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long codigoCategoria, @PathVariable Long codigoProduto,  @Valid @RequestBody Produto produto){
        return ResponseEntity.ok(produtoService.atualizar(codigoCategoria, codigoProduto, produto));
    }

}
