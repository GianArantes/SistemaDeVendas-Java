package br.com.sistema_de_vendas.DTOs;

import jakarta.validation.constraints.NotBlank;

public record ProdutoDTO(

        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Referência NF é obrigatória")
        String refNf,
        @NotBlank(message = "Peso é obrigatório")
        double peso,
        @NotBlank(message = "IPI é obrigatório")
        double ipi,
        @NotBlank(message = "Quantidade por embalagem é obrigatória")
        int quantidadePorEmbalagem,
        @NotBlank(message = "Produto litragem é obrigatória")
        long produtoLitragemId,
        @NotBlank(message = "Produto categoria é obrigatória")
        long produtoCategoriaId,
        @NotBlank(message = "NCM é obrigatório")
        String ncmId

    ) {
}
