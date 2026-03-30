/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.DTOs;

import java.time.LocalDate;

import java.util.UUID;

public record TabelaPrecoDTO(
    UUID id,
    String nomeTabela,
    LocalDate dataCriacao,
    String status // Recebendo como String para o seu Enum.valueOf
) {}