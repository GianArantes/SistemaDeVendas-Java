package br.com.sistema_de_vendas.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.sistema_de_vendas.models.NcmModel;

@Repository
public interface NcmRepository extends JpaRepository<NcmModel, UUID> {

    public NcmModel findNcmByCodigo(String codigo);



}
