/**
 * Modelo: Endereco
 * Representa um endereço no sistema que pode ser compartilhado por várias pessoas.
 * Armazena informações geográficas completas.
 */
package com.example.aula.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Endereco {
    
    private int id;
    private String logradouro;
    private String numero;
    private String cidade;
    private String estado;
    private String pais;
    private LocalDateTime createdAt;
    private List<Pessoa> pessoas;
    
    public Endereco() {
        this.pessoas = new ArrayList<>();
    }
    
    public Endereco(String logradouro, String numero, String cidade, String estado, String pais) {
        this();
        this.logradouro = logradouro;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }
    
    public Endereco(int id, String logradouro, String numero, String cidade, String estado, String pais, LocalDateTime createdAt) {
        this();
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.createdAt = createdAt;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getLogradouro() {
        return logradouro;
    }
    
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getPais() {
        return pais;
    }
    
    public void setPais(String pais) {
        this.pais = pais;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<Pessoa> getPessoas() {
        return pessoas;
    }
    
    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
    
    public void adicionarPessoa(Pessoa pessoa) {
        if (!this.pessoas.contains(pessoa)) {
            this.pessoas.add(pessoa);
        }
    }
    
    public void removerPessoa(Pessoa pessoa) {
        this.pessoas.remove(pessoa);
    }
    
    public String getEnderecoCompleto() {
        return logradouro + ", " + numero + " - " + cidade + "/" + estado + " - " + pais;
    }
    
    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", pais='" + pais + '\'' +
                ", pessoas=" + pessoas.size() +
                '}';
    }
}
