package dao;

import static dao.ConexaoBD.getConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import telas.Quarto;
import telas.cadquarto;

public class QuartoDAO {
    
    
    public static Quarto buscarPorId(int idQuarto) throws SQLException {
    String sql = "SELECT * FROM quarto WHERE id = ?";
    try (Connection conexao = ConexaoBD.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setInt(1, idQuarto);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Quarto(
                    rs.getInt("id"),
                    rs.getString("numero"),
                    rs.getString("tipo"),
                    rs.getString("status")
                );
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
   public static void atualizarQuarto(Quarto quarto) throws SQLException {
    String sql = "UPDATE hospede SET nome=?, cpf=?, telefone=? WHERE id = ?";
    
    try (Connection conn = getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, quarto.getNomeHospede());
        stmt.setString(2, quarto.getCpfHospede());
        stmt.setString(3, quarto.getTelefone());
        stmt.setString(4, quarto.getNumero());
        
        stmt.executeUpdate();
    }

}

   public static boolean atualizarStatusQuarto(Quarto quarto) throws SQLException {
       String sql = "UPDATE quarto SET status = ?, nome = ?, cpf = ?, telefone = ? WHERE numero = ?";
    
    try (Connection conexao = ConexaoBD.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {
        
        // Debug: Mostra os valores que serão atualizados
        System.out.println("DEBUG - Atualizando quarto " + quarto.getNumero() + 
                         " para status: " + quarto.getStatus() +
                         " - Nome: " + quarto.getNomeHospede());
        
        stmt.setString(1, quarto.getStatus().toLowerCase()); // Garante minúsculas
        stmt.setString(2, quarto.getNomeHospede());
        stmt.setString(3, quarto.getCpfHospede());
        stmt.setString(4, quarto.getTelefone());
        stmt.setString(5, quarto.getNumero());
        
        int rowsUpdated = stmt.executeUpdate();
        System.out.println("DEBUG - Linhas atualizadas: " + rowsUpdated);
        
        return rowsUpdated > 0;
    }
}

}



    