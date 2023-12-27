package com.lucascode.gvendas.gestaovendas.entidade;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "categoria" )
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;
    @Column(name = "nome")
    private String nome;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Categoria)) return false;

        Categoria categoria = (Categoria) obj;

        if (!codigo.equals(categoria.codigo)) return false;
        return Objects.equals(codigo, categoria.codigo) && Objects.equals(nome, categoria.nome);
    }

    @Override
    public int hashCode() {
       return Objects.hash(codigo,nome);
    }
}
