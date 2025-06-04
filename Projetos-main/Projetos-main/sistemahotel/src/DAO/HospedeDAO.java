package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Hospede;  // Import adicionado

public class HospedeDAO {
    private final Connection conexao;

    public HospedeDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public int salvar(Hospede hospede) throws SQLException {
        String sql = "INSERT INTO hospede (nome, cpf, telefone, email, check_in, status, id_quarto) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, hospede.getNome());
            stmt.setString(2, hospede.getCpf());
            stmt.setString(3, hospede.getTelefone());
            stmt.setString(4, hospede.getEmail());
            stmt.setTimestamp(5, new java.sql.Timestamp(hospede.getCheckIn().getTime()));
            stmt.setString(6, "hospedado"); // Status inicial
            stmt.setInt(7, hospede.getQuartoId());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna o ID gerado
                }
            }
        }
        throw new SQLException("Falha ao obter ID do hóspede");
    }
    

    public List<Hospede> listarTodos() throws SQLException {
        String sql = "SELECT * FROM hospede";
        List<Hospede> hospedes = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Hospede hospede = new Hospede();
                hospede.setId(rs.getInt("id"));
                hospede.setNome(rs.getString("nome"));
                hospede.setCpf(rs.getString("cpf"));
                hospede.setTelefone(rs.getString("telefone"));
                hospede.setEmail(rs.getString("email"));

                hospedes.add(hospede);
            }
        }
        return hospedes;
    }

    public void atualizarStatusHospede(int idHospedagem, String checkout) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
}

    