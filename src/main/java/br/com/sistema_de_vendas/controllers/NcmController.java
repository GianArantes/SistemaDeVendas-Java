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
import br.com.sistema_de_vendas.DTOs.NcmDTO;
import br.com.sistema_de_vendas.models.NcmModel;
import br.com.sistema_de_vendas.repositories.NcmRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ncms")
public class NcmController {
    @Autowired
    private NcmRepository ncmRepository;

    @PostMapping
    public void cadastrarNcm(@Valid @RequestBody NcmDTO ncm) {
        NcmModel ncmModel = new NcmModel();
        ncmModel.setCodigo(ncm.codigo());
        ncmModel.setDescricao(ncm.descricao());
        ncmRepository.save(ncmModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NcmModel> atualizarNcm(@Valid @PathVariable UUID id, @RequestBody NcmDTO ncm) {
        System.out.println(ncm);
        return ncmRepository.findById(id)
                .map(ncmModel -> {
                    ncmModel.setCodigo(ncm.codigo());
                    ncmModel.setDescricao(ncm.descricao());
                    ncmRepository.save(ncmModel);
                    return ResponseEntity.ok().body(ncmModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NcmModel> getNcm(@PathVariable UUID id) {
        return ncmRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/listar")
    public Iterable<NcmModel> listarNcms() {
        return ncmRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNcm(@PathVariable UUID id) {
        return ncmRepository.findById(id)
                .map(ncmModel -> {
                    ncmRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
