package br.com.sistema_de_vendas.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vendas")
public class VendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID vendedorId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteModel cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tabela_preco_id", nullable = false)
    private TabelaPrecoModel tabelaPreco;

    @Column(nullable = false, length = 20)
    private String frete;

    @Column(length = 200)
    private String transportadoraNome;

    @Column(length = 20)
    private String transportadoraCnpj;

    @Column(length = 20)
    private String transportadoraPlaca;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendaItemModel> itens = new ArrayList<>();

    @Column(nullable = false)
    private Double valorProdutos;

    @Column(nullable = false)
    private Double valorIpi;

    @Column(nullable = false)
    private Double valorSt;

    @Column(nullable = false)
    private Double valorGeral;
}
