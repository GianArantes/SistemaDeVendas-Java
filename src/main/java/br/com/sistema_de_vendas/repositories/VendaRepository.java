package br.com.sistema_de_vendas.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sistema_de_vendas.models.VendaModel;

public interface VendaRepository extends JpaRepository<VendaModel, UUID> {
}
