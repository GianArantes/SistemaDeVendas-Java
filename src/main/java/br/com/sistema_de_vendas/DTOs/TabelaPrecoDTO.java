package br.com.sistema_de_vendas.DTOs;

import java.time.LocalDate;

import java.util.UUID;

public record TabelaPrecoDTO(
    UUID id,
    String nomeTabela,
    LocalDate dataCriacao,
    String status // Recebendo como String para o seu Enum.valueOf
) {}