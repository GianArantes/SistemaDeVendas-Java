package br.com.sistema_de_vendas.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "venda_itens")
public class VendaItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "venda_id", nullable = false)
    private VendaModel venda;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private ProdutoModel produto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_tabela_preco_id", nullable = false)
    private ProdutoTabelaPrecoModel produtoTabelaPreco;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private Double precoUnitario;

    @Column(nullable = false)
    private Double valorBruto;

    @Column(nullable = false)
    private Double descontoPercentual;

    @Column(nullable = false)
    private Double valorLiquido;

    @Column(nullable = false)
    private Double valorIpi;

    @Column(nullable = false)
    private Double valorSt;

    @Column(nullable = false)
    private Double custoUnitarioCliente;
}
