package com.lucascode.gvendas.gestaovendas.services;

import com.lucascode.gvendas.gestaovendas.dto.cliente.ClienteResponseDTO;
import com.lucascode.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.lucascode.gvendas.gestaovendas.dto.venda.ItemVendaResponseDTO;
import com.lucascode.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.lucascode.gvendas.gestaovendas.entidade.Cliente;
import com.lucascode.gvendas.gestaovendas.entidade.ItemVenda;
import com.lucascode.gvendas.gestaovendas.entidade.Venda;
import com.lucascode.gvendas.gestaovendas.exception.RegraNegocioException;
import com.lucascode.gvendas.gestaovendas.repository.ItemVendaRepository;
import com.lucascode.gvendas.gestaovendas.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaService {

    private ClienteService clienteService;
    private VendaRepository vendaRepository;
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    public VendaService(ClienteService clienteService, VendaRepository vendaRepository, ItemVendaRepository itemVendaRepository) {
        this.clienteService = clienteService;
        this.vendaRepository = vendaRepository;
        this.itemVendaRepository = itemVendaRepository;
    }

    public ClienteVendaResponseDTO listarVendaPorCliente(Long codigoCliente){
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<VendaResponseDTO> vendaResponseDtoList = vendaRepository.findByClienteCodigo(codigoCliente).stream()
                .map(this::criandoVendaResponseDTO).collect(Collectors.toList());
        return new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDtoList);

    }

    //ClienteVendaResponseDTO aqui vai retornar uma lista de um elemento so
    public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda){
        Venda venda = validarVendaExiste(codigoVenda);
        return new ClienteVendaResponseDTO(venda.getCliente().getNome(), Arrays.asList(criandoVendaResponseDTO(venda)));

    }

    private Venda validarVendaExiste(Long codigoVenda){
        Optional<Venda> venda = vendaRepository.findById(codigoVenda);
        if(venda.isEmpty()){
            throw new RegraNegocioException(String.format("Venda de codigo %s não encontrada.", codigoVenda));
        }
        return venda.get();

    }

    private Cliente validarClienteVendaExiste(Long codigoCliente) {
        Optional<Cliente> cliente = clienteService.buscarPorCodigo(codigoCliente);
        if (cliente.isEmpty()) {
            throw new RegraNegocioException(
                    String.format("O Cliente de código %s informado não existe no cadastro.", codigoCliente));
        }
        return cliente.get();
    }

    private VendaResponseDTO criandoVendaResponseDTO(Venda venda){
        List<ItemVendaResponseDTO> itensVendaResponseDto = itemVendaRepository.findByVendaCodigo(venda.getCodigo())
                .stream().map(this::criandoItensVendaResponseDto).collect(Collectors.toList());
        return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itensVendaResponseDto);


    }

    private ItemVendaResponseDTO criandoItensVendaResponseDto(ItemVenda itemVenda) {
        return new ItemVendaResponseDTO(itemVenda.getCodigo(), itemVenda.getQuantidade(), itemVenda.getPrecoVendido(),
        itemVenda.getProduto().getCodigo(), itemVenda.getProduto().getDescricao());
    }


}
