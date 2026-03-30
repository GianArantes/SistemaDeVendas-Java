package br.com.sistema_de_vendas.DTOs;

public record VendaItemResponseDTO(
    Long produtoId,
    Integer quantidade,
    Double precoUnitario,
    Double valorBruto,
    Double descontoPercentual,
    Double valorLiquido,
    Double valorIpi,
    Double valorSt,
    Double custoUnitarioCliente
) {
}
