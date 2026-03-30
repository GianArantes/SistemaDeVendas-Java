package br.com.sistema_de_vendas.controllers;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.sistema_de_vendas.DTOs.ClienteDTO;
import br.com.sistema_de_vendas.DTOs.EnderecoDTO;
import br.com.sistema_de_vendas.Exception.BusinessException;
import br.com.sistema_de_vendas.models.ClienteModel;
import br.com.sistema_de_vendas.models.EnderecoModel;
import br.com.sistema_de_vendas.models.Enum.ClienteStatus;
import br.com.sistema_de_vendas.repositories.ClienteRepository;
import br.com.sistema_de_vendas.services.ClienteService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    /**
     * Cadastra um novo cliente após validações básicas.
     */
    @PostMapping
    public ResponseEntity<ClienteModel> cadastrarCliente(@Valid @RequestBody ClienteDTO cliente) {
        clienteService.validate(cliente);
        if (clienteRepository.existsByCnpj(cliente.cnpj())) {
            throw new BusinessException("CNPJ já cadastrado");
        }

        ClienteModel clienteNovo = new ClienteModel();
        clienteNovo.setRazaoSocial(cliente.razaoSocial());
        clienteNovo.setNomeFantasia(cliente.nomeFantasia());
        clienteNovo.setCnpj(cliente.cnpj());
        if (cliente.dataFundacao() != null && !cliente.dataFundacao().trim().isEmpty()) {
            clienteNovo.setDataFundacao(LocalDate.parse(cliente.dataFundacao()));
        }
        clienteNovo.setStatus(ClienteStatus.valueOf(cliente.status()));
        clienteNovo.setIe(cliente.ie());
        clienteNovo.setEnderecoRegistro(buildEnderecoModel(cliente.enderecoRegistro()));
        clienteNovo.setEnderecoEntrega(buildEnderecoModel(cliente.enderecoEntrega()));
        clienteNovo.setEnderecoCobranca(buildEnderecoModel(cliente.enderecoCobranca()));
        clienteNovo.setEmail(cliente.email());
        clienteNovo.setTelefone(cliente.telefone());
        ClienteModel salvo = clienteRepository.save(clienteNovo);
        return ResponseEntity.status(201).body(salvo);
    }
    /**
     * Retorna a lista completa de clientes cadastrados.
     */
    @GetMapping("/listar")
    public Iterable<ClienteModel> listarClientes() {
        return clienteRepository.findAll();
    }
    
    /**
     * Recupera um cliente pelo identificador UUID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> buscarPorId(@PathVariable UUID id) {
        return clienteRepository.findById(id)
                .map(cliente -> ResponseEntity.ok().body(cliente))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza um cliente existente pelo seu ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> atualizarCliente(@Valid @PathVariable UUID id, @RequestBody ClienteDTO cliente) {
        clienteService.validate(cliente);
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    clienteExistente.setRazaoSocial(cliente.razaoSocial());
                    clienteExistente.setNomeFantasia(cliente.nomeFantasia());
                    clienteExistente.setCnpj(cliente.cnpj());
                    if (cliente.dataFundacao() != null && !cliente.dataFundacao().trim().isEmpty()) {
                        clienteExistente.setDataFundacao(LocalDate.parse(cliente.dataFundacao()));
                    }
                    clienteExistente.setStatus(ClienteStatus.valueOf(cliente.status()));
                    clienteExistente.setIe(cliente.ie());
                    clienteExistente.setEnderecoRegistro(buildEnderecoModel(cliente.enderecoRegistro()));
                    clienteExistente.setEnderecoEntrega(buildEnderecoModel(cliente.enderecoEntrega()));
                    clienteExistente.setEnderecoCobranca(buildEnderecoModel(cliente.enderecoCobranca()));
                    clienteExistente.setEmail(cliente.email());
                    clienteExistente.setTelefone(cliente.telefone());

                    ClienteModel atualizado = clienteRepository.save(clienteExistente);
                    return ResponseEntity.ok().body(atualizado);
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um cliente pelo ID e retorna resposta sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable UUID id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }

    private EnderecoModel buildEnderecoModel(EnderecoDTO endereco) {
        EnderecoModel model = new EnderecoModel();
        model.setLogradouro(endereco.logradouro());
        model.setNumero(endereco.numero());
        model.setComplemento(endereco.complemento());
        model.setCep(endereco.cep());
        model.setBairro(endereco.bairro());
        model.setCidade(endereco.cidade());
        model.setEstado(endereco.estado());
        return model;
    }

}
