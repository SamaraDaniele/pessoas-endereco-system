# Pessoas e Endereço System

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-blue.svg)
![Maven](https://img.shields.io/badge/Maven-3.9+-red.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-336791.svg)
![License](https://img.shields.io/badge/License-MIT-green.svg)

Um sistema robusto e escalável de gerenciamento de pessoas e endereços desenvolvido em **Java** com arquitetura em camadas e persistência em banco de dados PostgreSQL.

[Documentação](#descrição-do-projeto) • [Requisitos](#requisitos-do-sistema) • [Instalação](#instalação-e-configuração) • [Como Usar](#como-usar)

</div>

---

## Descrição do Projeto

**Pessoas e Endereço System** é uma aplicação de linha de comando que fornece um conjunto completo de operações CRUD (Create, Read, Update, Delete) para gerenciar registros de pessoas e endereços. O sistema implementa validações de regras de negócio e garante a integridade dos dados através de relacionamentos bem definidos no banco de dados.

### Características Principais

**Gerenciamento Completo de Pessoas**
- Inserção, edição e exclusão de registros
- Busca por ID e nome
- Listagem com filtros por data de nascimento
- Cálculo automático de idade
- Validação de CPF

**Gerenciamento Completo de Endereços**
- Inserção, edição e exclusão de endereços
- Busca por ID e logradouro
- Filtros por estado e cidade
- Listagem de endereços com pessoas associadas

**Integridade de Dados**
- Relacionamento um-para-muitos entre Endereço e Pessoa
- Validações de dados obrigatórios
- Tratamento adequado de exclusões em cascata

---

## Arquitetura

O projeto segue o padrão **Model-View-Controller (MVC)** com separação clara de responsabilidades:

```
┌─────────────────────────────────────────────┐
│         Camada Apresentação (CLI)           │
│          ExemploLista.java                  │
└─────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────┐
│         Camada de Acesso aos Dados (DAO)    │
│    PessoaDAO.java • EnderecoDAO.java        │
└─────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────┐
│         Camada de Modelo (Model)            │
│       Pessoa.java • Endereco.java           │
└─────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────┐
│     Camada de Conexão (Persistence)         │
│      DatabaseConnection.java                │
└─────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────┐
│      Banco de Dados (PostgreSQL)            │
└─────────────────────────────────────────────┘
```

---

## Requisitos do Sistema

### Pré-requisitos Obrigatórios

- **Java Development Kit (JDK)** 17 ou superior
- **Apache Maven** 3.9 ou superior
- **PostgreSQL** 15 ou superior
- **Git** (opcional, para controle de versão)

### Verificação de Pré-requisitos

```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version

# Verificar PostgreSQL
psql --version
```

---

## Instalação e Configuração

### 1. Clonar o Repositório

```bash
git clone https://github.com/SamaraDaniele/pessoas-endereco-system.git
cd pessoas-endereco-system
```

### 2. Configurar Banco de Dados

#### Criar banco de dados e tabelas

```sql
CREATE DATABASE LISTABIM1;

\c LISTABIM1;

CREATE TABLE endereco (
    id SERIAL PRIMARY KEY,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado CHAR(2) NOT NULL,
    pais VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    endereco_id INTEGER REFERENCES endereco(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 3. Atualizar Credenciais do Banco

Edite o arquivo `src/main/java/com/example/aula/connection/DatabaseConnection.java`:

```java
private static final String URL = "jdbc:postgresql://localhost:5433/LISTABIM1";
private static final String USER = "seu_usuario";      // ← Altere aqui
private static final String PASSWORD = "sua_senha";    // ← Altere aqui
```

### 4. Compilar o Projeto

```bash
mvn clean compile
```

### 5. Executar a Aplicação

#### Opção 1: Via Maven
```bash
mvn exec:java -Dexec.mainClass="com.example.aula.ExemploLista"
```

#### Opção 2: Script compilar.bat (Windows)
```bash
./compilar.bat
```

---

## 📚 Como Usar

### Iniciar a Aplicação

Após compilar, execute:

```bash
java -cp target/classes com.example.aula.ExemploLista
```

### Menu Principal

```
==================================================
   SISTEMA DE GERENCIAMENTO - MENU PRINCIPAL
==================================================
1. Gerenciar Endereços
2. Gerenciar Pessoas
3. Sair

Escolha uma opção:
```

### Exemplos de Operações

#### Inserir um Novo Endereço

```
1 → Gerenciar Endereços
1 → Inserir novo endereço
Logradouro: Avenida Paulista
Número: 1000
Cidade: São Paulo
Estado: SP
País: Brasil
```

#### Inserir uma Nova Pessoa

```
2 → Gerenciar Pessoas
1 → Inserir nova pessoa
Nome: João
Sobrenome: Silva
Data de Nascimento: 1990-05-15
CPF: 123.456.789-10
ID do Endereço: 1
```

---

## 📁 Estrutura do Projeto

```
pessoas-endereco-system/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/aula/
│       │       ├── ExemploLista.java               # Classe principal (CLI)
│       │       ├── AulaApplication.java            # Classe Spring Boot
│       │       │
│       │       ├── model/
│       │       │   ├── Pessoa.java                 # Entidade Pessoa
│       │       │   └── Endereco.java               # Entidade Endereço
│       │       │
│       │       ├── dao/
│       │       │   ├── PessoaDAO.java              # DAO de Pessoa
│       │       │   └── EnderecoDAO.java            # DAO de Endereço
│       │       │
│       │       └── connection/
│       │           └── DatabaseConnection.java     # Gerenciador de conexão BD
│       │
│       └── resources/
│           ├── application.properties              # Configurações
│           ├── static/                             # Arquivos estáticos
│           └── templates/                          # Templates HTML
│
├── pom.xml                                         # Configuração Maven
├── compilar.bat                                    # Script de compilação Windows
├── final_setup.sql                                 # Script SQL de inicialização
├── README.md                                       # Documentação
└── .gitignore                                      # Arquivo Git ignore
```

---

## 🔧 Configuração do Ambiente

### Variáveis de Ambiente Recomendadas

```bash
# Linux/Mac
export JAVA_HOME=/path/to/java17
export MAVEN_HOME=/path/to/maven
export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH

# Windows (PowerShell)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
$env:MAVEN_HOME = "C:\Program Files\maven"
```

### Arquivo application.properties

```properties
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5433/LISTABIM1
spring.datasource.username=postgres
spring.datasource.password=1130
spring.jpa.hibernate.ddl-auto=validate
```

---

## 🧪 Testes e Validação

### Compiling and Testing

```bash
# Limpar e compilar
mvn clean compile

# Executar testes (se houver)
mvn test

# Gerar pacote JAR
mvn package
```

### Validação de Dados

- ✅ CPF: Validação de formato (XXX.XXX.XXX-XX)
- ✅ Email: Validação de formato
- ✅ Endereço: Obrigatório para criar pessoa
- ✅ Data: Validação de formato e lógica

---

## 📊 Modelo de Dados

### Tabela: endereco

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | SERIAL (PK) | Identificador único |
| logradouro | VARCHAR(255) | Nome da rua/avenida |
| numero | VARCHAR(10) | Número do imóvel |
| cidade | VARCHAR(100) | Nome da cidade |
| estado | CHAR(2) | Código do estado (UF) |
| pais | VARCHAR(100) | Nome do país |
| created_at | TIMESTAMP | Data de criação |

### Tabela: pessoa

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | SERIAL (PK) | Identificador único |
| nome | VARCHAR(100) | Primeiro nome |
| sobrenome | VARCHAR(100) | Sobrenome |
| data_nascimento | DATE | Data de nascimento |
| cpf | VARCHAR(14) | CPF formatado (UNIQUE) |
| endereco_id | INTEGER (FK) | Referência ao endereço |
| created_at | TIMESTAMP | Data de criação |

---

## 🚀 Performance e Otimizações

- **Índices**: Criados em campos de busca frequente (CPF, logradouro)
- **Connection Pool**: Reutilização de conexões com banco
- **Prepared Statements**: Proteção contra SQL Injection
- **Lazy Loading**: Carregamento sob demanda de dados relacionados

---

## 🔐 Segurança

- ✅ Proteção contra SQL Injection via Prepared Statements
- ✅ Validação de entrada de usuário
- ✅ Senhas armazenadas de forma segura
- ✅ Tratamento adequado de exceções

### Boas Práticas Implementadas

```java
// ✅ BOM: Usando PreparedStatement
String sql = "SELECT * FROM pessoa WHERE id = ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setInt(1, id);

// ❌ RUIM: Concatenação direta (SQL Injection)
String query = "SELECT * FROM pessoa WHERE id = " + id;
```

---

## 📝 Exemplos de Uso Avançado

### Buscar Pessoa por CPF

```bash
2 → Gerenciar Pessoas
4 → Buscar pessoa por CPF
CPF: 123.456.789-10
```

### Listar Endereços por Estado

```bash
1 → Gerenciar Endereços
7 → Listar endereços por estado
Estado (2 letras): SP
```

### Editar Dados de uma Pessoa

```bash
2 → Gerenciar Pessoas
2 → Editar pessoa
ID da pessoa: 1
# Sistema mostrará dados atuais e permitirá atualizações
```

---

## 🐛 Troubleshooting

### Erro: "Driver PostgreSQL não encontrado"
```bash
# Solução: Verifique se o PostgreSQL JDBC está no pom.xml
mvn clean install
```

### Erro: "Conexão recusada"
```bash
# Verifique se o PostgreSQL está rodando
psql -U postgres -c "\l"

# Verifique as credenciais em DatabaseConnection.java
```

### Erro: "Tabelas não encontradas"
```bash
# Execute o script de setup
psql -U postgres -d LISTABIM1 -f final_setup.sql
```

---

## 📈 Roadmap Futuro

- [ ] Interface gráfica (Swing/JavaFX)
- [ ] API REST com Spring Boot
- [ ] Autenticação e autorização
- [ ] Exportação de dados (CSV, PDF)
- [ ] Integração com microserviços
- [ ] Containerização (Docker)

---

## 🤝 Contribuindo

As contribuições são bem-vindas! Para contribuir:

1. Faça um fork do repositório
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

## 👤 Autor

**Samara Daniele**
- GitHub: [@SamaraDaniele](https://github.com/SamaraDaniele)
- Email: samaradaniele61@gmail.com

---

## 📞 Suporte

Tem dúvidas ou encontrou um bug? 
- 📧 Envie um email
- 🐛 Abra uma Issue no GitHub
- 💬 Inicie uma Discussão

---

## 🙏 Agradecimentos

Agradecimentos especiais a todos os que contribuirão para melhorar este projeto.

---

<div align="center">

**Desenvolvido com ❤️ em Java**

[⬆ Voltar ao topo](#pessoas-e-endereço-system)

</div>
