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
import br.com.sistema_de_vendas.DTOs.ProdutoCategoriaDTO;
import br.com.sistema_de_vendas.models.ProdutoCategoriaModel;
import br.com.sistema_de_vendas.repositories.ProdutoCategoriaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto-categoria")
public class ProdutoCategoriaController {

    @Autowired
    private ProdutoCategoriaRepository produtoCategoriaRepository;

    @PostMapping
    public ResponseEntity<ProdutoCategoriaModel> cadastrarProdutoCategoria(
            @Valid @RequestBody ProdutoCategoriaDTO produtoCategoria) {
        ProdutoCategoriaModel produtoCategoriaModel = new ProdutoCategoriaModel();
        produtoCategoriaModel.setNome(produtoCategoria.nome());
        ProdutoCategoriaModel salvo = produtoCategoriaRepository.save(produtoCategoriaModel);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoCategoriaModel> atualizarProdutoCategoria(@PathVariable Long id,
            ProdutoCategoriaDTO produtoCategoria) {
        if (!produtoCategoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ProdutoCategoriaModel produtoCategoriaModel = new ProdutoCategoriaModel();
        produtoCategoriaModel.setId(id);
        produtoCategoriaModel.setNome(produtoCategoria.nome());
        ProdutoCategoriaModel salvo = produtoCategoriaRepository.save(produtoCategoriaModel);
        return ResponseEntity.ok().body(salvo);
    }

    @GetMapping("/listar")
    public Iterable<ProdutoCategoriaModel> listarProdutoCategoria() {
        return produtoCategoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoCategoriaModel> getProdutoCategoria(@PathVariable Long id) {
        return produtoCategoriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProdutoCategoria(@PathVariable Long id) {
        return produtoCategoriaRepository.findById(id)
                .map(produtoCategoria -> {
                    produtoCategoriaRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
