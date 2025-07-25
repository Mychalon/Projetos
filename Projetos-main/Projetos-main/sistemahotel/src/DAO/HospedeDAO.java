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
           
         // Primeiro verifica se o CPF já existe
    if (existeHospedePorCPF(hospede.getCpfHospede())) {
        throw new SQLException("Já existe um hóspede cadastrado com este CPF");
    }

        String sql = "INSERT INTO hospede (nome, cpf, telefone, email, check_in, check_out, id_quarto, placa) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, hospede.getNome());
            stmt.setString(2, hospede.getCpfHospede());
            stmt.setString(3, hospede.getTelefone());
            stmt.setString(4, hospede.getEmail());
            
            // Usar java.sql.Timestamp corretamente
            stmt.setTimestamp(5, new java.sql.Timestamp(hospede.getCheckIn().getTime()));
            
            if (hospede.getCheckOut() != null) {
                stmt.setTimestamp(6, new java.sql.Timestamp(hospede.getCheckOut().getTime()));
            } else {
                stmt.setNull(6, java.sql.Types.TIMESTAMP);
            }
            
            stmt.setInt(7, hospede.getIdQuarto());
            stmt.setString(8, hospede.getPlacaVeiculo());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public void atualizarCheckout(Hospede hospede) throws SQLException {
        String sql = "UPDATE hospede SET check_out = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            if (hospede.getCheckOut() != null) {
                stmt.setTimestamp(1, new java.sql.Timestamp(hospede.getCheckOut().getTime()));
            } else {
                stmt.setNull(1, java.sql.Types.TIMESTAMP);
            }
            stmt.setInt(2, hospede.getIdhospede());
            stmt.executeUpdate();
        }
    }

    
    public static Hospede buscarPorQuarto(int idQuarto) throws SQLException {
    String sql = "SELECT * FROM hospede WHERE id_quarto = ? AND check_out IS NULL";
    
    try (Connection conexao = ConexaoBD.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {
        
        stmt.setInt(1, idQuarto);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Hospede hospede = new Hospede();
                hospede.setIdhospede(rs.getInt("id"));
                hospede.setNome(rs.getString("nome"));
                hospede.setCpfHospede(rs.getString("cpf"));
                hospede.setTelefone(rs.getString("telefone"));
                hospede.setEmail(rs.getString("email"));
                hospede.setIdQuarto(rs.getInt("id_quarto"));
                hospede.setCheckIn(rs.getTimestamp("check_in"));
                hospede.setCheckOut(rs.getTimestamp("check_out"));
                hospede.setPlacaVeiculo(rs.getString("placa")); // Carrega a placa
                
                return hospede;
            }
        }
        return null;
    }
}
    private boolean existeHospedePorCPF(String cpf) throws SQLException {
    String sql = "SELECT COUNT(*) FROM hospede WHERE cpf = ?";
    
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setString(1, cpf);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    }
    return false;
    }

    public List<Hospede> listarTodos() throws SQLException {
        String sql = "SELECT * FROM hospede";
        List<Hospede> hospedes = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Hospede hospede = new Hospede();
                hospede.setIdhospede(rs.getInt("id"));
                hospede.setNome(rs.getString("nome"));
                hospede.setCpfHospede(rs.getString("cpf"));
                hospede.setTelefone(rs.getString("telefone"));
                hospede.setEmail(rs.getString("email"));
                hospede.setPlacaVeiculo(rs.getString("placa"));

                hospedes.add(hospede);
            }
        }
        return hospedes;
    }

    public void atualizarStatusHospede(int idHospedagem, String checkout) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void atualizar(Hospede hospede) throws SQLException {
    String sql = "UPDATE hospede SET nome=?, cpf=?, telefone=?, email=?, placa=? WHERE id=?";
    
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setString(1, hospede.getNome());
        stmt.setString(2, hospede.getCpfHospede());
        stmt.setString(3, hospede.getTelefone());
        stmt.setString(4, hospede.getEmail());
        stmt.setString(5, hospede.getPlacaVeiculo());
        stmt.setInt(6, hospede.getIdhospede());
        
        stmt.executeUpdate();
    }
    }

    
    public Hospede buscarPorId(int idHospede) throws SQLException {
    String sql = "SELECT * FROM hospede WHERE id = ?";
    
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setInt(1, idHospede);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Hospede hospede = new Hospede();
                hospede.setIdhospede(rs.getInt("id"));
                hospede.setNome(rs.getString("nome"));
                hospede.setCpfHospede(rs.getString("cpf"));
                hospede.setTelefone(rs.getString("telefone"));
                hospede.setPlacaVeiculo(rs.getString("placa"));
                hospede.setIdQuarto(rs.getInt("id_quarto"));
                hospede.setCheckIn(rs.getDate("check_in"));
                hospede.setCheckOut(rs.getDate("check_out"));
                return hospede;
            }
        }
    }
    return null;
 }
 
}

    