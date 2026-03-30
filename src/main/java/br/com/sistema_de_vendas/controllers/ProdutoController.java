package br.com.sistema_de_vendas.controllers;

import java.util.List;
import java.util.UUID;

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
import br.com.sistema_de_vendas.services.ProdutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final NcmRepository ncmRepository;
    private final ProdutoService produtoService;
    private final ProdutoCategoriaRepository categoriaRepository;
    private final ProdutoLitragemRepository litragemRepository;

    /**
     * Cadastra um novo produto com suas referências de NCM, categoria e litragem.
     */
    @PostMapping
    public void cadastrarProduto(@Valid @RequestBody ProdutoDTO produtoNovo) {
        produtoService.validate(produtoNovo);
        System.out.println(produtoNovo);
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome(produtoNovo.nome());
        produtoModel.setRefNf(produtoNovo.refNf());
        produtoModel.setIpi(produtoNovo.ipi());
        produtoModel.setPeso(produtoNovo.peso());
        produtoModel.setQtdPorEmbalagem(produtoNovo.qtdadePorEmbalagem());
        produtoModel.setNcm(ncmRepository.findById(UUID.fromString(produtoNovo.ncmId())).orElse(null));
        produtoModel.setCategoria(categoriaRepository.findById(produtoNovo.categoriaId()).orElse(null));
        produtoModel.setLitragem(litragemRepository.findById(produtoNovo.litragemId()).orElse(null));
        produtoRepository.save(produtoModel);

    }

    /**
     * Atualiza os dados de um produto existente.
     */
    @PutMapping("/{id}")
    public void atualizarProduto(@PathVariable long id, @Valid @RequestBody ProdutoDTO produtoNovo) {
        produtoService.validate(produtoNovo);
        ProdutoModel produtoModel = produtoRepository.findById(id).orElse(null);
        if (produtoModel != null) {
            produtoModel.setNome(produtoNovo.nome());
            produtoModel.setRefNf(produtoNovo.refNf());
            produtoModel.setIpi(produtoNovo.ipi());
            produtoModel.setPeso(produtoNovo.peso());
            produtoModel.setQtdPorEmbalagem(produtoNovo.qtdadePorEmbalagem());
            produtoModel.setNcm(ncmRepository.findById(UUID.fromString(produtoNovo.ncmId())).orElse(null));
            produtoModel.setCategoria(categoriaRepository.findById(produtoNovo.categoriaId()).orElse(null));
            produtoModel.setLitragem(litragemRepository.findById(produtoNovo.litragemId()).orElse(null));
            produtoRepository.save(produtoModel);
        }

    }

    /**
     * Recupera um produto pelo seu identificador.
     */
    @GetMapping("/{id}")
    public ProdutoModel getProduto(@PathVariable long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    /**
     * Retorna todos os produtos cadastrados.
     */
    @GetMapping ("/listar")
    public List<ProdutoModel> listarProdutos() {
        return produtoRepository.findAll();
    }

    /**
     * Exclui um produto pelo seu identificador.
     */
    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable long id) {
        produtoRepository.deleteById(id);
    }

}
