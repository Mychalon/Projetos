/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import model.Produto;
import dao.ProdutoDAO;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author Mycha
 */
public class Vendaprodutos extends javax.swing.JInternalFrame {

    private ArrayList<String> descricaoItens = new ArrayList<>();
    private double valorTotalVenda;
    private ProdutoDAO produtoDAO;
    private javax.swing.JTextArea descricaoItensTextField;
    private javax.swing.JScrollPane scrollPane; // Adicione esta linha

   public Vendaprodutos() {
    super();
    initComponents(); // Primeiro inicializa todos os componentes
    
    // Agora podemos configurar a área de descrição
    configurarAreaDescricao();
    
    try {
        produtoDAO = new ProdutoDAO();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao conectar: " + ex.getMessage());
        consultarProduto.setEnabled(false);
        vendaFeita.setEnabled(false);
    }
    
    configurarEventos();
}

   private void configurarAreaDescricao() {
    if (jPanel3 == null) {
        jPanel3 = new javax.swing.JPanel();
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Descrição"));
    }

    // Cria a JTextArea do Swing
    descricaoItensTextArea = new javax.swing.JTextArea();
    scrollPane = new javax.swing.JScrollPane(descricaoItensTextArea);
    
    // Configurações básicas
    descricaoItensTextArea.setEditable(false);
    descricaoItensTextArea.setLineWrap(true);
    descricaoItensTextArea.setWrapStyleWord(true);
    
    // Remove todos os componentes existentes
    jPanel3.removeAll();
    jPanel3.setLayout(new java.awt.BorderLayout());
    jPanel3.add(scrollPane, java.awt.BorderLayout.CENTER);
    
    // Atualiza a interface
    jPanel3.revalidate();
    jPanel3.repaint();
}
   
   
   
private void configurarEventos() {
    // Consulta produto ao pressionar Enter no campo de código
    txtCodigo.addActionListener(e -> consultarProdutoPorCodigo());
    
    // Adiciona produto ao pressionar Enter no campo de quantidade
    txtQuantidade.addActionListener(e -> {
        calcularValorTotalItem();
        adicionarProdutoAVenda();
    });
    
    // Calcula valor total quando o preço ou quantidade muda
    valorUnitario.addActionListener(e -> calcularValorTotalItem());
    txtQuantidade.addActionListener(e -> calcularValorTotalItem());
}

    private void consultarProdutoPorCodigo() {
        String codigo = txtCodigo.getText().trim();
        
        if (!codigo.isEmpty()) {
            try {
                Produto produto = produtoDAO.buscarPorCodigo(codigo);
                
                if (produto != null) {
                    txtnomeProduto.setText(produto.getNome());
                    valorUnitario.setText(String.format("%.2f", produto.getPreco()));
                    txtQuantidade.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(this, "Produto não encontrado!");
                    txtCodigo.setText("");
                    txtCodigo.requestFocus();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar produto: " + ex.getMessage());
            }
        }
    }

    private void calcularValorTotalItem() {
        try {
            int quantidade = Integer.parseInt(txtQuantidade.getText());
            double precoUnitario = Double.parseDouble(valorUnitario.getText().replace(",", "."));
            double valorTotal = quantidade * precoUnitario;
            valorUnitario.setText(String.format("%.2f", valorTotal));
        } catch (NumberFormatException e) {
            // Ignora erros de formatação
        }
    }

    private void adicionarProdutoAVenda() {
        try {
            String codigo = txtCodigo.getText();
            int quantidade = Integer.parseInt(txtQuantidade.getText());
            double precoUnitario = Double.parseDouble(valorUnitario.getText().replace(",", "."));
            double valorTotalItem = quantidade * precoUnitario;

            Produto produto = produtoDAO.buscarPorCodigo(codigo);
            if (produto != null) {
                String descricaoItem = String.format("%s - %d x R$ %.2f = R$ %.2f", 
                    produto.getNome(), quantidade, precoUnitario, valorTotalItem);
                
                descricaoItens.add(descricaoItem);
                atualizarDescricao();
                
                valorTotalVenda += valorTotalItem;
                valorTotal.setText(String.format("R$ %.2f", valorTotalVenda));
                quantidadedeItens.setText(String.valueOf(descricaoItens.size()));

                // Limpa campos para próximo produto
                txtCodigo.setText("");
                txtnomeProduto.setText("");
                valorUnitario.setText("");
                txtQuantidade.setText("");
                valorUnitario.setText("");
                txtCodigo.requestFocus();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade ou preço inválido!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar produto: " + ex.getMessage());
        }
    }

    private void atualizarDescricao() {
    StringBuilder descricao = new StringBuilder();
    for (String item : descricaoItens) {
        descricao.append(item).append("\n");
    }
    descricaoItensTextArea.setText(descricao.toString());
    descricaoItensTextArea.setCaretPosition(descricaoItensTextArea.getDocument().getLength());
}
    
    
    



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        quantidadedeItens = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        valorTotal = new javax.swing.JTextField();
        consultarProduto = new javax.swing.JButton();
        cancelarProduto = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtnomeProduto = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtQuantidade = new javax.swing.JTextField();
        valorUnitario = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        descricaoItensTextArea = new javax.swing.JTextArea();
        Voltar = new javax.swing.JButton();
        vendaFeita = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Vendas");

        jPanel2.setBackground(new java.awt.Color(109, 109, 109));

        jLabel5.setBackground(new java.awt.Color(90, 90, 90));
        jLabel5.setText("Quant. Itens");

        jLabel7.setText("Valor Total (R$)");

        valorTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valorTotalActionPerformed(evt);
            }
        });

        consultarProduto.setText("Consultar");
        consultarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarProdutoActionPerformed(evt);
            }
        });

        cancelarProduto.setText("Cancelar Prod.");
        cancelarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(consultarProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(cancelarProduto))
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(quantidadedeItens)
                    .addComponent(valorTotal))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quantidadedeItens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(valorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(consultarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        jPanel1.setBackground(new java.awt.Color(255, 175, 41));

        jLabel1.setText("código");

        jLabel2.setText("Quantidade");

        jLabel3.setText("Preço Unitario");

        txtQuantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidadeActionPerformed(evt);
            }
        });

        valorUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valorUnitarioActionPerformed(evt);
            }
        });

        jLabel6.setText("Nome do Produto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(91, 91, 91)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(valorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addComponent(jLabel6)
                    .addComponent(txtnomeProduto))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Descrição"));

        descricaoItensTextArea.setColumns(20);
        descricaoItensTextArea.setRows(5);
        jScrollPane1.setViewportView(descricaoItensTextArea);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        Voltar.setText("Voltar");
        Voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VoltarActionPerformed(evt);
            }
        });

        vendaFeita.setText("Finalizar");
        vendaFeita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendaFeitaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Voltar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(vendaFeita)
                                .addGap(17, 17, 17)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vendaFeita)
                            .addComponent(Voltar)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void valorUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valorUnitarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valorUnitarioActionPerformed

    private void valorTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valorTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valorTotalActionPerformed

    private void cancelarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarProdutoActionPerformed
        if (!descricaoItens.isEmpty()) {
        // Remove o último item da descrição
        descricaoItens.remove(descricaoItens.size() - 1);

        // Atualiza a descrição na interface
        atualizarDescricao();

        // Atualiza a quantidade de itens
        quantidadedeItens.setText(String.valueOf(descricaoItens.size()));

        // Recalcula o valor total da venda
        valorTotalVenda = 0;
        for (String item : descricaoItens) {
            String[] partes = item.split(" - Valor: R\\$ ");
            String valorStr = partes[1].replace(",", "."); // Substitui vírgula por ponto
            valorTotalVenda += Double.parseDouble(valorStr); // Converte para double
        }
        valorTotal.setText(String.format("R$ %.2f", valorTotalVenda));
    } else {
        JOptionPane.showMessageDialog(this, "Nenhum item para cancelar!");
    }
    }//GEN-LAST:event_cancelarProdutoActionPerformed

    private void VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoltarActionPerformed

        setVisible(false);
    }//GEN-LAST:event_VoltarActionPerformed

    private void consultarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultarProdutoActionPerformed
    consprodutos consulta = new consprodutos(); // Cria a tela de consulta
    consulta.setVisible(true); // Torna a tela visível
    }//GEN-LAST:event_consultarProdutoActionPerformed

    private void vendaFeitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendaFeitaActionPerformed

    // Finalizar a venda
    if (descricaoItens.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nenhum produto selecionado para venda!");
    } else {
        // Exibir resumo da venda
        StringBuilder resumo = new StringBuilder("Resumo da Venda:\n");
        for (String item : descricaoItens) {
            resumo.append(item).append("\n");
        }
        resumo.append("Valor Total da Venda: R$ ").append(String.format("%.2f", (double) valorTotalVenda));

        // Exibir o resumo em uma mensagem
        JOptionPane.showMessageDialog(this, resumo.toString());

        // Simular a impressão do resumo
        imprimirResumoVenda(resumo.toString());

        // Limpar a lista de produtos da venda
        descricaoItens.clear();
        valorTotalVenda = 0;
        quantidadedeItens.setText("0");
        valorTotal.setText("R$ 0.00");
        descricaoItensTextField.setText(""); // Limpa o campo de descrição
    }
}
private void imprimirResumoVenda(String resumo) {
    // Simulação de impressão (pode ser substituída por uma impressão real)
    System.out.println("=== Impressão do Resumo da Venda ===");
    System.out.println(resumo);
    System.out.println("===================================");
    }//GEN-LAST:event_vendaFeitaActionPerformed

    private void txtQuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantidadeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Voltar;
    private javax.swing.JButton cancelarProduto;
    private javax.swing.JButton consultarProduto;
    private javax.swing.JTextArea descricaoItensTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField quantidadedeItens;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtnomeProduto;
    private javax.swing.JTextField valorTotal;
    private javax.swing.JTextField valorUnitario;
    private javax.swing.JButton vendaFeita;
    // End of variables declaration//GEN-END:variables

   
    private double calcularValorTotalProduto(int quantidade, double precoUnitario) {
    return quantidade * precoUnitario; // Retorna o valor total do produto
 }

}
