package br.com.sistema_de_vendas.models;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table (name = "clientes")
@AllArgsConstructor
@NoArgsConstructor
public class ClienteModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private LocalDate dataFundacao;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String ie;
    private String endereco;
    private String email;
    private String telefone;

}
