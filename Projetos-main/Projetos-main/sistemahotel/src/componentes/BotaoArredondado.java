package componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class BotaoArredondado extends JButton {
    private int arcWidth;  // Largura do arco para as bordas arredondadas
    private int arcHeight; // Altura do arco para as bordas arredondadas

    public BotaoArredondado(String text, int arcWidth, int arcHeight) {
        super(text);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        setContentAreaFilled(false); // Remove o preenchimento padrão do botão
        setFocusPainted(false);     // Remove o efeito de foco padrão
        setBorderPainted(false);    // Remove a borda padrão
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Define a cor de fundo do botão
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));

        // Desenha o texto do botão
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics metrics = g2.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(getText())) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g2.drawString(getText(), x, y);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Desenha a borda arredondada
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getForeground());
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));
        g2.dispose();
    }
}