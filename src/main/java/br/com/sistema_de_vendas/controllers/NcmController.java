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
import br.com.sistema_de_vendas.DTOs.NcmDTO;
import br.com.sistema_de_vendas.models.NcmModel;
import br.com.sistema_de_vendas.repositories.NcmRepository;
import br.com.sistema_de_vendas.services.NcmService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/ncms")
@AllArgsConstructor
public class NcmController {
    
    private final NcmRepository ncmRepository;
    private final NcmService ncmService;

    /**
     * Registra um novo NCM na base de dados.
     */
    @PostMapping
    public void cadastrarNcm(@Valid @RequestBody NcmDTO ncm) {
        ncmService.validate(ncm);
        NcmModel ncmModel = new NcmModel();
        ncmModel.setCodigo(ncm.codigo());
        ncmModel.setDescricao(ncm.descricao());
        ncmRepository.save(ncmModel);
    }

    /**
     * Atualiza um NCM existente pelo seu identificador.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NcmModel> atualizarNcm(@Valid @PathVariable UUID id, @RequestBody NcmDTO ncm) {
        ncmService.validate(ncm);
        System.out.println(ncm);
        return ncmRepository.findById(id)
                .map(ncmModel -> {
                    ncmModel.setCodigo(ncm.codigo());
                    ncmModel.setDescricao(ncm.descricao());
                    ncmRepository.save(ncmModel);
                    return ResponseEntity.ok().body(ncmModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retorna os dados de um NCM pelo id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NcmModel> getNcm(@PathVariable UUID id) {
        return ncmRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todos os NCMs cadastrados.
     */
    @GetMapping("/listar")
    public Iterable<NcmModel> listarNcms() {
        return ncmRepository.findAll();
    }

    /**
     * Remove um NCM pelo id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNcm(@PathVariable UUID id) {
        return ncmRepository.findById(id)
                .map(ncmModel -> {
                    ncmRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
