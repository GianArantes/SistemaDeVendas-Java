package br.com.sistema_de_vendas.DTOs;

public record VendaItemDTO(
    Long produtoId,
    Integer quantidade,
    Double descontoPercentual
) {
}
