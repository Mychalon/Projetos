package telas;

import dao.AcompanhanteDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
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
import javax.swing.border.TitledBorder;
import model.Acompanhante;
import model.Hospede;

public class JanelaQuarto extends JPanel {

    private Quarto quarto;
    private JDialog parentDialog;
    private Tela_principal telaPrincipal; // ÚNICA declaração
    private Hospede hospedeAtual; // Variável para armazenar o hóspede atual
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel panelAcompanhantes;
    private JButton btnCheckOut;
    private JLabel lblDiariaStatus;
    private JTextField txtValorDiaria;
    // Componentes do formulário
    private JTextField txtNomeHospede;
    private JFormattedTextField txtCPF;
    private JFormattedTextField telefone;
    private JFormattedTextField txtCPFAcompanhante;
    private JFormattedTextField txtTelefoneAcompanhante;
    private Connection conexao;
    private JFormattedTextField Email;
    private QuartoDAO quartoDAO;
    private JPanel novoAcompanhantePanel;
    private JLabel lblLimiteAcompanhantes;
    private Connection connection;
    private Hospede hospede;
    private Acompanhante acompanhante;
    private JTextField txtAcompanhantes;
    private JTextField txtPlacaVeiculo;
    private JButton btnAdiantamento;
    private JButton btnEditarHospede;
    private JButton btnEditarAcompanhante;
    private JLabel lblCheckIn;
    private JLabel lblCheckOut;

    public JanelaQuarto(Quarto quarto, JDialog parentDialog, Tela_principal telaPrincipal, Connection conexao) {
        if (quarto == null) {
            throw new IllegalArgumentException("O objeto Quarto não pode ser nulo");
        }

        this.quarto = quarto;
        this.parentDialog = parentDialog;
        this.telaPrincipal = telaPrincipal;
        this.connection = conexao;
        this.quartoDAO = new QuartoDAO(conexao);

        // Busca o hóspede associado ao quarto ao inicializar
        try {
            this.hospedeAtual = HospedeDAO.buscarPorQuarto(quarto.getIdQuarto());
            carregarHorariosQuarto(quarto.getIdQuarto());
            if (this.hospedeAtual != null) {
                System.out.println("Hóspede encontrado. ID: " + this.hospedeAtual.getIdhospede());
                System.out.println("Nome: " + this.hospedeAtual.getNome());
                System.out.println("Quarto ID: " + this.hospedeAtual.getIdQuarto());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao buscar hóspede: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            this.hospedeAtual = null;
        }
        try {
            this.hospedeAtual = HospedeDAO.buscarPorQuarto(quarto.getIdQuarto());
            if (this.hospedeAtual != null) {
                System.out.println("Hóspede encontrado. ID: " + this.hospedeAtual.getIdhospede());
            } else {
                System.out.println("Nenhum hóspede encontrado para este quarto.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao buscar hóspede: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            this.hospedeAtual = null;
        }
       
         
        initComponents();
    }

    private void initComponents() {

        
        // No método initComponents():
        lblCheckIn = new JLabel("14:00"); // Inicialização explícita
        lblCheckOut = new JLabel("12:00"); // Inicialização explícita

        // Adicione estilos (opcional):
        Font boldFont = new Font("SansSerif", Font.BOLD, 12);
        lblCheckIn.setFont(boldFont);
        lblCheckOut.setFont(boldFont);
        lblCheckIn.setForeground(new Color(0, 100, 200)); // Azul
        lblCheckOut.setForeground(new Color(200, 0, 0)); // Vermelho
        
        
        
        // Painel principal com borda e espaçamento
    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(15, 15, 15, 15),
        BorderFactory.createLineBorder(new Color(200, 200, 200), 1))
    );

    // Painel de formulário com rolagem e fundo branco
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    formPanel.setBackground(Color.WHITE);
    
    // ========== SEÇÃO 1: INFORMAÇÕES DO QUARTO ==========
    JPanel quartoPanel = createStyledPanel("Informações do Quarto");
    quartoPanel.setLayout(new GridLayout(0, 2, 10, 8));
    
    // Adiciona os campos do quarto com ícones
    addLabelWithIcon(quartoPanel, "Número:", "/imagens/number.png", new JLabel(quarto.getNumero()));
    addLabelWithIcon(quartoPanel, "Tipo:", "/imagens/type.png", new JLabel(quarto.getTipo()));
    
    JLabel lblStatus = new JLabel(quarto.getStatus());
    lblStatus.setForeground("ocupado".equalsIgnoreCase(quarto.getStatus()) ? new Color(200, 50, 50) : new Color(50, 150, 50));
    addLabelWithIcon(quartoPanel, "Status:", "/imagens/status.png", lblStatus);
    
    // Horários com estilo especial
    lblCheckIn = createTimeLabel("14:00");
    addLabelWithIcon(quartoPanel, "Check-in:", "/imagens/checkin.png", lblCheckIn);
    
    lblCheckOut = createTimeLabel("12:00");
    addLabelWithIcon(quartoPanel, "Check-out:", "/imagens/checkout.png", lblCheckOut);
    
    formPanel.add(quartoPanel);
    formPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espaçamento
    
    // ========== SEÇÃO 2: INFORMAÇÕES DO HÓSPEDE ==========
    JPanel hospedePanel = createStyledPanel("Informações do Hóspede");
    
    
     // Painel do hóspede - estilo igual ao do acompanhante
  
    hospedePanel.setLayout(new BoxLayout(hospedePanel, BoxLayout.Y_AXIS));
    hospedePanel.setBorder(BorderFactory.createTitledBorder("Informações do Hóspede"));
    
    // Campos do hóspede com o mesmo estilo
    JPanel hospedeFields = new JPanel(new GridLayout(0, 2, 10, 10));
    hospedeFields.add(new JLabel("Nome:"));
    txtNomeHospede = new JTextField();
    hospedeFields.add(txtNomeHospede);
    
    hospedeFields.add(new JLabel("CPF:"));
    txtCPF = createStyledFormattedField("###.###.###-##");
    hospedeFields.add(txtCPF);
    
    hospedeFields.add(new JLabel("Telefone:"));
    telefone = createStyledFormattedField("(##) #####-####");
    hospedeFields.add(telefone);
    
    hospedeFields.add(new JLabel("Placa do Veículo:"));
    txtPlacaVeiculo = new JTextField();
    hospedeFields.add(txtPlacaVeiculo);
    
    hospedePanel.add(hospedeFields);
    formPanel.add(hospedePanel);

    // Painel de acompanhantes (mantenha o existente)
    panelAcompanhantes = new JPanel();
    panelAcompanhantes.setLayout(new BoxLayout(panelAcompanhantes, BoxLayout.Y_AXIS));
    panelAcompanhantes.setBorder(BorderFactory.createTitledBorder("Acompanhantes Cadastrados"));
    
   
    
    // ========== SEÇÃO 3: ACOMPANHANTES ==========

// Inicialize o painel de novo acompanhante
novoAcompanhantePanel = new JPanel(new GridLayout(0, 2, 10, 10));
novoAcompanhantePanel.setBorder(BorderFactory.createTitledBorder("Adicionar Acompanhante"));

// Inicialize os campos
txtAcompanhantes = new JTextField();
txtCPFAcompanhante = createStyledFormattedField("###.###.###-##");
txtTelefoneAcompanhante = createStyledFormattedField("(##) #####-####");

// Adicione os componentes ao painel
novoAcompanhantePanel.add(new JLabel("Nome do Acompanhante:"));
novoAcompanhantePanel.add(txtAcompanhantes);
novoAcompanhantePanel.add(new JLabel("CPF do Acompanhante:"));
novoAcompanhantePanel.add(txtCPFAcompanhante);
novoAcompanhantePanel.add(new JLabel("Telefone do Acompanhante:"));
novoAcompanhantePanel.add(txtTelefoneAcompanhante);

// Adicione o painel ao formPanel
formPanel.add(novoAcompanhantePanel);

// Inicialize o label de limite
lblLimiteAcompanhantes = new JLabel();
formPanel.add(lblLimiteAcompanhantes);



    panelAcompanhantes = new JPanel();
    panelAcompanhantes.setLayout(new BoxLayout(panelAcompanhantes, BoxLayout.Y_AXIS));
    panelAcompanhantes.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(180, 180, 220), 1),
        "Acompanhantes Cadastrados",
        TitledBorder.LEFT,
        TitledBorder.TOP,
        new Font("SansSerif", Font.BOLD, 12),
        new Color(70, 70, 120))
    );
    panelAcompanhantes.setBackground(Color.WHITE);
    
    if (hospedeAtual != null) {
        carregarAcompanhantes(hospedeAtual.getidhospede());
    }
    
    formPanel.add(panelAcompanhantes);
    formPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espaçamento
    
    // ========== SEÇÃO 4: CHECK-OUT (se aplicável) ==========
    if (hospedeAtual != null && "ocupado".equals(quarto.getStatus())) {
        JPanel checkOutPanel = createStyledPanel("Check-out");
        checkOutPanel.setLayout(new GridLayout(0, 2, 10, 8));
        
        lblDiariaStatus = new JLabel("NÃO PAGA");
        lblDiariaStatus.setForeground(new Color(200, 50, 50));
        lblDiariaStatus.setFont(new Font("SansSerif", Font.BOLD, 12));
        addLabelWithIcon(checkOutPanel, "Status:", "/imagens/payment.png", lblDiariaStatus);
        
        txtValorDiaria = createStyledTextField();
        addLabelWithIcon(checkOutPanel, "Valor:", "/imagens/money.png", txtValorDiaria);
        
        btnCheckOut = createStyledButton("Realizar Check-out", new Color(200, 50, 50));
        btnCheckOut.addActionListener(e -> realizarCheckout());
        
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonWrapper.setBackground(Color.WHITE);
        buttonWrapper.add(btnCheckOut);
        
        formPanel.add(checkOutPanel);
        formPanel.add(buttonWrapper);
    }
    
    // ========== BOTÕES DE AÇÃO ==========
    JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
    actionPanel.setBackground(Color.WHITE);
    
    btnEditarHospede = createStyledButton("Editar Hóspede", new Color(70, 130, 180));
    btnEditarHospede.addActionListener(e -> editarHospede());
    actionPanel.add(btnEditarHospede);
    
    btnAdiantamento = createStyledButton("Adiantamento", new Color(80, 160, 80));
    btnAdiantamento.addActionListener(e -> registrarAdiantamento());
    actionPanel.add(btnAdiantamento);
    
    JButton btnSalvar = createStyledButton("Salvar", new Color(70, 130, 180));
    btnSalvar.addActionListener(e -> {
        try {
            salvarInformacoes();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });
    actionPanel.add(btnSalvar);
    
    JButton btnFechar = createStyledButton("Fechar", new Color(160, 80, 80));
    btnFechar.addActionListener(e -> fecharJanela());
    actionPanel.add(btnFechar);
    
    // Adiciona tudo ao painel principal
    mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
    mainPanel.add(actionPanel, BorderLayout.SOUTH);
    
    this.setBackground(Color.WHITE);
    this.add(mainPanel);
}

// ========== MÉTODOS AUXILIARES PARA ESTILIZAÇÃO ==========

private JPanel createStyledPanel(String title) {
    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(180, 180, 220), 1),
        title,
        TitledBorder.LEFT,
        TitledBorder.TOP,
        new Font("SansSerif", Font.BOLD, 12),
        new Color(70, 70, 120))
    );
    panel.setBackground(Color.WHITE);
    return panel;
}

private JTextField createStyledTextField() {
    JTextField field = new JTextField();
    field.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 220), 1),
        BorderFactory.createEmptyBorder(5, 8, 5, 8))
    );
    field.setBackground(new Color(250, 250, 255));
    return field;
}

private JFormattedTextField createStyledFormattedField(String mask) {
      try {
        JFormattedTextField field = new JFormattedTextField(new MaskFormatter(mask));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 220)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8))
        );
        field.setBackground(new Color(250, 250, 255));
        return field;
    } catch (Exception e) {
        return new JFormattedTextField();
    }
}

private JButton createStyledButton(String text, Color bgColor) {
    JButton button = new JButton(text);
    button.setBackground(bgColor);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    button.setFont(new Font("SansSerif", Font.BOLD, 12));
    
    // Efeito hover
    button.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(bgColor.darker());
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setBackground(bgColor);
        }
    });
    
    return button;
}

private JLabel createTimeLabel(String time) {
    JLabel label = new JLabel(time);
    label.setFont(new Font("SansSerif", Font.BOLD, 12));
    label.setForeground(new Color(70, 70, 120));
    label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    return label;
}

private void addLabelWithIcon(JPanel panel, String labelText, String iconName, JComponent component) {
    JLabel label = new JLabel(labelText);
    
    // Usa texto como fallback se não tiver ícone
    if (iconName != null) {
        label.setIconTextGap(5);
        
    }
    
    panel.add(label);
    panel.add(component);

}

private void setFieldsEditable(boolean editable) {
    txtNomeHospede.setEditable(editable);
    txtCPF.setEditable(editable);
    telefone.setEditable(editable);
    txtPlacaVeiculo.setEditable(editable);
}
    
private void salvarInformacoes() throws IOException, SQLException {
        if (!validarCampos()) {
            return;
        }

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            conexao.setAutoCommit(false);

            // Criar hóspede a partir do formulário
            Hospede hospede = criarHospedeFromForm();
            HospedeDAO hospedeDAO = new HospedeDAO(conexao);
            int idhospede = hospedeDAO.salvar(hospede);

            // 2. Salvar acompanhantes (se houver)
            if (txtAcompanhantes.getText() != null && !txtAcompanhantes.getText().trim().isEmpty()) {
                AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(conexao);
                Acompanhante acompanhante = criarAcompanhanteFromForm(idhospede);
                acompanhanteDAO.salvar(acompanhante);
            }

            if (hospedeAtual != null) {
                List<Acompanhante> acompanhantes = AcompanhanteDAO.buscarPorHospede(hospedeAtual.getIdhospede());
                if (acompanhantes.size() >= quarto.getLimiteAcompanhantes()) {
                    JOptionPane.showMessageDialog(this,
                            "Limite de " + quarto.getLimiteAcompanhantes() + " acompanhantes atingido para este quarto",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            // Atualiza a lista de acompanhantes após salvar
            if (hospedeAtual != null) {
                carregarAcompanhantes(hospedeAtual.getidhospede());
            }

            // Atualizar status do quarto
            quarto.setStatus("ocupado");
            QuartoDAO quartoDAO = new QuartoDAO(conexao);
            boolean sucesso = quartoDAO.atualizarStatusQuarto(quarto);

            if (!sucesso) {
                conexao.rollback();
                JOptionPane.showMessageDialog(this, "Falha ao atualizar status do quarto",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
             if (txtAcompanhantes.getText() != null && !txtAcompanhantes.getText().trim().isEmpty()) {
        if (hospedeAtual != null) {
            List<Acompanhante> acompanhantes = AcompanhanteDAO.buscarPorHospede(hospedeAtual.getIdhospede());
            if (acompanhantes.size() >= calcularLimiteAcompanhantes()) {
                JOptionPane.showMessageDialog(this,
                    "Limite de acompanhantes atingido para este quarto",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(conexao);
        Acompanhante acompanhante = criarAcompanhanteFromForm(idhospede);
        acompanhanteDAO.salvar(acompanhante);
        
        // Atualiza a lista e verifica o limite
        carregarAcompanhantes(idhospede);
    }
            // Registrar hospedagem
            int idFuncionario = obterIdFuncionarioLogado(); // Implemente este método
            HospedagemDAO hospedagemDAO = new HospedagemDAO(conexao);
            hospedagemDAO.registrarHospedagem(quarto.getIdQuarto(), idhospede, idFuncionario);

            conexao.commit();

            // Atualizar interface
            telaPrincipal.atualizarBotaoQuarto(quarto);
            JOptionPane.showMessageDialog(this, "Hospedagem registrada com sucesso!");
            fecharJanela();

        } catch (IllegalArgumentException e) {
            if (conexao != null) {
                try {
                    conexao.rollback();
                } catch (SQLException ex) {
                }
            }
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            if (conexao != null) {
                try {
                    conexao.rollback();
                } catch (SQLException ex) {
                }
            }
            JOptionPane.showMessageDialog(this,
                    "Erro ao registrar hospedagem: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            throw e;
        } finally {
            if (conexao != null) {
                try {
                    conexao.setAutoCommit(true);
                    conexao.close();
                } catch (SQLException e) {
                }
            }
        }
    }
    

private void verificarLimiteAcompanhantes() {
        try {
        if (hospedeAtual != null && novoAcompanhantePanel != null && lblLimiteAcompanhantes != null) {
            List<Acompanhante> acompanhantes = AcompanhanteDAO.buscarPorHospede(hospedeAtual.getIdhospede());
            int limite = calcularLimiteAcompanhantes();
            
            if (acompanhantes.size() >= limite) {
                novoAcompanhantePanel.setVisible(false);
                lblLimiteAcompanhantes.setText("Limite de " + limite + " acompanhantes atingido!");
                lblLimiteAcompanhantes.setForeground(Color.RED);
            } else {
                novoAcompanhantePanel.setVisible(true);
                lblLimiteAcompanhantes.setText("Limite: " + acompanhantes.size() + "/" + limite);
                lblLimiteAcompanhantes.setForeground(Color.BLACK);
            }
            
            // Forçar redesenho
            novoAcompanhantePanel.revalidate();
            novoAcompanhantePanel.repaint();
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao verificar acompanhantes: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

private int calcularLimiteAcompanhantes() {
     String tipo = quarto.getTipo().toLowerCase();
    switch(tipo) {
        case "duplo":
            return 1;
        case "triplo":
            return 2;
        case "quadruplo":
            return 3;
        default:
            return 1;
    }
}
    
    private void carregarHorariosQuarto(int idQuarto) {
      try (Connection conexao = ConexaoBD.getConexao()) {
        String sql = "SELECT check_in, check_out FROM quarto WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idQuarto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String checkIn = rs.getString("check_in");
                    String checkOut = rs.getString("check_out");
                    
                    // Verifica se os labels foram inicializados
                    if (lblCheckIn != null) {
                        lblCheckIn.setText(checkIn != null ? checkIn : "14:00");
                    }
                    if (lblCheckOut != null) {
                        lblCheckOut.setText(checkOut != null ? checkOut : "12:00");
                    }
                }
            }
        }
    } catch (SQLException ex) {
        System.err.println("Erro ao carregar horários: " + ex.getMessage());
        // Define valores padrão em caso de erro
        if (lblCheckIn != null) lblCheckIn.setText("14:00");
        if (lblCheckOut != null) lblCheckOut.setText("12:00");
    }
}
   
    
    private void registrarAdiantamento() {
        String valor = JOptionPane.showInputDialog(this, "Valor do adiantamento:");
        if (valor != null && !valor.isEmpty()) {
            try {
                double adiantamento = Double.parseDouble(valor);
                // Aqui você implementaria a lógica para salvar o adiantamento
                JOptionPane.showMessageDialog(this, "Adiantamento de R$ " + adiantamento + " registrado!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarHospede() {
        txtNomeHospede.setEditable(true);
        txtCPF.setEditable(true);
        telefone.setEditable(true);
        txtPlacaVeiculo.setEditable(true);
        JOptionPane.showMessageDialog(this, "Edite os campos e clique em Salvar para confirmar");
    }

    private void editarAcompanhante() {
        txtAcompanhantes.setEditable(true);
        txtCPFAcompanhante.setEditable(true);
        txtTelefoneAcompanhante.setEditable(true);
        JOptionPane.showMessageDialog(this, "Edite os campos e clique em Salvar para confirmar");
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

                conexao.commit();

                // Atualiza interface
                telaPrincipal.atualizarBotaoQuarto(quarto);
                JOptionPane.showMessageDialog(this, "Check-out realizado com sucesso!");

                // Limpar campos após checkout
                txtNomeHospede.setText("");
                txtCPF.setText("");
                telefone.setText("");
                txtPlacaVeiculo.setText("");
                txtAcompanhantes.setText("");
                txtCPFAcompanhante.setText("");
                txtTelefoneAcompanhante.setText("");
                panelAcompanhantes.removeAll();
                panelAcompanhantes.revalidate();
                panelAcompanhantes.repaint();

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
        // Implementação temporária - substitua pela lógica real de autenticação
        return 1; // ID do funcionário padrão

        // Em um sistema real, você obteria isso da sessão do usuário:
        // return SessaoUsuario.getFuncionarioLogado().getId();
    }

    private Hospede criarHospedeFromForm() {
        Hospede hospede = new Hospede();

        // Obter dados dos campos do formulário
        String nome = txtNomeHospede.getText().trim();
        String cpf = txtCPF.getText().replaceAll("[^0-9]", "");
        String telefone = this.telefone.getText().replaceAll("[^0-9]", "");

        // Obter email apenas se o campo estiver inicializado
        String email = "";
        if (Email != null) {
            email = Email.getText().trim();
        }

        // Validar campos obrigatórios
        if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty()) {
            throw new IllegalArgumentException("Nome, CPF e telefone são obrigatórios");
        }

        // Configurar os campos básicos
        hospede.setNome(txtNomeHospede.getText().trim());
        hospede.setCpfHospede(txtCPF.getText().replaceAll("[^0-9]", ""));
        hospede.setTelefone(this.telefone.getText().replaceAll("[^0-9]", ""));
        hospede.setPlacaVeiculo(txtPlacaVeiculo.getText().trim());
        hospede.setIdQuarto(quarto.getIdQuarto());

        // Configurar datas
        hospede.setCheckIn(new Date()); // Check-in sempre com data atual

        // Check-out permanece null até ser feito o checkout
        hospede.setCheckOut(null);

        return hospede;
    }

    private void carregarAcompanhantes(int idHospede) {
      try {
        panelAcompanhantes.removeAll();
        
        List<Acompanhante> acompanhantes = AcompanhanteDAO.buscarPorHospede(idHospede);
        
        for (Acompanhante a : acompanhantes) {
            JPanel panelAcomp = new JPanel(new GridLayout(0, 2, 5, 5));
            panelAcomp.setBorder(BorderFactory.createEtchedBorder());
            
            panelAcomp.add(new JLabel("Nome:"));
            panelAcomp.add(new JLabel(a.getNome()));
            
            panelAcomp.add(new JLabel("CPF:"));
            panelAcomp.add(new JLabel(a.getCpfacompanhante()));
            
            panelAcomp.add(new JLabel("Telefone:"));
            panelAcomp.add(new JLabel(a.getTelefone()));
            
            panelAcompanhantes.add(panelAcomp);
        }
        
        // Adiciona o label de limite
        lblLimiteAcompanhantes = new JLabel();
        panelAcompanhantes.add(lblLimiteAcompanhantes);
        
        // Verifica o limite
        verificarLimiteAcompanhantes();
        
        panelAcompanhantes.revalidate();
        panelAcompanhantes.repaint();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
            "Erro ao carregar acompanhantes: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
    }

    private void realizarCheckout() {
        if (txtValorDiaria.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Informe o valor da diária!",
                    "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double valorDiaria = Double.parseDouble(txtValorDiaria.getText());

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Confirmar check-out? Valor da diária: R$ " + valorDiaria,
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                quarto.setStatus("disponivel");
                quartoDAO.atualizarStatusQuarto(quarto);

                telaPrincipal.atualizarBotaoQuarto(quarto);
                JOptionPane.showMessageDialog(this, "Check-out realizado com sucesso!");
                fecharJanela();
            }
            // Atualizar o hóspede com a data de checkout
            if (hospedeAtual != null) {
                hospedeAtual.setCheckOut(new Date());
                new HospedeDAO(conexao).atualizarCheckout(hospedeAtual);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Valor da diária inválido! Use números (ex: 150.00)",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao atualizar o quarto: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private Acompanhante criarAcompanhanteFromForm(int idhospede) {
        Acompanhante acompanhante = new Acompanhante();

        acompanhante.setNome(txtAcompanhantes.getText().trim());
        acompanhante.setCpfacompanhante(txtCPFAcompanhante.getText().replaceAll("[^0-9]", ""));
        acompanhante.setTelefone(txtTelefoneAcompanhante.getText().replaceAll("[^0-9]", ""));
        acompanhante.setidhospede(idhospede);          // Definindo hospede_id
        acompanhante.setIdQuarto(quarto.getIdQuarto()); // Definindo id_quarto

        return acompanhante;
    }
    
    
    private void atualizarHospedeExistente() throws SQLException {
    Hospede hospede = criarHospedeFromForm();
    hospede.setIdhospede(hospedeAtual.getIdhospede());
    
    HospedeDAO hospedeDAO = new HospedeDAO(connection);
    hospedeDAO.atualizar(hospede);
    
    // Atualiza o hóspede atual com os novos dados
    this.hospedeAtual = hospede;
    
    // Atualiza os campos na interface
    txtPlacaVeiculo.setText(hospede.getPlacaVeiculo());
    
    JOptionPane.showMessageDialog(this, "Dados do hóspede atualizados com sucesso!");
}
    
    private static class JTextField1 {

        public JTextField1() {
        }

        private void setText(String nome) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private Object getText() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}
