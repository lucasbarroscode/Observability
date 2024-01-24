package com.lucascode.gvendas.gestaovendas.services;


import com.lucascode.gvendas.gestaovendas.entidade.Produto;
import com.lucascode.gvendas.gestaovendas.exception.RegraNegocioException;
import com.lucascode.gvendas.gestaovendas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaService categoriaService;

    public List<Produto> listarTodos(Long codigoCategoria) {
        return produtoRepository.findByCategoriaCodigo(codigoCategoria);
    }

    public Optional<Produto> buscarPorCodigo(Long codigoProduto, Long codigoCategoria) {
        return produtoRepository.buscarPorCodigo(codigoProduto, codigoCategoria);
    }

    public Produto salvar (Produto produto){
        validarCategoriaDoProdutoExiste(produto.getCategoria().getCodigo());
        validarProdutoDuplicado(produto);
        return produtoRepository.save(produto);
    }

    private void validarProdutoDuplicado(Produto produto){
        if(produtoRepository.findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(), produto.getDescricao()).isPresent()){
            throw new RegraNegocioException(String.format("A produto %s ja esta cadastrado", produto.getDescricao()));
        }
    }

    private void validarCategoriaDoProdutoExiste(Long codigoCategoria){
        if(codigoCategoria == null){
            throw new RegraNegocioException("A categoria nao pode ser nula");
        }
        if (categoriaService.buscarPorCodigo(codigoCategoria).isEmpty()){
            throw new RegraNegocioException(String.format("A categoria de codigo %s informada nao existe no cadastro", codigoCategoria));
        }
    }

}
