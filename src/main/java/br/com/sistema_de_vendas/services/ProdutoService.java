package br.com.sistema_de_vendas.services;

import org.springframework.stereotype.Service;

import br.com.sistema_de_vendas.DTOs.ProdutoDTO;

import static br.com.sistema_de_vendas.services.ValidationUtils.*;

@Service
public class ProdutoService {

    /**
     * Valida os campos obrigatórios de um produto.
     */
    public void validate(ProdutoDTO dto) {
        assertNotBlank(dto.nome(), "nome");
        assertNotBlank(dto.refNf(), "refNf");
        if (dto.peso() == null || dto.peso() <= 0) {
            throw new br.com.sistema_de_vendas.Exception.BusinessException("peso deve ser maior que zero");
        }
        if (dto.ipi() == null || dto.ipi() < 0) {
            throw new br.com.sistema_de_vendas.Exception.BusinessException("ipi não pode ser negativo");
        }
        if (dto.qtdadePorEmbalagem() == null || dto.qtdadePorEmbalagem() <= 0) {
            throw new br.com.sistema_de_vendas.Exception.BusinessException("qtdadePorEmbalagem deve ser maior que zero");
        }
        if (dto.litragemId() == null) {
            throw new br.com.sistema_de_vendas.Exception.BusinessException("litragemId é obrigatório");
        }
        if (dto.categoriaId() == null) {
            throw new br.com.sistema_de_vendas.Exception.BusinessException("categoriaId é obrigatório");
        }
        assertNotBlank(dto.ncmId(), "ncmId");
        assertValidUUID(dto.ncmId(), "ncmId");
    }
}
