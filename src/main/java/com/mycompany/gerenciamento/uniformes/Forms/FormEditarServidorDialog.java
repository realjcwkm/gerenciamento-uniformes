/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Forms;

import com.mycompany.gerenciamento.uniformes.Controllers.ServidorController;
import com.mycompany.gerenciamento.uniformes.Models.DepartamentoModel;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import com.mycompany.gerenciamento.uniformes.View.ViewsSistema;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author barbara
 */
public class FormEditarServidorDialog extends JDialog {
    private JTextField tfNome, tfSobrenome, tfEmail, tfTelefone, tfMatricula;
    private JRadioButton rbAtivo, rbInativo;
    private JComboBox<DepartamentoModel> cbDepartamentos;
    private JLabel lblAvisoMatricula;

    private final ServidorController servidorController;
    private final ServidorModel servidorParaEditar;
    private boolean salvo = false;

    public FormEditarServidorDialog(ViewsSistema parent, ServidorModel servidor) {
        super(parent, "Editar Servidor", true);
        
        this.servidorController = new ServidorController();
        this.servidorParaEditar = servidor;

        setResizable(false);
        setLayout(new BorderLayout());
        JPanel panelPai = new JPanel(new GridBagLayout());
        panelPai.setBackground(Color.WHITE);
        panelPai.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Fontes e Cores
        Font fonteTitulo = new Font("Segoe UI", Font.BOLD, 22);
        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 14);
        Font fonteAviso = new Font("Segoe UI", Font.ITALIC, 11);
        Font fonteBotao = new Font("Segoe UI", Font.BOLD, 14);
        Color corBotaoSalvar = new Color(0, 164, 55);
        Color corBotaoCancelar = new Color(238, 63, 63);
        Dimension tamanhoCampo = new Dimension(250, 35);

        // Componentes
        tfNome = new JTextField();
        tfNome.setPreferredSize(tamanhoCampo);
        tfSobrenome = new JTextField();
        tfSobrenome.setPreferredSize(tamanhoCampo);
        tfEmail = new JTextField();
        tfEmail.setPreferredSize(tamanhoCampo);
        tfTelefone = new JTextField();
        tfTelefone.setPreferredSize(tamanhoCampo);
        cbDepartamentos = new JComboBox<>();
        cbDepartamentos.setPreferredSize(tamanhoCampo);
        
        tfMatricula = new JTextField();
        tfMatricula.setPreferredSize(tamanhoCampo);
        tfMatricula.setEditable(false);
        
        lblAvisoMatricula = new JLabel("A matrícula não pode ser alterada.");
        lblAvisoMatricula.setFont(fonteAviso);
        lblAvisoMatricula.setForeground(Color.GRAY);

        rbAtivo = new JRadioButton("Ativo");
        rbAtivo.setFont(fonteLabel);
        rbAtivo.setBackground(Color.WHITE);
        rbInativo = new JRadioButton("Inativo");
        rbInativo.setFont(fonteLabel);
        rbInativo.setBackground(Color.WHITE);
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(rbAtivo);
        statusGroup.add(rbInativo);
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.add(rbAtivo);
        statusPanel.add(rbInativo);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel lblTitulo = new JLabel("Editar Informações do Servidor");
        lblTitulo.setFont(fonteTitulo);
        panelPai.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        // Formulário
        addFormField(panelPai, gbc, "Nome:", tfNome, fonteLabel, 0, 1);
        addFormField(panelPai, gbc, "Sobrenome:", tfSobrenome, fonteLabel, 1, 1);
        addFormField(panelPai, gbc, "E-mail:", tfEmail, fonteLabel, 0, 3);
        addFormField(panelPai, gbc, "Telefone:", tfTelefone, fonteLabel, 1, 3);
        addFormField(panelPai, gbc, "Departamento:", cbDepartamentos, fonteLabel, 1, 5);

        // Matrícula
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 5, 0);
        JLabel lblMatricula = new JLabel("Matrícula:");
        lblMatricula.setFont(fonteLabel);
        panelPai.add(lblMatricula, gbc);
        
        // Campo de Texto da Matrícula
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 2, 20);
        panelPai.add(tfMatricula, gbc);

        // Label de Aviso
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 20);
        panelPai.add(lblAvisoMatricula, gbc);
        
        // Status
        gbc.gridx = 0; gbc.gridy = 8;
        gbc.insets = new Insets(15, 0, 5, 0);
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(fonteLabel);
        panelPai.add(lblStatus, gbc);
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelPai.add(statusPanel, gbc);
        
        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        Dimension tamanhoBotao = new Dimension(120, 40);
        JButton btnSalvar = createStyledButton("Salvar", corBotaoSalvar, fonteBotao, tamanhoBotao);
        btnSalvar.addActionListener(e -> salvar());
        JButton btnCancelar = createStyledButton("Cancelar", corBotaoCancelar, fonteBotao, tamanhoBotao);
        btnCancelar.addActionListener(e -> dispose());
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnSalvar);
        gbc.gridx = 0; gbc.gridy = 10; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 0, 0, 0);
        panelPai.add(buttonPanel, gbc);

        // Finalização
        add(panelPai, BorderLayout.CENTER);
        carregarComboBoxDepartamentos();
        preencherFormulario();
        pack();
        setLocationRelativeTo(parent);
    }
    
    private void preencherFormulario() {
        tfNome.setText(servidorParaEditar.getNome());
        tfSobrenome.setText(servidorParaEditar.getSobrenome());
        tfEmail.setText(servidorParaEditar.getEmail());
        tfTelefone.setText(servidorParaEditar.getTelefone());
        tfMatricula.setText(servidorParaEditar.getMatricula());

        if (servidorParaEditar.isAtivo()) {
            rbAtivo.setSelected(true);
        } else {
            rbInativo.setSelected(true);
        }
        
        DepartamentoModel deptoAtual = null;
        for (int i = 0; i < cbDepartamentos.getItemCount(); i++) {
            if (cbDepartamentos.getItemAt(i).getId() == servidorParaEditar.getFk_departamento()) {
                deptoAtual = cbDepartamentos.getItemAt(i);
                break;
            }
        }
        if (deptoAtual != null) {
            cbDepartamentos.setSelectedItem(deptoAtual);
        }
    }
    
    private void salvar() {
        String nome = tfNome.getText().trim();
        String sobrenome = tfSobrenome.getText().trim();
        String email = tfEmail.getText().trim();
        String telefone = tfTelefone.getText().trim();
        boolean isAtivo = rbAtivo.isSelected();
        DepartamentoModel deptoSelecionado = (DepartamentoModel) cbDepartamentos.getSelectedItem();

        boolean houveMudanca = 
                !nome.equals(servidorParaEditar.getNome()) ||
                !sobrenome.equals(servidorParaEditar.getSobrenome()) ||
                !email.equals(servidorParaEditar.getEmail()) ||
                !telefone.equals(servidorParaEditar.getTelefone()) ||
                isAtivo != servidorParaEditar.isAtivo() ||
                (deptoSelecionado != null && deptoSelecionado.getId() != servidorParaEditar.getFk_departamento());

        if (!houveMudanca) {
            dispose();
            return;
        }

        servidorParaEditar.setNome(nome);
        servidorParaEditar.setSobrenome(sobrenome);
        servidorParaEditar.setEmail(email);
        servidorParaEditar.setTelefone(telefone);
        servidorParaEditar.setAtivo(isAtivo);
        if (deptoSelecionado != null) {
            servidorParaEditar.setFk_departamento(deptoSelecionado.getId());
        }

        try {
            servidorController.atualizarServidor(servidorParaEditar);
            this.salvo = true;
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao salvar no banco de dados.\nVerifique se o e-mail já está em uso.", 
                "", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void carregarComboBoxDepartamentos() {
        cbDepartamentos.removeAllItems(); 
        this.servidorController.getAllDepartamentos().forEach(cbDepartamentos::addItem);
    }
    
    public boolean isSalvo() {
        return this.salvo;
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, Component component, Font font, int gridx, int gridy) {
        gbc.gridx = gridx; gbc.gridy = gridy;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label, gbc);
        gbc.gridy = gridy + 1;
        gbc.insets = new Insets(0, 0, 0, 20);
        panel.add(component, gbc);
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
}