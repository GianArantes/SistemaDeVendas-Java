/*
 * Copyright (C) 2026 Jean Paulo Arantes (Gian)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

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
