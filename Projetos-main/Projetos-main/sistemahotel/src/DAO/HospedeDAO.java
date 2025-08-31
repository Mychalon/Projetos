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

    public static void setStatus(String checkout) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

 

    
    private final Connection conexao;

    public HospedeDAO(Connection conexao) {
        this.conexao = conexao;
    }
    
  public int salvar(Hospede hospede) throws SQLException {
    Hospede existente = buscarPorCPF(hospede.getCpfHospede());
    if (existente != null) {
        return -2; // Já existe
    }
    
    String sql = "INSERT INTO hospede (nome, cpf, telefone, email, id_quarto, placa, status) " +
               "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, hospede.getNome());
        stmt.setString(2, hospede.getCpfHospede());
        stmt.setString(3, hospede.getTelefone());
        stmt.setString(4, hospede.getEmail() != null ? hospede.getEmail() : "");
        stmt.setInt(5, hospede.getIdQuarto());
        stmt.setString(6, hospede.getPlacaVeiculo() != null ? hospede.getPlacaVeiculo() : "");
        stmt.setString(7, "Hospedado"); // STATUS padrão
        
        stmt.executeUpdate();
        
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
    }
    return -1;
}
       //método para buscar por CPF
public Hospede buscarPorCPF(String cpf) throws SQLException {
    String sql = "SELECT * FROM hospede WHERE cpf = ?";
    
    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
        stmt.setString(1, cpf);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Hospede hospede = new Hospede();
                hospede.setIdhospede(rs.getInt("id"));
                hospede.setNome(rs.getString("nome"));
                hospede.setCpfHospede(rs.getString("cpf"));
                hospede.setTelefone(rs.getString("telefone"));
                hospede.setEmail(rs.getString("email"));
                hospede.setPlacaVeiculo(rs.getString("placa"));
                hospede.setIdQuarto(rs.getInt("id_quarto"));
                hospede.setStatus(rs.getString("status")); // Carrega o status
                
                return hospede;
            }
        }
    }
    return null;
}

    

    
     public static Hospede buscarPorQuarto(int idQuarto) throws SQLException {
      String sql = "SELECT * FROM hospede WHERE id_quarto = ? AND status = 'Hospedado'";
    
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
                hospede.setPlacaVeiculo(rs.getString("placa"));
                hospede.setStatus(rs.getString("status")); // Carrega o status
                
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

   
    
    
    public void atualizarStatus(int idHospede, String status) throws SQLException {
        String sql = "UPDATE hospede SET status = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, idHospede);
            stmt.executeUpdate();
        }
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
               
                return hospede;
            }
        }
    }
    return null;
 }
 public static List<Hospede> buscarPorStatus(String status) throws SQLException {
    List<Hospede> hospedes = new ArrayList<>();
    String sql = "SELECT * FROM hospede WHERE status = ?";
    
    try (Connection conexao = ConexaoBD.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {
        
        stmt.setString(1, status);
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Hospede hospede = new Hospede();
                hospede.setIdhospede(rs.getInt("id"));
                hospede.setNome(rs.getString("nome"));
                hospede.setCpfHospede(rs.getString("cpf"));
                hospede.setTelefone(rs.getString("telefone"));
                hospede.setEmail(rs.getString("email"));
                hospede.setIdQuarto(rs.getInt("id_quarto"));
                hospede.setPlacaVeiculo(rs.getString("placa"));
                hospede.setStatus(rs.getString("status")); // Carrega o status
                
                hospedes.add(hospede);
            }
        }
    }
    return hospedes;
}

    public void atualizarStatusHospede(int idHospedagem, String checkout) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
}

    