package com.lucascode.gvendas.gestaovendas.repository;

import com.lucascode.gvendas.gestaovendas.entidade.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
