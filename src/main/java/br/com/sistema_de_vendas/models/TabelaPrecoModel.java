/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.sistema_de_vendas.models.Enum.TabelaStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Data
@Table(name = "tabela_preco")
public class TabelaPrecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String nomeTabela;

    @Column(nullable = false)
    private LocalDate dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TabelaStatus status;
    @OneToMany(mappedBy = "tabelaPreco", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoTabelaPrecoModel> produtos = new ArrayList<>();

}
