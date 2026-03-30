/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */


package br.com.sistema_de_vendas.models;

import java.time.LocalDate;
import java.util.UUID;

import br.com.sistema_de_vendas.models.Enum.ClienteStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "clientes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cnpj"}),
        @UniqueConstraint(columnNames = {"email"})
})
@AllArgsConstructor
@NoArgsConstructor
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDate dataFundacao;

    @Column(nullable = false, length = 200)
    private String razaoSocial;

    @Column(nullable = false, length = 200)
    private String nomeFantasia;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(nullable = false, length = 30)
    private String ie;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "endereco_registro_id", referencedColumnName = "id", nullable = false)
    private EnderecoModel enderecoRegistro;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "endereco_entrega_id", referencedColumnName = "id", nullable = false)
    private EnderecoModel enderecoEntrega;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "endereco_cobranca_id", referencedColumnName = "id", nullable = false)
    private EnderecoModel enderecoCobranca;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 30)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClienteStatus status;

}
