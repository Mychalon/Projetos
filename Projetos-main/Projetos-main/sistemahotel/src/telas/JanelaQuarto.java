package telas;

import dao.ConexaoBD;
import dao.HospedeDAO;
import dao.QuartoDAO;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.MaskFormatter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import dao.HospedagemDAO;
import model.Hospede;







public class JanelaQuarto extends JPanel {
    private Quarto quarto;
    private JDialog parentDialog;
    private Tela_principal telaPrincipal; // ÚNICA declaração
    
    // Componentes do formulário
    private JTextField txtNomeHospede;
    private JFormattedTextField txtCPF;
    private final JTextField[] txtAcompanhantes = new JTextField[3];
    private JFormattedTextField telefone;
    private Connection conexao;
    private JFormattedTextField Email;
    
   

    
    
   public JanelaQuarto(Quarto quarto, JDialog parent, Tela_principal telaPrincipal) {
    this.quarto = quarto;
    this.parentDialog = parent;
    this.telaPrincipal = telaPrincipal;
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

        // Telefones
        formPanel.add(new JLabel("Telefone:"));
        try {
            telefone = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
        } catch (Exception e) {
            telefone = new JFormattedTextField();
        }
        telefone.setText(quarto.getTelefone());
        formPanel.add(telefone);

        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);

        // Painel de botões (SALVAR e FECHAR)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener((ActionEvent e) -> {
            try {
                salvarInformacoes();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(JanelaQuarto.this,
                        "Erro no banco de dados: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(JanelaQuarto.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> fecharJanela());
        
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnFechar);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
    }

   private void salvarInformacoes() throws IOException, SQLException {
    if (!validarCampos()) {
        return;
    }

    try (Connection conexao = ConexaoBD.getConexao()) {
        conexao.setAutoCommit(false);
        
        try {
            // 1. Salva o hóspede
            Hospede hospede = new Hospede();
            hospede.setNome(txtNomeHospede.getText().trim());
            hospede.setCpf(txtCPF.getText().replaceAll("[^0-9]", ""));
            hospede.setTelefone(telefone.getText());
            hospede.setCheckIn(new Date());
            hospede.setIdQuarto(quarto.getIdQuarto());
            
            HospedeDAO hospedeDAO = new HospedeDAO(conexao);
            int idHospede = hospedeDAO.salvar(hospede);
            
            // 2. Atualiza o quarto
            quarto.setNomeHospede(hospede.getNome());
            quarto.setCpfHospede(hospede.getCpf());
            quarto.setTelefone(hospede.getTelefone());
            quarto.setStatus("hospedado");
            
            if (!QuartoDAO.atualizarStatusQuarto(quarto)) {
                throw new SQLException("Falha ao atualizar quarto");
            }
            
            // 3. Registra a hospedagem (obter idFuncionario da sessão)
            int idFuncionario = obterIdFuncionarioLogado(); // Você precisa implementar isso
            HospedagemDAO hospedagemDAO = new HospedagemDAO(conexao);
            hospedagemDAO.registrarHospedagem(quarto.getIdQuarto(), idHospede, idFuncionario);
            
            conexao.commit();
            
            // Atualiza a interface
            telaPrincipal.atualizarBotaoQuarto(quarto);
            JOptionPane.showMessageDialog(this, "Hospedagem registrada com sucesso!");
            fecharJanela();
            
        } catch (SQLException e) {
            conexao.rollback();
            JOptionPane.showMessageDialog(this, 
                "Erro ao registrar hospedagem: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }
}
    
   public void realizarCheckout(int idHospedagem, int idQuarto) {
    try (Connection conexao = ConexaoBD.getConexao()) {
        conexao.setAutoCommit(false);
        
        try {
            // 1. Atualiza hospedagem com check-out
            HospedagemDAO hospedagemDAO = new HospedagemDAO(conexao);
            hospedagemDAO.registrarCheckout(idHospedagem);
            
            // 2. Atualiza status do hóspede
            HospedeDAO hospedeDAO = new HospedeDAO(conexao);
            hospedeDAO.atualizarStatusHospede(idHospedagem, "checkout");
            
            // 3. Libera o quarto
            Quarto quarto = new Quarto();
            quarto.setIdQuarto(idQuarto);
            quarto.setStatus("disponivel");
            quarto.setNomeHospede(null);
            quarto.setCpfHospede(null);
            quarto.setTelefone(null);
            
            QuartoDAO.atualizarStatusQuarto(quarto);
            
            conexao.commit();
            
            // Atualiza interface
            telaPrincipal.atualizarBotaoQuarto(quarto);
            JOptionPane.showMessageDialog(this, "Check-out realizado com sucesso!");
            
        } catch (SQLException e) {
            conexao.rollback();
            JOptionPane.showMessageDialog(this, 
                "Erro ao realizar check-out: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
   
   
    private void fecharJanela() {
    Window window = SwingUtilities.getWindowAncestor(this);
    if (window != null) {
        window.dispose();
    }
}
    
    public void dispose() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao fechar conexão: " + ex.getMessage());
        }
        
        if (parentDialog != null) {
            parentDialog.dispose();
        }
    }

   private boolean validarCampos() {
    // Validação do nome
    if (txtNomeHospede.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "O nome do hóspede é obrigatório", 
            "Erro de Validação", 
            JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Validação do CPF
    String cpf = txtCPF.getText().replaceAll("[^0-9]", "");
    if (cpf.length() != 11) {
        JOptionPane.showMessageDialog(this, 
            "CPF deve conter 11 dígitos", 
            "Erro de Validação", 
            JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Validação do telefone
    String telefoneStr = telefone.getText().replaceAll("[^0-9]", "");
    if (telefoneStr.length() < 10 || telefoneStr.length() > 11) {
        JOptionPane.showMessageDialog(this, 
            "Telefone inválido (deve ter 10 ou 11 dígitos)", 
            "Erro de Validação", 
            JOptionPane.ERROR_MESSAGE);
        return false;
    }

    return true;
}

    private int obterIdFuncionarioLogado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}