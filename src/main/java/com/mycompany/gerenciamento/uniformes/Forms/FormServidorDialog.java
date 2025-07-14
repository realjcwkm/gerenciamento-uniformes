package com.mycompany.gerenciamento.uniformes.Forms;

import com.mycompany.gerenciamento.uniformes.Controllers.ServidorController;
import com.mycompany.gerenciamento.uniformes.Models.DepartamentoModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
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

public class FormServidorDialog extends JDialog {

    private JTextField tfNome, tfSobrenome, tfEmail, tfTelefone, tfMatricula;
    private JComboBox<DepartamentoModel> cbDepartamentos;
    private JRadioButton rbAtivo, rbInativo;

    private final ServidorController servidorController;
    private boolean salvo = false;

    public FormServidorDialog(Frame parent) {
        super(parent, "Cadastrar Servidor", true);
        this.servidorController = new ServidorController();

        setResizable(false);
        setLayout(new BorderLayout());

        JPanel panelPai = new JPanel(new GridBagLayout());
        panelPai.setBackground(Color.WHITE);
        panelPai.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); 

        // Fontes e Cores
        Font fonteTitulo = new Font("Segoe UI", Font.BOLD, 22);
        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 14);
        Font fonteBotao = new Font("Segoe UI", Font.BOLD, 14);
        Color corBotaoSalvar = new Color(0, 164, 55);
        Color corBotaoCancelar = new Color(238, 63, 63);

        // Componentes
        Dimension tamanhoCampo = new Dimension(250, 35);
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

        rbAtivo = new JRadioButton("Ativo");
        rbAtivo.setFont(fonteLabel);
        rbAtivo.setBackground(Color.WHITE);
        rbAtivo.setSelected(true);

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

        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel lblTitulo = new JLabel("Cadastrar Servidor");
        lblTitulo.setFont(fonteTitulo);
        panelPai.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        // Formulário
        addFormField(panelPai, gbc, "Nome:", tfNome, fonteLabel, 0, 1);
        addFormField(panelPai, gbc, "Sobrenome:", tfSobrenome, fonteLabel, 1, 1);
        addFormField(panelPai, gbc, "E-mail:", tfEmail, fonteLabel, 0, 3);
        addFormField(panelPai, gbc, "Telefone:", tfTelefone, fonteLabel, 1, 3);
        addFormField(panelPai, gbc, "Departamento:", cbDepartamentos, fonteLabel, 0, 5);
        addFormField(panelPai, gbc, "Matrícula:", tfMatricula, fonteLabel, 1, 5);

        // Status
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.insets = new Insets(15, 0, 5, 0);
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(fonteLabel);
        panelPai.add(lblStatus, gbc);

        gbc.gridx = 0; gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelPai.add(statusPanel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        Dimension tamanhoBotao = new Dimension(120, 40);
        JButton btnSalvar = createStyledButton("Salvar", corBotaoSalvar, fonteBotao, tamanhoBotao);
        btnSalvar.addActionListener(e -> salvar());

        JButton btnCancelar = createStyledButton("Cancelar", corBotaoCancelar, fonteBotao, tamanhoBotao);
        btnCancelar.addActionListener(e -> dispose());

        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnSalvar);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 0, 0, 0);
        panelPai.add(buttonPanel, gbc);

        carregarComboBoxDepartamentos();
        add(panelPai, BorderLayout.CENTER);

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
    
    private void carregarComboBoxDepartamentos() {
        this.servidorController.getAllDepartamentos().forEach(cbDepartamentos::addItem);
    }
    
    private void salvar() {
        String nome = tfNome.getText().trim();
        String sobrenome = tfSobrenome.getText().trim();
        String email = tfEmail.getText().trim();
        String telefone = tfTelefone.getText().trim();
        String matricula = tfMatricula.getText().trim();
        boolean isAtivo = rbAtivo.isSelected();
        DepartamentoModel departamento = (DepartamentoModel) cbDepartamentos.getSelectedItem();

        if (nome.isEmpty() || matricula.isEmpty() || departamento == null) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean sucesso = servidorController.cadastrar(nome, sobrenome, email, telefone, matricula, isAtivo, departamento);

        if (sucesso) {
            this.salvo = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao cadastrar o servidor.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSalvo() {
        return this.salvo;
    }
}