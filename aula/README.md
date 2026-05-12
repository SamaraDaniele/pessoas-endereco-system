# Pessoas e Endereço System

Sistema em Java para gerenciamento de pessoas e endereços com banco de dados PostgreSQL. Possui operações CRUD completas, validações de dados e arquitetura em camadas utilizando DAO Pattern.

## Tecnologias

* Java 17
* Maven
* PostgreSQL
* JDBC

## Funcionalidades

* Cadastro, edição, busca e exclusão de pessoas
* Cadastro, edição, busca e exclusão de endereços
* Relacionamento entre pessoa e endereço
* Validação de dados
* Proteção contra SQL Injection

## Estrutura do Projeto

* `model/` → entidades Pessoa e Endereco
* `dao/` → acesso aos dados
* `connection/` → conexão com banco
* `ExemploLista.java` → menu principal

## Como Executar

1. Criar o banco PostgreSQL e as tabelas.
2. Configurar usuário e senha no arquivo `DatabaseConnection.java`.
3. Compilar o projeto:

```bash
mvn clean compile
```

4. Executar:

```bash
mvn exec:java -Dexec.mainClass="com.example.aula.ExemploLista"
```

## Segurança

* Uso de Prepared Statements
* Validação de entrada de dados
* Tratamento de exceções

## Autor

**Samara Daniele**
GitHub: [@SamaraDaniele](https://github.com/SamaraDaniele?utm_source=chatgpt.com)
