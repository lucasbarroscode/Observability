package com.lucascode.gvendas.gestaovendas.services;


import com.lucascode.gvendas.gestaovendas.entidade.Produto;
import com.lucascode.gvendas.gestaovendas.exception.RegraNegocioException;
import com.lucascode.gvendas.gestaovendas.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public Produto salvar (Long codigoCategoria, Produto produto){
        validarCategoriaDoProdutoExiste(codigoCategoria);
        validarProdutoDuplicado(produto);
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Long codigoCategoria, Long codigoProduto, Produto produto){
        Produto produtoAtualizar = validarProdutoExiste(codigoProduto, codigoCategoria);
        validarCategoriaDoProdutoExiste(codigoCategoria);
        validarProdutoDuplicado(produto);
        BeanUtils.copyProperties(produto, produtoAtualizar, "codigo");
        return produtoRepository.save(produtoAtualizar);
    }

    public void deletar(Long codigoCategoria, Long codigoProduto){
        Produto produto = validarProdutoExiste(codigoProduto, codigoCategoria);
        produtoRepository.delete(produto);

    }

    protected void atualizarQuantidadeAposVenda(Produto produto){
        produtoRepository.save(produto);
    }

    private Produto validarProdutoExiste(Long codigoProduto, Long codigoCategoria) {
        Optional<Produto> produto = buscarPorCodigo(codigoProduto, codigoCategoria);
        if(produto.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return produto.get();
    }

    protected Produto validarProdutoExiste(Long codigoProduto) {
        Optional<Produto> produto = produtoRepository.findById(codigoProduto);
        if(produto.isEmpty()) {
            throw new RegraNegocioException(String.format("Produto de codigo %s nao encontrado", codigoProduto));
        }
        return produto.get();
    }

    private void validarProdutoDuplicado(Produto produto){
        Optional<Produto> produtoPorDescricao = produtoRepository.findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(), produto.getDescricao());
        if(produtoPorDescricao.isPresent() && produtoPorDescricao.get().getCodigo() != produto.getCodigo()){
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
