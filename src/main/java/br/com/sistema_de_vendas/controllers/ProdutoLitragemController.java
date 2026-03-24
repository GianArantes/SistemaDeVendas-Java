package br.com.sistema_de_vendas.controllers;

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

import br.com.sistema_de_vendas.DTOs.ProdutoLitragemDTO;
import br.com.sistema_de_vendas.models.ProdutoLitragemModel;
import br.com.sistema_de_vendas.repositories.ProdutoLitragemRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto-litragem")
public class ProdutoLitragemController {

    @Autowired
    private ProdutoLitragemRepository produtoLitragemRepository;

    @PostMapping
    public ResponseEntity<ProdutoLitragemModel> cadastrarProdutoLitragem(@Valid @RequestBody ProdutoLitragemDTO produtoLitragem) {
        ProdutoLitragemModel produtoLitragemModel = new ProdutoLitragemModel();
        produtoLitragemModel.setNome(produtoLitragem.nome());
        ProdutoLitragemModel salvo = produtoLitragemRepository.save(produtoLitragemModel);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoLitragemModel> atualizarProdutoLitragem(@PathVariable Long id, @Valid @RequestBody ProdutoLitragemDTO produtoLitragem) {
        if (!produtoLitragemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ProdutoLitragemModel produtoLitragemModel = new ProdutoLitragemModel();
        produtoLitragemModel.setId(id);
        produtoLitragemModel.setNome(produtoLitragem.nome());       
        ProdutoLitragemModel salvo = produtoLitragemRepository.save(produtoLitragemModel);
        return ResponseEntity.ok().body(salvo);
    }

    @GetMapping("/listar")
    public Iterable<ProdutoLitragemModel> listarProdutoLitragem() {
        return produtoLitragemRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoLitragemModel> getProdutoLitragem(@PathVariable Long id) {
        return produtoLitragemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProdutoLitragem(@PathVariable Long id) {
        return produtoLitragemRepository.findById(id)
                .map(produtoLitragem -> {
                    produtoLitragemRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
