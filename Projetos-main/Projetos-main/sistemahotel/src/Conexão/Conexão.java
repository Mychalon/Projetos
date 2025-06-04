/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexão;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexão {
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3307/sishotel?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private final String USER = "root";
    private final String PASS = "1234";
    
    public Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do banco de dados não encontrado", e);
        }
    }
    
    // Método para testar a conexão (opcional)
    public static void testarConexao() {
        try {
            Conexão conexao = new Conexão();
            try (Connection conn = conexao.getConnection()) {
                System.out.println("Conexão estabelecida com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro na conexão:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Código de erro: " + e.getErrorCode());
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
              


    
 
    
    
    
    
    
    

