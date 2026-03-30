/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema_de_vendas.models.ClienteModel;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, UUID> {

    /**
     * Verifica se já existe cliente cadastrado com o email informado.
     */
    boolean existsByEmail(String email);

    /**
     * Verifica se já existe cliente cadastrado com o mesmo CNPJ.
     */
    boolean existsByCnpj(String cnpj);

    /**
     * Busca cliente pelo CNPJ.
     */
    Optional<ClienteModel> findByCnpj(String cnpj);

}

