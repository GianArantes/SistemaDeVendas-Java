package br.com.sistema_de_vendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sistema_de_vendas.models.ProdutoModel;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {

}
