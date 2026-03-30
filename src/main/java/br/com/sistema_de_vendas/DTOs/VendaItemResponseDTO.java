/*
 * Copyright (C) 2026 Jean Paulo Arantes (Gian)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

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
