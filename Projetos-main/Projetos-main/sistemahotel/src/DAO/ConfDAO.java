/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Configurar;

public class ConfDAO {
    private Connection conexao;
    
    public ConfDAO(Connection conexao) {
        this.conexao = conexao;
    }
    
    public void inserir(Configurar configurar) throws SQLException {
        String sql = "INSERT INTO conf (check_in, check_out) VALUES (?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, configurar.getCheckIn());
            stmt.setString(2, configurar.getCheckOut());
            stmt.executeUpdate();
        }
    }
    
    
    
    public void aplicarConfiguracaoGlobal(String checkIn, String checkOut) throws SQLException {
    String sql = "UPDATE quarto SET check_in = ?, check_out = ?";
    
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setString(1, checkIn);
        stmt.setString(2, checkOut);
        stmt.executeUpdate();
    
}
}
}