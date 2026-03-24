package br.com.sistema_de_vendas.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.sistema_de_vendas.models.NcmEstadoModel;
import br.com.sistema_de_vendas.models.NcmModel;

import java.util.List;


@Repository
public interface NcmEstadoRepository extends JpaRepository<NcmEstadoModel, UUID> {

    List<NcmEstadoModel> findByNcm(NcmModel ncm);

}
