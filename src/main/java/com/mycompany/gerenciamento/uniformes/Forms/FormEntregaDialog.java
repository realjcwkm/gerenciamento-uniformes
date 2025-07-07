/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Forms;

import com.mycompany.gerenciamento.uniformes.Controllers.AlunoController;
import com.mycompany.gerenciamento.uniformes.Controllers.EntregaController;
import com.mycompany.gerenciamento.uniformes.Controllers.UniformeController;
import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import com.mycompany.gerenciamento.uniformes.Session.AuthSession;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
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
    private JComboBox<TipoUniformeModel> comboUniformes;
    private JComboBox<TamanhoModel> comboTamanhos;
    private JTextField txtQuantidade;
    
    private AlunoModel alunoSelecionado;
    private EntregaModel entregaCriada;
    private final UniformeController uniformeController;
    private final EntregaController entregaController;
    private final AlunoController alunoController;
    
    public FormEntregaDialog(Frame parent) {
        super(parent, "Cadastrar Nova Distribuição", true);
        
        this.uniformeController = new UniformeController();
        this.entregaController = new EntregaController();
        this.alunoController = new AlunoController();
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        txtMatricula = new JTextField(15);
        lblNomeAluno = new JLabel("<- Digite a matrícula e pressione Enter");
        lblNomeAluno.setFont(new Font("Segoe UI", Font.ITALIC, 12)); 
        comboUniformes = new JComboBox<>();
        comboTamanhos = new JComboBox<>();
        txtQuantidade = new JTextField("1");
        
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        
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
        
        this.alunoSelecionado = this.alunoController.getByMatricula(matricula);
        
        if(this.alunoSelecionado != null) {
            lblNomeAluno.setText(this.alunoSelecionado.getNome());
            lblNomeAluno.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        } else {
            lblNomeAluno.setText("Aluno não encontrado!");
            lblNomeAluno.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            JOptionPane.showMessageDialog(this, "Nenhum aluno encontrado com a matrícula informada.", "Erro", JOptionPane.ERROR_MESSAGE);
       }
        
    }
    
    private void salvar() {
       if(alunoSelecionado == null) {
           JOptionPane.showMessageDialog(this, "Por favor, informe uma matrícula de aluno válida.", "Erro", JOptionPane.ERROR_MESSAGE);
           return;
       } 
       
       try {
           LocalDate hoje = LocalDate.now();
           int anoAtual = hoje.getYear();
           int mesAtual = hoje.getMonthValue();
           
           int semestreAtual = (mesAtual <= 6) ? 1 : 2;
           
           
           TipoUniformeModel uniforme = (TipoUniformeModel) comboUniformes.getSelectedItem();
           TamanhoModel tamanho = (TamanhoModel) comboTamanhos.getSelectedItem();
           int quantidade = Integer.parseInt(txtQuantidade.getText());
           
           entregaCriada = new EntregaModel();
           
           entregaCriada.setData_entrega(hoje);
           entregaCriada.setAno(anoAtual);
           entregaCriada.setSemestre(semestreAtual);
           
           entregaCriada.setAluno(alunoSelecionado);
           entregaCriada.setQuantidade(quantidade);
           entregaCriada.setTrocado(false);
           
           UniformeModel uniformeCompleto = this.uniformeController.buscarPorTipoETamanho(uniforme.getId(), tamanho.getId());
           entregaCriada.setUniforme(uniformeCompleto);
           
           AuthSession sessao = AuthSession.getInstance();
           
           ServidorModel servidorLogado = new ServidorModel();
           servidorLogado.setId(sessao.getId());
           servidorLogado.setNome(sessao.getNome());
           servidorLogado.setMatricula(sessao.getMatricula());
           
           entregaCriada.setServidor(servidorLogado);
                   
           dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    public EntregaModel getEntregaCriada() {
        return entregaCriada;
    }
}
