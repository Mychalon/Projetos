package dao;

import static dao.ConexaoBD.getConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class CaixaDAO {
    
    
    // Adicione este método na classe CaixaDAO
    public static int abrirCaixa(int idFuncionario, double valorAbertura) throws SQLException {
    String sql = "INSERT INTO caixa (id_funcionario, valor_abertura, data_abertura, status) " +
                 "VALUES (?, ?, NOW(), 'Aberto')";
    
    try (Connection conexao = ConexaoBD.getConexao();
         PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        
        ps.setInt(1, idFuncionario);
        ps.setDouble(2, valorAbertura);
        
        int affectedRows = ps.executeUpdate();
        
        if (affectedRows == 0) {
            throw new SQLException("Falha ao abrir caixa, nenhuma linha afetada.");
        }
        
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // Retorna o ID gerado
            } else {
                throw new SQLException("Falha ao obter ID do caixa aberto.");
            }
        }
    }
}
    
    
    public static int obterCaixaAberto(int idFuncionario) throws SQLException {
    String sql = "SELECT id FROM caixa WHERE id_funcionario = ? AND status = 'Aberto' LIMIT 1";
    
    try (Connection conexao = ConexaoBD.getConexao();
         PreparedStatement ps = conexao.prepareStatement(sql)) {
        
        ps.setInt(1, idFuncionario);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt("id") : -1;
        }
    }
}   
    
    
    
   // Método para verificar se caixa está aberto - VERSÃO CORRIGIDA
    public static boolean verificarCaixaAberto(int idCaixa) throws SQLException {
        String sql = "SELECT 1 FROM caixa WHERE id = ? AND status = 'Aberto' LIMIT 1";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, idCaixa);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    
    public static String obterResumoVendas(int idCaixa) throws SQLException {
    StringBuilder resumo = new StringBuilder();
    
    String sql = "SELECT id, forma_pagamento, valor, descricao, DATE_FORMAT(data, '%d/%m/%Y %H:%i') as data_formatada " +
                 "FROM vendas WHERE id_caixa = ? ORDER BY data";
    
    try (Connection conn = getConexao();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idCaixa);
        ResultSet rs = ps.executeQuery();
        
        int contador = 1;
        double totalVendas = 0;
        
        while (rs.next()) {
            resumo.append(contador).append(". ")
                  .append(rs.getString("data_formatada")).append(" - ")
                  .append(rs.getString("forma_pagamento")).append(" - ")
                  .append("R$ ").append(String.format("%.2f", rs.getDouble("valor"))).append("\n")
                  .append("   ").append(rs.getString("descricao")).append("\n\n");
            
            totalVendas += rs.getDouble("valor");
            contador++;
        }
        
        if (contador == 1) {
            resumo.append("Nenhuma venda registrada neste caixa.\n");
        } else {
            resumo.append("\nTotal de vendas: R$ ").append(String.format("%.2f", totalVendas)).append("\n");
            resumo.append("Quantidade de vendas: ").append(contador - 1).append("\n");
        }
        
        // Adicionar resumo por forma de pagamento
        resumo.append("\nResumo por forma de pagamento:\n");
        resumo.append(obterResumoPorFormaPagamento(idCaixa));
    }
    
    return resumo.toString();
}
  

private static String obterResumoPorFormaPagamento(int idCaixa) throws SQLException {
    StringBuilder resumo = new StringBuilder();
    
    String sql = "SELECT forma_pagamento, SUM(valor) as total " +
                 "FROM vendas WHERE id_caixa = ? " +
                 "GROUP BY forma_pagamento ORDER BY total DESC";
    
    try (Connection conn = getConexao();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idCaixa);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            resumo.append("- ").append(rs.getString("forma_pagamento")).append(": ")
                  .append("R$ ").append(String.format("%.2f", rs.getDouble("total"))).append("\n");
        }
        
        if (!resumo.toString().isEmpty()) {
            return resumo.toString();
        } else {
            return "Nenhuma venda registrada por forma de pagamento.\n";
        }
    }
}

    

public static boolean temCaixaAberto(int idFuncionario) throws SQLException {
    return obterCaixaAberto(idFuncionario) > 0;
}
    // Método para registrar venda - VERSÃO CORRIGIDA
    public static boolean registrarVenda(int idCaixa, double valor, 
                                       String formaPagamento, String descricao) throws SQLException {
        // Verificação robusta do caixa
        if (!verificarCaixaAberto(idCaixa)) {
            throw new SQLException("Caixa não está aberto ou não existe");
        }

        String sql = "INSERT INTO vendas (id_caixa, valor, forma_pagamento, descricao, data) " +
                     "VALUES (?, ?, ?, ?, NOW())";
        
        try (Connection conn = getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCaixa);
            ps.setDouble(2, valor);
            ps.setString(3, formaPagamento);
            ps.setString(4, descricao);
            
            return ps.executeUpdate() > 0;
        }
    }

    
    
    public static boolean registrarConsumoQuarto(int idCaixa, int idQuarto, String descricao, double valor) 
    throws SQLException {
    String sql = "INSERT INTO consumos_quarto (id_caixa, id_quarto, descricao, valor, data) " +
                 "VALUES (?, ?, ?, ?, NOW())";
    
    try (Connection conn = getConexao();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idCaixa);
        ps.setInt(2, idQuarto);
        ps.setString(3, descricao);
        ps.setDouble(4, valor);
        
        return ps.executeUpdate() > 0;
    }
}
   

        // Método para fechar caixa - VERSÃO CORRIGIDA
    public static boolean fecharCaixa(int idCaixa, double valorFechamento) throws SQLException {
        String sql = "UPDATE caixa SET data_fechamento = NOW(), " +
                    "valor_fechamento = ?, status = 'Fechado' " +
                    "WHERE id = ? AND status = 'Aberto'";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setDouble(1, valorFechamento);
            ps.setInt(2, idCaixa);
            return ps.executeUpdate() > 0;
        }
    }
    
    

    // Adicione este método para atualizar o status
    public static boolean atualizarStatusCaixa(int idCaixa, String status) throws SQLException {
    String sql = "UPDATE caixa SET status = ? WHERE id = ?";
    
    try (Connection conexao = ConexaoBD.getConexao();
         PreparedStatement ps = conexao.prepareStatement(sql)) {
        
        ps.setString(1, status);
        ps.setInt(2, idCaixa);
        return ps.executeUpdate() > 0;
    }
}
    

 
  

    public static String gerarResumoCaixa(int idCaixa) throws SQLException {
    StringBuilder resumo = new StringBuilder();
    
    // Obter informações básicas do caixa
    String sqlCaixa = "SELECT c.valor_abertura as valor_inicial, " +
                     "DATE_FORMAT(c.data_abertura, '%d/%m/%Y %H:%i') as abertura, " +
                     "DATE_FORMAT(c.data_fechamento, '%d/%m/%Y %H:%i') as fechamento, " +
                     "f.nome as funcionario, c.valor_fechamento " +
                     "FROM caixa c " +
                     "JOIN pessoa f ON c.id_funcionario = f.id " +
                     "WHERE c.id = ?";
    
    try (Connection conn = getConexao();
         PreparedStatement ps = conn.prepareStatement(sqlCaixa)) {
        
        ps.setInt(1, idCaixa);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            resumo.append("RESUMO DO CAIXA #").append(idCaixa).append("\n");
            resumo.append("=================================\n");
            resumo.append("Data Abertura: ").append(rs.getString("abertura")).append("\n");
            resumo.append("Data Fechamento: ").append(rs.getString("fechamento")).append("\n");
            resumo.append("Responsável: ").append(rs.getString("funcionario")).append("\n");
            resumo.append("Valor Inicial: R$ ").append(String.format("%.2f", rs.getDouble("valor_inicial"))).append("\n\n");
        }
    }
    
    // Adicionar resumo de vendas
    resumo.append("--- VENDAS REALIZADAS ---\n");
    resumo.append(obterResumoVendas(idCaixa)).append("\n");
    
    // Adicionar resumo de consumos
    resumo.append("--- CONSUMOS DE HOSPEDAGEM ---\n");
    resumo.append(obterResumoConsumos(idCaixa)).append("\n");
    
    // Calcular totais
    resumo.append("--- TOTAIS ---\n");
    Map<String, Double> totais = obterTotaisCaixa(idCaixa);
    resumo.append("Total Vendas: R$ ").append(String.format("%.2f", totais.getOrDefault("totalVendas", 0.0))).append("\n");
    resumo.append("Total Consumos: R$ ").append(String.format("%.2f", totais.getOrDefault("totalConsumos", 0.0))).append("\n");
    resumo.append("Total Geral: R$ ").append(String.format("%.2f", 
        totais.getOrDefault("totalVendas", 0.0) + 
        totais.getOrDefault("totalConsumos", 0.0))).append("\n");
    
    return resumo.toString();
}
    
   
    
    
    public static String obterResumoConsumos(int idCaixa) throws SQLException {
     StringBuilder resumo = new StringBuilder();
    
    String sql = "SELECT c.id, q.numero as quarto, h.nome as hospede, " +
                 "p.nome as produto, c.quantidade, c.valor_unitario, " +
                 "c.desconto, c.valor as total, " +
                 "DATE_FORMAT(c.data_hora, '%d/%m/%Y %H:%i') as data_formatada " +
                 "FROM consumo c " +
                 "JOIN hospedagem hg ON c.id_hospedagem = hg.id " +
                 "JOIN quarto q ON hg.id_quarto = q.id " +
                 "JOIN hospede h ON c.hospede_id = h.id " +
                 "LEFT JOIN produto p ON c.produto_id = p.id " +
                 "WHERE hg.id_caixa = ? " +
                 "ORDER BY c.data_hora";
    
    try (Connection conn = getConexao();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idCaixa);
        ResultSet rs = ps.executeQuery();
        
        int contador = 1;
        double totalConsumos = 0;
        
        while (rs.next()) {
            resumo.append(contador).append(". Quarto ")
                  .append(rs.getString("quarto")).append(" - ")
                  .append(rs.getString("data_formatada")).append("\n")
                  .append("   Hóspede: ").append(rs.getString("hospede")).append("\n")
                  .append("   Produto: ").append(rs.getString("produto")).append(" - ")
                  .append(rs.getInt("quantidade")).append(" x R$ ")
                  .append(String.format("%.2f", rs.getDouble("valor_unitario"))).append("\n")
                  .append("   Desconto: R$ ").append(String.format("%.2f", rs.getDouble("desconto"))).append("\n")
                  .append("   Total: R$ ").append(String.format("%.2f", rs.getDouble("total"))).append("\n\n");
            
            totalConsumos += rs.getDouble("total");
            contador++;
        }
        
        if (contador == 1) {
            resumo.append("Nenhum consumo registrado neste caixa.\n");
        } else {
            resumo.append("\nTOTAL CONSUMOS: R$ ").append(String.format("%.2f", totalConsumos)).append("\n");
        }
    }
    
    return resumo.toString();
}
    
    
    public static String obterOutrasTransacoes(int idCaixa) throws SQLException {
    StringBuilder resumo = new StringBuilder();
    
    String sql = "SELECT tipo, descricao, valor, DATE_FORMAT(data, '%d/%m/%Y %H:%i') as data_formatada " +
                 "FROM transacoes_caixa " +
                 "WHERE id_caixa = ? AND tipo NOT IN ('VENDA', 'CONSUMO') " +
                 "ORDER BY data";
    
    try (Connection conn = getConexao();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idCaixa);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            String tipo = rs.getString("tipo");
            String sinal = tipo.equals("ENTRADA") ? "+" : "-";
            
            resumo.append(sinal).append(" R$ ").append(String.format("%.2f", rs.getDouble("valor")))
                 .append(" - ").append(rs.getString("descricao"))
                 .append(" (").append(rs.getString("data_formatada")).append(")\n");
        }
        
        if (resumo.length() == 0) {
            resumo.append("Nenhuma outra transação registrada.\n");
        }
    }
    
    return resumo.toString();
}
    
    
    
    public static List<Map<String, Object>> obterConsumosPorHospedagem(int idHospedagem) throws SQLException {
    List<Map<String, Object>> consumos = new ArrayList<>();
    
    String sql = "SELECT c.id, p.nome as produto, c.quantidade, " +
                 "c.valor_unitario, c.desconto, c.valor as total, " +
                 "DATE_FORMAT(c.data_hora, '%d/%m/%Y %H:%i') as data_hora " +
                 "FROM consumo c " +
                 "LEFT JOIN produtos p ON c.produto_id = p.id " +
                 "WHERE c.id_hospedagem = ? " +
                 "ORDER BY c.data_hora DESC";
    
    try (Connection conn = getConexao();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idHospedagem);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Map<String, Object> consumo = new HashMap<>();
            consumo.put("id", rs.getInt("id"));
            consumo.put("produto", rs.getString("produto"));
            consumo.put("quantidade", rs.getInt("quantidade"));
            consumo.put("valor_unitario", rs.getDouble("valor_unitario"));
            consumo.put("desconto", rs.getDouble("desconto"));
            consumo.put("total", rs.getDouble("total"));
            consumo.put("data_hora", rs.getString("data_hora"));
            
            consumos.add(consumo);
        }
    }
    
    return consumos;
}
    
    
    
    public static boolean cancelarConsumo(int idConsumo) throws SQLException {
    String sql = "DELETE FROM consumo WHERE id = ?";
    
    try (Connection conn = getConexao();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idConsumo);
        return ps.executeUpdate() > 0;
    }
}
    
    
    public static Map<String, Double> obterTotaisCaixa(int idCaixa) throws SQLException {
        Map<String, Double> totais = new HashMap<>();
    
    // Total de vendas
    String sqlVendas = "SELECT COALESCE(SUM(valor), 0) as total FROM vendas WHERE id_caixa = ?";
    // Total de consumos
    String sqlConsumos = "SELECT COALESCE(SUM(valor), 0) as total FROM consumos_quarto WHERE id_caixa = ?";
    // Total de entradas
    String sqlEntradas = "SELECT COALESCE(SUM(valor), 0) as total FROM transacoes_caixa " +
                         "WHERE id_caixa = ? AND tipo = 'ENTRADA'";
    // Total de saídas
    String sqlSaidas = "SELECT COALESCE(SUM(valor), 0) as total FROM transacoes_caixa " +
                       "WHERE id_caixa = ? AND tipo = 'SAIDA'";
    
    try (Connection conn = getConexao()) {
        // Obter total vendas
        try (PreparedStatement ps = conn.prepareStatement(sqlVendas)) {
            ps.setInt(1, idCaixa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) totais.put("totalVendas", rs.getDouble("total"));
        }
        
        // Obter total consumos
        try (PreparedStatement ps = conn.prepareStatement(sqlConsumos)) {
            ps.setInt(1, idCaixa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) totais.put("totalConsumos", rs.getDouble("total"));
        }
        
        // Obter total entradas
        try (PreparedStatement ps = conn.prepareStatement(sqlEntradas)) {
            ps.setInt(1, idCaixa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) totais.put("totalEntradas", rs.getDouble("total"));
        }
        
        // Obter total saídas
        try (PreparedStatement ps = conn.prepareStatement(sqlSaidas)) {
            ps.setInt(1, idCaixa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) totais.put("totalSaidas", rs.getDouble("total"));
        }
    }
    
    return totais;
    }
  
   
    
    public static Map<String, Double> obterTotaisPorFormaPagamento(int idCaixa) throws SQLException {
    Map<String, Double> totais = new HashMap<>();
    String sql = "SELECT forma_pagamento, SUM(valor) as total FROM vendas WHERE id_caixa = ? GROUP BY forma_pagamento";
    
    try (Connection conn = getConexao();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idCaixa);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            totais.put(rs.getString("forma_pagamento"), rs.getDouble("total"));
        }
    }
    return totais;
}

    
 
    public static double obterTotalVendas(int idCaixa) throws SQLException {
    String sql = "SELECT SUM(valor) as total FROM vendas WHERE id_caixa = ?";
    
    try (Connection conn = getConexao();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idCaixa);
        ResultSet rs = ps.executeQuery();
        return rs.next() ? rs.getDouble("total") : 0.0;
    }
}
}