package br.com.sistema_de_vendas.models;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "produtos")
public class ProdutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refNf;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private ProdutoCategoriaModel categoria;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "litragem_id")
    private ProdutoLitragemModel litragem;
    private Integer qtdPorEmbalagem;
    private Double ipi;
    private Double peso;
    @ManyToOne
    @JoinColumn(name = "ncm_id")
    private NcmModel ncm;
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoTabelaPrecoModel> tabelasDePreco = new ArrayList<>();

    public ProdutoModel(String nome, Double peso, NcmModel ncm) {
        this.nome = nome;
        this.peso = peso;
        this.ncm = ncm;
    }
    


}
