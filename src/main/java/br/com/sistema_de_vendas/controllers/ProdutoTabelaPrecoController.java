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

import br.com.sistema_de_vendas.DTOs.ProdutoTabelaPrecoDTO;
import br.com.sistema_de_vendas.models.ProdutoModel;
import br.com.sistema_de_vendas.models.ProdutoTabelaPrecoModel;
import br.com.sistema_de_vendas.models.TabelaPrecoModel;
import br.com.sistema_de_vendas.repositories.ProdutoRepository;
import br.com.sistema_de_vendas.repositories.ProdutoTabelaPrecoRepository;
import br.com.sistema_de_vendas.repositories.TabelaPrecoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto-tabela-preco")
public class ProdutoTabelaPrecoController {

    @Autowired
    ProdutoTabelaPrecoRepository produtoTabelaPrecoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    TabelaPrecoRepository tabelaPrecoRepository;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody ProdutoTabelaPrecoDTO dto) {
        ProdutoModel produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        TabelaPrecoModel tabelaPreco = tabelaPrecoRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Tabela de preço não encontrada"));

        ProdutoTabelaPrecoModel model = new ProdutoTabelaPrecoModel();
        model.setProduto(produto);
        model.setTabelaPreco(tabelaPreco);
        model.setPreco(dto.preco());
        produtoTabelaPrecoRepository.save(model);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @Valid @RequestBody ProdutoTabelaPrecoDTO dto) {
        ProdutoTabelaPrecoModel model = produtoTabelaPrecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProdutoTabelaPreco não encontrado"));

        ProdutoModel produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        TabelaPrecoModel tabelaPreco = tabelaPrecoRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Tabela de preço não encontrada"));

        model.setProduto(produto);
        model.setTabelaPreco(tabelaPreco);
        model.setPreco(dto.preco());
        produtoTabelaPrecoRepository.save(model);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoTabelaPrecoModel> getProdutoTabelaPreco(@PathVariable UUID id) {
        return produtoTabelaPrecoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/listar")
    public Iterable<ProdutoTabelaPrecoModel> listarProdutoTabelaPreco() {
        return produtoTabelaPrecoRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        produtoTabelaPrecoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
