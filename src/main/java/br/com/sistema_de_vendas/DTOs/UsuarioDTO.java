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
