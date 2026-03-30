package br.com.sistema_de_vendas.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sistema_de_vendas.models.ProdutoTabelaPrecoModel;

public interface ProdutoTabelaPrecoRepository extends JpaRepository<ProdutoTabelaPrecoModel, UUID> {

    Optional<ProdutoTabelaPrecoModel> findByTabelaPrecoIdAndProdutoId(UUID tabelaPrecoId, Long produtoId);

}
