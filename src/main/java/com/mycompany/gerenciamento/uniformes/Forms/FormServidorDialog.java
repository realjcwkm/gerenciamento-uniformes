package com.mycompany.gerenciamento.uniformes.Forms;

import com.mycompany.gerenciamento.uniformes.Controllers.ServidorController;
import com.mycompany.gerenciamento.uniformes.Models.DepartamentoModel;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
    private ButtonGroup statusGroup;

    private final ServidorController servidorController;
    private boolean salvo = false;

    public FormServidorDialog(Frame parent) {
        super(parent, "Cadastrar Novo Servidor", true); // 'true' para ser modal

        this.servidorController = new ServidorController();

        // Layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Inicializando Componentes
        tfNome = new JTextField(20);
        tfSobrenome = new JTextField(20);
        tfMatricula = new JTextField(15);
        tfEmail = new JTextField(20);
        tfTelefone = new JTextField(15);
        cbDepartamentos = new JComboBox<>();
        rbAtivo = new JRadioButton("Ativo", true);
        rbInativo = new JRadioButton("Inativo");
        statusGroup = new ButtonGroup();
        statusGroup.add(rbAtivo);
        statusGroup.add(rbInativo);
        JPanel statusPanel = new JPanel();
        statusPanel.add(rbAtivo);
        statusPanel.add(rbInativo);

        // Adicionando componentes ao painel
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(tfNome, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Sobrenome:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(tfSobrenome, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Matrícula:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(tfMatricula, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(tfEmail, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; panel.add(tfTelefone, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Departamento:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; panel.add(cbDepartamentos, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; panel.add(statusPanel, gbc);
        
        carregarComboBoxDepartamentos();

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
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
            JOptionPane.showMessageDialog(this, "Nome, Matrícula e Departamento são obrigatórios!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean sucesso = servidorController.cadastrarNovoServidor(
                nome, sobrenome, email, telefone, matricula, isAtivo, departamento
        );

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
