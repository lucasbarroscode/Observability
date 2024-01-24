package com.lucascode.gvendas.gestaovendas.services;


import com.lucascode.gvendas.gestaovendas.entidade.Produto;
import com.lucascode.gvendas.gestaovendas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos(Long codigoCategoria) {
        return produtoRepository.findByCategoriaCodigo(codigoCategoria);
    }

    public Optional<Produto> buscarPorCodigo(Long codigoProduto, Long codigoCategoria) {
        return produtoRepository.buscarPorCodigo(codigoProduto, codigoCategoria);
    }

    public Produto salvar (Produto produto){
        return produtoRepository.save(produto);
    }

}
