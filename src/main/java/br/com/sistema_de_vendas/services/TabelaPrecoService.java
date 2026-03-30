/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.services;

import org.springframework.stereotype.Service;

import br.com.sistema_de_vendas.DTOs.TabelaPrecoDTO;
import br.com.sistema_de_vendas.models.Enum.TabelaStatus;

import static br.com.sistema_de_vendas.services.ValidationUtils.*;

@Service
public class TabelaPrecoService {

    /**
     * Valida os dados de uma tabela de preço antes de salvar.
     */
    public void validate(TabelaPrecoDTO dto) {
        assertNotBlank(dto.nomeTabela(), "nomeTabela");
        if (dto.dataCriacao() == null) {
            throw new br.com.sistema_de_vendas.Exception.BusinessException("dataCriacao é obrigatória");
        }
        assertNotBlank(dto.status(), "status");
        assertValidEnum(dto.status(), TabelaStatus.class, "status");
    }
}
