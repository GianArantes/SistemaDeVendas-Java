package br.com.sistema_de_vendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.sistema_de_vendas.models.ProdutoLitragemModel;


@Repository
public interface ProdutoLitragemRepository extends JpaRepository<ProdutoLitragemModel, Long>{

    
}