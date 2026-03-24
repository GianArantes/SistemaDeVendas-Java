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

import br.com.sistema_de_vendas.DTOs.UsuarioDTO;
import br.com.sistema_de_vendas.Exception.BusinessException;
import br.com.sistema_de_vendas.models.UsuarioModel;
import br.com.sistema_de_vendas.models.Enum.UsuarioRole;
import br.com.sistema_de_vendas.models.Enum.UsuarioStatus;
import br.com.sistema_de_vendas.repositories.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<UsuarioModel> cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuario) {
        // Verificar se o email já existe
        if (usuarioRepository.existsByEmail(usuario.email())) {
            throw new BusinessException("Email já cadastrado");
        }
        UsuarioModel usuarioNovo = new UsuarioModel();
        usuarioNovo.setNomeCompleto(usuario.nomeCompleto());
        usuarioNovo.setApelido(usuario.apelido());
        usuarioNovo.setEmail(usuario.email());
        usuarioNovo.setSenha(usuario.senha());
        usuarioNovo.setTelefone(usuario.telefone());
        usuarioNovo.setRole(UsuarioRole.valueOf(usuario.role().toUpperCase()));
        usuarioNovo.setStatus(UsuarioStatus.valueOf(usuario.status().toUpperCase())); // Definir status como ATIVO por padrão
        UsuarioModel salvo = usuarioRepository.save(usuarioNovo);
        return ResponseEntity.status(201).body(salvo);
    }

    @GetMapping("/listar")
    public Iterable<UsuarioModel> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> buscarPorId(@PathVariable UUID id) {
        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok().body(usuario))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> atualizarUsuario(@Valid @PathVariable UUID id, @RequestBody UsuarioDTO usuarioDto) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setNomeCompleto(usuarioDto.nomeCompleto());
                    usuarioExistente.setApelido(usuarioDto.apelido());
                    usuarioExistente.setEmail(usuarioDto.email());
                    usuarioExistente.setSenha(usuarioDto.senha());
                    usuarioExistente.setTelefone(usuarioDto.telefone());
                    usuarioExistente.setRole(UsuarioRole.valueOf(usuarioDto.role().toUpperCase()));
                    usuarioExistente.setStatus(UsuarioStatus.valueOf(usuarioDto.status().toUpperCase()));
                    UsuarioModel atualizado = usuarioRepository.save(usuarioExistente);
                    return ResponseEntity.ok().body(atualizado);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuarioRepository.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
