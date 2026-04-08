/**
 * Gerenciador de Conexão: DatabaseConnection
 * 
 * Responsável por gerenciar a conexão com o banco de dados PostgreSQL.
 * Implementa o padrão Singleton para garantir uma única instância de conexão
 * e facilitar o acesso em toda a aplicação.
 */
package com.example.aula.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    private static final String URL = "jdbc:postgresql://localhost:5433/LISTABIM1";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1130";
    private static final String DRIVER = "org.postgresql.Driver";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL não encontrado", e);
        }
    }
    
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
