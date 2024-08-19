package com.lucascode.gvendas.gestaovendas.services;

import com.lucascode.gvendas.gestaovendas.dto.venda.*;
import com.lucascode.gvendas.gestaovendas.entidade.Cliente;
import com.lucascode.gvendas.gestaovendas.entidade.ItemVenda;
import com.lucascode.gvendas.gestaovendas.entidade.Produto;
import com.lucascode.gvendas.gestaovendas.entidade.Venda;
import com.lucascode.gvendas.gestaovendas.exception.RegraNegocioException;
import com.lucascode.gvendas.gestaovendas.repository.ItemVendaRepository;
import com.lucascode.gvendas.gestaovendas.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaService extends AbstractVendaServico {

    private ClienteService clienteService;
    private VendaRepository vendaRepository;
    private ItemVendaRepository itemVendaRepository;
    private ProdutoService produtoService;

    @Autowired
    public VendaService(ClienteService clienteService, VendaRepository vendaRepository, ItemVendaRepository itemVendaRepository, ProdutoService produtoService) {
        this.clienteService = clienteService;
        this.vendaRepository = vendaRepository;
        this.itemVendaRepository = itemVendaRepository;
        this.produtoService = produtoService;
    }

    public ClienteVendaResponseDTO listarVendaPorCliente(Long codigoCliente) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<VendaResponseDTO> vendaResponseDtoList = vendaRepository.findByClienteCodigo(codigoCliente).stream()
                .map(venda -> criandoVendaResponseDTO(venda, itemVendaRepository.findByVendaPorCodigo(venda.getCodigo()))).collect(Collectors.toList());
        return new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDtoList);

    }

    //ClienteVendaResponseDTO aqui vai retornar uma lista de um elemento so
    public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
        Venda venda = validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVendaList = itemVendaRepository.findByVendaPorCodigo(venda.getCodigo());
        return retornandoClienteVendaResponseDto(venda, itensVendaList);

    }

    /*precisa colocar o transactional pq como estamos "atualizando" duas tabelas diferentes (itens e vendas)
     para se der alguma execao der rollback nas operações de banco de dados feitas antes */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaDto) {
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        validarProdutoExisteEAtualizarQuantidade(vendaDto.getItensVendaDto());
        Venda vendaSalva = salvarVenda(cliente, vendaDto);
        return retornandoClienteVendaResponseDto(vendaSalva, itemVendaRepository.findByVendaPorCodigo(vendaSalva.getCodigo()));

    }

    public ClienteVendaResponseDTO atualizar(Long codigoVenda, Long codigoCliente, VendaRequestDTO vendaDto){
        validarVendaExiste(codigoVenda);
        Cliente cliente = validarClienteVendaExiste(codigoCliente);
        List<ItemVenda> itensVendaList = itemVendaRepository.findByVendaPorCodigo(codigoVenda);
        validarProdutoExisteEDevolverEstoque(itensVendaList);
        validarProdutoExisteEAtualizarQuantidade(vendaDto.getItensVendaDto());
        itemVendaRepository.deleteAll(itensVendaList);
        Venda vendaAtualizada = atualizarVenda(codigoVenda, cliente, vendaDto);
        return retornandoClienteVendaResponseDto(vendaAtualizada, itemVendaRepository.findByVendaPorCodigo(vendaAtualizada.getCodigo()));
    }

    /*precisa colocar o transactional pq como estamos "atualizando" duas tabelas diferentes (itens e vendas)
     para se der alguma execao der rollback nas operações de banco de dados feitas antes */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void deletar(Long codigoVenda) {
        Venda venda = validarVendaExiste(codigoVenda);
        List<ItemVenda> itensVenda = itemVendaRepository.findByVendaPorCodigo(codigoVenda);
        validarProdutoExisteEDevolverEstoque(itensVenda);
        itemVendaRepository.deleteAll(itensVenda);
        vendaRepository.deleteById(codigoVenda);
    }

    private void validarProdutoExisteEDevolverEstoque(List<ItemVenda> itensVenda){
        itensVenda.forEach(item -> {
            Produto produto = produtoService.validarProdutoExiste(item.getProduto().getCodigo());
            produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
            produtoService.atualizarQuantidadeEmEstoque(produto);
        });
    }

    private Venda salvarVenda(Cliente cliente, VendaRequestDTO vendaDto) {
        Venda vendaSalva = vendaRepository.save(new Venda(vendaDto.getData(), cliente));
        vendaDto.getItensVendaDto().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
                .forEach(itemVendaRepository::save);
        return vendaSalva;
    }

    private Venda atualizarVenda(Long codigoVenda, Cliente cliente, VendaRequestDTO vendaDto) {
        Venda vendaSalva = vendaRepository.save(new Venda(codigoVenda, vendaDto.getData(), cliente));
        vendaDto.getItensVendaDto().stream().map(itemVendaDto -> criandoItemVenda(itemVendaDto, vendaSalva))
                .forEach(itemVendaRepository::save);
        return vendaSalva;
    }

    private void validarProdutoExisteEAtualizarQuantidade(List<ItemVendaRequestDTO> itensVendaDto) {
        itensVendaDto.forEach(item -> {
            Produto produto = produtoService.validarProdutoExiste(item.getCodigoProduto());
            validarQuantidadeProdutoExiste(produto, item.getQuantidade());
            produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
            produtoService.atualizarQuantidadeEmEstoque(produto);
        });
    }

    private void validarQuantidadeProdutoExiste(Produto produto, Integer qtdeVendaDto){
        if(!(produto.getQuantidade() >= qtdeVendaDto)){
            throw new RegraNegocioException(String.format("A quantidade %s informada para o produto %s nao esta disponivel em estoque ",
                    qtdeVendaDto, produto.getDescricao()));
        }
    }

    private Venda validarVendaExiste(Long codigoVenda) {
        Optional<Venda> venda = vendaRepository.findById(codigoVenda);
        if (venda.isEmpty()) {
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
}
