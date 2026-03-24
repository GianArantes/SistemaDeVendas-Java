package br.com.sistema_de_vendas.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "produto_tabela_preco")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoTabelaPrecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private ProdutoModel produto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tabela_preco_id")
    private TabelaPrecoModel tabelaPreco;
    private Double preco;

}
