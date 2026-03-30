package br.com.sistema_de_vendas.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import br.com.sistema_de_vendas.DTOs.ClienteDTO;
import br.com.sistema_de_vendas.DTOs.EnderecoDTO;
import br.com.sistema_de_vendas.Exception.BusinessException;
import br.com.sistema_de_vendas.models.ClienteModel;
import br.com.sistema_de_vendas.models.Enum.ClienteStatus;
import br.com.sistema_de_vendas.repositories.ClienteRepository;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Spy
    private br.com.sistema_de_vendas.services.ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private ClienteDTO clienteDTO;

    @BeforeEach
    void setup() {
        EnderecoDTO endereco = new EnderecoDTO(
                "Rua Teste",
                "100",
                "Apto 101",
                "12345678",
                "Centro",
                "São Paulo",
                "SP");

        clienteDTO = new ClienteDTO(
                "Empresa Exemplo",
                "2010-05-20",
                "Exemplo Ltda",
                "12345678000199",
                "123456789",
                endereco,
                endereco,
                endereco,
                "contato@exemplo.com",
                "(11) 99999-9999",
                ClienteStatus.ATIVO.name());
    }

    @Test
    void cadastrarCliente_sucesso_deveRetornarCriado() {
        when(clienteRepository.existsByCnpj(clienteDTO.cnpj())).thenReturn(false);
        when(clienteRepository.save(any(ClienteModel.class))).thenAnswer(invocation -> {
            ClienteModel saved = invocation.getArgument(0);
            saved.setId(UUID.randomUUID());
            return saved;
        });

        ResponseEntity<ClienteModel> response = clienteController.cadastrarCliente(clienteDTO);

        assertEquals(201, response.getStatusCode().value(), "Deve retornar status 201 para cliente criado");
        assertNotNull(response.getBody(), "O corpo da resposta não deve ser nulo");
        assertNotNull(response.getBody().getId(), "O cliente salvo deve receber um id");
        assertEquals("Empresa Exemplo", response.getBody().getRazaoSocial());
    }

    @Test
    void cadastrarCliente_comCnpjVazio_deveLancarBusinessException() {
        EnderecoDTO endereco = new EnderecoDTO(
                "Rua Teste",
                "100",
                "Apto 101",
                "12345678",
                "Centro",
                "São Paulo",
                "SP");

        ClienteDTO invalid = new ClienteDTO(
                "Empresa Exemplo",
                "2010-05-20",
                "Exemplo Ltda",
                "",
                "123456789",
                endereco,
                endereco,
                endereco,
                "contato@exemplo.com",
                "(11) 99999-9999",
                ClienteStatus.ATIVO.name());

        assertThrows(BusinessException.class, () -> clienteController.cadastrarCliente(invalid));
    }

    @Test
    void buscarPorId_quandoExiste_deveRetornarOk() {
        UUID id = UUID.randomUUID();
        ClienteModel cliente = new ClienteModel();
        cliente.setId(id);
        cliente.setRazaoSocial("Empresa Exemplo");
        cliente.setStatus(ClienteStatus.ATIVO);
        cliente.setCnpj(clienteDTO.cnpj());
        cliente.setDataFundacao(LocalDate.parse(clienteDTO.dataFundacao()));

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        ResponseEntity<ClienteModel> response = clienteController.buscarPorId(id);

        assertEquals(200, response.getStatusCode().value(), "Deve retornar status 200 quando o cliente existe");
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void deletarCliente_quandoExiste_deveRetornarNoContent() {
        UUID id = UUID.randomUUID();
        ClienteModel cliente = new ClienteModel();
        cliente.setId(id);

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        ResponseEntity<Void> response = clienteController.deletarCliente(id);

        verify(clienteRepository).deleteById(id);
        assertEquals(204, response.getStatusCode().value(), "O retorno deve ser 204 No Content");
    }

    @Test
    void listarClientes_deveRetornarLista() {
        when(clienteRepository.findAll()).thenReturn(List.of(new ClienteModel()));

        Iterable<ClienteModel> clientes = clienteController.listarClientes();

        assertNotNull(clientes, "A lista de clientes não deve ser nula");
    }
}
