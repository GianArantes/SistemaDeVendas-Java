/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.services;

import org.springframework.stereotype.Service;

import br.com.sistema_de_vendas.DTOs.NcmEstadoDTO;

import static br.com.sistema_de_vendas.services.ValidationUtils.*;

@Service
public class NcmEstadoService {

    /**
     * Valida os dados de alíquota de um NCM por estado.
     */
    public void validate(NcmEstadoDTO dto) {
        if (dto.ncmId() == null) {
            throw new br.com.sistema_de_vendas.Exception.BusinessException("ncmId não pode ser nulo");
        }
        assertValidUUID(dto.ncmId().toString(), "ncmId");
        assertNotBlank(dto.estado(), "estado");
        assertState(dto.estado());
        assertNonNegative(dto.aliquota(), "aliquota");
    }
}
