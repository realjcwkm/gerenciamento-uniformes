/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Components;

/**
 *
 * @author Wagner
 */
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.LayoutManager;
import java.awt.Dimension;

public class CardPanel extends JPanel {

    // Define o raio dos cantos arredondados
    private int cornerRadius = 20;

    public CardPanel(LayoutManager layout) {
        super(layout);
        initialize();
    }

    public CardPanel() {
        super();
        initialize();
    }

    private void initialize() {
        // Define o painel como não opaco. Isso é CRUCIAL para que o fundo
        // retangular padrão não seja desenhado por baixo das nossas bordas arredondadas.
        setOpaque(false);
    }
    
    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint(); // Chama repaint() para que a mudança seja visualizada imediatamente
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Primeiro, chamamos o método da superclasse para garantir que a cadeia de pintura não seja quebrada.
        super.paintComponent(g);

        // Criamos uma cópia do objeto Graphics e o convertemos para Graphics2D.
        // Isso nos dá acesso a métodos de desenho mais avançados.
        Graphics2D g2d = (Graphics2D) g.create();

        // Ativamos o Antialiasing para que as curvas das bordas fiquem suaves (sem serrilhados).
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- Desenho do Card ---
        // Pega a cor de fundo definida para o painel (ou a padrão).
        g2d.setColor(getBackground());

        // Desenha o retângulo com cantos arredondados.
        // Os últimos dois parâmetros são a largura e altura do arco do canto.
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // --- Desenho da Borda (Opcional) ---
        // Define a cor da borda (pode ser qualquer cor).
        g2d.setColor(Color.LIGHT_GRAY); 
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        // Libera os recursos do objeto Graphics2D. É uma boa prática.
        g2d.dispose();
    }
}
