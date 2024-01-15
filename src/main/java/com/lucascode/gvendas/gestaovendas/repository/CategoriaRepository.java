package com.lucascode.gvendas.gestaovendas.repository;

import com.lucascode.gvendas.gestaovendas.entidade.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Categoria findByNome(String nome);

}
