package br.com.sistema_de_vendas.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;

public record ProdutoDTO(

        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Referência NF é obrigatória")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        String refNf,
        
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Double peso,
        
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Double ipi,
        
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Integer qtdadePorEmbalagem,
        
        Long litragemId,
        
        Long categoriaId,
        
        String ncmId

    ) {
}
