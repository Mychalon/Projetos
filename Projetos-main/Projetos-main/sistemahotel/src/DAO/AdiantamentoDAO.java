/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Adiantamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdiantamentoDAO {
    private Connection connection;
    
    public AdiantamentoDAO(Connection connection) {
        this.connection = connection;
    }
    
    public boolean registrarAdiantamento(Adiantamento adiantamento) throws SQLException {
        String sql = "INSERT INTO adiantamento (id_hospede, valor) VALUES (?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, adiantamento.getIdHospede());
            stmt.setDouble(2, adiantamento.getValor());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public double saldoDisponivel(int idHospede) throws SQLException {
        String sql = "SELECT SUM(valor) FROM adiantamento WHERE id_hospede = ? AND utilizado = FALSE";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idHospede);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getDouble(1) : 0.0;
            }
        }
    }
}
