/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author barbara
 */
public class FormMensagemConfirmacao extends JDialog {

    private boolean confirmado = false;

    public FormMensagemConfirmacao(Frame parent, String titulo, String mensagem, String textoBotaoConfirmar, String textoBotaoCancelar) {
        super(parent, titulo, true);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // --- Estilos ---
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        
        Font fonteTitulo = new Font("Segoe UI", Font.BOLD, 18);
        Font fonteMensagem = new Font("Segoe UI", Font.PLAIN, 14);
        Font fonteBotao = new Font("Segoe UI", Font.BOLD, 14);
        Color corBotaoConfirmar = new Color(4, 120, 87); 
        Color corBotaoCancelar = new Color(220, 53, 69);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(fonteTitulo);
        
        JLabel lblMensagem = new JLabel("<html><div style='text-align: center;'>" + mensagem + "</div></html>", SwingConstants.CENTER);
        lblMensagem.setFont(fonteMensagem);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);

        Dimension tamanhoBotao = new Dimension(130, 40);
        
        // Botão de Confirmação
        JButton btnConfirmar = createStyledButton(textoBotaoConfirmar, corBotaoConfirmar, fonteBotao, tamanhoBotao);
        btnConfirmar.addActionListener(e -> {
            this.confirmado = true;
            dispose();
        });
        
        // Botão de cancelar
        if (textoBotaoCancelar != null && !textoBotaoCancelar.isEmpty()) {
            JButton btnCancelar = createStyledButton(textoBotaoCancelar, corBotaoCancelar, fonteBotao, tamanhoBotao);
            btnCancelar.addActionListener(e -> {
                this.confirmado = false;
                dispose();
            });
            buttonPanel.add(btnCancelar);
        }
        
        buttonPanel.add(btnConfirmar);

        // --- Layout ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(lblTitulo, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 30, 0);
        panel.add(lblMensagem, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(buttonPanel, gbc);
        
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    // Botões estilizados
    private JButton createStyledButton(String text, Color background, Font font, Dimension size) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setPreferredSize(size);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    public boolean isConfirmado() {
        return confirmado;
    }
}