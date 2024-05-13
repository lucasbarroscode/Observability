package com.lucascode.gvendas.gestaovendas.services;

import com.lucascode.gvendas.gestaovendas.dto.venda.ItemVendaResponseDTO;
import com.lucascode.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.lucascode.gvendas.gestaovendas.entidade.ItemVenda;
import com.lucascode.gvendas.gestaovendas.entidade.Venda;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractVendaServico {

    protected VendaResponseDTO criandoVendaResponseDTO(Venda venda, List<ItemVenda> itensVendaList){
        List<ItemVendaResponseDTO> itensVendaResponseDto = itensVendaList
                .stream().map(this::criandoItensVendaResponseDto).collect(Collectors.toList());
        return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itensVendaResponseDto);


    }

    protected ItemVendaResponseDTO criandoItensVendaResponseDto(ItemVenda itemVenda) {
        return new ItemVendaResponseDTO(itemVenda.getCodigo(), itemVenda.getQuantidade(), itemVenda.getPrecoVendido(),
                itemVenda.getProduto().getCodigo(), itemVenda.getProduto().getDescricao());
    }

}
