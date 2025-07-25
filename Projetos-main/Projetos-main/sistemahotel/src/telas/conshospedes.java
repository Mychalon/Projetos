/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package telas;
import dao.HospedeDAO;
import dao.AcompanhanteDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Hospede;
import model.Acompanhante;
import dao.ConexaoBD;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author HOTEL FENIX
 */
public class conshospedes extends javax.swing.JInternalFrame {

    private Timer pesquisaTimer;
    /**
     * Creates new form conshospedes
     */
     public conshospedes() {
        initComponents();
        // Configura a pesquisa em tempo real
        configurarPesquisaInstantanea();
        carregarDadosHospedes();
        
        
    }

    private void carregarDadosHospedes() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Limpa a tabela
    
    
    // Simplesmente chama o método de pesquisa sem filtros
    jTextFieldPesquisa.setText("");
    jComboBoxFiltro.setSelectedIndex(0);
    pesquisarHospedes();
    
    try (Connection conexao = ConexaoBD.getConexao()) {
        HospedeDAO hospedeDAO = new HospedeDAO(conexao);
        List<Hospede> hospedes = hospedeDAO.listarTodos();
        
        for (Hospede hospede : hospedes) {
            // Busca acompanhantes
            List<Acompanhante> acompanhantes = AcompanhanteDAO.buscarPorHospede(hospede.getIdhospede());
            String nomesAcompanhantes = acompanhantes.stream()
                .map(Acompanhante::getNome)
                .collect(Collectors.joining(", "));
            
            // Trata placa nula ou vazia
            String placa = hospede.getPlacaVeiculo();
            if (placa == null || placa.trim().isEmpty()) {
                placa = "Não informada";
            }
            
            // Adiciona linha na tabela
            model.addRow(new Object[]{
                hospede.getNome(),
                hospede.getCpfHospede(),
                hospede.getTelefone(),
                placa, // Campo da placa
                nomesAcompanhantes,
                "Quarto " + hospede.getIdQuarto(),
                acompanhantes.size()
            });
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Erro ao carregar dados: " + ex.getMessage(), 
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
    }
    
    
       /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Voltar = new javax.swing.JButton();
        Atualizar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldPesquisa = new javax.swing.JTextField();
        jComboBoxFiltro = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setClosable(true);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "CPF", "Telefone", "Placa Veiculo", "Acompanhantes", "Quarto", "Qnt Acomp."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        Voltar.setText("Voltar");
        Voltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VoltarActionPerformed(evt);
            }
        });

        Atualizar.setText("Atualizar");
        Atualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtualizarActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        jComboBoxFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Nome", "CPF", "Placa", "Telefone", "Quarto" }));
        jComboBoxFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFiltroActionPerformed(evt);
            }
        });

        jButton1.setText("Pesquisar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jTextFieldPesquisa)
                            .addGap(18, 18, 18)
                            .addComponent(jComboBoxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(Voltar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(Atualizar))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 802, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Voltar)
                    .addComponent(Atualizar)
                    .addComponent(jButton1))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    
    
    
    
    
    
    private void AtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtualizarActionPerformed
        // TODO add your handling code here:
        carregarDadosHospedes();
           
    }//GEN-LAST:event_AtualizarActionPerformed

    
    
    private void pesquisarHospedes() {
    String termo = jTextFieldPesquisa.getText().trim().toLowerCase();
    String filtro = (String) jComboBoxFiltro.getSelectedItem();
    
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Limpa a tabela
    
    try (Connection conexao = ConexaoBD.getConexao()) {
        HospedeDAO hospedeDAO = new HospedeDAO(conexao);
        List<Hospede> hospedes = hospedeDAO.listarTodos();
        
        for (Hospede hospede : hospedes) {
            // Verifica se o hóspede corresponde ao critério de pesquisa
            boolean matches = false;
            
            if (termo.isEmpty()) {
                matches = true; // Mostra todos se não há termo de pesquisa
            } else {
                switch (filtro) {
                    case "Todos":
                        matches = hospede.getNome().toLowerCase().contains(termo) ||
                                 hospede.getCpfHospede().toLowerCase().contains(termo) ||
                                 hospede.getTelefone().toLowerCase().contains(termo) ||
                                 (hospede.getPlacaVeiculo()!= null && hospede.getPlacaVeiculo().toLowerCase().contains(termo)) ||
                                 String.valueOf(hospede.getIdQuarto()).contains(termo);
                        break;
                    case "Nome":
                        matches = hospede.getNome().toLowerCase().contains(termo);
                        break;
                    case "CPF":
                        matches = hospede.getCpfHospede().toLowerCase().contains(termo);
                        break;
                    case "Placa":
                        matches = hospede.getPlacaVeiculo()!= null && hospede.getPlacaVeiculo().toLowerCase().contains(termo);
                        break;
                    case "Telefone":
                        matches = hospede.getTelefone().toLowerCase().contains(termo);
                        break;
                    case "Quarto":
                        matches = String.valueOf(hospede.getIdQuarto()).contains(termo);
                        break;
                }
            }
            
            if (matches) {
                // Busca acompanhantes para este hóspede
                List<Acompanhante> acompanhantes = AcompanhanteDAO.buscarPorHospede(hospede.getIdhospede());
                String nomesAcompanhantes = acompanhantes.stream()
                    .map(Acompanhante::getNome)
                    .collect(Collectors.joining(", "));
                
                // Adiciona linha na tabela
                model.addRow(new Object[]{
                    hospede.getNome(),
                    hospede.getCpfHospede(),
                    hospede.getTelefone(),
                    hospede.getPlacaVeiculo()!= null ? hospede.getPlacaVeiculo(): "Não informada",
                    nomesAcompanhantes,
                    "Quarto " + hospede.getIdQuarto(),
                    acompanhantes.size()
                });
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Erro ao pesquisar hóspedes: " + ex.getMessage(), 
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
    private void VoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoltarActionPerformed
        // TODO add your handling code here:
       this.dispose();

    }//GEN-LAST:event_VoltarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        pesquisarHospedes();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBoxFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxFiltroActionPerformed
    
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Atualizar;
    private javax.swing.JButton Voltar;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBoxFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldPesquisa;
    // End of variables declaration//GEN-END:variables


    private void configurarPesquisaInstantanea() {
    // Adiciona o listener para pesquisa em tempo real
    jTextFieldPesquisa.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            agendarPesquisa();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            agendarPesquisa();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            agendarPesquisa();
        }
    });
    
    // Inicializa o timer
    pesquisaTimer = new Timer(300, e -> {
        pesquisarHospedes();
        pesquisaTimer.stop();
    });
    pesquisaTimer.setRepeats(false);
}

private void agendarPesquisa() {
    if (pesquisaTimer != null) {
        pesquisaTimer.stop();
    }
    pesquisaTimer.start();
}}