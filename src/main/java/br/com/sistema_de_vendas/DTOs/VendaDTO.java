package br.com.sistema_de_vendas.DTOs;

import java.util.List;
import java.util.UUID;

public record VendaDTO(
    UUID vendedorId,
    String clienteCnpj,
    UUID tabelaPrecoId,
    String frete,
    TransportadoraDTO transportadora,
    List<VendaItemDTO> itens
) {
}
