package br.com.sistema_de_vendas.DTOs;

import jakarta.validation.constraints.NotBlank;

public record ProdutoLitragemDTO(
    @NotBlank(message = "Nome é obrigatório")
    String nome
) {

}
