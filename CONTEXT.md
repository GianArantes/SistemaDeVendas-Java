<!--
Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License.
-->

# CONTEXT

## Objetivo
Este arquivo orienta a IA na criação e manutenção do aplicativo de forma assistida. Ele descreve a arquitetura, os principais componentes, o fluxo de dados e as regras de implementação específicas do projeto.

## LICENCIAMENTO
O projeto é regido pela GNU GPLv3. Todo arquivo do projeto deve conter o cabeçalho de copyright no topo.

Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License.

Sempre atualize o arquivo `CONTEXT.md` quando uma solicitação alterar o dicionário de entidades, campos ou a estrutura de domínio.

## Visão geral do projeto
Projeto Java Spring Boot de backend para um sistema de vendas simples, com API REST e persistência em banco de dados MySQL.

### Tecnologias principais
- Java 21
- Spring Boot 4.0.3
- Spring Web MVC
- Spring Data JPA
- Hibernate
- MySQL Connector/J
- Spring Boot Validation
- Lombok
- JUnit 5 + Mockito para testes

## Estrutura de pacotes
- `br.com.sistema_de_vendas.controllers`
  - Controladores REST que expõem endpoints CRUD para entidades de negócio.
- `br.com.sistema_de_vendas.services`
  - Serviços responsáveis por validação de DTOs e regras de negócio leves.
- `br.com.sistema_de_vendas.DTOs`
  - Objetos de transferência de dados usados pelos endpoints.
- `br.com.sistema_de_vendas.models`
  - Entidades JPA que mapeiam as tabelas do banco.
- `br.com.sistema_de_vendas.repositories`
  - Interfaces Spring Data JPA para acesso a dados.
- `br.com.sistema_de_vendas.Exception`
  - Exceções customizadas usadas para comunicar erros de validação ou regra.

## Principais entidades e relações
- `ClienteModel`
  - Possui três endereços distintos: `enderecoRegistro`, `enderecoEntrega` e `enderecoCobranca`.
  - Usa `EnderecoModel` em relacionamentos `@OneToOne` com `cascade = CascadeType.ALL`.
- `UsuarioModel`
  - Usuários com status e role por enumeração.
- `ProdutoModel`
  - Produto com referências a `Ncm`, `ProdutoCategoria` e `ProdutoLitragem`.
- `TabelaPrecoModel` e `ProdutoTabelaPrecoModel`
  - Tabelas de preço e associações de preços por produto.
- `NcmModel` e `NcmEstadoModel`
  - Tabelas de NCM e alíquotas por estado.

## Dicionário de Entidades e Campos
- `ClienteModel`: `id`, `razaoSocial`, `nomeFantasia`, `cnpj`, `ie`, `dataFundacao`, `email`, `telefone`, `status`, `enderecoRegistro`, `enderecoEntrega`, `enderecoCobranca`.
- `EnderecoModel`: `id`, `logradouro`, `numero`, `complemento`, `bairro`, `cidade`, `estado`, `cep`.
- `UsuarioModel`: `id`, `nome`, `email`, `senha`, `role`, `status`.
- `ProdutoModel`: `id`, `refNf`, `nome`, `qtdPorEmbalagem`, `ipi`, `peso`, `ncm`, `categoria`, `litragem`.
- `TabelaPrecoModel`: `id`, `nomeTabela`, `dataCriacao`, `status`.
- `ProdutoTabelaPrecoModel`: `id`, `produto`, `tabelaPreco`, `preco`.
- `NcmModel`: `id`, `codigo`, `descricao`.
- `NcmEstadoModel`: `id`, `ncm`, `estado`, `aliquota`.
- `VendaModel`: `id`, `vendedorId`, `cliente`, `tabelaPreco`, `frete`, `transportadoraNome`, `transportadoraCnpj`, `transportadoraPlaca`, `itens`, `valorProdutos`, `valorIpi`, `valorSt`, `valorGeral`.
- `VendaItemModel`: `id`, `venda`, `produto`, `produtoTabelaPreco`, `quantidade`, `precoUnitario`, `valorBruto`, `descontoPercentual`, `valorLiquido`, `valorIpi`, `valorSt`, `custoUnitarioCliente`.

## 📦 Domínio de Vendas e Tributação
### Entidades de Venda
- `VendaModel`: registra vendedor, cliente, tabela de preço, frete, transportadora, itens e totais.
- `VendaItemModel`: armazena produto, preço referenciado em `ProdutoTabelaPrecoModel`, quantidade, descontos, valores de imposto e custo unitário.
- `TransportadoraDTO`: `nome`, `cnpj`, `placa`.

### Regras de Cálculo e Impostos
1. **Preço de Item:** obrigatoriamente buscar em `ProdutoTabelaPrecoModel` para o produto e tabela de preço selecionados.
2. **Desconto:** aplicar percentual no valor bruto antes de impostos.
3. **IPI:** calcular sobre o valor líquido após desconto.
4. **ST (Substituição Tributária):** buscar `NcmEstadoModel` por `NCM` do produto e UF do cliente; se não existir, lançar `BusinessException`.
5. **Custo Unitário Cliente:** `(valor líquido + IPI + ST) / qtdPorEmbalagem`.
6. **Frete:** aceitar `CIF`, `RETIRA`, `FOB`; se `FOB`, os dados da transportadora no DTO são obrigatórios.

### Contratos de Venda
- `VendaDTO`: `vendedorId`, `clienteCnpj`, `tabelaPrecoId`, `frete`, `transportadora`, `List<VendaItemDTO>`.
- `VendaItemDTO`: `produtoId`, `quantidade`, `descontoPercentual`.
- `VendaResponseDTO`: retorna totais e itens calculados.
- `TransportadoraDTO`: nome, cnpj, placa.

- `vendedorId` deve ser recebido pelo DTO hoje; mais tarde deve ser integrado a Spring Security via TODO.

## Assinatura de Métodos de Negócio (Service Contracts)
- Cada serviço deve expor um contrato claro em métodos públicos simples.
- Serviços de validação recebem DTOs e não retornam entidades: `void validate(EntidadeDTO dto)`.
- Serviços de domínio podem incluir métodos CRUD quando necessário, por exemplo: `ProdutoModel save(ProdutoDTO dto)`, `void delete(UUID id)`, `Optional<ProdutoModel> findById(UUID id)`.
- Serviços não devem expor implementações de repositório ou consultas complexas diretamente ao controller.
- Contratos devem ser descritos com comentários sucintos em cada método público do service.

## Padrão de Resposta de Erros (Error Handling)
- Usar `BusinessException` para erros de validação e regras de negócio.
- Capturar exceções no `GlobalExceptionHandler` e retornar payload JSON consistente.
- Padrão de resposta de erro deve conter ao menos:
  - `timestamp`
  - `status`
  - `error`
  - `message`
  - `path`
- Para erros de validação, retornar status `400 Bad Request` com mensagem clara sobre o campo inválido.
- Para recursos não encontrados, retornar `404 Not Found`.
- Para erros inesperados, retornar `500 Internal Server Error` com mensagem genérica e registrar detalhes internamente.

## Fluxo principal
1. Requisição HTTP chega ao `Controller` correspondente.
2. Controller aceita um `DTO` e chama o serviço específico para validação.
3. Serviço aplica regras de validação e lança `BusinessException` em caso de dados inválidos.
4. Controller converte o `DTO` em `Model` e salva via `Repository`.
5. Resposta HTTP é retornada ao cliente.

## Padrões usados
- Separação clara entre controllers, serviços e repositories.
- Validação de entrada centralizada em serviços específicos de cada domínio.
- Uso de DTOs para isolar o contrato de API da modelagem de persistência.
- Mapeamento manual DTO -> Model no controller para manter o domínio explícito.

## Regras de abordagem para a IA
- Priorizar a criação de serviços específicos por entidade em vez de um serviço genérico único.
- Garantir validações de campo no serviço antes de persistir qualquer entidade.
- Usar DTOs com `@Valid` e anotações de validação sempre que possível.
- Manter os controllers focados em orquestração: receber o DTO, validar, mapear e chamar o repository.
- Não misturar lógica de validação de negócio com persistência ou formatação de resposta.

## Pontos de atenção
- O banco é MySQL e as entidades JPA devem conter constraints adequadas (`nullable = false`, `unique`, etc.).
- As rotas seguem convenções REST simples: CRUD para cada recurso.
- Existe `EnderecoModel` compartilhado; não duplicar atributos de endereço em `ClienteModel`.
- Erros de validação devem ser comunicados via `BusinessException` e tratados globalmente quando possível.

## Estratégia para evolução do aplicativo
- Adicionar novos campos nos DTOs e modelos com validações correspondentes no serviço.
- Criar novos serviços para regras especificas de domínio, evitando a ampliação de classes monolíticas.
- Preferir `@OneToOne`/`@ManyToOne` em vez de strings planas para referências a entidades relacionadas.
- Manter o controlador enxuto e delegar lógica a camadas inferiores.

## Observações finais
- Não alterar a estrutura de pacotes existente sem necessidade.
- Ao modificar qualquer endpoint, atualizar também os testes unitários correspondentes.
- Se a solicitação alterar o dicionário de entidades, campos ou estrutura de domínio do CONTEXT, atualizar também o arquivo `CONTEXT.md`.
- Usar nomes em português nos comentários e mensagens de erro, mantendo consistência com o projeto.

## Infraestrutura e deploy
- O deploy deve ser preparado para containerização com Docker.
- Aplicação Java 21 deve empacotar um artefato executável (`jar`) com `mvn clean package`.
- O serviço deve expor a porta `8080` por padrão.
- O banco de dados de produção é MySQL; usar MySQL 8+ para compatibilidade.
- Parametrizar a configuração do banco via variáveis de ambiente, não fixar valores no código.
- Variáveis de ambiente importantes:
  - `SPRING_DATASOURCE_URL`
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
  - `SPRING_JPA_HIBERNATE_DDL_AUTO`
  - `SPRING_PROFILES_ACTIVE`
  - `SERVER_PORT`
- Para Docker, usar `application.properties` como template local e permitir sobrescrita via `SPRING_*`.
- A imagem do container deve ser baseada em um JDK leve compatível com Java 21.
- Usar um `Dockerfile` multi-stage para build e runtime: primeiro stage com Maven para construir o jar, segundo stage com runtime apenas para executar o jar.
- Usar `ENTRYPOINT ["java", "-jar", "/app/app.jar"]` ou equivalente no container final.
- A aplicação deve suportar logs no console para capturar saída de container.
- Se usar Docker Compose, definir:
  - serviço `app` para a aplicação
  - serviço `db` para MySQL
  - rede interna para comunicação entre os serviços
  - volume persistente para os dados do MySQL
- Exemplo de parâmetros de banco em Docker Compose:
  - `MYSQL_ROOT_PASSWORD`
  - `MYSQL_DATABASE`
  - `MYSQL_USER`
  - `MYSQL_PASSWORD`
- Manter a configuração de rede e volumes separadas da imagem do aplicativo.
- O container deve ser capaz de reiniciar usando apenas variáveis e volume existente.
