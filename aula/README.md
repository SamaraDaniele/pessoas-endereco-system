# Lista de Exercícios - Bimestre 1
## Estrutura de Dados 2
**Prof. Nelson**

---

## 📋 Descrição do Projeto
Sistema de gerenciamento de pessoas e endereços, desenvolvido em Java com persistência em banco de dados.

## 📁 Estrutura do Projeto

```
aula/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/aula/
│       │       ├── AulaApplication.java          # Classe principal da aplicação
│       │       ├── ExemploLista.java             # Exemplo com lista de exercícios
│       │       ├── connection/
│       │       │   └── DatabaseConnection.java   # Conexão com banco de dados
│       │       ├── dao/
│       │       │   ├── PessoaDAO.java           # Data Access Object - Pessoa
│       │       │   └── EnderecoDAO.java         # Data Access Object - Endereço
│       │       └── model/
│       │           ├── Pessoa.java              # Modelo de dados - Pessoa
│       │           └── Endereco.java            # Modelo de dados - Endereço
│       └── resources/
│           ├── application.properties            # Configurações da aplicação
│           ├── static/                          # Arquivos estáticos
│           └── templates/                       # Templates HTML
├── pom.xml                                       # Configuração Maven
├── compilar.bat                                  # Script de compilação
└── final_setup.sql                              # Script SQL inicial

```

## 🎯 Funcionalidades

### Gerenciamento de Endereços
- ✅ Inserir novo endereço
- ✅ Editar endereço
- ✅ Excluir endereço
- ✅ Buscar endereço por ID
- ✅ Buscar endereço por logradouro
- ✅ Listar todos os endereços
- ✅ Listar endereços por estado
- ✅ Listar endereços por cidade

### Gerenciamento de Pessoas
- ✅ Inserir nova pessoa
- ✅ Editar pessoa
- ✅ Excluir pessoa
- ✅ Buscar pessoa por ID
- ✅ Buscar pessoa por nome
- ✅ Listar todas as pessoas
- ✅ Listar pessoas por data de nascimento

### Regras de Negócio
- ✅ Validações de dados
- ✅ Integração pessoa-endereço

## 🛠️ Tecnologias Utilizadas
- **Java 11+**
- **Spring Boot**
- **Maven**
- **Banco de Dados Relacional**

## 📝 Como Compilar

```bash
./compilar.bat
```

Ou manualmente:
```bash
mvn clean compile
```

## ▶️ Como Executar

```bash
mvn spring-boot:run
```

## 📊 Diagrama de Classes

```
Pessoa
├── id
├── nome
├── dataNascimento
└── endereco: Endereco

Endereco
├── id
├── logradouro
├── numero
├── cidade
└── estado
```

## 📌 Notas Importantes
- Todos os dados são persistidos em banco de dados
- O sistema valida entradas de usuário
- Menu interativo em linha de comando

---

**Autor:** [Seu Nome]  
**Data:** Abril de 2026  
**Disciplina:** Estrutura de Dados 2  
**Professor:** Nelson
