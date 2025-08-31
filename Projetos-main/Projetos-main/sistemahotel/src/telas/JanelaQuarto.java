package telas;

import dao.ConsumoDAO;  // CORRETO (pacote em minúsculas)
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
import javax.swing.text.MaskFormatter;
import java.io.IOException;
import com.toedter.calendar.JCalendar;
import java.util.Date;
import dao.HospedagemDAO;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import model.Acompanhante;
import model.Hospede;
import com.toedter.calendar.JDateChooser;
import dao.AdiantamentoDAO;
import dao.FormaPagamentoDAO;
import dao.PagamentoDAO;
import java.util.Calendar;
import model.Adiantamento;
import model.FormaPagamento;
import model.Pagamento;
import componentes.BotaoArredondado;
import java.time.format.DateTimeFormatter;
import model.Consumo;
import model.Produto;
import dao.ProdutoDAO;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import model.Hospedagem;
import model.WrapLayout;


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
    private JButton btnAdicionarAcompanhante;
    private JButton btnSalvar;
    private JLabel lblCheckIn;
    private JLabel lblCheckOut;
    // parte calendario
    private JCalendar calendar;
    private JTable tabelaConsumo;
    private DefaultTableModel modeloTabela;
    private Timer timerDiaria;
     private JDateChooser dateChooserCheckIn;
    private JDateChooser dateChooserCheckOut;
    private JPanel hospedePanel;
    private JPanel pagamentoPanel;
    private JButton btnExtraAcompanhante;
    private JTextField txtValorExtra;
    private boolean hospedeCadastrado = false;
    // parte do pagamento
    private JComboBox<String> cbFormasPagamento;
private JTextField txtValorAdiantamento;
private JTextField txtValorPagamento;
private JTextArea txtObservacoesPagamento;
private JLabel lblSaldoDisponivel;

//consumo do quarto

    private DefaultTableModel modeloTabelaConsumo;
    private javax.swing.JTextField txtCodigoConsumo;
    private javax.swing.JTextField txtQuantidadeConsumo;
    private ProdutoDAO produtoDAO;
    private ConsumoDAO consumoDAO;

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
    if (this.hospedeAtual != null) {
        System.out.println("Hóspede encontrado. ID: " + this.hospedeAtual.getIdhospede());
        
        // Buscar dados da hospedagem
        HospedagemDAO hospedagemDAO = new HospedagemDAO(connection);
        // Você precisará criar um método para buscar hospedagem por quarto
        // ou obter as datas da hospedagem atual
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
        
         
        carregarDadosHospede(); // Adicione esta linha
        configurarVisibilidadeBotoes();
        
        
        //Parte do consumo do quarto
        this.produtoDAO = new ProdutoDAO(conexao);
        this.consumoDAO = new ConsumoDAO(conexao);
         
        inicializarTabelaConsumo();
        carregarConsumos();
        
    }
    
    
    
   
    // tabela do consumo 
    
     private void inicializarTabelaConsumo() {
        String[] colunas = {"Produto", "Quantidade", "Valor Unitário", "Valor Total", "Data/Hora"};
        modeloTabelaConsumo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };
        tabelaConsumo.setModel(modeloTabelaConsumo);
    }

    private void carregarConsumos() {
        if (hospedeAtual == null) return;
        
        try {
            int idHospedagem = obterIdHospedagemAtual(); // Implemente este método
            List<Consumo> consumos = consumoDAO.listarConsumosPorHospedagem(idHospedagem);
            
            modeloTabelaConsumo.setRowCount(0); // Limpa a tabela
            
            for (Consumo consumo : consumos) {
                Object[] row = {
                    consumo.getNomeProduto(),
                    consumo.getQuantidade(),
                    String.format("R$ %.2f", consumo.getValorUnitario()),
                    String.format("R$ %.2f", consumo.getValorTotal()),
                    consumo.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                };
                modeloTabelaConsumo.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar consumos: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarConsumo() {
     // Verificar campos
    String codigo = txtCodigoConsumo.getText().trim();
    String quantidadeStr = txtQuantidadeConsumo.getText().trim();
    
    if (codigo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Informe o código do produto!", "Erro", JOptionPane.ERROR_MESSAGE);
        txtCodigoConsumo.requestFocus();
        return;
    }
    
    try {
        int quantidade = Integer.parseInt(quantidadeStr);
        if (quantidade <= 0) {
            JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
            txtQuantidadeConsumo.requestFocus();
            return;
        }

        // Buscar produto
        Produto produto = produtoDAO.buscarPorCodigo(codigo);
        if (produto == null) {
            JOptionPane.showMessageDialog(this, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar se o produto tem nome válido (usando apenas campos existentes)
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Produto sem nome válido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Obtenha o ID do caixa ativo
        int idCaixaAtivo = obterIdCaixaAtivo(); // Você precisa implementar este método
       
        // Criar consumo
        Consumo consumo = new Consumo();
        consumo.setIdHospedagem(obterIdHospedagemAtual());
        consumo.setIdProduto(produto.getId());
        consumo.setNomeProduto(produto.getNome()); // Usando apenas o nome
        consumo.setQuantidade(quantidade);
        consumo.setValorUnitario(produto.getPreco());
        consumo.setDataHora(LocalDateTime.now());

        // Registrar no banco
        if (consumoDAO.registrarConsumo(consumo, idCaixaAtivo)) {
            // Atualizar tabela
            carregarConsumos();
            
            // Limpar campos
            txtCodigoConsumo.setText("");
            txtQuantidadeConsumo.setText("1");
            
            JOptionPane.showMessageDialog(this, "Consumo registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Quantidade inválida! Use números inteiros.", "Erro", JOptionPane.ERROR_MESSAGE);
        txtQuantidadeConsumo.requestFocus();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, 
            "Erro ao registrar consumo: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
    }

   private int obterIdCaixaAtivo() throws SQLException {
    // Implemente a lógica para obter o ID do caixa aberto
    // Exemplo simplificado:
    String sql = "SELECT id FROM caixa WHERE status = 'aberto' LIMIT 1";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
        throw new SQLException("Nenhum caixa aberto encontrado");
    }
}
    
    private int obterIdHospedagemAtual() throws SQLException {
        String sql = "SELECT h.id FROM hospedagem h " +
                 "JOIN quarto q ON h.id_quarto = q.id " +
                 "WHERE h.id_quarto = ? AND q.status = 'ocupado'";
    
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, quarto.getIdQuarto());
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getInt("id") : -1;
    }
    }




    
    private void carregarDadosHospede() {
       if (hospedeAtual != null) {
        txtNomeHospede.setText(hospedeAtual.getNome());
        txtCPF.setText(hospedeAtual.getCpfHospede());
        telefone.setText(hospedeAtual.getTelefone());
        txtPlacaVeiculo.setText(hospedeAtual.getPlacaVeiculo());
        
        try {
            // Buscar datas da hospedagem
            Date[] datas = obterDatasHospedagem(quarto.getIdQuarto());
            Date dataCheckIn = datas[0];
            Date dataCheckOut = datas[1];
            
            if (dataCheckIn != null) {
                dateChooserCheckIn.setDate(dataCheckIn);
                dateChooserCheckIn.getDateEditor().setEnabled(false);
                
                Calendar calCheckIn = Calendar.getInstance();
                calCheckIn.setTime(dataCheckIn);
                
                // Se não tiver check-out, calcula automaticamente
                if (dataCheckOut == null) {
                    Calendar calCheckOut = (Calendar) calCheckIn.clone();
                    
                    // Verificar se o check-in foi entre 00:00 e 11:59 (manhã)
                    boolean isManhaCheckIn = calCheckIn.get(Calendar.HOUR_OF_DAY) < 12;
                    
                    if (isManhaCheckIn) {
                        // Check-out no mesmo dia às 12:00
                        calCheckOut.set(Calendar.HOUR_OF_DAY, 12);
                    } else {
                        // Check-out no dia seguinte às 12:00
                        calCheckOut.add(Calendar.DAY_OF_MONTH, 1);
                        calCheckOut.set(Calendar.HOUR_OF_DAY, 12);
                    }
                    calCheckOut.set(Calendar.MINUTE, 0);
                    calCheckOut.set(Calendar.SECOND, 0);
                    dataCheckOut = calCheckOut.getTime();
                }
                
                dateChooserCheckOut.setDate(dataCheckOut);
                dateChooserCheckOut.getDateEditor().setEnabled(false);
                
                calendar.setDate(dataCheckIn);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar datas da hospedagem: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        if (btnAdicionarAcompanhante != null) {
            btnAdicionarAcompanhante.setVisible(true);
        }
    } else {
        // Comportamento padrão para novo hóspede
        Calendar now = Calendar.getInstance();
        dateChooserCheckIn.setDate(now.getTime());
        
        Calendar checkoutCal = (Calendar) now.clone();
        
        // Verifica se é antes do meio-dia (00:00 às 11:59)
        if (now.get(Calendar.HOUR_OF_DAY) < 12) {
            // Check-out no mesmo dia às 12:00
            checkoutCal.set(Calendar.HOUR_OF_DAY, 12);
        } else {
            // Check-out no dia seguinte às 12:00
            checkoutCal.add(Calendar.DAY_OF_MONTH, 1);
            checkoutCal.set(Calendar.HOUR_OF_DAY, 12);
        }
        checkoutCal.set(Calendar.MINUTE, 0);
        checkoutCal.set(Calendar.SECOND, 0);
        
        dateChooserCheckOut.setDate(checkoutCal.getTime());
        
        if (btnAdicionarAcompanhante != null) {
            btnAdicionarAcompanhante.setVisible(false);
        }
    }
  }

    
    
    
   
    
private void initComponents() {
    // ========== CONFIGURAÇÃO PRINCIPAL ==========
    this.setLayout(new BorderLayout());
    this.setBackground(Color.WHITE);
    
    // ========== PAINEL PRINCIPAL ==========
    mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    // ========== PAINEL SUPERIOR (Quarto + Calendário) ==========
JPanel topPanel = new JPanel(new BorderLayout(10, 10));

// Painel de informações do quarto (acima) - GridLayout 2 colunas
JPanel quartoInfoPanel = new JPanel(new GridLayout(0, 2, 5, 5));
quartoInfoPanel.setBorder(BorderFactory.createTitledBorder("Informações do Quarto"));
quartoInfoPanel.setPreferredSize(new Dimension(300, 80)); // Altura reduzida

Font fonteMaior = new Font("SansSerif", Font.BOLD, 14); // Altere o tamanho (14) conforme necessário

// Adiciona componentes do quarto com a nova fonte
JLabel lblNumero = new JLabel("Número:");
lblNumero.setFont(fonteMaior);
quartoInfoPanel.add(lblNumero);

JLabel lblNumeroValor = new JLabel(quarto.getNumero());
lblNumeroValor.setFont(fonteMaior);
quartoInfoPanel.add(lblNumeroValor);

JLabel lblTipo = new JLabel("Tipo:");
lblTipo.setFont(fonteMaior);
quartoInfoPanel.add(lblTipo);

JLabel lblTipoValor = new JLabel(quarto.getTipo());
lblTipoValor.setFont(fonteMaior);
quartoInfoPanel.add(lblTipoValor);

JLabel lblStatus = new JLabel("Status:");
lblStatus.setFont(fonteMaior);
quartoInfoPanel.add(lblStatus);

JLabel lblStatusValor = new JLabel(quarto.getStatus());
lblStatusValor.setFont(fonteMaior);
lblStatusValor.setForeground("ocupado".equalsIgnoreCase(quarto.getStatus()) ? Color.RED : Color.GREEN);
quartoInfoPanel.add(lblStatusValor);

// Painel de datas (abaixo) - mais compacto
JPanel datesPanel = new JPanel(new GridLayout(2, 2, 5, 5));
datesPanel.setBorder(BorderFactory.createTitledBorder("Datas"));
datesPanel.setPreferredSize(new Dimension(300, 80)); // Mesma largura, altura reduzida

// Configura DateChoosers
dateChooserCheckIn = new JDateChooser();
dateChooserCheckIn.setDateFormatString("dd/MM/yyyy HH:mm");
dateChooserCheckIn.setPreferredSize(new Dimension(120, 25)); // Tamanho compacto

dateChooserCheckOut = new JDateChooser();
dateChooserCheckOut.setDateFormatString("dd/MM/yyyy HH:mm");
dateChooserCheckOut.setPreferredSize(new Dimension(120, 25));
dateChooserCheckOut.setEnabled(false);

// Adiciona componentes ao datesPanel
datesPanel.add(new JLabel("Check-in:"));
datesPanel.add(dateChooserCheckIn);
datesPanel.add(new JLabel("Check-out:"));
datesPanel.add(dateChooserCheckOut);

// Painel container vertical para os dois painéis
JPanel infoContainer = new JPanel();
infoContainer.setLayout(new BoxLayout(infoContainer, BoxLayout.Y_AXIS));
infoContainer.add(quartoInfoPanel);
infoContainer.add(Box.createRigidArea(new Dimension(0, 5))); // Pequeno espaçamento
infoContainer.add(datesPanel);

topPanel.add(infoContainer, BorderLayout.WEST);
    
    // ========== CALENDÁRIO (direita) ==========
    JPanel calendarPanel = new JPanel(new BorderLayout());
    calendarPanel.setBorder(BorderFactory.createTitledBorder("Selecione a Data de Hospedagem"));
    calendarPanel.setPreferredSize(new Dimension(350, 300));
    
    calendar = new JCalendar();
    calendar.setPreferredSize(new Dimension(300, 250));
    calendar.setDecorationBackgroundColor(new Color(200, 230, 200));
    
   calendar.addPropertyChangeListener("calendar", evt -> {
    if (!hospedeCadastrado) {
        Calendar selected = calendar.getCalendar();
        Date selectedDate = selected.getTime();
        dateChooserCheckIn.setDate(selectedDate);
        
        Calendar checkoutCal = (Calendar) selected.clone();
        
        // Verifica se a hora selecionada é antes do meio-dia (00:00 às 11:59)
        if (selected.get(Calendar.HOUR_OF_DAY) < 12) {
            // Check-out no mesmo dia às 12:00
            checkoutCal.set(Calendar.HOUR_OF_DAY, 12);
        } else {
            // Check-out no dia seguinte às 12:00
            checkoutCal.add(Calendar.DAY_OF_MONTH, 1);
            checkoutCal.set(Calendar.HOUR_OF_DAY, 12);
        }
        checkoutCal.set(Calendar.MINUTE, 0);
        checkoutCal.set(Calendar.SECOND, 0);
        
        dateChooserCheckOut.setDate(checkoutCal.getTime());
    }
});
    
    calendarPanel.add(calendar, BorderLayout.CENTER);
    topPanel.add(calendarPanel, BorderLayout.CENTER);
    
    mainPanel.add(topPanel, BorderLayout.NORTH);
    
    // ========== PAINEL CENTRAL (Formulário) ==========
    formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.PAGE_AXIS));
    formPanel.setBackground(Color.WHITE);
    
    // ========== SEÇÃO: INFORMAÇÕES DO HÓSPEDE ==========
    hospedePanel = createStyledPanel("Informações do Hóspede");
    hospedePanel.setLayout(new BoxLayout(hospedePanel, BoxLayout.Y_AXIS));
    hospedePanel.setPreferredSize(new Dimension(300, 150));

    JPanel hospedeFields = new JPanel(new GridLayout(0, 2, 2, 4));
    
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

    // =========== PAGAMENTOS =================//
    pagamentoPanel = createStyledPanel("Pagamentos");
    pagamentoPanel.setLayout(new WrapLayout(WrapLayout.LEFT, 5, 5));
    pagamentoPanel.setPreferredSize(new Dimension(250, 100));

    // Painel de Adiantamento
    JPanel adiantamentoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    adiantamentoPanel.add(new JLabel("Adiantamento:"));
    adiantamentoPanel.setVisible(false);
    txtValorAdiantamento = new JTextField(10);
    adiantamentoPanel.add(txtValorAdiantamento);
    JButton btnRegistrarAdiantamento = createStyledButton("Registrar", new Color(80, 160, 80));
    adiantamentoPanel.add(btnRegistrarAdiantamento);
    lblSaldoDisponivel = new JLabel("Saldo: R$ 0,00");
    adiantamentoPanel.add(lblSaldoDisponivel);
    pagamentoPanel.add(adiantamentoPanel);

    // Painel de Pagamento
    JPanel pagamentoFormPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    pagamentoFormPanel.add(new JLabel("Pagamento:"));
    cbFormasPagamento = new JComboBox<>();
    pagamentoFormPanel.add(cbFormasPagamento);
    txtValorPagamento = new JTextField(10);
    pagamentoFormPanel.add(txtValorPagamento);
    JButton btnRegistrarPagamento = createStyledButton("Registrar", new Color(70, 130, 180));
    pagamentoFormPanel.add(btnRegistrarPagamento);
    pagamentoPanel.add(pagamentoFormPanel);

    // Painel de Observações
    JPanel obsPanel = new JPanel(new BorderLayout());
    obsPanel.add(new JLabel("Observações:"), BorderLayout.NORTH);
    txtObservacoesPagamento = new JTextArea(2, 20);
    obsPanel.add(new JScrollPane(txtObservacoesPagamento), BorderLayout.CENTER);
    pagamentoPanel.add(obsPanel);

    // Listeners
    btnRegistrarAdiantamento.addActionListener(e -> registrarAdiantamento());
    btnRegistrarPagamento.addActionListener(e -> registrarPagamento());

    // Carrega as formas de pagamento
    carregarFormasPagamento();

    formPanel.add(pagamentoPanel);
        
        
    
        
        // ========== SEÇÃO: ACOMPANHANTES ==========
        novoAcompanhantePanel = new JPanel(new GridLayout(0, 2, 2, 4));
        novoAcompanhantePanel.setBorder(BorderFactory.createTitledBorder("Adicionar Acompanhante"));
        novoAcompanhantePanel.setPreferredSize(new Dimension(300, 100));
        novoAcompanhantePanel.setVisible(false);

        txtAcompanhantes = new JTextField();
        txtCPFAcompanhante = createStyledFormattedField("###.###.###-##");
        txtTelefoneAcompanhante = createStyledFormattedField("(##) #####-####");

        novoAcompanhantePanel.add(new JLabel("Nome do Acompanhante:"));
        novoAcompanhantePanel.add(txtAcompanhantes);
        novoAcompanhantePanel.add(new JLabel("CPF do Acompanhante:"));
        novoAcompanhantePanel.add(txtCPFAcompanhante);
        novoAcompanhantePanel.add(new JLabel("Telefone do Acompanhante:"));
        novoAcompanhantePanel.add(txtTelefoneAcompanhante);

        formPanel.add(novoAcompanhantePanel);

        lblLimiteAcompanhantes = new JLabel();
        formPanel.add(lblLimiteAcompanhantes);

        panelAcompanhantes = new JPanel();
        panelAcompanhantes.setLayout(new BoxLayout(panelAcompanhantes, BoxLayout.Y_AXIS));
        panelAcompanhantes.setBorder(BorderFactory.createTitledBorder("Acompanhantes Cadastrados"));
        panelAcompanhantes.setBackground(Color.WHITE);
        
        if (hospedeAtual != null) {
            carregarAcompanhantes(hospedeAtual.getIdhospede());
        }
        
        formPanel.add(panelAcompanhantes);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // ========== SEÇÃO: CHECK-OUT ==========
        if (hospedeAtual != null && "ocupado".equals(quarto.getStatus())) {
            JPanel checkOutPanel = createStyledPanel("Check-out");
            checkOutPanel.setLayout(new GridLayout(0, 2, 2, 4));
            checkOutPanel.setPreferredSize(new Dimension(300, 100));
            
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

        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setPreferredSize(new Dimension(600, 300));
        mainPanel.add(formScrollPane, BorderLayout.CENTER);
        // Dentro do método initComponents(), após a criação do JScrollPane
formScrollPane.getVerticalScrollBar().setUnitIncrement(20);   // Ajuste o valor
formScrollPane.getHorizontalScrollBar().setUnitIncrement(20); // Para rolagem mais rápida
        
        
        
        // ========== ÁREA DE CONSUMO ==========
        JPanel consumoPanel = new JPanel(new BorderLayout());
        consumoPanel.setBorder(BorderFactory.createTitledBorder("Registro de Consumo"));
        consumoPanel.setPreferredSize(new Dimension(600, 150));
        
        String[] colunas = {"Item", "Quantidade", "Valor Unitário", "Total"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaConsumo = new JTable(modeloTabela);
        
        JPanel consumoInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Inicialize os campos de texto
        txtCodigoConsumo = new javax.swing.JTextField();
        txtCodigoConsumo.setColumns(10); // Define o tamanho
    
        txtQuantidadeConsumo = new javax.swing.JTextField();
        txtQuantidadeConsumo.setColumns(3);
        txtQuantidadeConsumo.setText("1"); // Valor padrão
        
        
     
        
      
        
        // Configura ações dos botões
        // Por:
        JButton btnConsultarProduto = new JButton("Consultar");
        btnConsultarProduto.addActionListener(e -> abrirConsultaProdutos());
        
        
          
        JButton btnAdicionarConsumo = new JButton("Adicionar");
        btnAdicionarConsumo.addActionListener(e -> adicionarConsumo());


        // Adiciona componentes ao painel
        consumoInputPanel.add(new JLabel("Código:"));
        consumoInputPanel.add(txtCodigoConsumo);
        consumoInputPanel.add(new JLabel("Quantidade:"));
        consumoInputPanel.add(txtQuantidadeConsumo);
        consumoInputPanel.add(btnConsultarProduto);
        consumoInputPanel.add(btnAdicionarConsumo);
        // Tabela de consumos
        tabelaConsumo = new JTable();
        JScrollPane scrollConsumo = new JScrollPane(tabelaConsumo);
        
       
    
        
        consumoPanel.add(consumoInputPanel, BorderLayout.NORTH);
        consumoPanel.add(new JScrollPane(tabelaConsumo), BorderLayout.CENTER);
        
        mainPanel.add(consumoPanel, BorderLayout.SOUTH);
        
        // ========== BOTÕES DE AÇÃO ==========
        
        int larguraBotao = 180;  // Aumentei a largura
        int alturaBotao = 40;    // Aumentei a altura
        Font fonteBotao = new Font("SansSerif", Font.BOLD, 14);  // Fonte maior (opcional)
        Font fonteBotaoExtra = new Font("SansSerif", Font.BOLD, 12);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        
       
        
        btnSalvar = new BotaoArredondado("Salvar", 15, 15);
        btnSalvar.setBackground(new Color (70,130,180));
        btnSalvar.setForeground(Color.white);
        btnSalvar.setPreferredSize(new Dimension(larguraBotao, alturaBotao));
        btnSalvar.setFont(fonteBotao);
        btnSalvar.addActionListener(e -> {
            try {
                salvarInformacoes();
                btnSalvar.setVisible(false);
                btnAdicionarAcompanhante.setVisible(true);
                novoAcompanhantePanel.setVisible(true);
                hospedeCadastrado = true;
                dateChooserCheckIn.getDateEditor().setEnabled(false);
                configurarVisibilidadeBotoes();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.add(btnSalvar);
        
        btnAdicionarAcompanhante = new BotaoArredondado("Adicionar Acompanhante", 15, 15);
        btnAdicionarAcompanhante.setBackground(new Color(100, 150, 200));
        btnAdicionarAcompanhante.setForeground(Color.white);
        btnAdicionarAcompanhante.setPreferredSize(new Dimension(larguraBotao, alturaBotao));
        btnAdicionarAcompanhante.setFont(fonteBotao);        
        btnAdicionarAcompanhante.setVisible(false);
        btnAdicionarAcompanhante.addActionListener(e -> adicionarAcompanhante());
        buttonPanel.add(btnAdicionarAcompanhante);
        
        btnExtraAcompanhante = new BotaoArredondado("Acompanhante Extra (+R$50)",15, 15);
        btnExtraAcompanhante.setBackground( new Color(150, 100, 200));
        btnExtraAcompanhante.setForeground(Color.white);
        btnExtraAcompanhante.setPreferredSize(new Dimension(larguraBotao, alturaBotao));
        btnExtraAcompanhante.setFont(fonteBotaoExtra);
        btnExtraAcompanhante.setVisible(false);
        btnExtraAcompanhante.addActionListener(e -> adicionarAcompanhanteExtra());
        buttonPanel.add(btnExtraAcompanhante);
        
        txtValorExtra = new JTextField("50.00", 5);
        txtValorExtra.setVisible(false);
        buttonPanel.add(txtValorExtra);
        
        JButton btnFechar = new BotaoArredondado("Fechar", 15, 15);
        btnFechar.setBackground( Color.red);
        btnFechar.setForeground(Color.black);
        btnFechar.setPreferredSize(new Dimension(larguraBotao, alturaBotao));
        btnFechar.setFont(fonteBotao);
        btnFechar.addActionListener(e -> fecharJanela());
        buttonPanel.add(btnFechar);
        
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        // Configurações iniciais
        configurarVisibilidadeBotoes();
        configurarNavegacaoPorEnter();

        
}
private void configurarEnterParaCPF() {
    txtCPF.addActionListener(e -> {
        try {
            String cpf = txtCPF.getText().replaceAll("[^0-9]", "");
            
            if (cpf.length() == 11) {
                // Verifica se já existe hóspede com este CPF
                HospedeDAO hospedeDAO = new HospedeDAO(connection);
                Hospede hospedeExistente = hospedeDAO.buscarPorCPF(cpf);
                
                if (hospedeExistente != null) {
                    int resposta = JOptionPane.showConfirmDialog(
                        this,
                        "Já existe um hóspede cadastrado com este CPF.\nDeseja usar os dados existentes?",
                        "Hóspede Existente",
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (resposta == JOptionPane.YES_OPTION) {
                        // Preenche os campos com os dados existentes
                        txtNomeHospede.setText(hospedeExistente.getNome());
                        telefone.setText(hospedeExistente.getTelefone());
                        txtPlacaVeiculo.setText(hospedeExistente.getPlacaVeiculo());
                        
                        // Se quiser preencher email também (se tiver o campo)
                        if (Email != null) {
                            Email.setText(hospedeExistente.getEmail());
                        }
                        
                        // Vai para o próximo campo
                        telefone.requestFocusInWindow();
                    } else {
                        // Limpa o CPF e mantém o foco nele
                        txtCPF.setText("");
                        txtCPF.requestFocus();
                    }
                } else {
                    // Se não existir, vai para o próximo campo normalmente
                    telefone.requestFocusInWindow();
                }
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "CPF inválido! Deve conter 11 dígitos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
                txtCPF.requestFocus();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Erro ao verificar CPF: " + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    });
}
// ========== MÉTODOS AUXILIARES PARA ESTILIZAÇÃO ==========
private void configurarNavegacaoPorEnter() {
    
    // Configuração para os campos do hóspede
    txtNomeHospede.addActionListener(e -> txtCPF.requestFocusInWindow());
    txtCPF.addActionListener(e -> verificarHospedePorCPF()); // Alterado para chamar a verificação
    configurarEnterParaProximoCampo(telefone, txtPlacaVeiculo);
    
    // Configuração para os campos de acompanhante
    configurarEnterParaProximoCampo(txtAcompanhantes, txtCPFAcompanhante);
    configurarEnterParaProximoCampo(txtCPFAcompanhante, txtTelefoneAcompanhante);
    
    // Configuração para os campos de consumo
    configurarEnterParaProximoCampo(txtCodigoConsumo, txtQuantidadeConsumo);
    
    // No último campo, executar a ação de adicionar consumo
    txtQuantidadeConsumo.addActionListener(e -> adicionarConsumo());
}
  


private void configurarEnterParaProximoCampo(JTextField campoAtual, JTextField proximoCampo) {
    campoAtual.addActionListener(e -> proximoCampo.requestFocusInWindow());
}
    
    // Metodo para buscar o produto 
private void buscarProduto() {
    String codigo = txtCodigoConsumo.getText().trim();
    if (codigo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Informe o código do produto!");
        return;
    }

    try {
        Produto produto = produtoDAO.buscarPorCodigo(codigo);
        if (produto != null) {
            JOptionPane.showMessageDialog(this, 
                "Produto: " + produto.getNome() + "\n" +
                "Preço: R$ " + String.format("%.2f", produto.getPreco()),
                "Produto Encontrado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Produto não encontrado!");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao buscar produto: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
  }
  
    
    // Método para carregar formas de pagamento
private void carregarFormasPagamento() {
    try {
        FormaPagamentoDAO fpDAO = new FormaPagamentoDAO(connection);
        List<FormaPagamento> formas = fpDAO.listarAtivas();
        
        cbFormasPagamento.removeAllItems();
        for (FormaPagamento fp : formas) {
            cbFormasPagamento.addItem(fp.getNome());
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar formas de pagamento: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

// Método para registrar adiantamento
private void registrarAdiantamento() {
    try {
        double valor = Double.parseDouble(txtValorAdiantamento.getText());
        if (valor <= 0) {
            throw new NumberFormatException();
        }
        
        Adiantamento adiantamento = new Adiantamento();
        adiantamento.setIdHospede(hospedeAtual.getIdhospede());
        adiantamento.setValor(valor);
        
        AdiantamentoDAO adiantamentoDAO = new AdiantamentoDAO(connection);
        if (adiantamentoDAO.registrarAdiantamento(adiantamento)) {
            JOptionPane.showMessageDialog(this, "Adiantamento registrado com sucesso!");
            txtValorAdiantamento.setText("");
            atualizarSaldoDisponivel();
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Valor inválido para adiantamento",
            "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao registrar adiantamento: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

// Método para registrar pagamento
private void registrarPagamento() {
    try {
        double valor = Double.parseDouble(txtValorPagamento.getText());
        if (valor <= 0) {
            throw new NumberFormatException();
        }
        
        Pagamento pagamento = new Pagamento();
        pagamento.setIdHospedagem(/* obtenha o id da hospedagem atual */);
        pagamento.setIdFormaPagamento(cbFormasPagamento.getSelectedIndex() + 1);
        pagamento.setValor(valor);
        pagamento.setStatus("pago");
        pagamento.setObservacoes(txtObservacoesPagamento.getText());
        
        PagamentoDAO pagamentoDAO = new PagamentoDAO(connection);
        if (pagamentoDAO.registrarPagamento(pagamento)) {
            JOptionPane.showMessageDialog(this, "Pagamento registrado com sucesso!");
            txtValorPagamento.setText("");
            txtObservacoesPagamento.setText("");
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Valor inválido para pagamento",
            "Erro", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao registrar pagamento: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

// Método para atualizar saldo disponível
private void atualizarSaldoDisponivel() {
    try {
        AdiantamentoDAO adiantamentoDAO = new AdiantamentoDAO(connection);
        double saldo = adiantamentoDAO.saldoDisponivel(hospedeAtual.getIdhospede());
        lblSaldoDisponivel.setText(String.format("Saldo disponível: R$ %.2f", saldo));
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao consultar saldo: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
}


private void adicionarAcompanhanteExtra() {
         try {
        double valorExtra = Double.parseDouble(txtValorExtra.getText());
        
        if (valorExtra <= 0) {
            JOptionPane.showMessageDialog(this, 
                "O valor extra deve ser maior que zero",
                "Valor inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validarCamposAcompanhante()) {
            return;
        }

        Acompanhante acompanhante = criarAcompanhanteFromForm(hospedeAtual.getIdhospede());
        acompanhante.setExtra(true);
        acompanhante.setValorExtra(valorExtra);
        
        AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(connection);
        boolean sucesso = acompanhanteDAO.salvarextra   (acompanhante);
        
        if (sucesso) {
            JOptionPane.showMessageDialog(this, 
                "Acompanhante extra adicionado com sucesso!\nValor adicional: R$ " + valorExtra,
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
            // Limpa os campos
            txtAcompanhantes.setText("");
            txtCPFAcompanhante.setText("");
            txtTelefoneAcompanhante.setText("");
            
            carregarAcompanhantes(hospedeAtual.getIdhospede());
            configurarVisibilidadeBotoes();
        } else {
            JOptionPane.showMessageDialog(this,
                "Falha ao cadastrar acompanhante extra",
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "Por favor, insira um valor numérico válido",
            "Valor inválido", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
            "Erro ao cadastrar acompanhante extra: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
    }

private Acompanhante criarAcompanhanteFromForm(int idhospede) {
    Acompanhante acompanhante = new Acompanhante();
    acompanhante.setNome(txtAcompanhantes.getText().trim());
    acompanhante.setCpfacompanhante(txtCPFAcompanhante.getText().replaceAll("[^0-9]", ""));
    acompanhante.setTelefone(txtTelefoneAcompanhante.getText().replaceAll("[^0-9]", ""));
    acompanhante.setidhospede(idhospede);
    acompanhante.setIdQuarto(quarto.getIdQuarto());
    acompanhante.setExtra(false); // Padrão é false, será alterado para true no método extra
    acompanhante.setValorExtra(0.0); // Valor padrão
    return acompanhante;
}
   
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

// ========== MÉTODOS do calendario ==========
// Método para verificar a diária
private void verificarDiaria() {
    // Implemente a lógica para calcular o tempo restante
    long tempoRestante = calcularTempoRestante();
    
    if (tempoRestante < 360000) { // Menos de 1 hora
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, 
                "A diária do quarto " + quarto.getNumero() + " está prestes a expirar!",
                "Aviso", JOptionPane.WARNING_MESSAGE);
        });
    }
}


// Adicione este método para marcar datas no calendário
private void marcarDatasCalendario(Date checkIn, Date checkOut) {
    Calendar cal = Calendar.getInstance();
        cal.setTime(checkIn);
        calendar.setCalendar(cal);
        
        // Mensagem informativa
        JOptionPane.showMessageDialog(this, 
            "Check-in: " + checkIn + "\nCheck-out: " + checkOut,
            "Datas da Hospedagem", JOptionPane.INFORMATION_MESSAGE);
    }




    private void adicionarAcompanhante() {
    // Verificar se há um hóspede principal
    if (hospedeAtual == null) {
        JOptionPane.showMessageDialog(this, 
            "Você precisa primeiro salvar o hóspede principal antes de adicionar acompanhantes",
            "Atenção", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Validar campos do acompanhante
    if (txtAcompanhantes.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "O nome do acompanhante é obrigatório",
            "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String cpf = txtCPFAcompanhante.getText().replaceAll("[^0-9]", "");
    if (cpf.length() != 11) {
        JOptionPane.showMessageDialog(this, 
            "CPF deve conter 11 dígitos",
            "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String telefone = txtTelefoneAcompanhante.getText().replaceAll("[^0-9]", "");
    if (telefone.length() < 10 || telefone.length() > 11) {
        JOptionPane.showMessageDialog(this, 
            "Telefone inválido (deve ter 10 ou 11 dígitos)",
            "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        // Verificar se o acompanhante já existe
        AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(connection);
        
        // Verificar se o acompanhante já existe no sistema (independente do hóspede)
        Acompanhante existente = buscarAcompanhantePorCPF(cpf);
        if (existente != null) {
            int resposta = JOptionPane.showConfirmDialog(this,
                "Já existe um acompanhante cadastrado com este CPF.\nDeseja usar os dados existentes?",
                "Acompanhante Existente", JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                // Usar dados existentes
                existente.setIdhospede(hospedeAtual.getIdhospede());
                existente.setIdQuarto(quarto.getIdQuarto());
                acompanhanteDAO.salvar(existente);
                // Atualizar interface
                carregarAcompanhantes(hospedeAtual.getIdhospede());
                JOptionPane.showMessageDialog(this, 
                    "Acompanhante vinculado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        // Verificar limite de acompanhantes
        List<Acompanhante> acompanhantes = AcompanhanteDAO.buscarPorHospede(hospedeAtual.getIdhospede());
        if (acompanhantes.size() >= calcularLimiteAcompanhantes()) {
            JOptionPane.showMessageDialog(this,
                "Limite de " + calcularLimiteAcompanhantes() + " acompanhantes atingido",
                "Limite Atingido", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmar com o usuário
        int confirm = JOptionPane.showConfirmDialog(this,
            "Confirmar cadastro do acompanhante?\n" +
            "Nome: " + txtAcompanhantes.getText() + "\n" +
            "CPF: " + txtCPFAcompanhante.getText() + "\n" +
            "Telefone: " + txtTelefoneAcompanhante.getText(),
            "Confirmar Acompanhante", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Criar e salvar o acompanhante
        Acompanhante acompanhante = criarAcompanhanteFromForm(hospedeAtual.getIdhospede());
        acompanhanteDAO.salvar(acompanhante);

        // Limpar campos e atualizar lista
        txtAcompanhantes.setText("");
        txtCPFAcompanhante.setText("");
        txtTelefoneAcompanhante.setText("");
        carregarAcompanhantes(hospedeAtual.getIdhospede());

        JOptionPane.showMessageDialog(this, 
            "Acompanhante cadastrado com sucesso!",
            "Sucesso", JOptionPane.INFORMATION_MESSAGE);

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
            "Erro ao cadastrar acompanhante: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

    //método para buscar acompanhante por CPF
private Acompanhante buscarAcompanhantePorCPF(String cpf) throws SQLException {
    String sql = "SELECT * FROM acompanhantes WHERE cpf = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, cpf);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Acompanhante a = new Acompanhante();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setCpfacompanhante(rs.getString("cpf"));
                a.setTelefone(rs.getString("telefone"));
                a.setIdhospede(rs.getInt("hospede_id"));
                a.setIdQuarto(rs.getInt("id_quarto"));
                return a;
            }
        }
    }
    return null;
}
    
    
    private boolean validarCamposAcompanhante() {
    if (txtAcompanhantes.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "O nome do acompanhante é obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    String cpf = txtCPFAcompanhante.getText().replaceAll("[^0-9]", "");
    if (cpf.length() != 11) {
        JOptionPane.showMessageDialog(this, "CPF do acompanhante deve conter 11 dígitos", "Erro", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    String telefone = txtTelefoneAcompanhante.getText().replaceAll("[^0-9]", "");
    if (telefone.length() < 10 || telefone.length() > 11) {
        JOptionPane.showMessageDialog(this, "Telefone do acompanhante inválido", "Erro", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    return true;
}

    private void salvarInformacoes() throws IOException, SQLException {
       if (!validarCampos()) {
        return;
    }

    try (Connection conexao = ConexaoBD.getConexao()) {
        conexao.setAutoCommit(false);

        // Criar hóspede a partir do formulário
        Hospede hospede = criarHospedeFromForm();
        
        // Obter a data/hora do check-in
        Date dataCheckIn = dateChooserCheckIn.getDate();
        if (dataCheckIn == null) {
            dataCheckIn = new Date();
        }
        
        // Calcular check-out
        Calendar calCheckIn = Calendar.getInstance();
        calCheckIn.setTime(dataCheckIn);
        
        Calendar calCheckOut = (Calendar) calCheckIn.clone();
        
        boolean isManhaCheckIn = calCheckIn.get(Calendar.HOUR_OF_DAY) < 12;
        
        if (isManhaCheckIn) {
            calCheckOut.set(Calendar.HOUR_OF_DAY, 12);
        } else {
            calCheckOut.add(Calendar.DAY_OF_MONTH, 1);
            calCheckOut.set(Calendar.HOUR_OF_DAY, 12);
        }
        calCheckOut.set(Calendar.MINUTE, 0);
        calCheckOut.set(Calendar.SECOND, 0);
        
        // Salvar o hóspede
        HospedeDAO hospedeDAO = new HospedeDAO(conexao);
        int idhospede = hospedeDAO.salvar(hospede);

        if (idhospede == -2) {
            // Hóspede já existe, atualizar dados
            Hospede existente = hospedeDAO.buscarPorCPF(hospede.getCpfHospede());
            existente.setIdQuarto(quarto.getIdQuarto());
            existente.setStatus("Hospedado"); // Atualizar status
            existente.setPlacaVeiculo(hospede.getPlacaVeiculo());
            hospedeDAO.atualizar(existente);
            idhospede = existente.getIdhospede();
        }

        if (idhospede <= 0) {
            conexao.rollback();
            JOptionPane.showMessageDialog(this, "Falha ao salvar hóspede", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.hospedeAtual = hospedeDAO.buscarPorId(idhospede);
        
        if (this.hospedeAtual == null) {
            conexao.rollback();
            throw new SQLException("Falha ao recuperar dados do hóspede após cadastro");
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
        
        // Registrar hospedagem
        int idFuncionario = obterIdFuncionarioLogado();
        HospedagemDAO hospedagemDAO = new HospedagemDAO(conexao);
        hospedagemDAO.registrarHospedagem(quarto.getIdQuarto(), idhospede, idFuncionario, 
                                         dataCheckIn, calCheckOut.getTime());

        conexao.commit();

        // Atualizar interface
        telaPrincipal.atualizarBotaoQuarto(quarto);
        JOptionPane.showMessageDialog(this, "Hospedagem registrada com sucesso!");
        configurarVisibilidadeBotoes();
        
    } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(),
                "Erro de Validação", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Erro ao registrar hospedagem: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        throw e;
    }
    }
    
    
    private void verificarHospedePorCPF() {
    try {
        String cpf = txtCPF.getText().replaceAll("[^0-9]", "");
        
        if (cpf.length() == 11) {
            HospedeDAO hospedeDAO = new HospedeDAO(connection);
            Hospede hospedeExistente = hospedeDAO.buscarPorCPF(cpf);
            
            if (hospedeExistente != null) {
                int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Hóspede já cadastrado com este CPF.\nDeseja usar os dados existentes?",
                    "Hóspede Existente",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (resposta == JOptionPane.YES_OPTION) {
                    // Preenche os campos com os dados existentes
                    txtNomeHospede.setText(hospedeExistente.getNome());
                    telefone.setText(hospedeExistente.getTelefone());
                    txtPlacaVeiculo.setText(hospedeExistente.getPlacaVeiculo());
                    
                    // Se houver campo de email, preencha também
                    if (Email != null) {
                        Email.setText(hospedeExistente.getEmail());
                    }
                    
                    // Move o foco para o próximo campo
                    telefone.requestFocusInWindow();
                } else {
                    // Limpa o CPF e mantém o foco nele
                    txtCPF.setText("");
                    txtCPF.requestFocus();
                }
            } else {
                // Se não existir, vai para o próximo campo normalmente
                telefone.requestFocusInWindow();
            }
        } else {
            JOptionPane.showMessageDialog(
                this,
                "CPF inválido! Deve conter 11 dígitos.",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
            txtCPF.requestFocus();
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(
            this,
            "Erro ao verificar CPF: " + ex.getMessage(),
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
    
    private void configurarVisibilidadeBotoes() {
          boolean hospedeExiste = hospedeAtual != null;
    
    btnSalvar.setVisible(!hospedeExiste);
    btnAdicionarAcompanhante.setVisible(hospedeExiste);
    novoAcompanhantePanel.setVisible(hospedeExiste);

    if (hospedeExiste) {
        try {
            // Buscar apenas acompanhantes da hospedagem ATUAL
            List<Acompanhante> acompanhantes = AcompanhanteDAO.buscarPorHospedagemAtual(
                hospedeAtual.getIdhospede(), quarto.getIdQuarto());
            
            int limite = calcularLimiteAcompanhantes();
            
            if (acompanhantes.size() >= limite) {
                btnAdicionarAcompanhante.setVisible(false);
                btnExtraAcompanhante.setVisible(true);
                txtValorExtra.setVisible(true);
                lblLimiteAcompanhantes.setText("Limite atingido! Adicione como extra.");
            } else {
                btnAdicionarAcompanhante.setVisible(true);
                btnExtraAcompanhante.setVisible(false);
                txtValorExtra.setVisible(false);
                lblLimiteAcompanhantes.setText("Acompanhantes: " + acompanhantes.size() + "/" + limite);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao verificar acompanhantes: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
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
    if (timerDiaria != null) {
        timerDiaria.stop();
    }
    try {
        if (connection != null && !connection.isClosed()) {
            connection.close();
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

        

        

        return hospede;
    }

    private void carregarAcompanhantes(int idHospede) {
      try {
        panelAcompanhantes.removeAll();
        
        List<Acompanhante> acompanhantes = AcompanhanteDAO.buscarPorHospede(idHospede);
        
        
        if (acompanhantes.isEmpty()) {
            panelAcompanhantes.add(new JLabel("Nenhum acompanhante cadastrado"));
        } else {
            for (Acompanhante a : acompanhantes) {
                JPanel panelAcomp = new JPanel(new GridLayout(0, 2, 5, 5));
                panelAcomp.setBorder(BorderFactory.createTitledBorder("Acompanhante #" + a.getId()));
                
                panelAcomp.add(new JLabel("Nome:"));
                panelAcomp.add(new JLabel(a.getNome()));
                
                panelAcomp.add(new JLabel("CPF:"));
                panelAcomp.add(new JLabel(formatarCPF(a.getCpfacompanhante())));
                
                panelAcomp.add(new JLabel("Telefone:"));
                panelAcomp.add(new JLabel(formatarTelefone(a.getTelefone())));
                
                panelAcompanhantes.add(panelAcomp);
                panelAcompanhantes.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        // Atualizar contagem
        lblLimiteAcompanhantes.setText(String.format(
            "Acompanhantes: %d/%d (Limite)", 
            acompanhantes.size(), 
            calcularLimiteAcompanhantes()));
        
        panelAcompanhantes.revalidate();
        panelAcompanhantes.repaint();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
            "Erro ao carregar acompanhantes: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
    
        // Adiciona o label de limite
        lblLimiteAcompanhantes = new JLabel();
        panelAcompanhantes.add(lblLimiteAcompanhantes);
        
        // Verifica o limite
        verificarLimiteAcompanhantes();
        
        panelAcompanhantes.revalidate();
        panelAcompanhantes.repaint();
    } 
    
    
    private String formatarCPF(String cpf) {
    if (cpf == null || cpf.length() != 11) return cpf;
    return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
}

    private String formatarTelefone(String telefone) {
    if (telefone == null) return "";
    if (telefone.length() == 11) {
        return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 7) + "-" + telefone.substring(7);
    } else if (telefone.length() == 10) {
        return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 6) + "-" + telefone.substring(6);
    }
    return telefone;
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
            // Atualiza o status do quarto
            quarto.setStatus("disponivel");
            quartoDAO.atualizarStatusQuarto(quarto);
            
            
            
            // Atualiza o status do hóspede para "Saída"
            HospedeDAO hospedeDAO = new HospedeDAO(connection);
            hospedeDAO.atualizarStatusHospede(hospedeAtual.getIdhospede(), "Checkout");

            // Atualiza a interface
            telaPrincipal.atualizarBotaoQuarto(quarto);
            JOptionPane.showMessageDialog(this, "Check-out realizado com sucesso!");
            fecharJanela();
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
private Date[] obterDatasHospedagem(int idQuarto) throws SQLException {
    String sql = "SELECT check_in, check_out FROM hospedagem WHERE id_quarto = ? ORDER BY id DESC LIMIT 1";
    
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, idQuarto);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Date checkIn = rs.getTimestamp("check_in");
                Date checkOut = rs.getTimestamp("check_out");
                return new Date[]{checkIn, checkOut};
            }
        }
    }
    return new Date[]{null, null};
}

   private long calcularTempoRestante() {
    try {
        Date[] datas = obterDatasHospedagem(quarto.getIdQuarto());
        Date checkIn = datas[0];
        
        if (checkIn == null) {
            return Long.MAX_VALUE;
        }
        
        Calendar checkInCal = Calendar.getInstance();
        checkInCal.setTime(checkIn);
        
        Calendar agora = Calendar.getInstance();
        Calendar fimDiaria = (Calendar) checkInCal.clone();
        
        // Verifica se é check-in antes do meio-dia
        boolean isManhaCheckIn = checkInCal.get(Calendar.HOUR_OF_DAY) < 12;
        
        if (isManhaCheckIn) {
            // Diária até 12:00 do mesmo dia
            fimDiaria.set(Calendar.HOUR_OF_DAY, 12);
        } else {
            // Diária até 12:00 do dia seguinte
            fimDiaria.add(Calendar.DAY_OF_MONTH, 1);
            fimDiaria.set(Calendar.HOUR_OF_DAY, 12);
        }
        fimDiaria.set(Calendar.MINUTE, 0);
        fimDiaria.set(Calendar.SECOND, 0);
        
        return fimDiaria.getTimeInMillis() - agora.getTimeInMillis();
        
    } catch (SQLException ex) {
        return Long.MAX_VALUE;
    }
         }

    private void abrirConsultaProdutos() {
      try {
        // Cria a janela de consulta
        consprodutos consulta = new consprodutos(connection);
        
        // Cria um JDialog para conter a janela de consulta
        JDialog dialog = new JDialog();
        dialog.setTitle("Consulta de Produtos");
        dialog.setModal(true);
        dialog.setContentPane(consulta);
        dialog.pack();
        dialog.setLocationRelativeTo(this); // Centraliza em relação à janela atual
        
        // Adiciona listener para quando fechar
        consulta.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                if (consulta.getProdutoSelecionado() != null) {
                    txtCodigoConsumo.setText(consulta.getProdutoSelecionado().getCodigo());
                    txtQuantidadeConsumo.requestFocus();
                }
                dialog.dispose();
            }
        });
        
        dialog.setVisible(true);
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
            "Erro ao abrir consulta de produtos: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
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
