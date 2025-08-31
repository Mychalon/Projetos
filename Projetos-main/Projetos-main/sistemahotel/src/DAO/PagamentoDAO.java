/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Pagamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {
    private Connection connection;
    
    public PagamentoDAO(Connection connection) {
        this.connection = connection;
    }
    
    public boolean registrarPagamento(Pagamento pagamento) throws SQLException {
        String sql = "INSERT INTO pagamento (id_hospedagem, id_forma_pagamento, valor, status, observacoes) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pagamento.getIdHospedagem());
            stmt.setInt(2, pagamento.getIdFormaPagamento());
            stmt.setDouble(3, pagamento.getValor());
            stmt.setString(4, pagamento.getStatus());
            stmt.setString(5, pagamento.getObservacoes());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Pagamento> listarPorHospedagem(int idHospedagem) throws SQLException {
        String sql = "SELECT * FROM pagamento WHERE id_hospedagem = ?";
        List<Pagamento> pagamentos = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idHospedagem);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pagamento p = new Pagamento();
                    p.setId(rs.getInt("id"));
                    p.setIdHospedagem(rs.getInt("id_hospedagem"));
                    p.setIdFormaPagamento(rs.getInt("id_forma_pagamento"));
                    p.setValor(rs.getDouble("valor"));
                    p.setDataPagamento(rs.getTimestamp("data_pagamento"));
                    p.setStatus(rs.getString("status"));
                    p.setObservacoes(rs.getString("observacoes"));
                    pagamentos.add(p);
                }
            }
        }
        return pagamentos;
    }
}
