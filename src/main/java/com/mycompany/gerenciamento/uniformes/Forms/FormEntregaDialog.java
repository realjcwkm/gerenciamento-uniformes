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
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
    private JLabel lblNomeAluno;
    private JLabel lblCursoAluno;
    private JComboBox<TipoUniformeModel> comboUniformes;
    private JComboBox<TamanhoModel> comboTamanhos;
    private JTextField txtQuantidade;
    
    private AlunoModel alunoSelecionado;
    private final EntregaController entregaController;
    private boolean salvo = false;
    
    public FormEntregaDialog(Frame parent) {
        super(parent, "Cadastrar Nova Distribuição", true);

        this.entregaController = new EntregaController();
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        txtMatricula = new JTextField(15);
        lblNomeAluno = new JLabel("<- Digite a matrícula e pressione Enter");
        lblCursoAluno= new JLabel("");
        comboUniformes = new JComboBox<>();
        comboTamanhos = new JComboBox<>();
        txtQuantidade = new JTextField("1");
        
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Matrícula Aluno:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(txtMatricula, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Nome do Aluno:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(lblNomeAluno, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Curso do Aluno:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(lblCursoAluno, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Uniforme:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(comboUniformes, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Tamanho:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; panel.add(comboTamanhos, gbc);
        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; panel.add(txtQuantidade, gbc);
        
        carregarComboBoxes();
        
        txtMatricula.addActionListener(e -> buscarAluno());
        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
        
        add(panel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(parent);
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
