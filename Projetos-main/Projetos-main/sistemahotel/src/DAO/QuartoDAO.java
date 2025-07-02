package dao;

import static dao.ConexaoBD.getConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Hospede;
import telas.Quarto;
import telas.cadquarto;

public class QuartoDAO {
     private final Connection connection;
     
     
     public QuartoDAO(Connection connection) {
        this.connection = connection;
    }
    
    public static Quarto buscarPorId(int idQuarto) throws SQLException {
   String sql = "SELECT * FROM quarto WHERE id = ?";
    try (Connection conexao = ConexaoBD.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setInt(1, idQuarto);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Quarto quarto = new Quarto(
                    rs.getInt("id"),
                    rs.getString("numero"),
                    rs.getString("tipo"),
                    rs.getString("status")
                );
                quarto.setCheckIn(rs.getString("check_in"));
                quarto.setCheckOut(rs.getString("check_out"));
                return quarto;
            }
        }
    }
    return null;
}
    
    public static void cadastrarQuarto(cadquarto quarto) {
        String sql = "INSERT INTO quarto (numero, tipo, Descricao) VALUES (?, ?, ? )";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Configura os parâmetros
            stmt.setString(1, quarto.getNomeQuarto());
            stmt.setString(2, quarto.getTipoQuarto());
            stmt.setString(3, quarto.getDescricao());
            
            // Executa a inserção
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar quarto: " + e.getMessage());
            throw new RuntimeException("Erro no banco de dados", e);
        }
    }
    
public static List<Quarto> listarQuartos() throws SQLException {
    List<Quarto> quartos = new ArrayList<>();
    String sql = "SELECT * FROM quarto";
    
    try (Connection conexao = ConexaoBD.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            Quarto quarto = new Quarto();
            quarto.setIdQuarto(rs.getInt("id"));
            quarto.setNumero(rs.getString("numero"));
            quarto.setTipo(rs.getString("tipo"));
            quarto.setStatus(rs.getString("status"));
            
            
            quartos.add(quarto);
        }
    }
    return quartos;
}
   public static void atualizarQuarto(Quarto quarto, Hospede hospede) throws SQLException {
    String sql = "UPDATE hospede SET nome=?, cpf=?, telefone=? WHERE id = ?";
    
    try (Connection conn = getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, hospede.getNome());
        stmt.setString(2, hospede.getCpfHospede());
        stmt.setString(3, hospede.getTelefone());
        stmt.setString(4, quarto.getNumero());
        
        stmt.executeUpdate();
    }

}

    public QuartoDAO() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean atualizarStatusQuarto(Quarto quarto) throws SQLException {
    String sql = "UPDATE quarto SET status = ? WHERE id = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        // Definir timeout para a consulta (em segundos)
        stmt.setQueryTimeout(30);
        
        stmt.setString(1, quarto.getStatus());
        stmt.setInt(2, quarto.getIdQuarto());
        
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        if (e.getMessage().contains("Lock wait timeout")) {
            throw new SQLException("Timeout ao atualizar quarto. Tente novamente.", e);
        }
        throw e;
    }
}


    public static boolean existeQuarto(int idQuarto) throws SQLException {
    String sql = "SELECT id FROM quarto WHERE id = ?";
    try (Connection conexao = ConexaoBD.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setInt(1, idQuarto);
        try (ResultSet rs = stmt.executeQuery()) {
            return rs.next(); // Retorna true se o quarto existir
        }
    }
}

    

}



    