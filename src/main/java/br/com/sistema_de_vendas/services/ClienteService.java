package br.com.sistema_de_vendas.services;

import org.springframework.stereotype.Service;

import br.com.sistema_de_vendas.DTOs.ClienteDTO;
import br.com.sistema_de_vendas.models.Enum.ClienteStatus;

import static br.com.sistema_de_vendas.services.ValidationUtils.*;

@Service
public class ClienteService {

    /**
     * Valida os dados de um cliente antes de persistir.
     */
    public void validate(ClienteDTO dto) {
        assertNotBlank(dto.razaoSocial(), "razaoSocial");
        assertNotBlank(dto.dataFundacao(), "dataFundacao");
        assertValidDate(dto.dataFundacao(), "dataFundacao");
        assertNotBlank(dto.nomeFantasia(), "nomeFantasia");
        assertNotBlank(dto.cnpj(), "cnpj");
        assertCnpj(dto.cnpj(), "cnpj");
        assertNotBlank(dto.ie(), "ie");
        validateEnderecoDTO(dto.enderecoRegistro(), "enderecoRegistro");
        validateEnderecoDTO(dto.enderecoEntrega(), "enderecoEntrega");
        validateEnderecoDTO(dto.enderecoCobranca(), "enderecoCobranca");
        assertNotBlank(dto.email(), "email");
        assertEmail(dto.email(), "email");
        assertNotBlank(dto.telefone(), "telefone");
        assertNotBlank(dto.status(), "status");
        assertValidEnum(dto.status(), ClienteStatus.class, "status");
    }
}
