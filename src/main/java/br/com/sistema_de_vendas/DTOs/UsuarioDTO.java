/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.DTOs;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(
        @NotBlank(message = "Nome completo é obrigatório")
        String nomeCompleto,
        @NotBlank(message = "Apelido é obrigatório")
        String apelido,
        @NotBlank(message = "Email é obrigatório")
        String email,
        @NotBlank(message = "Senha é obrigatória")
        String senha,
        @NotBlank(message = "Telefone é obrigatório")
        String telefone,
        @NotBlank(message = "Role é obrigatória")
        String role,
        @NotBlank(message = "Status é obrigatório")
        String status

) {

}
