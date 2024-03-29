package com.lucascode.gvendas.gestaovendas.repository;

import com.lucascode.gvendas.gestaovendas.entidade.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaCodigo(Long codigoCategoria);

    @Query("Select prod"
            + " from Produto prod"
            + " where prod.codigo = :codigo"
            + "   and prod.categoria.codigo = :codigoCategoria")

    //Query de cima eh outro exemplo de como fazer
    Optional<Produto> buscarPorCodigo(Long codigo, Long codigoCategoria);

    Optional<Produto> findByCategoriaCodigoAndDescricao(Long codigoCategoria, String descricao);
}
