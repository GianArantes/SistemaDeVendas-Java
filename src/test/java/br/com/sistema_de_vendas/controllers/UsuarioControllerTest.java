/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import br.com.sistema_de_vendas.DTOs.UsuarioDTO;
import br.com.sistema_de_vendas.Exception.BusinessException;
import br.com.sistema_de_vendas.models.UsuarioModel;
import br.com.sistema_de_vendas.models.Enum.UsuarioRole;
import br.com.sistema_de_vendas.models.Enum.UsuarioStatus;
import br.com.sistema_de_vendas.repositories.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Spy
    private br.com.sistema_de_vendas.services.UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setup() {
        usuarioDTO = new UsuarioDTO(
                "João Silva",
                "Joao",
                "joao@teste.com",
                "senhaSegura",
                "(11) 99999-9999",
                UsuarioRole.REPRESENTANTE.name(),
                UsuarioStatus.ATIVO.name());
    }

    @Test
    void cadastrarUsuario_quandoEmailJaExiste_deveLancarBusinessException() {
        when(usuarioRepository.existsByEmail(usuarioDTO.email())).thenReturn(true);

        assertThrows(BusinessException.class, () -> usuarioController.cadastrarUsuario(usuarioDTO));
    }

    @Test
    void cadastrarUsuario_sucesso_deveRetornarCriado() {
        when(usuarioRepository.existsByEmail(usuarioDTO.email())).thenReturn(false);
        when(usuarioRepository.save(any(UsuarioModel.class))).thenAnswer(invocation -> {
            UsuarioModel saved = invocation.getArgument(0);
            saved.setId(UUID.randomUUID());
            return saved;
        });

        ResponseEntity<UsuarioModel> response = usuarioController.cadastrarUsuario(usuarioDTO);

        assertEquals(201, response.getStatusCode().value(), "Deve retornar status 201 ao criar usuário");
        assertNotNull(response.getBody().getId(), "O usuário criado deve receber um id");
        assertEquals("joao@teste.com", response.getBody().getEmail());
    }

    @Test
    void buscarPorId_quandoExiste_deveRetornarOk() {
        UUID id = UUID.randomUUID();
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(id);
        usuario.setEmail(usuarioDTO.email());
        usuario.setNomeCompleto(usuarioDTO.nomeCompleto());
        usuario.setRole(UsuarioRole.REPRESENTANTE);
        usuario.setStatus(UsuarioStatus.ATIVO);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        ResponseEntity<UsuarioModel> response = usuarioController.buscarPorId(id);

        assertEquals(200, response.getStatusCode().value(), "Deve retornar 200 quando encontrar o usuário");
        assertEquals(id, response.getBody().getId());
    }
}
