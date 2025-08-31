package telas;

import javax.swing.*;
import java.awt.*;
import componentes.BotaoArredondado;
import dao.CaixaDAO;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.text.*;

public class PainelCaixa extends JDialog {
    private JTextArea txtResumo;
    private JTextField txtValor;
    private boolean modoAbertura;
    private int idFuncionario;
    private int idCaixa;
    private boolean operacaoConcluida = false;
    private boolean modoFechamento = false;
    private JLabel lblTitulo;

    public PainelCaixa(JFrame parent, boolean modoAbertura, int idFuncionario) {
        super(parent, modoAbertura ? "Abertura de Caixa" : "Fechamento de Caixa", true);
        this.modoAbertura = modoAbertura;
        this.idFuncionario = idFuncionario;
        
         try {
        // Verifica se já existe caixa aberto quando em modo abertura
        if (modoAbertura && CaixaDAO.temCaixaAberto(idFuncionario)) {
            JOptionPane.showMessageDialog(parent,
                "Já existe um caixa aberto para este funcionário!",
                "Atenção", JOptionPane.WARNING_MESSAGE);
            this.dispose();
            return;
        }
      

        configurarInterface();
        setLocationRelativeTo(parent);
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(parent,
            "Erro ao verificar caixa: " + ex.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
        this.dispose();
    }
         
         
         
        configurarInterface();
        setLocationRelativeTo(parent);
        
    }

    
    
    
    

    private void configurarInterface() {
       setSize(500, 400);
        setLayout(new BorderLayout(10, 10));
        
        // Painel de cabeçalho
        JPanel panelTopo = new JPanel();
        panelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTopo.setBackground(new Color(70, 130, 180));
        
        lblTitulo = new JLabel(modoAbertura ? "ABERTURA DE CAIXA" : "FECHAMENTO DE CAIXA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTopo.add(lblTitulo);
        
        // Painel central
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Inicializa txtResumo sempre, mas só mostra no fechamento
        txtResumo = new JTextArea();
        txtResumo.setEditable(false);
        txtResumo.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtResumo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scroll = new JScrollPane(txtResumo);
        
        if (!modoAbertura) {
            try {
                this.idCaixa = CaixaDAO.obterCaixaAberto(idFuncionario);
                if (this.idCaixa > 0) {
                    txtResumo.setText(CaixaDAO.gerarResumoCaixa(this.idCaixa));
                }
            } catch (SQLException e) {
                txtResumo.setText("Erro ao carregar resumo: " + e.getMessage());
            }
            panelCentro.add(scroll, BorderLayout.CENTER);
        }

         if (!modoAbertura) {
        try {
            this.idCaixa = CaixaDAO.obterCaixaAberto(idFuncionario);
            if (this.idCaixa > 0) {
                String resumo = CaixaDAO.gerarResumoCaixa(this.idCaixa);
                resumo += "\n\nVendas realizadas:\n";
                resumo += CaixaDAO.obterResumoVendas(this.idCaixa);
                txtResumo.setText(resumo);
            }
        } catch (SQLException e) {
            txtResumo.setText("Erro ao carregar resumo: " + e.getMessage());
        }
        panelCentro.add(scroll, BorderLayout.CENTER);
    }
        JPanel panelValor = new JPanel(new GridLayout(2, 1, 5, 5));
        JLabel lblValor = new JLabel("Valor:");
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtValor = new JTextField();
        txtValor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtValor.setHorizontalAlignment(JTextField.RIGHT);
        
        // Máscara para valores monetários
        txtValor.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) 
                throws BadLocationException {
                String text = getText(0, getLength());
                text += str;
                
                if (text.matches("^\\d{1,10}([.,]\\d{0,2})?$")) {
                    super.insertString(offs, str, a);
                }
            }
        });
        
        panelValor.add(lblValor);
        panelValor.add(txtValor);
        panelCentro.add(panelValor, BorderLayout.SOUTH);
        
        // Painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        BotaoArredondado btnConfirmar = criarBotao(modoAbertura ? "Abrir Caixa" : "Fechar Caixa", 
                                                 new Color(70, 130, 180));
        btnConfirmar.addActionListener(e -> processarCaixa());
        
        BotaoArredondado btnCancelar = criarBotao("Cancelar", new Color(160, 80, 80));
        btnCancelar.addActionListener(e -> {
            operacaoConcluida = false;
            dispose();
        });
        
        panelBotoes.add(btnConfirmar);
        panelBotoes.add(btnCancelar);
        
        // Montagem final
        add(panelTopo, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
        
        SwingUtilities.invokeLater(() -> txtValor.requestFocusInWindow());
    }

    private BotaoArredondado criarBotao(String texto, Color cor) {
        BotaoArredondado botao = new BotaoArredondado(texto, 15, 15);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setPreferredSize(new Dimension(120, 40));
        botao.setFocusPainted(false);
        
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor);
            }
        });
        
        return botao;
    }

    private void processarCaixa() {
         // Validação básica
        if (txtValor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o valor!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Formatação do valor
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            double valor = format.parse(txtValor.getText().trim()).doubleValue();
            
            if (valor <= 0) {
                JOptionPane.showMessageDialog(this, "Valor deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (modoAbertura) {
                // Lógica de abertura
                int novoIdCaixa = CaixaDAO.abrirCaixa(idFuncionario, valor);
                if (novoIdCaixa > 0) {
                    this.idCaixa = novoIdCaixa;
                    operacaoConcluida = true;
                    JOptionPane.showMessageDialog(this, 
                        "Caixa #" + idCaixa + " aberto com sucesso!\nValor inicial: R$ " + String.format("%.2f", valor),
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Falha ao abrir o caixa!", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Lógica de fechamento
                if (idCaixa <= 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Nenhum caixa aberto encontrado!", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (CaixaDAO.fecharCaixa(idCaixa, valor)) {
                    operacaoConcluida = true;
                    String resumo = CaixaDAO.gerarResumoCaixa(idCaixa);
                    JTextArea areaResumo = new JTextArea(resumo);
                    areaResumo.setEditable(false);
                    areaResumo.setFont(new Font("Consolas", Font.PLAIN, 14));
                    
                    JOptionPane.showMessageDialog(this, new JScrollPane(areaResumo), 
                        "Resumo do Caixa #" + idCaixa, JOptionPane.INFORMATION_MESSAGE);
                    
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Falha ao fechar o caixa!", 
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Valor inválido! Use números com ponto ou vírgula.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Erro no banco de dados: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    public boolean isOperacaoConcluida() {
        return operacaoConcluida;
    }

    public int getIdCaixa() {
        return idCaixa;
    }

    public void setIdCaixa(int idCaixa) {
        this.idCaixa = idCaixa;
    }

   public void setModoFechamento(boolean modoFechamento) {
            this.modoFechamento = modoFechamento;
    if (modoFechamento) {
        this.setTitle("Fechamento de Caixa");
        // Obter o ID do caixa aberto
        try {
            this.idCaixa = CaixaDAO.obterCaixaAberto(idFuncionario);
            if (this.idCaixa > 0) {
                txtResumo.setText(CaixaDAO.gerarResumoCaixa(this.idCaixa));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao obter caixa aberto: " + ex.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    }
}