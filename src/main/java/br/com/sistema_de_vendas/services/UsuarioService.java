package br.com.sistema_de_vendas.services;

import org.springframework.stereotype.Service;

import br.com.sistema_de_vendas.DTOs.UsuarioDTO;
import br.com.sistema_de_vendas.models.Enum.UsuarioRole;
import br.com.sistema_de_vendas.models.Enum.UsuarioStatus;

import static br.com.sistema_de_vendas.services.ValidationUtils.*;

@Service
public class UsuarioService {

    /**
     * Valida campos de um usuário antes de criação ou atualização.
     */
    public void validate(UsuarioDTO dto) {
        assertNotBlank(dto.nomeCompleto(), "nomeCompleto");
        assertNotBlank(dto.apelido(), "apelido");
        assertNotBlank(dto.email(), "email");
        assertEmail(dto.email(), "email");
        assertNotBlank(dto.senha(), "senha");
        assertMinLength(dto.senha(), 6, "senha");
        assertNotBlank(dto.telefone(), "telefone");
        assertNotBlank(dto.role(), "role");
        assertValidEnum(dto.role(), UsuarioRole.class, "role");
        assertNotBlank(dto.status(), "status");
        assertValidEnum(dto.status(), UsuarioStatus.class, "status");
    }
}
