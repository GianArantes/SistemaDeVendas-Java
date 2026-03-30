package br.com.sistema_de_vendas.controllers;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistema_de_vendas.DTOs.TransportadoraDTO;
import br.com.sistema_de_vendas.DTOs.VendaDTO;
import br.com.sistema_de_vendas.DTOs.VendaItemResponseDTO;
import br.com.sistema_de_vendas.DTOs.VendaResponseDTO;
import br.com.sistema_de_vendas.models.VendaItemModel;
import br.com.sistema_de_vendas.models.VendaModel;
import br.com.sistema_de_vendas.services.VendaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/vendas")
@AllArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    /**
     * Cria uma nova venda e retorna os totais calculados.
     */
    @PostMapping
    public ResponseEntity<VendaResponseDTO> criarVenda(@Valid @RequestBody VendaDTO vendaDTO) {
        VendaModel venda = vendaService.criarVenda(vendaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(venda));
    }

    /**
     * Recupera uma venda pelo seu identificador.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscarVenda(@PathVariable UUID id) {
        VendaModel venda = vendaService.buscarVenda(id);
        return ResponseEntity.ok(toResponse(venda));
    }

    private VendaResponseDTO toResponse(VendaModel venda) {
        TransportadoraDTO transportadora = null;
        if (venda.getTransportadoraNome() != null) {
            transportadora = new TransportadoraDTO(
                venda.getTransportadoraNome(),
                venda.getTransportadoraCnpj(),
                venda.getTransportadoraPlaca()
            );
        }

        return new VendaResponseDTO(
            venda.getId(),
            venda.getVendedorId(),
            venda.getCliente().getCnpj(),
            venda.getTabelaPreco().getId(),
            venda.getFrete(),
            transportadora,
            venda.getValorProdutos(),
            venda.getValorIpi(),
            venda.getValorSt(),
            venda.getValorGeral(),
            venda.getItens().stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList())
        );
    }

    private VendaItemResponseDTO toItemResponse(VendaItemModel item) {
        return new VendaItemResponseDTO(
            item.getProduto().getId(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            item.getValorBruto(),
            item.getDescontoPercentual(),
            item.getValorLiquido(),
            item.getValorIpi(),
            item.getValorSt(),
            item.getCustoUnitarioCliente()
        );
    }
}
