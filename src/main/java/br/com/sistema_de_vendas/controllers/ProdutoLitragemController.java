/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistema_de_vendas.DTOs.ProdutoLitragemDTO;
import br.com.sistema_de_vendas.models.ProdutoLitragemModel;
import br.com.sistema_de_vendas.repositories.ProdutoLitragemRepository;
import br.com.sistema_de_vendas.services.ProdutoLitragemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/litragem")
@AllArgsConstructor
public class ProdutoLitragemController {

    private final ProdutoLitragemRepository produtoLitragemRepository;
    private final  ProdutoLitragemService produtoLitragemService;

    /**
     * Grava uma nova litragem de produto.
     */
    @PostMapping
    public ResponseEntity<ProdutoLitragemModel> cadastrarProdutoLitragem(
            @Valid @RequestBody ProdutoLitragemDTO produtoLitragem) {
        produtoLitragemService.validate(produtoLitragem);
        ProdutoLitragemModel produtoLitragemModel = new ProdutoLitragemModel();
        produtoLitragemModel.setNome(produtoLitragem.nome());
        ProdutoLitragemModel salvo = produtoLitragemRepository.save(produtoLitragemModel);
        return ResponseEntity.status(201).body(salvo);
    }

    /**
     * Atualiza o nome de uma litragem existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoLitragemModel> atualizarProdutoLitragem(@PathVariable Long id,
            @Valid @RequestBody ProdutoLitragemDTO produtoLitragem) {
        produtoLitragemService.validate(produtoLitragem);
        if (!produtoLitragemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ProdutoLitragemModel produtoLitragemModel = new ProdutoLitragemModel();
        produtoLitragemModel.setId(id);
        produtoLitragemModel.setNome(produtoLitragem.nome());
        ProdutoLitragemModel salvo = produtoLitragemRepository.save(produtoLitragemModel);
        return ResponseEntity.ok().body(salvo);
    }

    /**
     * Lista todas as litragems de produto existentes.
     */
    @GetMapping("/listar")
    public Iterable<ProdutoLitragemModel> listarProdutoLitragem() {
        return produtoLitragemRepository.findAll();
    }

    /**
     * Busca uma litragem pelo identificador.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoLitragemModel> getProdutoLitragem(@PathVariable Long id) {
        return produtoLitragemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Exclui uma litragem pelo seu ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProdutoLitragem(@PathVariable Long id) {
        return produtoLitragemRepository.findById(id)
                .map(produtoLitragem -> {
                    produtoLitragemRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
