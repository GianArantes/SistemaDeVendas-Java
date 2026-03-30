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

import br.com.sistema_de_vendas.DTOs.NcmEstadoDTO;
import br.com.sistema_de_vendas.models.NcmEstadoModel;
import br.com.sistema_de_vendas.repositories.NcmEstadoRepository;
import br.com.sistema_de_vendas.repositories.NcmRepository;
import br.com.sistema_de_vendas.services.NcmEstadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/ncms-estados")
@AllArgsConstructor
public class NcmEstadoController {

    private final NcmEstadoRepository ncmEstadoRepository;
    private final NcmRepository ncmRepository;
    private final NcmEstadoService ncmEstadoService;

    /**
     * Cadastra um estado de NCM com alíquota e referência ao NCM.
     */
    @PostMapping
    public void cadastrarNcmEstado(@Valid @RequestBody NcmEstadoDTO ncmEstado) {
        ncmEstadoService.validate(ncmEstado);
        NcmEstadoModel ncmEstadoModel = new NcmEstadoModel();
        ncmEstadoModel.setNcm(ncmRepository.findById(ncmEstado.ncmId()).orElse(null));
        ncmEstadoModel.setEstado(ncmEstado.estado());
        ncmEstadoModel.setAliquota(ncmEstado.aliquota());
        ncmEstadoRepository.save(ncmEstadoModel);
    }

    /**
     * Atualiza um registro de NCM por estado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NcmEstadoModel> atualizarNcmEstado(@Valid @PathVariable UUID id,
            @RequestBody NcmEstadoDTO ncmEstado) {
        ncmEstadoService.validate(ncmEstado);
        return ncmEstadoRepository.findById(id)
                .map(ncmEstadoModel -> {
                    ncmEstadoModel.setNcm(ncmRepository.findById(ncmEstado.ncmId()).orElse(null));
                    ncmEstadoModel.setEstado(ncmEstado.estado());
                    ncmEstadoModel.setAliquota(ncmEstado.aliquota());
                    ncmEstadoRepository.save(ncmEstadoModel);
                    return ResponseEntity.ok().body(ncmEstadoModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um registro de NCM por estado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        return ncmEstadoRepository.findById(id)
                .map(ncmEstado -> {
                    ncmEstadoRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Recupera um registro de NCM por seu id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NcmEstadoModel> buscarPorId(@PathVariable UUID id) {
        return ncmEstadoRepository.findById(id)
                .map(ncmEstado -> ResponseEntity.ok().body(ncmEstado))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todos os estados de um NCM ordenados por nome do estado.
     */
    @GetMapping("/listar/{id}")
    public Iterable<NcmEstadoModel> listarNcmsEstados(@PathVariable UUID id) {
        return ncmEstadoRepository.findByNcmIdOrderByEstadoAsc(id);
    }
}
