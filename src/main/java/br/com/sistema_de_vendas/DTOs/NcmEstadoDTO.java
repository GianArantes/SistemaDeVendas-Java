package br.com.sistema_de_vendas.DTOs;

import java.math.BigDecimal;
import java.util.UUID;

public record NcmEstadoDTO(UUID ncmId, String estado, BigDecimal aliquota) {

}