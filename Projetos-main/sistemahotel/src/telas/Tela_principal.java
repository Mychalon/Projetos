/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Mycha
 */
    public class Tela_principal extends javax.swing.JFrame {

// Campo para nome do quarto
    // Painel onde os botões de quartos serão adicionados
        /**
     * Creates new form Tela_principal
     */
    public Tela_principal() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        
        
    }
    public void incluirTela(Component tela){
            teladefundo.add(tela);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        teladefundo = new javax.swing.JDesktopPane();
        quartosPainel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        JScrollPane = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        cadastro = new javax.swing.JMenu();
        Hospedes = new javax.swing.JMenu();
        Cadastrarhospede = new javax.swing.JMenuItem();
        Consultarhospede = new javax.swing.JMenuItem();
        Quartos = new javax.swing.JMenu();
        casdastrarquartos = new javax.swing.JMenuItem();
        Consultarquartos = new javax.swing.JMenuItem();
        Funcionarios = new javax.swing.JMenu();
        Cadastrarfuncionario = new javax.swing.JMenuItem();
        Consultarfuncionario = new javax.swing.JMenuItem();
        Produtos = new javax.swing.JMenu();
        Cadastrarprodutos = new javax.swing.JMenuItem();
        Consultarprodutos = new javax.swing.JMenuItem();
        Serviços = new javax.swing.JMenu();
        Cadastrarserviços = new javax.swing.JMenuItem();
        ConsultarServiços = new javax.swing.JMenuItem();
        Reserva = new javax.swing.JMenu();
        Cadastrarreserva = new javax.swing.JMenuItem();
        Consultarreserva = new javax.swing.JMenuItem();
        Vendas = new javax.swing.JMenu();
        Financeiro = new javax.swing.JMenu();
        contasapagar = new javax.swing.JMenu();
        cadastrarcontas = new javax.swing.JMenuItem();
        Consultarcontas = new javax.swing.JMenuItem();
        contasreceber = new javax.swing.JMenu();
        cadastrarrecebimentos = new javax.swing.JMenuItem();
        Consultarrecebimentos = new javax.swing.JMenuItem();
        balanço = new javax.swing.JMenuItem();
        Relatorios = new javax.swing.JMenu();
        jMenu16 = new javax.swing.JMenu();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenu17 = new javax.swing.JMenu();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenu18 = new javax.swing.JMenu();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        Segurança = new javax.swing.JMenu();
        Fazerbackup = new javax.swing.JMenuItem();
        Restaurarbackup = new javax.swing.JMenuItem();
        Ferramentas = new javax.swing.JMenu();
        Teladefundo = new javax.swing.JMenu();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        sobresistema = new javax.swing.JMenuItem();
        Logoff = new javax.swing.JMenu();
        Sair = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setText("Sistema Hotelaria Projeto PIM - Sistema desenvolvido por Mychalon de O. Silva - Todos os direitos reservados - 2024");

        jLabel2.setText("Usuário: ");

        jLabel3.setText("Nome");

        jLabel4.setText("Cargo:    ");

        jLabel5.setText("Nível ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jSeparator1))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        javax.swing.GroupLayout teladefundoLayout = new javax.swing.GroupLayout(teladefundo);
        teladefundo.setLayout(teladefundoLayout);
        teladefundoLayout.setHorizontalGroup(
            teladefundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 887, Short.MAX_VALUE)
        );
        teladefundoLayout.setVerticalGroup(
            teladefundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 513, Short.MAX_VALUE)
        );

        quartosPainel.setBackground(new java.awt.Color(153, 153, 255));

        jLabel6.setText("Quartos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 471, Short.MAX_VALUE)
        );

        JScrollPane.setViewportView(jPanel2);

        javax.swing.GroupLayout quartosPainelLayout = new javax.swing.GroupLayout(quartosPainel);
        quartosPainel.setLayout(quartosPainelLayout);
        quartosPainelLayout.setHorizontalGroup(
            quartosPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quartosPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(quartosPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, quartosPainelLayout.createSequentialGroup()
                        .addGap(0, 141, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(0, 139, Short.MAX_VALUE))
                    .addComponent(JScrollPane))
                .addContainerGap())
        );
        quartosPainelLayout.setVerticalGroup(
            quartosPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quartosPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JScrollPane)
                .addContainerGap())
        );

        cadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Cadastro menor.png"))); // NOI18N
        cadastro.setText("Cadastros   |");

        Hospedes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Hospede.png"))); // NOI18N
        Hospedes.setText("Hospedes ");

        Cadastrarhospede.setText("Cadastrar");
        Cadastrarhospede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CadastrarhospedeActionPerformed(evt);
            }
        });
        Hospedes.add(Cadastrarhospede);

        Consultarhospede.setText("Consultar ");
        Consultarhospede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarhospedeActionPerformed(evt);
            }
        });
        Hospedes.add(Consultarhospede);

        cadastro.add(Hospedes);

        Quartos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Quarto.png"))); // NOI18N
        Quartos.setText("Quartos ");

        casdastrarquartos.setText("Cadastrar ");
        casdastrarquartos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                casdastrarquartosActionPerformed(evt);
            }
        });
        Quartos.add(casdastrarquartos);

        Consultarquartos.setText("Consultar ");
        Consultarquartos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarquartosActionPerformed(evt);
            }
        });
        Quartos.add(Consultarquartos);

        cadastro.add(Quartos);

        Funcionarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Funcionarios.png"))); // NOI18N
        Funcionarios.setText("Funcionários ");

        Cadastrarfuncionario.setText("Cadastrar ");
        Cadastrarfuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CadastrarfuncionarioActionPerformed(evt);
            }
        });
        Funcionarios.add(Cadastrarfuncionario);

        Consultarfuncionario.setText("Consultar");
        Consultarfuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarfuncionarioActionPerformed(evt);
            }
        });
        Funcionarios.add(Consultarfuncionario);

        cadastro.add(Funcionarios);

        Produtos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Produto.png"))); // NOI18N
        Produtos.setText("Produtos");

        Cadastrarprodutos.setText("Cadastrar Prod.");
        Cadastrarprodutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CadastrarprodutosActionPerformed(evt);
            }
        });
        Produtos.add(Cadastrarprodutos);

        Consultarprodutos.setText("Consultar Prod.");
        Consultarprodutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarprodutosActionPerformed(evt);
            }
        });
        Produtos.add(Consultarprodutos);

        cadastro.add(Produtos);

        Serviços.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Serviços 2.png"))); // NOI18N
        Serviços.setText("Serviços");

        Cadastrarserviços.setText("Cadastrar Serviços");
        Cadastrarserviços.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CadastrarserviçosActionPerformed(evt);
            }
        });
        Serviços.add(Cadastrarserviços);

        ConsultarServiços.setText("Consultar Serviços");
        ConsultarServiços.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarServiçosActionPerformed(evt);
            }
        });
        Serviços.add(ConsultarServiços);

        cadastro.add(Serviços);

        jMenuBar1.add(cadastro);

        Reserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/calendario menor.png"))); // NOI18N
        Reserva.setText("Reservas   |");

        Cadastrarreserva.setText("Cadastrar Reserva");
        Reserva.add(Cadastrarreserva);

        Consultarreserva.setText("Consultar Reserva");
        Consultarreserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConsultarreservaActionPerformed(evt);
            }
        });
        Reserva.add(Consultarreserva);

        jMenuBar1.add(Reserva);

        Vendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Financeiro menor.png"))); // NOI18N
        Vendas.setText("Vendas   |");
        Vendas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VendasMouseClicked(evt);
            }
        });
        jMenuBar1.add(Vendas);

        Financeiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/images financeiro enor.png"))); // NOI18N
        Financeiro.setText("Financeiro   |");

        contasapagar.setText("Contas a Pagar");

        cadastrarcontas.setText("Cadastrar");
        contasapagar.add(cadastrarcontas);

        Consultarcontas.setText("Consultar ");
        contasapagar.add(Consultarcontas);

        Financeiro.add(contasapagar);

        contasreceber.setText("Contas a Receber");

        cadastrarrecebimentos.setText("Cadastrar");
        contasreceber.add(cadastrarrecebimentos);

        Consultarrecebimentos.setText("Consultar");
        contasreceber.add(Consultarrecebimentos);

        Financeiro.add(contasreceber);

        balanço.setText("Balanço");
        balanço.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balançoActionPerformed(evt);
            }
        });
        Financeiro.add(balanço);

        jMenuBar1.add(Financeiro);

        Relatorios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/imagem relatorio menor.png"))); // NOI18N
        Relatorios.setText("Relátorios   |");

        jMenu16.setText("Financeiro");

        jMenuItem23.setText("Contas a Pagar");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem23);

        jMenuItem27.setText("Contas a Receber");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem27);

        jMenuItem28.setText("Balanço");
        jMenu16.add(jMenuItem28);

        Relatorios.add(jMenu16);

        jMenu17.setText("Reservas");

        jMenuItem24.setText("Quartos Disponíveis ");
        jMenu17.add(jMenuItem24);

        jMenuItem25.setText("Quartos Ocupados");
        jMenu17.add(jMenuItem25);

        jMenuItem26.setText("Reservas Futuras");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu17.add(jMenuItem26);

        Relatorios.add(jMenu17);

        jMenu18.setText("Vendas ");
        Relatorios.add(jMenu18);

        jMenuItem21.setText("Hospedes ");
        Relatorios.add(jMenuItem21);

        jMenuItem22.setText("Fucionários");
        Relatorios.add(jMenuItem22);

        jMenuBar1.add(Relatorios);

        Segurança.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/segurança menor.png"))); // NOI18N
        Segurança.setText("Segurança   |");

        Fazerbackup.setText("Fazer Backup");
        Segurança.add(Fazerbackup);

        Restaurarbackup.setText("Restaurar Backup");
        Segurança.add(Restaurarbackup);

        jMenuBar1.add(Segurança);

        Ferramentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ferramenta.png"))); // NOI18N
        Ferramentas.setText("Ferramentas   |");

        Teladefundo.setText("Tela de Fundo");

        jMenuItem32.setText("Add Imagem");
        Teladefundo.add(jMenuItem32);

        jMenuItem33.setText("Remover Imagem");
        Teladefundo.add(jMenuItem33);

        jMenuItem34.setText("Restaurar Padrão");
        Teladefundo.add(jMenuItem34);

        Ferramentas.add(Teladefundo);

        sobresistema.setText("Sobre o Sistema");
        Ferramentas.add(sobresistema);

        jMenuBar1.add(Ferramentas);

        Logoff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/logoff.png"))); // NOI18N
        Logoff.setText("Logoff   |");
        jMenuBar1.add(Logoff);

        Sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/saida.png"))); // NOI18N
        Sair.setText("Sair ");
        Sair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SairMouseClicked(evt);
            }
        });
        jMenuBar1.add(Sair);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(teladefundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quartosPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(teladefundo)
                    .addComponent(quartosPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CadastrarhospedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CadastrarhospedeActionPerformed
        // TODO add your handling code here:
        cadastrarhospede tela = new cadastrarhospede();
        teladefundo.add(tela);
        tela.setVisible(true);
    }//GEN-LAST:event_CadastrarhospedeActionPerformed

    private void ConsultarreservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarreservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConsultarreservaActionPerformed

    private void balançoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balançoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_balançoActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void SairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SairMouseClicked
        // TODO add your handling code here: 
                JFrame frame = new JFrame("EXIT");
        if(JOptionPane.showConfirmDialog(frame,"deseja sair mesmo?", "EXIT",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION)
        {
            System.exit(0);
        }
    }//GEN-LAST:event_SairMouseClicked

    private void VendasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VendasMouseClicked
        // TODO add your handling code here:
        Vendaprodutos vendas = new Vendaprodutos();
      teladefundo.add(vendas);
      vendas.setVisible(true);
      
    }//GEN-LAST:event_VendasMouseClicked

    private void casdastrarquartosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_casdastrarquartosActionPerformed
        // TODO add your handling code here:
        
        cadquarto quartos = new cadquarto();
        teladefundo.add(quartos);
        quartos.setVisible(true);
        
        
    }//GEN-LAST:event_casdastrarquartosActionPerformed

    private void CadastrarfuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CadastrarfuncionarioActionPerformed
        // TODO add your handling code here:
        
        cadfuncionarios funcionario = new cadfuncionarios ();
        teladefundo.add(funcionario);
        funcionario.setVisible(true);
                
        
    }//GEN-LAST:event_CadastrarfuncionarioActionPerformed

    private void CadastrarprodutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CadastrarprodutosActionPerformed
        cadprodutos produtos = new cadprodutos();
        teladefundo.add(produtos);
        produtos.setVisible(true);



    }//GEN-LAST:event_CadastrarprodutosActionPerformed

    private void CadastrarserviçosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CadastrarserviçosActionPerformed
        // TODO add your handling code here:
        cadserviços serviços = new cadserviços();
        teladefundo.add(serviços);
        serviços.setVisible(true);
        
        
        
    }//GEN-LAST:event_CadastrarserviçosActionPerformed

    private void ConsultarhospedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarhospedeActionPerformed
        // TODO add your handling code here:
        conshospedes consultarh = new conshospedes();
        teladefundo.add(consultarh);
        consultarh.setVisible(true);
        
        
        
    }//GEN-LAST:event_ConsultarhospedeActionPerformed

    private void ConsultarquartosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarquartosActionPerformed
        // TODO add your handling code here:
        consquartos consultarq = new consquartos();
        teladefundo.add(consultarq);
        consultarq.setVisible(true);


    }//GEN-LAST:event_ConsultarquartosActionPerformed

    private void ConsultarfuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarfuncionarioActionPerformed
        // TODO add your handling code here:
        consfuncionario consultarf = new consfuncionario();
        teladefundo.add(consultarf);
        consultarf.setVisible(true);
        
        



    }//GEN-LAST:event_ConsultarfuncionarioActionPerformed

    private void ConsultarprodutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarprodutosActionPerformed
        // TODO add your handling code here:

        consprodutos consultarp = new consprodutos();
        teladefundo.add(consultarp);
        consultarp.setVisible(true);

    }//GEN-LAST:event_ConsultarprodutosActionPerformed

    private void ConsultarServiçosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConsultarServiçosActionPerformed
        // TODO add your handling code here:

        consserviços consultars = new consserviços();
        teladefundo.add(consultars);
        consultars.setVisible(true);


    }//GEN-LAST:event_ConsultarServiçosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Cadastrarfuncionario;
    private javax.swing.JMenuItem Cadastrarhospede;
    private javax.swing.JMenuItem Cadastrarprodutos;
    private javax.swing.JMenuItem Cadastrarreserva;
    private javax.swing.JMenuItem Cadastrarserviços;
    private javax.swing.JMenuItem ConsultarServiços;
    private javax.swing.JMenuItem Consultarcontas;
    private javax.swing.JMenuItem Consultarfuncionario;
    private javax.swing.JMenuItem Consultarhospede;
    private javax.swing.JMenuItem Consultarprodutos;
    private javax.swing.JMenuItem Consultarquartos;
    private javax.swing.JMenuItem Consultarrecebimentos;
    private javax.swing.JMenuItem Consultarreserva;
    private javax.swing.JMenuItem Fazerbackup;
    private javax.swing.JMenu Ferramentas;
    private javax.swing.JMenu Financeiro;
    private javax.swing.JMenu Funcionarios;
    private javax.swing.JMenu Hospedes;
    private javax.swing.JScrollPane JScrollPane;
    private javax.swing.JMenu Logoff;
    private javax.swing.JMenu Produtos;
    private javax.swing.JMenu Quartos;
    private javax.swing.JMenu Relatorios;
    private javax.swing.JMenu Reserva;
    private javax.swing.JMenuItem Restaurarbackup;
    private javax.swing.JMenu Sair;
    private javax.swing.JMenu Segurança;
    private javax.swing.JMenu Serviços;
    private javax.swing.JMenu Teladefundo;
    private javax.swing.JMenu Vendas;
    private javax.swing.JMenuItem balanço;
    private javax.swing.JMenuItem cadastrarcontas;
    private javax.swing.JMenuItem cadastrarrecebimentos;
    private javax.swing.JMenu cadastro;
    private javax.swing.JMenuItem casdastrarquartos;
    private javax.swing.JMenu contasapagar;
    private javax.swing.JMenu contasreceber;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu16;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu18;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel quartosPainel;
    private javax.swing.JMenuItem sobresistema;
    private javax.swing.JDesktopPane teladefundo;
    // End of variables declaration//GEN-END:variables

    private void jButton2ActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
