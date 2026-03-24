package br.com.sistema_de_vendas.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
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
        @NotBlank(message = "Endereço é obrigatório")
        String endereco,
        @NotBlank(message = "Email é obrigatório")
        String email,
        @NotBlank(message = "Telefone é obrigatório")  
        String telefone,
        @NotBlank(message = "Status é obrigatório")
        String status) {
}