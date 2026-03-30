/*
 * Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 */

package br.com.sistema_de_vendas.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.sistema_de_vendas.models.NcmModel;

@Repository
public interface NcmRepository extends JpaRepository<NcmModel, UUID> {

    public NcmModel findNcmByCodigo(String codigo);



}
