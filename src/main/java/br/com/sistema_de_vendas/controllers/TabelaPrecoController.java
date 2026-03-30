/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.controllers;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.sistema_de_vendas.DTOs.TabelaPrecoDTO;
import br.com.sistema_de_vendas.models.TabelaPrecoModel;
import br.com.sistema_de_vendas.models.Enum.TabelaStatus;
import br.com.sistema_de_vendas.repositories.TabelaPrecoRepository;
import br.com.sistema_de_vendas.services.TabelaPrecoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tabela-preco")
@AllArgsConstructor
public class TabelaPrecoController {

    
    private final TabelaPrecoRepository tabelaDePrecoRepository;
    private final TabelaPrecoService tabelaPrecoService;

    /**
     * Cria uma nova tabela de preços.
     */
    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid TabelaPrecoDTO dto) {
        tabelaPrecoService.validate(dto);
        TabelaPrecoModel model = new TabelaPrecoModel();
        model.setNomeTabela(dto.nomeTabela());
        model.setDataCriacao(dto.dataCriacao());
        model.setStatus(TabelaStatus.valueOf(dto.status().toUpperCase()));
        tabelaDePrecoRepository.save(model);

        return ResponseEntity.ok().build();
    }

    /**
     * Atualiza os dados de uma tabela de preço existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @Valid @RequestBody TabelaPrecoDTO dto) {
        tabelaPrecoService.validate(dto);
        TabelaPrecoModel model = tabelaDePrecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tabela de preço não encontrada"));
        model.setNomeTabela(dto.nomeTabela());
        model.setDataCriacao(dto.dataCriacao());
        model.setStatus(TabelaStatus.valueOf(dto.status().toUpperCase()));
        tabelaDePrecoRepository.save(model);
        return ResponseEntity.ok().build();
    }

    /**
     * Busca uma tabela de preço por seu identificador.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TabelaPrecoModel> getTabelaPreco(@PathVariable UUID id) {
        return tabelaDePrecoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /**
     * Retorna todas as tabelas de preço cadastradas.
     */
    @GetMapping("/listar")
    public Iterable<TabelaPrecoModel> listarTabelaPreco() { 
        return tabelaDePrecoRepository.findAll();
    }

    /**
     * Deleta uma tabela de preço pelo identificador.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTabelaPreco(@PathVariable UUID id) {
        tabelaDePrecoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
