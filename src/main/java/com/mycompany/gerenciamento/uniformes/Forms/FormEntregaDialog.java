/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Forms;

import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author geinfo
 */
public class FormEntregaDialog extends JDialog {
    
    private JTextField txtMatricula;
    private JLabel lblNomeAluno;
    private JComboBox<UniformeModel> comboUniformes;
    private JComboBox<TamanhoModel> comboTamanhos;
    private JTextField txtQuantidade;
    
    private AlunoModel alunoSelecionado = null;
    private EntregaModel entregaCriada = null;
    
    public FormEntregaDialog(Frame parent) {
        super(parent, "Cadastrar Nova Distribuição", true);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        txtMatricula = new JTextField(15);
        lblNomeAluno = new JLabel("Matrícula");
        lblNomeAluno.setFont(new Font("Segoe UI", Font.ITALIC, 12)); 
        comboUniformes = new JComboBox<>();
        comboTamanhos = new JComboBox<>();
        txtQuantidade = new JTextField("1");
        
        
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Matrícula Aluno:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtMatricula, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Nome do Aluno:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(lblNomeAluno, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Uniforme:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(comboUniformes, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Tamanho:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(comboTamanhos, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; panel.add(txtQuantidade, gbc);
        
        add(panel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(parent);
    }
}
