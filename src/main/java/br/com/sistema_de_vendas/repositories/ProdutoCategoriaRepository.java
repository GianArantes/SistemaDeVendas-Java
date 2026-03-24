package br.com.sistema_de_vendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sistema_de_vendas.models.ProdutoCategoriaModel;

import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoCategoriaRepository extends JpaRepository<ProdutoCategoriaModel, Long> {

}
