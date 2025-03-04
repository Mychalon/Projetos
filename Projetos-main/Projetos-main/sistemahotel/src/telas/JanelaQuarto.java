/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.text.MaskFormatter;
import model.Quarto;

public class JanelaQuarto extends JDialog {
    private Quarto quarto;
    
    // Componentes
    private JTextField txtNomeHospede;
    private JFormattedTextField txtCPF;
    private JTextField[] txtAcompanhantes = new JTextField[3];
    private JFormattedTextField txtTelefone1;
    private JFormattedTextField txtTelefone2;
    private JTextArea txtObservacao;
    private JFormattedTextField txtCheckIn;
    private JFormattedTextField txtCheckOut;

    public JanelaQuarto(JFrame parent, Quarto quarto) {
        super(parent, "Detalhes do Quarto: " + quarto.getNomeQuarto(), true);
        this.quarto = quarto;
        setSize(600, 500);
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de formulário com rolagem
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        
        // Campos principais
        formPanel.add(new JLabel("Nome do Hóspede:"));
        txtNomeHospede = new JTextField(quarto.getNomeHospede());
        formPanel.add(txtNomeHospede);

        formPanel.add(new JLabel("CPF:"));
        try {
            txtCPF = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
        } catch (Exception e) {
            txtCPF = new JFormattedTextField();
        }
        txtCPF.setText(quarto.getCpfHospede());
        formPanel.add(txtCPF);

        // Acompanhantes
        for(int i=0; i<3; i++){
            formPanel.add(new JLabel("Acompanhante " + (i+1) + ":"));
            txtAcompanhantes[i] = new JTextField();
            if(quarto.getAcompanhantes().size() > i) {
                txtAcompanhantes[i].setText(quarto.getAcompanhantes().get(i));
            }
            formPanel.add(txtAcompanhantes[i]);
        }

        // Telefones
        formPanel.add(new JLabel("Telefone 1:"));
        try {
            txtTelefone1 = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
        } catch (Exception e) {
            txtTelefone1 = new JFormattedTextField();
        }
        txtTelefone1.setText(quarto.getTelefone1());
        formPanel.add(txtTelefone1);

        formPanel.add(new JLabel("Telefone 2:"));
        try {
            txtTelefone2 = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
        } catch (Exception e) {
            txtTelefone2 = new JFormattedTextField();
        }
        txtTelefone2.setText(quarto.getTelefone2());
        formPanel.add(txtTelefone2);

        // Datas
        formPanel.add(new JLabel("Check-In (DD/MM/AAAA):"));
        try {
            txtCheckIn = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (Exception e) {
            txtCheckIn = new JFormattedTextField();
        }
        txtCheckIn.setText(quarto.getCheckIn());
        formPanel.add(txtCheckIn);

        formPanel.add(new JLabel("Check-Out (DD/MM/AAAA):"));
        try {
            txtCheckOut = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (Exception e) {
            txtCheckOut = new JFormattedTextField();
        }
        txtCheckOut.setText(quarto.getCheckOut());
        formPanel.add(txtCheckOut);

        // Observação
        formPanel.add(new JLabel("Observação:"));
        txtObservacao = new JTextArea(3, 20);
        txtObservacao.setText(quarto.getObservacao());
        formPanel.add(new JScrollPane(txtObservacao));

        // Botão Salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(this::salvarInformacoes);

        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        mainPanel.add(btnSalvar, BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    private void salvarInformacoes(ActionEvent e) {
        quarto.setNomeHospede(txtNomeHospede.getText());
        quarto.setCpfHospede(txtCPF.getText().replaceAll("[^0-9]", ""));
        
        quarto.getAcompanhantes().clear();
        for(JTextField acompanhante : txtAcompanhantes){
            if(!acompanhante.getText().isEmpty()) {
                quarto.getAcompanhantes().add(acompanhante.getText());
            }
        }
        
        quarto.setTelefone1(txtTelefone1.getText().replaceAll("[^0-9]", ""));
        quarto.setTelefone2(txtTelefone2.getText().replaceAll("[^0-9]", ""));
        quarto.setObservacao(txtObservacao.getText());
        quarto.setCheckIn(txtCheckIn.getText());
        quarto.setCheckOut(txtCheckOut.getText());
        
        JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");
        dispose();
    }
}
