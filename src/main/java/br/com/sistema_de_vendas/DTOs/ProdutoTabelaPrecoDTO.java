package br.com.sistema_de_vendas.DTOs;


import java.util.UUID;

public record ProdutoTabelaPrecoDTO(
    UUID id,
    Long produtoId, // ID do produto original
    Double preco // Exemplo de campo que costuma ter em tabelas de preço
) {}