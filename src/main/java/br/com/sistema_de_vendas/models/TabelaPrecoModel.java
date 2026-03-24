package br.com.sistema_de_vendas.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.sistema_de_vendas.models.Enum.TabelaStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Data
@Table(name = "tabela_de_preco")
public class TabelaPrecoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nomeTabela;
    private LocalDate dataCriacao;
    @Enumerated(EnumType.STRING)
    private TabelaStatus status;
    @OneToMany(mappedBy = "tabelaPreco", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoTabelaPrecoModel> produtos = new ArrayList<>();

}
