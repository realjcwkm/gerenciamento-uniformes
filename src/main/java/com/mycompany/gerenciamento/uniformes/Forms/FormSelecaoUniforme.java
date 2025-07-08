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
import java.awt.FlowLayout;
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
        panel.setBorder(BorderFactory.createEmptyBorder(70 , 70, 70, 70));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        comboTipos = new JComboBox<>();
        comboTamanhos = new JComboBox<>();
        btnCancelar = new JButton("Cancelar");
        btnAvancar = new JButton("Avançar");
        
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Selecione o tipo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(comboTipos, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Tamanho:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(comboTamanhos, gbc);
        
        carregarComboBoxes();
       
        add(panel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnAvancar);
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(parent);
        
        
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
        
        btnCancelar.addActionListener(e -> dispose());
    }
    
    private void carregarComboBoxes() {
        this.trocaController.getAllTamanhos().forEach(comboTamanhos::addItem);
        this.trocaController.getAllTipos().forEach(comboTipos::addItem);
    }
    
    public UniformeModel getUniformeSelecionado() { 
        return uniformeSelecionado; 
    }
}
