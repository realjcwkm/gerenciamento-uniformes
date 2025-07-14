/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author geinfo
 */
public class FlatButton extends JButton {
    private Color corDeFundo;

    public FlatButton() {
        this("FlatButton");
    }

    public FlatButton(String texto) {
        this(texto, new Color(22, 160, 133));
    }

    public FlatButton(String texto, Color corDeFundo) {
        super(texto);
        this.corDeFundo = corDeFundo;
        configurarEstilo();
    }
    
    private void configurarEstilo() {
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isRollover()) {
            g2.setColor(corDeFundo.darker());
        } else {
            g2.setColor(corDeFundo);
        }
        
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        super.paintComponent(g);
        g2.dispose();
    }

    public Color getCorDeFundo() {
        return corDeFundo;
    }

    public void setCorDeFundo(Color corDeFundo) {
        this.corDeFundo = corDeFundo;
        repaint();
    }
}
