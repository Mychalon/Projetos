package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Consumo;

public class ConsumoDAO {
    private final Connection conexao;

    public ConsumoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean registrarConsumo(Consumo consumo, int idCaixa) throws SQLException {
         String sql = "INSERT INTO consumo (id_hospedagem, descricao, valor, data, produto_id, " +
                "hospede_id, quantidade, valor_unitario, data_hora, id_caixa) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    try (PreparedStatement stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        // Configurar os parâmetros na ordem correta
        stmt.setInt(1, consumo.getIdHospedagem());
        stmt.setString(2, consumo.getdescricao());
        stmt.setDouble(3, consumo.getValorTotal()); // Assumindo que 'valor' é o total
        stmt.setDate(4, java.sql.Date.valueOf(consumo.getDataHora().toLocalDate())); // data
        stmt.setInt(5, consumo.getIdProduto());
        stmt.setInt(6, consumo.getHospedeId());
        stmt.setInt(7, consumo.getQuantidade());
        stmt.setDouble(8, consumo.getValorUnitario());
        stmt.setTimestamp(9, java.sql.Timestamp.valueOf(consumo.getDataHora()));
        stmt.setInt(10, idCaixa);
        
        
        int affectedRows = stmt.executeUpdate();
        
        // Se precisar obter o ID gerado
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    consumo.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        }
        return false;
    }
    }

    public List<Consumo> listarConsumosPorHospedagem(int idHospedagem) throws SQLException {
        List<Consumo> consumos = new ArrayList<>();
        String sql = "SELECT c.*, p.nome as nome_produto FROM consumo c " +
                     "JOIN produto p ON c.id = p.id " +
                     "WHERE c.id_hospedagem = ? ORDER BY c.data_hora DESC";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idHospedagem);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Consumo consumo = new Consumo();
                consumo.setId(rs.getInt("id"));
                consumo.setIdHospedagem(idHospedagem);
                consumo.setIdProduto(rs.getInt("id"));
                consumo.setNomeProduto(rs.getString("nome"));
                consumo.setQuantidade(rs.getInt("quantidade"));
                consumo.setValorUnitario(rs.getDouble("preço"));
                consumo.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                
                consumos.add(consumo);
            }
        }
        return consumos;
    }
}