package br.com.sistema_de_vendas.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sistema_de_vendas.models.NcmEstadoModel;

@Repository
public interface NcmEstadoRepository extends JpaRepository<NcmEstadoModel, UUID> {

    /**
     * Retorna estados de um NCM ordenados em ordem alfabética.
     */
    List<NcmEstadoModel> findByNcmIdOrderByEstadoAsc(UUID ncmId);

    /**
     * Busca a alíquota do NCM para um estado específico.
     */
    Optional<NcmEstadoModel> findByNcmCodigoAndEstadoIgnoreCase(String codigo, String estado);

}
