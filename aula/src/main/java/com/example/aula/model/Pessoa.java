/**
 * Entidade: Pessoa
 * 
 * Representa um indivíduo no sistema com dados pessoais, contato e localização.
 * Possui validações e cálculo automático de idade baseado na data de nascimento.
 * Pode estar associada a um endereço único.
 */
package com.example.aula.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Pessoa {
    
    private int id;
    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;
    private String cpf;
    private Integer enderecoId;
    private LocalDateTime createdAt;
    private Endereco endereco;
    
    public Pessoa() {
    }
    
    public Pessoa(String nome, String sobrenome, LocalDate dataNascimento, String cpf, Integer enderecoId) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.enderecoId = enderecoId;
    }
    
    public Pessoa(int id, String nome, String sobrenome, LocalDate dataNascimento, String cpf, Integer enderecoId, LocalDateTime createdAt) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.enderecoId = enderecoId;
        this.createdAt = createdAt;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getSobrenome() {
        return sobrenome;
    }
    
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public Integer getEnderecoId() {
        return enderecoId;
    }
    
    public void setEnderecoId(Integer enderecoId) {
        this.enderecoId = enderecoId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public Endereco getEndereco() {
        return endereco;
    }
    
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
        if (endereco != null) {
            this.enderecoId = endereco.getId();
        }
    }
    
    public int calcularIdade() {
        if (dataNascimento == null) {
            return 0;
        }
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
    
    public String getCpfFormatado() {
        if (cpf != null && cpf.length() == 11) {
            return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + 
                   cpf.substring(6, 9) + "-" + cpf.substring(9);
        }
        return cpf;
    }
    
    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }
    
    public boolean possuiEnderecoValido() {
        return enderecoId != null && enderecoId > 0;
    }
    
    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + getNomeCompleto() + '\'' +
                ", cpf='" + getCpfFormatado() + '\'' +
                ", idade=" + calcularIdade() +
                ", enderecoId=" + enderecoId +
                '}';
    }
}
