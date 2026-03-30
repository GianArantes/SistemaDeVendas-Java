/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.services;

import org.springframework.stereotype.Service;

import br.com.sistema_de_vendas.DTOs.NcmDTO;

import static br.com.sistema_de_vendas.services.ValidationUtils.*;

@Service
public class NcmService {

    /**
     * Valida o código e a descrição de um NCM.
     */
    public void validate(NcmDTO dto) {
        assertNotBlank(dto.codigo(), "codigo");
        assertNcm(dto.codigo());
        assertNotBlank(dto.descricao(), "descricao");
    }
}
