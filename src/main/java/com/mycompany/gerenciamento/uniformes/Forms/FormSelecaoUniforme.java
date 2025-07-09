/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Forms;

import com.mycompany.gerenciamento.uniformes.Controllers.TrocaController;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author geinfo
 */
public class FormSelecaoUniforme extends JDialog {
    private JComboBox<TipoUniformeModel> comboTipos;
    private JComboBox<TamanhoModel> comboTamanhos;
    private JButton btnAvancar;
    private JButton btnCancelar;
    
    private final TrocaController trocaController;
    private UniformeModel uniformeSelecionado = null;

    public FormSelecaoUniforme(Frame parent) {
        super(parent, "Cadastrar Troca de Uniforme", true);
        
        this.trocaController = new TrocaController();
         
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Fontes e Cores
        Font fonteTitulo = new Font("Segoe UI", Font.BOLD, 22);
        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 14);
        Font fonteBotao = new Font("Segoe UI", Font.BOLD, 14);
        Color corBotaoSalvar = new Color(0, 164, 55);
        Color corBotaoCancelar = new Color(238, 63, 63);
        
        Dimension tamanhoCampo = new Dimension(250, 35);
        comboTamanhos = new JComboBox<>();
        comboTamanhos.setPreferredSize(tamanhoCampo);
        comboTipos = new JComboBox<>();
        comboTipos.setPreferredSize(tamanhoCampo);
        
        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        
         // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel lblTitulo = new JLabel("Cadastrar Troca de Uniforme");
        lblTitulo.setFont(fonteTitulo);
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        
        // Formulário
        addFormField(panel, gbc, "Selecione o tipo:", comboTipos, fonteLabel, 0, 1);
        addFormField(panel, gbc, "Tamanho:", comboTamanhos, fonteLabel, 1, 1);
        
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        Dimension tamanhoBotao = new Dimension(120, 40);
        JButton btnAvancar = createStyledButton("Avançar", corBotaoSalvar, fonteBotao, tamanhoBotao);
        btnAvancar.addActionListener(e -> {
            TipoUniformeModel tipo = (TipoUniformeModel) comboTipos.getSelectedItem();
            TamanhoModel tamanho = (TamanhoModel) comboTamanhos.getSelectedItem();

            this.uniformeSelecionado = trocaController.buscarUniformePorTipoETamanho(tipo, tamanho);

            if (this.uniformeSelecionado != null) {
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Não existe um uniforme em estoque com esta combinação de tipo e tamanho.", "Uniforme Não Encontrado", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnCancelar = createStyledButton("Cancelar", corBotaoCancelar, fonteBotao, tamanhoBotao);
        btnCancelar.addActionListener(e -> dispose());

        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnAvancar);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 0, 0, 0);
        panel.add(buttonPanel, gbc);
       
        
        carregarComboBoxes();
       
        add(panel, BorderLayout.CENTER);
        
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
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, java.awt.Component component, Font font, int gridx, int gridy) {
        // Labels
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = new Insets(10, 0, 5, 0);
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label, gbc);
        
        // Componente (TextField, ComboBox)
        gbc.gridy = gridy + 1;
        gbc.insets = new Insets(0, 0, 0, 20);
        panel.add(component, gbc);
    }
    
    private void carregarComboBoxes() {
        this.trocaController.getAllTamanhos().forEach(comboTamanhos::addItem);
        this.trocaController.getAllTipos().forEach(comboTipos::addItem);
    }
    
    public UniformeModel getUniformeSelecionado() { 
        return uniformeSelecionado; 
    }
}
