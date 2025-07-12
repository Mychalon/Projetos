/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Acompanhante;

/**
 *
 * @author HOTEL FENIX
 */
public class AcompanhanteDAO {
    private final Connection conexao;
     
    public AcompanhanteDAO(Connection conexao) {
        this.conexao = conexao;
    }
     
    
    public static List<Acompanhante> buscarPorHospede(int idHospede) throws SQLException {
    List<Acompanhante> acompanhantes = new ArrayList<>();
    String sql = "SELECT * FROM acompanhantes WHERE hospede_id = ?";
    
    try (Connection conn = ConexaoBD.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        stmt.setInt(1, idHospede);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Acompanhante a = new Acompanhante();
            a.setId(rs.getInt("id"));
            a.setNome(rs.getString("nome"));
            a.setTelefone(rs.getString("telefone"));
            a.setCpfacompanhante(rs.getString("CPF"));
            a.setIdhospede(idHospede);
            acompanhantes.add(a);
        }
    }
    return acompanhantes;
}
    
    
    
    
    public int salvar(Acompanhante acompanhante) throws SQLException {
    String sql = "INSERT INTO acompanhantes (nome, cpf, telefone, hospede_id, id_quarto) " +
               "VALUES (?, ?, ?, ?, ?)";
    
    try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, acompanhante.getNome());
        stmt.setString(2, acompanhante.getCpfacompanhante());
        stmt.setString(3, acompanhante.getTelefone());
        stmt.setInt(4, acompanhante.getIdhospede());
        stmt.setInt(5, acompanhante.getIdQuarto());
        
        stmt.executeUpdate();
        
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Falha ao obter ID do Acompanhante");
    }
    }
    
    public boolean existeAcompanhantePorCPF(String cpf, int idHospede) throws SQLException {
    String sql = "SELECT COUNT(*) FROM acompanhantes WHERE cpf = ? AND hospede_id = ?";
    
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setString(1, cpf);
        stmt.setInt(2, idHospede);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    }
    return false;
    }
}

     
    



