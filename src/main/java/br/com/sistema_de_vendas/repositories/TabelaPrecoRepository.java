package br.com.sistema_de_vendas.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import br.com.sistema_de_vendas.models.TabelaPrecoModel;

@Repository
public interface TabelaPrecoRepository extends JpaRepository<TabelaPrecoModel, UUID> {

}
