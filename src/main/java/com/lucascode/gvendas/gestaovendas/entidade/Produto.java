package com.lucascode.gvendas.gestaovendas.entidade;


import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;

    @Column(name = "descricao")
    @NotBlank(message = "Descricao")
    @Length(min = 3, max = 100, message = "Descricao")
    private String descricao;

    @Column(name = "quantidade")
    @NotNull(message = "Quantidade")
    private Integer quantidade;

    @Column(name = "preco_custo")
    @NotNull(message = "Preco Custo")
    private BigDecimal precoCusto;
    @Column(name = "preco_venda")
    @NotNull(message = "Preco Venda")
    private BigDecimal precoVenda;

    @Column(name = "observacao")
    @Length(max = 500, message = "Observacao")
    private String observacao;

    @ManyToOne
    @NotNull(message = "Codigo Categoria")
    @JoinColumn(name = "codigo_categoria", referencedColumnName = "codigo")
    private Categoria categoria;


    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(BigDecimal precoCusto) {
        this.precoCusto = precoCusto;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Produto produto = (Produto) o;

        if (!codigo.equals(produto.codigo)) return false;
        if (!descricao.equals(produto.descricao)) return false;
        if (!quantidade.equals(produto.quantidade)) return false;
        if (!precoCusto.equals(produto.precoCusto)) return false;
        if (!precoVenda.equals(produto.precoVenda)) return false;
        if (!observacao.equals(produto.observacao)) return false;
        return categoria.equals(produto.categoria);
    }

    @Override
    public int hashCode() {
        int result = codigo.hashCode();
        result = 31 * result + descricao.hashCode();
        result = 31 * result + quantidade.hashCode();
        result = 31 * result + precoCusto.hashCode();
        result = 31 * result + precoVenda.hashCode();
        result = 31 * result + observacao.hashCode();
        result = 31 * result + categoria.hashCode();
        return result;
    }
}
