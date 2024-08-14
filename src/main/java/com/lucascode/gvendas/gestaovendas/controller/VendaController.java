package com.lucascode.gvendas.gestaovendas.controller;

import com.lucascode.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.lucascode.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.lucascode.gvendas.gestaovendas.services.VendaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "Venda")
@RestController
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @ApiOperation(value = "Listar vendas por cliente", nickname = "listarVendaPorCliente")
    @GetMapping("/cliente/{codigoCliente}")
    public ResponseEntity<ClienteVendaResponseDTO> listarVendaPorCliente(@PathVariable Long codigoCliente){
        return ResponseEntity.ok(vendaService.listarVendaPorCliente(codigoCliente));
    }

    @ApiOperation(value = "Listar vendas por codigo", nickname = "listarVendaPorCodigo")
    @GetMapping("/{codigoVenda}")
    public ResponseEntity<ClienteVendaResponseDTO> listarVendaPorCodigo(@PathVariable Long codigoVenda){
        return ResponseEntity.ok(vendaService.listarVendaPorCodigo(codigoVenda));
    }

    @ApiOperation(value = "Registrar Venda", nickname = "salvarVenda")
    @PostMapping("/cliente/{codigoCliente}")
    public ResponseEntity<ClienteVendaResponseDTO> salvar(@PathVariable Long codigoCliente, @Valid @RequestBody VendaRequestDTO vendaDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.salvar(codigoCliente, vendaDto));
    }

    @ApiOperation(value = "Deletar vendas por codigo", nickname = "deletarVenda")
    @DeleteMapping("/{codigoVenda}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long codigoVenda) {
        vendaService.deletar(codigoVenda);
    }


}
