package br.com.sistema_de_vendas.services;

import org.springframework.stereotype.Service;

import br.com.sistema_de_vendas.DTOs.ProdutoLitragemDTO;

import static br.com.sistema_de_vendas.services.ValidationUtils.*;

@Service
public class ProdutoLitragemService {

    /**
     * Valida o nome da litragem de produto.
     */
    public void validate(ProdutoLitragemDTO dto) {
        assertNotBlank(dto.nome(), "nome");
    }
}
