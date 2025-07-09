/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Forms;

import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author geinfo
 */
public class ConfirmacaoTroca extends JDialog {
    private boolean confirmado = false;

    public ConfirmacaoTroca(Frame parent, EntregaModel entregaAntiga, UniformeModel uniformeNovo) {
        super(parent, "", true);
        
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        Font fonteTitulo = new Font("Segoe UI", Font.BOLD, 22);
        Font fonteInfo = new Font("Segoe UI", Font.BOLD, 16);
        Font fonteSeta = new Font("Segoe UI", Font.BOLD, 36);
        Font fonteBotao = new Font("Segoe UI", Font.BOLD, 14);
        Color corBotaoSalvar = new Color(0, 164, 55);
        Color corBotaoCancelar = new Color(238, 63, 63);
        
        JLabel lblTitulo = new JLabel("Confirma Troca de Uniforme?");
        lblTitulo.setFont(fonteTitulo);

        String infoAluno = entregaAntiga.getAluno().getNome() + " (" + entregaAntiga.getAluno().getMatricula() + ")";
        JLabel lblAluno = new JLabel(infoAluno, SwingConstants.CENTER);
        lblAluno.setFont(fonteInfo);

        JPanel trocaPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        trocaPanel.setBackground(Color.WHITE);

        String descUniformeAntigo = "<html><div style='text-align: center;'>DE:<br>" 
                                  + entregaAntiga.getUniforme().getTipoUniforme().getNome() + "<br>" 
                                  + entregaAntiga.getUniforme().getTamanho().getNome() + "</div></html>";
        trocaPanel.add(new JLabel(descUniformeAntigo, SwingConstants.CENTER));

        JLabel seta = new JLabel("→", SwingConstants.CENTER);
        seta.setFont(fonteSeta);
        trocaPanel.add(seta);

        String descUniformeNovo = "<html><div style='text-align: center;'>PARA:<br>"  
                                + uniformeNovo.getTipoUniforme().getNome() + "<br>"
                                + uniformeNovo.getTamanho().getNome() + "</div></html>";
        trocaPanel.add(new JLabel(descUniformeNovo, SwingConstants.CENTER));
        
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        Dimension tamanhoBotao = new Dimension(120, 40);
        JButton btnConfirmar = createStyledButton("Confirmar", corBotaoSalvar, fonteBotao, tamanhoBotao);
        btnConfirmar.addActionListener(e -> {
            this.confirmado = true;
            dispose();
        });
        
        JButton btnCancelar = createStyledButton("Cancelar", corBotaoCancelar, fonteBotao, tamanhoBotao);
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnConfirmar);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 0); 

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblTitulo, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER; 
        panel.add(lblAluno, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(15, 0, 15, 0);
        panel.add(trocaPanel, gbc);

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.insets = new Insets(20, 0, 0, 0);
        panel.add(buttonPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 0, 0, 0);
        panel.add(buttonPanel, gbc);
        
        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }
    
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
