package com.lucascode.gvendas.gestaovendas.repository;

import com.lucascode.gvendas.gestaovendas.entidade.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByClienteCodigo(Long codigoCliente);
}
