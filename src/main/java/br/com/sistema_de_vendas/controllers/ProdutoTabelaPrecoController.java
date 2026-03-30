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
import br.com.sistema_de_vendas.services.ProdutoTabelaPrecoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto-tabela-preco")
public class ProdutoTabelaPrecoController {

    @Autowired
    ProdutoTabelaPrecoRepository produtoTabelaPrecoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoTabelaPrecoService produtoTabelaPrecoService;

    @Autowired
    TabelaPrecoRepository tabelaPrecoRepository;

    /**
     * Associa um produto a uma tabela de preço e salva a relação.
     */
    @PostMapping
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody ProdutoTabelaPrecoDTO dto) {
        produtoTabelaPrecoService.validate(dto);
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

    /**
     * Atualiza uma associação de produto e tabela de preço existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable UUID id, @Valid @RequestBody ProdutoTabelaPrecoDTO dto) {
        produtoTabelaPrecoService.validate(dto);
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

    /**
     * Recupera uma entrada de preço de produto pelo id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoTabelaPrecoModel> getProdutoTabelaPreco(@PathVariable UUID id) {
        return produtoTabelaPrecoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todas as associações entre produtos e tabelas de preço.
     */
    @GetMapping("/listar")
    public Iterable<ProdutoTabelaPrecoModel> listarProdutoTabelaPreco() {
        return produtoTabelaPrecoRepository.findAll();
    }

    /**
     * Exclui a associação de preço de produto indicada pelo id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        produtoTabelaPrecoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
