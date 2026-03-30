package br.com.sistema_de_vendas.services;

import org.springframework.stereotype.Service;

import br.com.sistema_de_vendas.DTOs.ProdutoTabelaPrecoDTO;

@Service
public class ProdutoTabelaPrecoService {

    /**
     * Valida associações entre produto e tabela de preço.
     */
    public void validate(ProdutoTabelaPrecoDTO dto) {
        if (dto.id() == null) {
            throw new br.com.sistema_de_vendas.Exception.BusinessException("id (tabelaPrecoId) é obrigatório");
        }
        if (dto.produtoId() == null) {
            throw new br.com.sistema_de_vendas.Exception.BusinessException("produtoId é obrigatório");
        }
        if (dto.preco() == null || dto.preco() < 0) {
            throw new br.com.sistema_de_vendas.Exception.BusinessException("preco deve ser maior ou igual a zero");
        }
    }
}
