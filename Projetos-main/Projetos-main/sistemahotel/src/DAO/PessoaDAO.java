package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PessoaDAO {
    public static boolean buscarPorNome(String nome) {
        String sql = "SELECT * FROM pessoa WHERE nome = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Define o parâmetro da consulta
            stmt.setString(1, nome);
            
            // Executa a consulta
            ResultSet rs = stmt.executeQuery();
            
            // Retorna true se encontrou algum registro
            return rs.next();
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pessoa no banco de dados:");
            e.printStackTrace();
            return false;
        }
    }

    // Método adicional para verificar login com senha
    public static boolean verificarLogin(String nome, String senha) {
        String sql = "SELECT * FROM pessoa WHERE nome = ? AND senha = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nome);
            stmt.setString(2, senha); // Aqui a senha é passada diretamente (não seguro para produção)
            
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Retorna true se encontrou um usuário com esse nome e senha
            
        } catch (SQLException e) {
            System.err.println("Erro ao verificar login:");
            e.printStackTrace();
            return false;
        }
    }
    
    // Método para obter o tipo de usuário
    public static String obterTipoUsuario(String nome, String senha) {
        String sql = "SELECT tipo FROM pessoa WHERE nome = ? AND senha = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nome);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("tipo");
            }
            return null;
            
        } catch (SQLException e) {
            System.err.println("Erro ao obter tipo de usuário:");
            e.printStackTrace();
            return null;
        }
    }
}