/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "produtos")
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String refNf;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private ProdutoCategoriaModel categoria;

    @Column(nullable = false, length = 200)
    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "litragem_id", nullable = false)
    private ProdutoLitragemModel litragem;

    @Column(nullable = false)
    private Integer qtdPorEmbalagem;

    @Column(nullable = false)
    private Double ipi;

    @Column(nullable = false)
    private Double peso;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ncm_id", nullable = false)
    private NcmModel ncm;
    // @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<ProdutoTabelaPrecoModel> tabelasDePreco = new ArrayList<>();
  


}
