/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.DTOs;

import jakarta.validation.constraints.NotBlank;

public record ProdutoLitragemDTO(
    @NotBlank(message = "Nome é obrigatório")
    String nome
) {

}
