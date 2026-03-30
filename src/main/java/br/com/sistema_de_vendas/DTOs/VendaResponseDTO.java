package br.com.sistema_de_vendas.DTOs;

import java.util.List;
import java.util.UUID;

public record VendaResponseDTO(
    UUID id,
    UUID vendedorId,
    String clienteCnpj,
    UUID tabelaPrecoId,
    String frete,
    TransportadoraDTO transportadora,
    Double valorProdutos,
    Double valorIpi,
    Double valorSt,
    Double valorGeral,
    List<VendaItemResponseDTO> itens
) {
}
