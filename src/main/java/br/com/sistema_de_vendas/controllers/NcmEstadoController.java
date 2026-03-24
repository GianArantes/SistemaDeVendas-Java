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

import br.com.sistema_de_vendas.DTOs.NcmEstadoDTO;
import br.com.sistema_de_vendas.models.NcmEstadoModel;
import br.com.sistema_de_vendas.repositories.NcmEstadoRepository;
import br.com.sistema_de_vendas.repositories.NcmRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ncms-estados")
public class NcmEstadoController {

    @Autowired
    NcmEstadoRepository ncmEstadoRepository;

    @Autowired
    NcmRepository ncmRepository;

    @PostMapping
    public void cadastrarNcmEstado(@Valid @RequestBody NcmEstadoDTO ncmEstado) {
        NcmEstadoModel ncmEstadoModel = new NcmEstadoModel();
        ncmEstadoModel.setNcm(ncmRepository.findById(ncmEstado.ncmId()).orElse(null));
        ncmEstadoModel.setEstado(ncmEstado.estado());
        ncmEstadoModel.setAliquota(ncmEstado.aliquota());
        ncmEstadoRepository.save(ncmEstadoModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NcmEstadoModel> atualizarNcmEstado(@Valid @PathVariable UUID id,
            @RequestBody NcmEstadoDTO ncmEstado) {
        return ncmEstadoRepository.findById(id)
                .map(ncmEstadoModel -> {
                    ncmEstadoModel.setNcm(ncmRepository.findById(ncmEstado.ncmId()).orElse(null));
                    ncmEstadoModel.setEstado(ncmEstado.estado());
                    ncmEstadoModel.setAliquota(ncmEstado.aliquota());
                    ncmEstadoRepository.save(ncmEstadoModel);
                    return ResponseEntity.ok().body(ncmEstadoModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        return ncmEstadoRepository.findById(id)
                .map(ncmEstado -> {
                    ncmEstadoRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<NcmEstadoModel> buscarPorId(@PathVariable UUID id) {
        return ncmEstadoRepository.findById(id)
                .map(ncmEstado -> ResponseEntity.ok().body(ncmEstado))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/listar")
    public Iterable<NcmEstadoModel> listarNcmsEstados() {
        return ncmEstadoRepository.findAll();
    }
}
