/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */package dao;

import model.FormaPagamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormaPagamentoDAO {
    private Connection connection;
    
    public FormaPagamentoDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<FormaPagamento> listarAtivas() throws SQLException {
        String sql = "SELECT * FROM forma_pagamento WHERE ativo = TRUE";
        List<FormaPagamento> formas = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                FormaPagamento fp = new FormaPagamento();
                fp.setId(rs.getInt("id"));
                fp.setNome(rs.getString("nome"));
                fp.setDescricao(rs.getString("descricao"));
                fp.setAtivo(rs.getBoolean("ativo"));
                formas.add(fp);
            }
        }
        return formas;
    }
}
