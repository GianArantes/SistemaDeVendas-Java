package br.com.sistema_de_vendas.models;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "NcmAliquotaEstado")
public class NcmEstadoModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(length = 2)
    private String estado;
    private BigDecimal aliquota;

    @ManyToOne
    @JoinColumn(name = "ncm_id")
    private NcmModel ncm;

}
