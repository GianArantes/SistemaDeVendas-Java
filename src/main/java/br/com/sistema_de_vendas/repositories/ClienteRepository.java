package br.com.sistema_de_vendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema_de_vendas.models.ClienteModel;

import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, UUID> {

    boolean existsByEmail(String email);
    boolean existsByCnpj(String cnpj);

}

