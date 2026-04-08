/**
 * Data Access Object (DAO): PessoaDAO
 * 
 * Responsável por todas as operações de persistência relacionadas à entidade Pessoa.
 * Implementa o padrão CRUD (Create, Read, Update, Delete) com validações
 * e gerenciamento de transações com banco de dados.
 */
package com.example.aula.dao;

import com.example.aula.connection.DatabaseConnection;
import com.example.aula.model.Pessoa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {
    
    public boolean inserir(Pessoa pessoa) {
        if (!pessoa.possuiEnderecoValido()) {
            System.err.println("ERRO: Erro: Pessoa deve ter um endereço válido!");
            return false;
        }
        
        String sql = "INSERT INTO pessoa (nome, sobrenome, data_nascimento, cpf, endereco_id) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getSobrenome());
            stmt.setDate(3, Date.valueOf(pessoa.getDataNascimento()));
            stmt.setString(4, pessoa.getCpf());
            stmt.setInt(5, pessoa.getEnderecoId());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        pessoa.setId(rs.getInt(1));
                    }
                }
            }
            
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao inserir pessoa: " + e.getMessage());
            return false;
        }
    }
    
    public boolean editar(Pessoa pessoa) {
        if (pessoa.getEnderecoId() == null) {
            System.err.println("ERRO: Erro: Pessoa deve ter um endereço para ser atualizada!");
            return false;
        }
        
        String sql = "UPDATE pessoa SET nome = ?, sobrenome = ?, data_nascimento = ?, " +
                    "cpf = ?, endereco_id = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getSobrenome());
            stmt.setDate(3, Date.valueOf(pessoa.getDataNascimento()));
            stmt.setString(4, pessoa.getCpf());
            stmt.setInt(5, pessoa.getEnderecoId());
            stmt.setInt(6, pessoa.getId());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao editar pessoa: " + e.getMessage());
            return false;
        }
    }
    
    public boolean excluir(int id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao excluir pessoa: " + e.getMessage());
            return false;
        }
    }
    
    public Pessoa recuperarPorId(int id) {
        String sql = "SELECT id, nome, sobrenome, data_nascimento, cpf, endereco_id, created_at " +
                    "FROM pessoa WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao recuperar pessoa: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<Pessoa> recuperarPorNome(String nome) {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT id, nome, sobrenome, data_nascimento, cpf, endereco_id, created_at " +
                    "FROM pessoa WHERE UPPER(nome) LIKE UPPER(?) OR UPPER(sobrenome) LIKE UPPER(?) " +
                    "ORDER BY nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nome + "%");
            stmt.setString(2, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                pessoas.add(mapearResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao recuperar pessoas por nome: " + e.getMessage());
        }
        
        return pessoas;
    }
    
    public List<Pessoa> recuperarPorIdade(int idade) {
        List<Pessoa> pessoas = new ArrayList<>();
        LocalDate dataMinima = LocalDate.now().minusYears(idade + 1);
        LocalDate dataMaxima = LocalDate.now().minusYears(idade);
        
        String sql = "SELECT id, nome, sobrenome, data_nascimento, cpf, endereco_id, created_at " +
                    "FROM pessoa WHERE data_nascimento >= ? AND data_nascimento <= ? " +
                    "ORDER BY data_nascimento DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(dataMinima));
            stmt.setDate(2, Date.valueOf(dataMaxima));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                pessoas.add(mapearResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao recuperar pessoas por idade: " + e.getMessage());
        }
        
        return pessoas;
    }
    
    public List<Pessoa> recuperarTodas() {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT id, nome, sobrenome, data_nascimento, cpf, endereco_id, created_at " +
                    "FROM pessoa ORDER BY nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                pessoas.add(mapearResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao recuperar todas as pessoas: " + e.getMessage());
        }
        
        return pessoas;
    }
    
    public List<Pessoa> recuperarSemEndereco() {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT id, nome, sobrenome, data_nascimento, cpf, endereco_id, created_at " +
                    "FROM pessoa WHERE endereco_id IS NULL ORDER BY nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                pessoas.add(mapearResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao recuperar pessoas sem endereço: " + e.getMessage());
        }
        
        return pessoas;
    }
    
    public List<Pessoa> recuperarPorEnderecoId(int enderecoId) {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT id, nome, sobrenome, data_nascimento, cpf, endereco_id, created_at " +
                    "FROM pessoa WHERE endereco_id = ? ORDER BY nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, enderecoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                pessoas.add(mapearResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao recuperar pessoas por endereço: " + e.getMessage());
        }
        
        return pessoas;
    }
    
    private Pessoa mapearResultSet(ResultSet rs) throws SQLException {
        return new Pessoa(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("sobrenome"),
            rs.getDate("data_nascimento").toLocalDate(),
            rs.getString("cpf"),
            rs.getObject("endereco_id") != null ? rs.getInt("endereco_id") : null,
            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
        );
    }
}
