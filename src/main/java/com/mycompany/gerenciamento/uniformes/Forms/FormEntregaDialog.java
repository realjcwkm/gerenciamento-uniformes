/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Forms;

import com.mycompany.gerenciamento.uniformes.Controllers.EntregaController;
import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
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
public class FormEntregaDialog extends JDialog {
    
    private JTextField txtMatricula;
    private JTextField lblNomeAluno;
    private JTextField lblCursoAluno;
    private JComboBox<TipoUniformeModel> comboUniformes;
    private JComboBox<TamanhoModel> comboTamanhos;
    private JTextField txtQuantidade;
    private JLabel lblAvisoMatricula;
    
    private AlunoModel alunoSelecionado;
    private final EntregaController entregaController;
    private boolean salvo = false;
    
    public FormEntregaDialog(Frame parent) {
        super(parent, "Cadastrar Nova Distribuição", true);

        this.entregaController = new EntregaController();
        
        setResizable(false);
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        // Fontes e Cores
        Font fonteTitulo = new Font("Segoe UI", Font.BOLD, 22);
        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 14);
        Font fonteAviso = new Font("Segoe UI", Font.ITALIC, 11);
        Font fonteBotao = new Font("Segoe UI", Font.BOLD, 14);
        Color corBotaoSalvar = new Color(0, 164, 55);
        Color corBotaoCancelar = new Color(238, 63, 63);
        
        Dimension tamanhoCampo = new Dimension(250, 35);
        txtMatricula = new JTextField();
        txtMatricula.setPreferredSize(tamanhoCampo);
        lblNomeAluno = new JTextField("");
        lblNomeAluno.setEditable(false);
        lblNomeAluno.setPreferredSize(tamanhoCampo);
        lblCursoAluno = new JTextField("");
        lblCursoAluno.setEditable(false);
        lblCursoAluno.setPreferredSize(tamanhoCampo);
        comboUniformes = new JComboBox<>();
        comboUniformes.setPreferredSize(tamanhoCampo);
        comboTamanhos = new JComboBox<>();
        comboTamanhos.setPreferredSize(tamanhoCampo);
        txtQuantidade = new JTextField("1");
        txtQuantidade.setPreferredSize(tamanhoCampo);
        
        lblAvisoMatricula = new JLabel("Digite a matrícula e pressione Enter.");
        lblAvisoMatricula.setFont(fonteAviso);
        lblAvisoMatricula.setForeground(Color.GRAY);
        
        //Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        
        //Titulo
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel lblTitulo = new JLabel("Cadastrar Entrega");
        lblTitulo.setFont(fonteTitulo);
        panel.add(lblTitulo, gbc);
        
        gbc.gridwidth = 1;
        
        //Formulario
        addFormField(panel, gbc, "Nome:", lblNomeAluno, fonteLabel, 1, 1);
        addFormField(panel, gbc, "Curso:", lblCursoAluno, fonteLabel, 0, 4);
        addFormField(panel, gbc, "Uniforme:", comboUniformes, fonteLabel, 1, 4);
        addFormField(panel, gbc, "Tamanho:", comboTamanhos, fonteLabel, 0, 6);
        addFormField(panel, gbc, "Quantidade:", txtQuantidade, fonteLabel, 1, 6);
        
        // Matrícula
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 1, 0);
        JLabel lblMatricula = new JLabel("Matrícula Aluno:");
        lblMatricula.setFont(fonteLabel);
        panel.add(lblMatricula, gbc);
        
        // Campo de Texto da Matrícula
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 20);
        panel.add(txtMatricula, gbc);

        // Label de Aviso
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 2);
        panel.add(lblAvisoMatricula, gbc);
        
        txtMatricula.addActionListener(e -> buscarAluno());
        
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
        this.entregaController.getAllTamanhos().forEach(comboTamanhos::addItem);
        this.entregaController.getAllTipos().forEach(comboUniformes::addItem);
    }
    
    private void buscarAluno() {
        String matricula = txtMatricula.getText().trim();
        if (matricula.isEmpty()) return;
        
        this.alunoSelecionado = entregaController.getAlunoByMatricula(matricula);
        
        if(this.alunoSelecionado != null) {
           lblNomeAluno.setText(this.alunoSelecionado.getNome());
            
           if (this.alunoSelecionado.getCurso() != null && this.alunoSelecionado.getCurso().getNome() != null) {
                lblCursoAluno.setText(this.alunoSelecionado.getCurso().getNome());
            } else {
                lblCursoAluno.setText("Sem curso definido");
            }
        } else {
            lblNomeAluno.setText("Aluno não encontrado!");
            lblCursoAluno.setText(" ");
            JOptionPane.showMessageDialog(this, "Nenhum aluno encontrado com a matrícula informada.", "Erro", JOptionPane.ERROR_MESSAGE);
       }
        
    }
    
    private void salvar() {
        if(alunoSelecionado == null) {
           JOptionPane.showMessageDialog(this, "Por favor, informe uma matrícula de aluno válida.", "Erro", JOptionPane.ERROR_MESSAGE);
           return;
        } 
       
        TipoUniformeModel tipo = (TipoUniformeModel) comboUniformes.getSelectedItem();
        TamanhoModel tamanho = (TamanhoModel) comboTamanhos.getSelectedItem();
        
        int quantidade;
        
        try {
           quantidade = Integer.parseInt(txtQuantidade.getText());
        } catch (NumberFormatException e) {
           JOptionPane.showMessageDialog(this, "Quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
           return;
        }
        
        boolean sucesso = entregaController.cadastrarNovaEntrega(
            this.alunoSelecionado,
            tipo,
            tamanho,
            quantidade
        );
        
        if (sucesso) {
            this.salvo = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao cadastrar a distribuição.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isSalvo() {
        return this.salvo;
    }
}
