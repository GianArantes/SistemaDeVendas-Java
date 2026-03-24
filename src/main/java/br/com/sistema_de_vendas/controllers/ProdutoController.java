package br.com.sistema_de_vendas.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistema_de_vendas.DTOs.ProdutoDTO;
import br.com.sistema_de_vendas.models.ProdutoModel;
import br.com.sistema_de_vendas.repositories.NcmRepository;
import br.com.sistema_de_vendas.repositories.ProdutoCategoriaRepository;
import br.com.sistema_de_vendas.repositories.ProdutoLitragemRepository;
import br.com.sistema_de_vendas.repositories.ProdutoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private NcmRepository ncmRepository;

    @Autowired
    private ProdutoCategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoLitragemRepository litragemRepository;

    @PostMapping
    public void cadastrarProduto(@Valid @RequestBody ProdutoDTO produtoNovo) {
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome(produtoNovo.nome());
        produtoModel.setRefNf(produtoNovo.refNf());
        produtoModel.setIpi(produtoNovo.ipi());
        produtoModel.setPeso(produtoNovo.peso());
        produtoModel.setQtdPorEmbalagem(produtoNovo.quantidadePorEmbalagem());
        produtoModel.setNcm(ncmRepository.findById(UUID.fromString(produtoNovo.ncmId())).orElse(null));
        produtoModel.setCategoria(categoriaRepository.findById(produtoNovo.produtoCategoriaId()).orElse(null));
        produtoModel.setLitragem(litragemRepository.findById(produtoNovo.produtoLitragemId()).orElse(null));
        produtoRepository.save(produtoModel);

    }

    @PutMapping("/{id}")
    public void atualizarProduto(@PathVariable long id, @Valid @RequestBody ProdutoDTO produtoNovo) {
        ProdutoModel produtoModel = produtoRepository.findById(id).orElse(null);
        if (produtoModel != null) {
            produtoModel.setNome(produtoNovo.nome());
            produtoModel.setRefNf(produtoNovo.refNf());
            produtoModel.setIpi(produtoNovo.ipi());
            produtoModel.setPeso(produtoNovo.peso());
            produtoModel.setQtdPorEmbalagem(produtoNovo.quantidadePorEmbalagem());
            produtoModel.setNcm(ncmRepository.findById(UUID.fromString(produtoNovo.ncmId())).orElse(null));
            produtoModel.setCategoria(categoriaRepository.findById(produtoNovo.produtoCategoriaId()).orElse(null));
            produtoModel.setLitragem(litragemRepository.findById(produtoNovo.produtoLitragemId()).orElse(null));
            produtoRepository.save(produtoModel);
        }

    }

    @GetMapping("/{id}")
    public ProdutoModel getProduto(@PathVariable long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    @GetMapping ("/listar")
    public List<ProdutoModel> listarProdutos() {
        return produtoRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable long id) {
        produtoRepository.deleteById(id);
    }

}
