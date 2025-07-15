/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Forms;

import com.mycompany.gerenciamento.uniformes.Controllers.AlunoController;
import com.mycompany.gerenciamento.uniformes.Models.CursoModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author geinfo
 */
public class FormAlunoDialog extends JDialog {
    private final JTextField tfNome, tfSobrenome, tfEmail, tfTelefone, tfMatricula, tfIdade;
    private final JComboBox<CursoModel> cbCurso;
    private final JComboBox<Integer> cbPeriodo;
    
    private final AlunoController alunoController;
    private boolean salvo = false;
    
    public FormAlunoDialog(Frame parent) {
        super(parent, "Cadastrar Aluno", true);
        this.alunoController = new AlunoController();
        
        setResizable(false);
        setLayout(new BorderLayout());
        
        JPanel panelPai = new JPanel(new GridBagLayout());
        panelPai.setBackground(Color.WHITE);
        panelPai.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        Font fonteTitulo = new Font("Segoe UI", Font.BOLD, 22);
        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 14);
        Font fonteBotao = new Font("Segoe UI", Font.BOLD, 14);
        Color corBotaoSalvar = new Color(0, 164, 55);
        Color corBotaoCancelar = new Color(238, 63, 63);
        
        // Campos
        Dimension tamanhoCampo = new Dimension(250, 35);
        tfNome = new JTextField();
        tfSobrenome = new JTextField();
        tfEmail = new JTextField();
        tfTelefone = new JTextField();
        tfMatricula = new JTextField();
        tfIdade = new JTextField();
        cbCurso = new JComboBox<>();
        cbPeriodo = new JComboBox<>();
        tfNome.setPreferredSize(tamanhoCampo);
        tfSobrenome.setPreferredSize(tamanhoCampo);
        tfEmail.setPreferredSize(tamanhoCampo);
        tfTelefone.setPreferredSize(tamanhoCampo);
        tfMatricula.setPreferredSize(tamanhoCampo);
        tfIdade.setPreferredSize(tamanhoCampo);
        cbCurso.setPreferredSize(tamanhoCampo);
        cbPeriodo.setPreferredSize(tamanhoCampo);
        
        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        
        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel lblTitulo = new JLabel("Cadastrar Aluno");
        lblTitulo.setFont(fonteTitulo);
        panelPai.add(lblTitulo, gbc);
        
        gbc.gridwidth = 1;
        
        // Formulário
        addFormField(panelPai, gbc, "Nome:", tfNome, fonteLabel, 0, 1);
        addFormField(panelPai, gbc, "Sobrenome:", tfSobrenome, fonteLabel, 1, 1);
        addFormField(panelPai, gbc, "E-mail", tfEmail, fonteLabel, 0, 3);
        addFormField(panelPai, gbc, "Telefone", tfTelefone, fonteLabel, 1, 3);
        addFormField(panelPai, gbc, "Matrícula", tfMatricula, fonteLabel, 0, 5);
        addFormField(panelPai, gbc, "Idade", tfIdade, fonteLabel, 1, 5);
        addFormField(panelPai, gbc, "Curso", cbCurso, fonteLabel, 0, 7);
        addFormField(panelPai, gbc, "Período", cbPeriodo, fonteLabel, 1, 7);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        Dimension tamanhoBotao = new Dimension(120, 40);
        JButton btnSalvar = createStyledButton("Salvar", corBotaoSalvar, fonteBotao, tamanhoBotao);
        btnSalvar.addActionListener(e -> salvar());
        
        JButton btnCancelar = createStyledButton("Cancelar", corBotaoCancelar, fonteBotao, tamanhoBotao);
        btnCancelar.addActionListener(e -> dispose());
        
        cbCurso.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                CursoModel cursoSelecionado = (CursoModel) e.getItem();
                carregarComboBoxPeriodos(cursoSelecionado);
            }
        });
        
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnSalvar);
        
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(20, 0, 0, 0);
        panelPai.add(buttonPanel, gbc);
        
        carregarComboBoxCursos();
        add(panelPai, BorderLayout.CENTER);
        
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
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return button;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, Component component, Font font, int gridx, int gridy) {
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

    private void carregarComboBoxCursos() {
        this.alunoController.getAllCursos().forEach(cbCurso::addItem);
    }
    
    private void carregarComboBoxPeriodos(CursoModel curso) {
        cbPeriodo.removeAllItems();
        for(int p = 1; p <= curso.getN_periodos(); p++ ) {
            cbPeriodo.addItem(p);
        }
    }
    
    private void salvar() {
        String nome = tfNome.getText().trim();
        String sobrenome = tfSobrenome.getText().trim();
        String email = tfEmail.getText().trim();
        String telefone = tfTelefone.getText().trim();
        String matricula = tfMatricula.getText().trim();
        int idade = Integer.parseInt(tfIdade.getText().trim());
        CursoModel curso = (CursoModel) cbCurso.getSelectedItem();
        int periodo = (Integer) cbPeriodo.getSelectedItem();
        
        if (nome.isEmpty() || sobrenome.isEmpty() || email.isEmpty() || telefone.isEmpty() || idade <= 0 || matricula.isEmpty() || curso == null || periodo <= 0) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String cadastro = this.alunoController.cadastrar(
            nome, 
            sobrenome,
            email, 
            telefone, 
            matricula, 
            idade, 
            curso, 
            periodo
        );
        
        if (cadastro.equals("sucesso")) {
            this.salvo = true;
            dispose();
        } else if (cadastro.equals("erro")) {
            JOptionPane.showMessageDialog(this, "Falha inesperada ao cadastrar o aluno.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        } else if (cadastro.equals("repetido")) {
            JOptionPane.showMessageDialog(this, "Falha ao cadastrar o aluno.\nMatricula já cadastrada no banco de dados!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isSalvo() {
        return this.salvo;
    }
}
