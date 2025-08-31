/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import dao.CaixaDAO;
import telas.consprodutos;
import model.Produto;
import dao.ProdutoDAO;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
/**
 *
 * @author Mycha
 */
public class Vendaprodutos extends javax.swing.JInternalFrame {

    private consprodutos Consprodutos;
    private ArrayList<String> descricaoItens = new ArrayList<>();
    private double valorTotalVenda;
    private ProdutoDAO produtoDAO;
    private javax.swing.JTextArea descricaoItensTextField;
    private javax.swing.JScrollPane scrollPane; // Adicione esta linha
    private JComboBox<String> formasPagamento;
    private int idFuncionario; // ID do funcionário logado
    private int idCaixa = -1; // Inicializa como -1 (nenhum caixa)

 

    public Vendaprodutos(int idFuncionario) {
    try {
        // Inicializa componentes da interface
        initComponents();
        
        // Configurações iniciais
        this.idFuncionario = idFuncionario;
        this.valorTotalVenda = 0.0;
        this.descricaoItens = new ArrayList<>();
        
        valorTotalItem = new javax.swing.JTextField();
        
        // Configura componentes
        configurarAreaDescricao();
        configurarFormasPagamento();
        
        // Inicializa DAO
        produtoDAO = new ProdutoDAO();
        
        //Inicializa Consprodutos
        Consprodutos = new consprodutos();
        
        
        
        // Verifica caixa aberto
        verificarCaixaAberto();
        
       valorTotalItem.setVisible(false);
        // Configura eventos
        configurarEventos();
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, 
            "Erro ao inicializar tela de vendas: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
        // Desativa botões importantes
        consultarProduto.setEnabled(false);
        vendaFeita.setEnabled(false);
    }
}
   
   
   
       private void verificarCaixaAberto() {
        try {
            idCaixa = CaixaDAO.obterCaixaAberto(idFuncionario);
            if (idCaixa == -1) {
                int resposta = JOptionPane.showConfirmDialog(
                    this, 
                    "Nenhum caixa aberto. Deseja abrir um novo caixa?", 
                    "Caixa Fechado", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if (resposta == JOptionPane.YES_OPTION) {
                    abrirNovoCaixa();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao verificar caixa: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirNovoCaixa() {
        try {
            // Valor de abertura padrão pode ser configurado ou solicitado ao usuário
            double valorAbertura = 0.0;
            idCaixa = CaixaDAO.abrirCaixa(idFuncionario, valorAbertura);
            
            JOptionPane.showMessageDialog(this, 
                "Novo caixa #" + idCaixa + " aberto com sucesso!",
                "Caixa Aberto", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao abrir caixa: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
   
   
   
   
   
   
   private void configurarFormasPagamento() {
    formasPagamento = new JComboBox<>(new String[]{"Dinheiro", "Cartão Débito", "Cartão Crédito", "PIX"});
    formasPagamento.setSelectedIndex(0);
    // Adicione o combobox ao painel de botões
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
        
        // Crie um campo separado para mostrar o valor total
        valorTotalItem.setText(String.format("R$ %.2f", valorTotal));
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "Valores inválidos! Verifique quantidade e preço unitário.",
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
    }

    private void adicionarProdutoAVenda() {
       try {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o código do produto!");
            return;
        }

        int quantidade;
        double precoUnitario;
        
        try {
            quantidade = Integer.parseInt(txtQuantidade.getText());
            precoUnitario = Double.parseDouble(valorUnitario.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade ou preço inválido!");
            return;
        }

        Produto produto = produtoDAO.buscarPorCodigo(codigo);
        if (produto != null) {
            // Verificar estoque
            if (produto.getQuantidade()< quantidade) {
                JOptionPane.showMessageDialog(this, 
                    "Estoque insuficiente! Disponível: " + produto.getQuantidade(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double valorTotalItem = quantidade * precoUnitario;
            String descricaoItem = String.format("%s - %d x R$ %.2f = R$ %.2f", 
                produto.getNome(), quantidade, precoUnitario, valorTotalItem);
            
            descricaoItens.add(descricaoItem);
            atualizarDescricao();
            
            valorTotalVenda += valorTotalItem;
            valorTotal.setText(String.format("R$ %.2f", valorTotalVenda));
            quantidadedeItens.setText(String.valueOf(descricaoItens.size()));

            // Atualizar estoque no banco de dados
            produtoDAO.atualizarEstoque(produto.getId(), produto.getQuantidade()- quantidade);

            // Limpa campos para próximo produto
            limparCamposProduto();
        } else {
            JOptionPane.showMessageDialog(this, "Produto não encontrado!");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, 
            "Erro ao acessar banco de dados: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

private void limparCamposProduto() {
    txtCodigo.setText("");
    txtnomeProduto.setText("");
    valorUnitario.setText("");
    txtQuantidade.setText("");
    valorTotalItem.setText("");
    txtCodigo.requestFocus();
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
        valorTotalItem = new javax.swing.JTextField();
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

        quantidadedeItens.setEditable(false);

        jLabel7.setText("Valor Total (R$)");

        valorTotal.setEditable(false);
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

        jLabel1.setText("Código");

        jLabel2.setText("Quantidade");

        jLabel3.setText("Preço Unitario");

        txtnomeProduto.setEditable(false);

        txtQuantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidadeActionPerformed(evt);
            }
        });

        valorUnitario.setEditable(false);
        valorUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valorUnitarioActionPerformed(evt);
            }
        });

        jLabel6.setText("Nome do Produto");

        valorTotalItem.setEditable(false);
        valorTotalItem.setBackground(new java.awt.Color(255, 175, 41));
        valorTotalItem.setBorder(null);

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
                        .addGap(92, 92, 92)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(92, 92, 92)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(valorUnitario)))
                    .addComponent(jLabel6)
                    .addComponent(txtnomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 636, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(valorTotalItem, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(valorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtnomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(valorTotalItem))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Descrição"));

        descricaoItensTextArea.setEditable(false);
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
 
        Consprodutos  = new consprodutos();
        
        Consprodutos.setVisible(true);
        
        
    }//GEN-LAST:event_consultarProdutoActionPerformed

    private void vendaFeitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendaFeitaActionPerformed
 if (descricaoItens.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nenhum produto selecionado para venda!");
        return;
    }

    // Verificar se temos um caixa válido
    if (idCaixa == -1) {
        JOptionPane.showMessageDialog(this, 
            "Nenhum caixa aberto para registrar a venda!",
            "Erro", JOptionPane.ERROR_MESSAGE);
        verificarCaixaAberto(); // Oferece a opção de abrir um novo caixa
        return;
    }

    String formaPagamento = (String) formasPagamento.getSelectedItem();
    
    try {
        // Verificar novamente o status do caixa
        if (!CaixaDAO.verificarCaixaAberto(idCaixa)) {
            JOptionPane.showMessageDialog(this, 
                "Caixa #" + idCaixa + " foi fechado! Por favor, abra um novo caixa.",
                "Erro", JOptionPane.ERROR_MESSAGE);
            verificarCaixaAberto();
            return;
        }
        
        // Registrar venda no caixa
        boolean sucesso = CaixaDAO.registrarVenda(
            idCaixa, 
            valorTotalVenda, 
            formaPagamento, 
            String.join("\n", descricaoItens)
        );
        
        if (sucesso) {
            // Gerar comprovante
            StringBuilder resumo = new StringBuilder("=== COMPROVANTE DE VENDA ===\n");
            resumo.append("Data: ").append(new java.util.Date()).append("\n");
            resumo.append("Caixa: #").append(idCaixa).append("\n");
            resumo.append("Itens:\n");
            
            for (String item : descricaoItens) {
                resumo.append("- ").append(item).append("\n");
            }
            
            resumo.append("----------------------------\n");
            resumo.append("Total: R$ ").append(String.format("%.2f", valorTotalVenda)).append("\n");
            resumo.append("Forma de Pagamento: ").append(formaPagamento).append("\n");
            
            // Mostrar comprovante
            JOptionPane.showMessageDialog(this, resumo.toString(), "Venda Concluída", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpar campos
            limparCamposVenda();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao registrar venda no caixa!");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, 
            "Erro no banco de dados: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
    
    }//GEN-LAST:event_vendaFeitaActionPerformed

    
    private void limparCamposVenda() {
    descricaoItens.clear();
    valorTotalVenda = 0;
    quantidadedeItens.setText("0");
    valorTotal.setText("R$ 0.00");
    descricaoItensTextArea.setText("");
    txtCodigo.setText("");
    txtnomeProduto.setText("");
    valorUnitario.setText("");
    txtQuantidade.setText("");
    valorTotalItem.setText("");
    txtCodigo.requestFocus();
}
    
    
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
    private javax.swing.JTextField valorTotalItem;
    private javax.swing.JTextField valorUnitario;
    private javax.swing.JButton vendaFeita;
    // End of variables declaration//GEN-END:variables

   
    private double calcularValorTotalProduto(int quantidade, double precoUnitario) {
    return quantidade * precoUnitario; // Retorna o valor total do produto
 }

}
