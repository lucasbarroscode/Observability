package com.lucascode.gvendas.gestaovendas.controller;

import com.lucascode.gvendas.gestaovendas.entidade.Cliente;
import com.lucascode.gvendas.gestaovendas.services.ClienteService;
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

@Api(tags = "Cliente")
@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @ApiOperation(value = "Listar", nickname = "listarTodos")
    @GetMapping
    public List<Cliente> listarTodas() {

        return service.listarTodos();
    }

    @ApiOperation(value = "Listar por código", nickname = "buscarPorId")
    @GetMapping("/{codigo}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long codigo) {
        Optional<Cliente> cliente = service.buscarPorCodigo(codigo);
        return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
    }


}
