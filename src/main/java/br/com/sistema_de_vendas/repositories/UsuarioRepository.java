/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sistema_de_vendas.models.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {
    /**
     * Verifica se o email já está em uso por outro usuário.
     */
    boolean existsByEmail(String email);
}
