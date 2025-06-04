
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class ConsumoDAO {

    private Connection conexao;

    // ... outros métodos ...
  
 public ConsumoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Modifique o método inserir para usar a conexão da classe:
public void inserir(Consumo consumo) throws SQLException {
    String sql = "INSERT INTO consumo (descricao, valor, hospede_id, quarto_id, data_hora) VALUES (?, ?, ?, ?, ?)";
    
    try (PreparedStatement stmt = conexao.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, consumo.getDescricao());
        stmt.setDouble(2, consumo.getValor());
        stmt.setInt(3, consumo.getHospedeId());
        stmt.setInt(4, consumo.getQuartoId());
        stmt.setTimestamp(5, java.sql.Timestamp.valueOf(consumo.getDataHora()));
        
        stmt.executeUpdate();
        
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                consumo.setId(rs.getInt(1));
            }
        }
    }
}

    public List<Consumo> listarPorHospede(int hospedeId) throws SQLException {
        String sql = "SELECT * FROM consumo WHERE hospede_id = ?";
        List<Consumo> consumos = new ArrayList<>();
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, hospedeId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Consumo consumo = new Consumo();
                    // preencher o objeto consumo com os dados do ResultSet
                    consumos.add(consumo);
                }
            }
        }
        return consumos;
    }
}
