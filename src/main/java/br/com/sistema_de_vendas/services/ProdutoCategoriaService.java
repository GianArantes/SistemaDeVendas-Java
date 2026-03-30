package br.com.sistema_de_vendas.services;

import org.springframework.stereotype.Service;

import br.com.sistema_de_vendas.DTOs.ProdutoCategoriaDTO;

import static br.com.sistema_de_vendas.services.ValidationUtils.*;

@Service
public class ProdutoCategoriaService {

    /**
     * Valida o nome da categoria de produto.
     */
    public void validate(ProdutoCategoriaDTO dto) {
        assertNotBlank(dto.nome(), "nome");
    }
}
