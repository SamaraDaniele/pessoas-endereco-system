package com.example.aula.dao;

import com.example.aula.connection.DatabaseConnection;
import com.example.aula.model.Endereco;
import com.example.aula.model.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {
    
    public boolean inserir(Endereco endereco) {
        String sql = "INSERT INTO endereco (logradouro, numero, cidade, estado, pais) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getCidade());
            stmt.setString(4, endereco.getEstado());
            stmt.setString(5, endereco.getPais());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        endereco.setId(rs.getInt(1));
                    }
                }
            }
            
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao inserir endereço: " + e.getMessage());
            return false;
        }
    }
    
    public boolean editar(Endereco endereco) {
        String sql = "UPDATE endereco SET logradouro = ?, numero = ?, cidade = ?, " +
                    "estado = ?, pais = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getCidade());
            stmt.setString(4, endereco.getEstado());
            stmt.setString(5, endereco.getPais());
            stmt.setInt(6, endereco.getId());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao editar endereço: " + e.getMessage());
            return false;
        }
    }
    
    public boolean excluir(int id) {
        String sql = "DELETE FROM endereco WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao excluir endereço: " + e.getMessage());
            return false;
        }
    }
    
    public Endereco recuperarPorId(int id) {
        String sql = "SELECT id, logradouro, numero, cidade, estado, pais, created_at " +
                    "FROM endereco WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao recuperar endereço: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<Endereco> recuperarPorLogradouro(String logradouro) {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT id, logradouro, numero, cidade, estado, pais, created_at " +
                    "FROM endereco WHERE UPPER(logradouro) LIKE UPPER(?) ORDER BY logradouro";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + logradouro + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                enderecos.add(mapearResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao recuperar endereços por logradouro: " + e.getMessage());
        }
        
        return enderecos;
    }
    
    public List<Endereco> recuperarTodos() {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT id, logradouro, numero, cidade, estado, pais, created_at " +
                    "FROM endereco ORDER BY cidade, logradouro";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                enderecos.add(mapearResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao recuperar todos os endereços: " + e.getMessage());
        }
        
        return enderecos;
    }
    
    public List<Endereco> recuperarPorEstado(String estado) {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT id, logradouro, numero, cidade, estado, pais, created_at " +
                    "FROM endereco WHERE UPPER(estado) = UPPER(?) ORDER BY cidade, logradouro";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                enderecos.add(mapearResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao recuperar endereços por estado: " + e.getMessage());
        }
        
        return enderecos;
    }
    
    public List<Endereco> recuperarPorCidade(String cidade) {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT id, logradouro, numero, cidade, estado, pais, created_at " +
                    "FROM endereco WHERE UPPER(cidade) LIKE UPPER(?) ORDER BY logradouro";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + cidade + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                enderecos.add(mapearResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("ERRO: Erro ao recuperar endereços por cidade: " + e.getMessage());
        }
        
        return enderecos;
    }
    
    public Endereco recuperarComPessoas(int id) {
        Endereco endereco = recuperarPorId(id);
        
        if (endereco != null) {
            PessoaDAO pessoaDAO = new PessoaDAO();
            endereco.setPessoas(pessoaDAO.recuperarPorEnderecoId(id));
        }
        
        return endereco;
    }
    
    public List<Endereco> recuperarTodosComPessoas() {
        List<Endereco> enderecos = recuperarTodos();
        PessoaDAO pessoaDAO = new PessoaDAO();
        
        for (Endereco endereco : enderecos) {
            endereco.setPessoas(pessoaDAO.recuperarPorEnderecoId(endereco.getId()));
        }
        
        return enderecos;
    }
    
    private Endereco mapearResultSet(ResultSet rs) throws SQLException {
        return new Endereco(
            rs.getInt("id"),
            rs.getString("logradouro"),
            rs.getString("numero"),
            rs.getString("cidade"),
            rs.getString("estado"),
            rs.getString("pais"),
            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
        );
    }
}
