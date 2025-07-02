package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;


public class HospedagemDAO {
    private Connection conexao;

    public HospedagemDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void registrarHospedagem(int idQuarto, int idHospede, int idFuncionario) throws SQLException {
        String sql = "INSERT INTO hospedagem (id_quarto, id_hospede, id_funcionario, check_in) VALUES (?, ?, ?, NOW())";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idQuarto);
            stmt.setInt(2, idHospede);
            stmt.setInt(3, idFuncionario);
            stmt.executeUpdate();
        }
    }

    public void registrarCheckout(int idHospedagem) throws SQLException {
        String sql = "UPDATE hospedagem SET check_out = NOW() WHERE id = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idHospedagem);
            stmt.executeUpdate();
        }
    }
}
    
