package dao;

import model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class ProdutoDAO {
     
    public ProdutoDAO() throws SQLException {
    // Sua implementação atual
}
    
  public static boolean cadastrarProduto(Produto produto) {
     String sql = "INSERT INTO produto (nome, codigo, estoque, preco) VALUES (?, ?, ?, ?)";
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
        conn = ConexaoBD.getConexao();
        // Desativa o autocommit para controlar manualmente a transação
        conn.setAutoCommit(false);
        
        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        // Define os parâmetros
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getCodigo());
        stmt.setInt(3, produto.getQuantidade());
        stmt.setDouble(4, produto.getPreco());
        
        // Executa a inserção
        int rowsAffected = stmt.executeUpdate();
        
        if (rowsAffected > 0) {
            // Obtém o ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getInt(1));
                }
            }
            // Confirma a transação
            conn.commit();
            return true;
        }
        return false;
        
    } catch (SQLException e) {
        // Em caso de erro, faz rollback
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Erro ao fazer rollback: " + ex.getMessage());
            }
        }
        System.err.println("Erro ao cadastrar produto: " + e.getMessage());
        return false;
    } finally {
        // Restaura o autocommit e fecha os recursos
        try {
            if (conn != null) {
                conn.setAutoCommit(true); // Restaura o estado original
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }}
    
    // Busca produto por nome aproximado
   
   public List<Produto> buscarTodos() throws SQLException {
    List<Produto> lista = new ArrayList<>();
    String sql = "SELECT * FROM produto";
    
   
    
    try (Connection conn = ConexaoBD.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            Produto p = criarProduto(rs);
            lista.add(p);
        }
    }
    return lista;
}


    // Atualiza estoque
    public void atualizarEstoque(int produtoId, int quantidade) throws SQLException {
        String sql = "UPDATE produto SET estoque = estoque + ? WHERE id = ?";
        
        try (Connection conn = ConexaoBD.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, quantidade);
            stmt.setInt(2, produtoId);
            stmt.executeUpdate();
        }
    }
    
    public static boolean atualizarProduto(Produto produto) throws SQLException {
     String sql = "UPDATE produto SET nome = ?, codigo = ?, estoque = ?, preco = ? WHERE id = ?";
    
    try (Connection conn = ConexaoBD.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, produto.getNome());
        stmt.setString(2, produto.getCodigo());
        stmt.setInt(3, produto.getQuantidade());
        stmt.setDouble(4, produto.getPreco());
        stmt.setInt(5, produto.getId());
        
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    }}
    
    
    
    private Produto criarProduto(ResultSet rs) throws SQLException {
       Produto produto = new Produto();
    produto.setId(rs.getInt("id"));
    produto.setNome(rs.getString("nome"));
    produto.setCodigo(rs.getString("codigo"));  // Adicionado
    produto.setQuantidade(rs.getInt("estoque"));  // Adicionado
    produto.setPreco(rs.getDouble("preco"));
    return produto;
    }

    public Produto buscarPorCodigo(String codigo) throws SQLException {
    String sql = "SELECT * FROM produto WHERE codigo = ?";
    
    try (Connection conn = ConexaoBD.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, codigo);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return criarProduto(rs);
            }
        }
    }
    return null;
}

public List<Produto> buscarPorNome(String nome) throws SQLException {
    List<Produto> lista = new ArrayList<>();
    String sql = "SELECT * FROM produto WHERE LOWER(nome) LIKE ?";
    
    try (Connection conn = ConexaoBD.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, "%" + nome.toLowerCase() + "%");
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(criarProduto(rs));
            }
        }
    }
    return lista;
}
    

    private static class e {

        private static String getMessage() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public e() {
        }
    }
}