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
import br.com.sistema_de_vendas.Exception.BusinessException;
import br.com.sistema_de_vendas.models.ClienteModel;
import br.com.sistema_de_vendas.repositories.ClienteRepository;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    //Cadastrar um novo cliente
    @PostMapping
    public ResponseEntity<ClienteModel> cadastrarCliente(@Valid @RequestBody ClienteDTO cliente) {
        
        if (cliente.cnpj() == null || cliente.cnpj().trim().isEmpty()) {
            throw new BusinessException("CNPJ é obrigatório");
        }
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
        clienteNovo.setIe(cliente.ie());
        clienteNovo.setEndereco(cliente.endereco());
        clienteNovo.setEmail(cliente.email());
        clienteNovo.setTelefone(cliente.telefone());
        ClienteModel salvo = clienteRepository.save(clienteNovo);
        return ResponseEntity.status(201).body(salvo);
    }
    // Listar todos os clientes
    @GetMapping("/listar")
    public Iterable<ClienteModel> listarClientes() {
        return clienteRepository.findAll();
    }
    
    // GET POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> buscarPorId(@PathVariable UUID id) {
        return clienteRepository.findById(id)
                .map(cliente -> ResponseEntity.ok().body(cliente))
                .orElse(ResponseEntity.notFound().build());
    }

    // ATUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> atualizarCliente(@PathVariable UUID id, @RequestBody ClienteDTO clienteDto) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    clienteExistente.setRazaoSocial(clienteDto.razaoSocial());
                    clienteExistente.setNomeFantasia(clienteDto.nomeFantasia());
                    clienteExistente.setCnpj(clienteDto.cnpj());
                    if (clienteDto.dataFundacao() != null && !clienteDto.dataFundacao().trim().isEmpty()) {
                        clienteExistente.setDataFundacao(LocalDate.parse(clienteDto.dataFundacao()));
                    }
                    clienteExistente.setIe(clienteDto.ie());
                    clienteExistente.setEndereco(clienteDto.endereco());
                    clienteExistente.setEmail(clienteDto.email());
                    clienteExistente.setTelefone(clienteDto.telefone());

                    ClienteModel atualizado = clienteRepository.save(clienteExistente);
                    return ResponseEntity.ok().body(atualizado);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable UUID id) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    clienteRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
