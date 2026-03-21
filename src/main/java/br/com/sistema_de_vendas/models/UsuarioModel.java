package br.com.sistema_de_vendas.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@Data
public class UsuarioModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String nomeCompleto;
    private String apelido;
    private String email;
    private String senha;
    private String telefone;

}
