package telas;

import dao.ProdutoDAO;
import model.Produto;
import javax.swing.JOptionPane;

public class EditarProduto extends javax.swing.JInternalFrame {
    private Produto produto;
    
    // Componentes da interface
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblQuantidade;
    private javax.swing.JLabel lblPreco;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtPreco;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnCancelar;

    public EditarProduto(Produto produto) {
        initComponents();
        this.produto = produto;
        carregarDados();
    }

    private void initComponents() {
        // Configuração da janela
        setClosable(true);
        setTitle("Editar Produto");
        setResizable(true);
        
        // Criação dos componentes
        lblCodigo = new javax.swing.JLabel("Código:");
        lblNome = new javax.swing.JLabel("Nome:");
        lblQuantidade = new javax.swing.JLabel("Quantidade:");
        lblPreco = new javax.swing.JLabel("Preço:");
        
        txtCodigo = new javax.swing.JTextField(20);
        txtNome = new javax.swing.JTextField(20);
        txtQuantidade = new javax.swing.JTextField(10);
        txtPreco = new javax.swing.JTextField(10);
        
        btnSalvar = new javax.swing.JButton("Salvar");
        btnCancelar = new javax.swing.JButton("Cancelar");
        
        // Configuração do layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        // Configuração do grupo horizontal
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQuantidade)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPreco)
                    .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        // Configuração do grupo vertical
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCodigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblQuantidade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPreco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );
        
        // Configuração dos listeners
        btnSalvar.addActionListener(this::salvarActionPerformed);
        btnCancelar.addActionListener(e -> this.dispose());
        
        pack();
    }

    private void carregarDados() {
        txtCodigo.setText(produto.getCodigo());
        txtNome.setText(produto.getNome());
        txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
        txtPreco.setText(String.valueOf(produto.getPreco()));
    }

    private void salvarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Atualiza o objeto produto com os novos valores
            produto.setCodigo(txtCodigo.getText());
            produto.setNome(txtNome.getText());
            produto.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
            produto.setPreco(Double.parseDouble(txtPreco.getText()));
            
            // Atualiza no banco
            if (ProdutoDAO.atualizarProduto(produto)) {
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao atualizar produto");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Valores numéricos inválidos!\n" +
                "Quantidade deve ser inteiro\n" +
                "Preço deve ser decimal",
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao atualizar: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}