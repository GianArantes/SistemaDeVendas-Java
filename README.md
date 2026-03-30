# Sistema de Vendas

Projeto backend em Java com Spring Boot para um sistema de vendas simples.

## Visão geral

Este sistema oferece endpoints REST para gerenciamento de:

- Clientes
- Usuários
- Produtos
- Categorias de produto
- Litragem de produto
- NCM e alíquotas de estado
- Tabelas de preço e associação de preço por produto

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Data JPA
- Spring MVC
- Bean Validation (Jakarta Validation)
- MySQL Connector (para conexão com banco de dados)
- JUnit 5 e Mockito para testes unitários

## Estrutura do projeto

- `src/main/java` - código de produção
- `src/test/java` - testes unitários
- `src/main/resources/application.properties` - configuração da aplicação

## Executando o projeto

1. Ajuste as configurações do banco de dados em `src/main/resources/application.properties`.
2. Rode o comando:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

## Executando testes

Para rodar todos os testes unitários:

```bash
./mvnw test
```

No Windows:

```powershell
.\mvnw.cmd test
```

## Observações

- Os comentários dos métodos foram adicionados para facilitar entendimento e manutenção.
- Os testes unitários cobrem cenários de criação, busca e tratamento de exceções.
