package br.com.sistema_de_vendas.DTOs;


import jakarta.validation.constraints.NotBlank;


public record EnderecoDTO(
        @NotBlank(message = "Logradouro é obrigatório")
        String logradouro,
        @NotBlank(message = "Número é obrigatório")
        String numero,
        String complemento,
        @NotBlank(message = "CEP é obrigatório")
        String cep,
        @NotBlank(message = "Bairro é obrigatório")
        String bairro,
        @NotBlank(message = "Cidade é obrigatória")
        String cidade,
        @NotBlank(message = "Estado é obrigatório")
        String estado) {
}
