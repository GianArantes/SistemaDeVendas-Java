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
