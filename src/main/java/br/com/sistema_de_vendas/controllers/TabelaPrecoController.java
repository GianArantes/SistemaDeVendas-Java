package br.com.sistema_de_vendas.controllers;

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
import br.com.sistema_de_vendas.DTOs.TabelaPrecoDTO;
import br.com.sistema_de_vendas.models.TabelaPrecoModel;
import br.com.sistema_de_vendas.models.Enum.TabelaStatus;
import br.com.sistema_de_vendas.repositories.TabelaPrecoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tabela-preco")
public class TabelaPrecoController {

    @Autowired
    TabelaPrecoRepository tabelaDePrecoRepository;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid TabelaPrecoDTO dto) {
        TabelaPrecoModel model = new TabelaPrecoModel();
        model.setNomeTabela(dto.nomeTabela());
        model.setDataCriacao(dto.dataCriacao());
        model.setStatus(TabelaStatus.valueOf(dto.status().toUpperCase()));
        tabelaDePrecoRepository.save(model);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @Valid @RequestBody TabelaPrecoDTO dto) {
        TabelaPrecoModel model = tabelaDePrecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tabela de preço não encontrada"));
        model.setNomeTabela(dto.nomeTabela());
        model.setDataCriacao(dto.dataCriacao());
        model.setStatus(TabelaStatus.valueOf(dto.status().toUpperCase()));
        tabelaDePrecoRepository.save(model);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TabelaPrecoModel> getTabelaPreco(@PathVariable UUID id) {
        return tabelaDePrecoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/listar")
    public Iterable<TabelaPrecoModel> listarTabelaPreco() { 
        return tabelaDePrecoRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTabelaPreco(@PathVariable UUID id) {
        tabelaDePrecoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
