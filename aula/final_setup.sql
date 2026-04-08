CREATE TABLE endereco (
    id SERIAL PRIMARY KEY,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    pais VARCHAR(100) NOT NULL DEFAULT 'Brasil',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_endereco_cidade ON endereco(cidade);
CREATE INDEX idx_endereco_estado ON endereco(estado);
CREATE INDEX idx_endereco_logradouro ON endereco(logradouro);

CREATE TABLE pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    sobrenome VARCHAR(150) NOT NULL,
    data_nascimento DATE NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    endereco_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (endereco_id) REFERENCES endereco(id) ON DELETE SET NULL
);

CREATE INDEX idx_pessoa_endereco_id ON pessoa(endereco_id);
CREATE INDEX idx_pessoa_cpf ON pessoa(cpf);
CREATE INDEX idx_pessoa_nome ON pessoa(nome);
CREATE INDEX idx_pessoa_data_nascimento ON pessoa(data_nascimento);

INSERT INTO endereco (logradouro, numero, cidade, estado, pais) VALUES
('Avenida Paulista', '1000', 'São Paulo', 'SP', 'Brasil'),
('Rua das Flores', '123', 'São Paulo', 'SP', 'Brasil'),
('Avenida Brasil', '500', 'Rio de Janeiro', 'RJ', 'Brasil');

INSERT INTO pessoa (nome, sobrenome, data_nascimento, cpf, endereco_id) VALUES
('João', 'Silva', '1990-05-15', '12345678901', 1),
('Maria', 'Santos', '1992-03-22', '98765432101', 1),
('Pedro', 'Oliveira', '1988-07-30', '55544433322', 2),
('Ana', 'Costa', '1995-11-10', '11122233344', 2),
('Carlos', 'Souza', '1985-01-05', '99988877766', 3);
