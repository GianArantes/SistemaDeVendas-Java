package br.com.sistema_de_vendas.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sistema_de_vendas.DTOs.TransportadoraDTO;
import lombok.AllArgsConstructor;
import br.com.sistema_de_vendas.DTOs.VendaDTO;
import br.com.sistema_de_vendas.DTOs.VendaItemDTO;
import br.com.sistema_de_vendas.Exception.BusinessException;
import br.com.sistema_de_vendas.models.ClienteModel;
import br.com.sistema_de_vendas.models.NcmEstadoModel;
import br.com.sistema_de_vendas.models.ProdutoModel;
import br.com.sistema_de_vendas.models.ProdutoTabelaPrecoModel;
import br.com.sistema_de_vendas.models.VendaItemModel;
import br.com.sistema_de_vendas.models.VendaModel;
import br.com.sistema_de_vendas.repositories.ClienteRepository;
import br.com.sistema_de_vendas.repositories.NcmEstadoRepository;
import br.com.sistema_de_vendas.repositories.ProdutoRepository;
import br.com.sistema_de_vendas.repositories.ProdutoTabelaPrecoRepository;
import br.com.sistema_de_vendas.repositories.TabelaPrecoRepository;
import br.com.sistema_de_vendas.repositories.VendaRepository;

import static br.com.sistema_de_vendas.services.ValidationUtils.*;

@Service
@AllArgsConstructor
public class VendaService {

    private final ClienteRepository clienteRepository;
    private final TabelaPrecoRepository tabelaPrecoRepository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoTabelaPrecoRepository produtoTabelaPrecoRepository;
    private final NcmEstadoRepository ncmEstadoRepository;
    private final VendaRepository vendaRepository;

    /**
     * Cria uma nova venda e calcula impostos a partir dos itens.
     */
    @Transactional
    public VendaModel criarVenda(VendaDTO dto) {
        assertNotBlank(dto.clienteCnpj(), "clienteCnpj");
        // TODO: integrar vendedorId com Spring Security futuramente
        if (dto.vendedorId() == null) {
            throw new BusinessException("VENDEDOR_ID_REQUIRED");
        }
        if (dto.tabelaPrecoId() == null) {
            throw new BusinessException("TABELA_PRECO_ID_REQUIRED");
        }
        assertNotBlank(dto.frete(), "frete");
        if (dto.itens() == null || dto.itens().isEmpty()) {
            throw new BusinessException("VENDA_MUST_HAVE_ITEMS");
        }

        if (dto.frete().equalsIgnoreCase("FOB")) {
            TransportadoraDTO transportadora = dto.transportadora();
            if (transportadora == null) {
                throw new BusinessException("TRANSPORTADORA_REQUIRED_FOR_FOB");
            }
            assertNotBlank(transportadora.nome(), "transportadora.nome");
            assertNotBlank(transportadora.cnpj(), "transportadora.cnpj");
            assertNotBlank(transportadora.placa(), "transportadora.placa");
        }

        ClienteModel cliente = clienteRepository.findByCnpj(dto.clienteCnpj())
                .orElseThrow(() -> new BusinessException("CLIENT_NOT_FOUND"));

        var tabelaPreco = tabelaPrecoRepository.findById(dto.tabelaPrecoId())
                .orElseThrow(() -> new BusinessException("TABELA_PRECO_NOT_FOUND"));

        VendaModel venda = new VendaModel();
        venda.setVendedorId(dto.vendedorId());
        venda.setCliente(cliente);
        venda.setTabelaPreco(tabelaPreco);
        venda.setFrete(dto.frete());

        if (dto.transportadora() != null) {
            venda.setTransportadoraNome(dto.transportadora().nome());
            venda.setTransportadoraCnpj(dto.transportadora().cnpj());
            venda.setTransportadoraPlaca(dto.transportadora().placa());
        }

        double somaProdutos = 0d;
        double somaIpi = 0d;
        double somaSt = 0d;

        for (VendaItemDTO itemDto : dto.itens()) {
            if (itemDto.produtoId() == null) {
                throw new BusinessException("PRODUCT_ID_REQUIRED");
            }
            if (itemDto.quantidade() == null || itemDto.quantidade() <= 0) {
                throw new BusinessException("INVALID_QUANTITY");
            }
            if (itemDto.descontoPercentual() == null || itemDto.descontoPercentual() < 0 || itemDto.descontoPercentual() > 100) {
                throw new BusinessException("INVALID_DISCOUNT_PERCENTAGE");
            }

            ProdutoModel produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new BusinessException("PRODUCT_NOT_FOUND"));

            ProdutoTabelaPrecoModel precoModel = produtoTabelaPrecoRepository
                    .findByTabelaPrecoIdAndProdutoId(dto.tabelaPrecoId(), produto.getId())
                    .orElseThrow(() -> new BusinessException("PRODUCT_PRICE_NOT_FOUND"));

            NcmEstadoModel ncmEstado = ncmEstadoRepository
                    .findByNcmCodigoAndEstadoIgnoreCase(produto.getNcm().getCodigo(), cliente.getEnderecoEntrega().getEstado())
                    .orElseThrow(() -> new BusinessException("ST_RATE_NOT_FOUND"));

            double quantidade = itemDto.quantidade();
            double precoUnitario = precoModel.getPreco();
            double valorBruto = precoUnitario * quantidade;
            double desconto = itemDto.descontoPercentual() / 100d;
            double valorLiquido = valorBruto * (1d - desconto);
            double ipi = valorLiquido * (produto.getIpi() / 100d);
            double st = valorLiquido * (ncmEstado.getAliquota().doubleValue() / 100d);

            if (produto.getQtdPorEmbalagem() == null || produto.getQtdPorEmbalagem() == 0) {
                throw new BusinessException("INVALID_PRODUCT_PACKAGING_QUANTITY");
            }
            double totalComImpostos = valorLiquido + ipi + st;
            double custoUnitarioCliente = totalComImpostos / produto.getQtdPorEmbalagem();

            VendaItemModel item = new VendaItemModel();
            item.setVenda(venda);
            item.setProduto(produto);
            item.setProdutoTabelaPreco(precoModel);
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(precoUnitario);
            item.setValorBruto(valorBruto);
            item.setDescontoPercentual(itemDto.descontoPercentual());
            item.setValorLiquido(valorLiquido);
            item.setValorIpi(ipi);
            item.setValorSt(st);
            item.setCustoUnitarioCliente(custoUnitarioCliente);

            venda.getItens().add(item);
            somaProdutos += valorLiquido;
            somaIpi += ipi;
            somaSt += st;
        }

        venda.setValorProdutos(somaProdutos);
        venda.setValorIpi(somaIpi);
        venda.setValorSt(somaSt);
        venda.setValorGeral(somaProdutos + somaIpi + somaSt);

        return vendaRepository.save(venda);
    }

    /**
     * Busca uma venda existente pelo id.
     */
    @Transactional(readOnly = true)
    public VendaModel buscarVenda(UUID vendaId) {
        if (vendaId == null) {
            throw new BusinessException("VENDA_ID_REQUIRED");
        }
        return vendaRepository.findById(vendaId)
                .orElseThrow(() -> new BusinessException("SALE_NOT_FOUND"));
    }
}
