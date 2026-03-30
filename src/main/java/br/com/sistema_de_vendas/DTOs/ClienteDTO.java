/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record ClienteDTO(
        @NotBlank(message = "Razão social é obrigatória")
        String razaoSocial,
        @NotBlank(message = "Data de fundação é obrigatória")
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Past(message = "A data de fundação deve ser no passado")
        String dataFundacao,
        String nomeFantasia,
        @NotBlank(message = "CNPJ é obrigatório")
        String cnpj,
        @NotBlank(message = "Inscrição estadual é obrigatória")
        String ie,
        @NotNull(message = "Endereço de registro é obrigatório")
        @Valid
        EnderecoDTO enderecoRegistro,
        @NotNull(message = "Endereço de entrega é obrigatório")
        @Valid
        EnderecoDTO enderecoEntrega,
        @NotNull(message = "Endereço de cobrança é obrigatório")
        @Valid
        EnderecoDTO enderecoCobranca,
        @NotBlank(message = "Email é obrigatório")
        String email,
        @NotBlank(message = "Telefone é obrigatório")  
        String telefone,
        @NotBlank(message = "Status é obrigatório")
        String status) {
}