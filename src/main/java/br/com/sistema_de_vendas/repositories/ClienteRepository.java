package br.com.sistema_de_vendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema_de_vendas.models.ClienteModel;

import java.util.UUID;

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

}

