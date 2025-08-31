package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.*;
import java.util.Date;


public class HospedagemDAO {
    private final Connection conexao;

  public HospedagemDAO(Connection conexao) {
        this.conexao = conexao;
    }

  
  
  
  
    public void registrarHospedagem(int idQuarto, int idHospede, int idFuncionario) throws SQLException {
        String sql = "INSERT INTO hospedagem (id_quarto, id_hospede, id_funcionario, check_in) VALUES (?, ?, ?, NOW())";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idQuarto);
            stmt.setInt(2, idHospede);
            stmt.setInt(3, idFuncionario);
            stmt.executeUpdate();
        }
    }

    public void registrarCheckout(int idHospedagem) throws SQLException {
        String sql = "UPDATE hospedagem SET check_out = NOW() WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idHospedagem);
            stmt.executeUpdate();
        }
    }

      public void registrarHospedagem(int idQuarto, int idHospede, int idFuncionario, 
                                  Date checkIn, Date checkOut) throws SQLException {
        String sql = "INSERT INTO hospedagem (id_quarto, id_hospede, id_funcionario, check_in, check_out) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idQuarto);
            stmt.setInt(2, idHospede);
            stmt.setInt(3, idFuncionario);
            stmt.setTimestamp(4, new java.sql.Timestamp(checkIn.getTime()));
            stmt.setTimestamp(5, new java.sql.Timestamp(checkOut.getTime()));
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao registrar hospedagem, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idHospedagem = generatedKeys.getInt(1);
                    // Você pode retornar este ID se necessário
                }
            }
        }
    }
}
    
