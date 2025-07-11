/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Forms;

import com.mycompany.gerenciamento.uniformes.Controllers.AlunoController;
import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import com.mycompany.gerenciamento.uniformes.Models.CursoModel;
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
public class FormEditarAlunoDialog extends JDialog{
    private final JTextField tfNome, tfSobrenome, tfEmail, tfTelefone, tfMatricula, tfIdade;
    private final JComboBox<CursoModel> cbCurso;
    private final JComboBox<Integer> cbPeriodo;
    
    private final AlunoController alunoController;
    private final AlunoModel alunoParaEditar;
    private boolean salvo = false;

    public FormEditarAlunoDialog(ViewsSistema parent, AlunoModel aluno) {
        super(parent, "Editar Aluno", true);
        this.alunoController = new AlunoController();
        
        this.alunoParaEditar = aluno;

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
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel lblTitulo = new JLabel("Editar Informações do Aluno");
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

        // Finalização
        add(panelPai, BorderLayout.CENTER);
        carregarComboBoxCursos();
        preencherFormulario();
        pack();
        setLocationRelativeTo(parent);
    }
    
    private void preencherFormulario() {
        tfNome.setText(alunoParaEditar.getNome());
        tfSobrenome.setText(alunoParaEditar.getSobrenome());
        tfEmail.setText(alunoParaEditar.getEmail());
        tfTelefone.setText(alunoParaEditar.getTelefone());
        tfMatricula.setText(alunoParaEditar.getMatricula());
        tfIdade.setText(String.valueOf(alunoParaEditar.getIdade()));
        
        int periodo = alunoParaEditar.getPeriodo();
        
        System.out.println(periodo);
        
        cbPeriodo.setSelectedItem(periodo);
        
        CursoModel cursoAtual = null;
        for (int i = 0; i < cbCurso.getItemCount(); i++) {
            if (cbCurso.getItemAt(i).getId() == alunoParaEditar.getFk_curso()) {
                cursoAtual = cbCurso.getItemAt(i);
                break;
            }
        }
        if (cursoAtual != null) {
            cbCurso.setSelectedItem(cursoAtual);
        }
        
        
    }
    
    private void salvar() {
        String nome = tfNome.getText().trim();
        String sobrenome = tfSobrenome.getText().trim();
        String email = tfEmail.getText().trim();
        String telefone = tfTelefone.getText().trim();
        String matricula = tfMatricula.getText().trim();       
        int idade = Integer.parseInt(tfIdade.getText());
        int periodo = (Integer) cbPeriodo.getSelectedItem();
        CursoModel cursoSelecionado = (CursoModel) cbCurso.getSelectedItem();

        boolean houveMudanca = 
                !nome.equals(alunoParaEditar.getNome()) ||
                !sobrenome.equals(alunoParaEditar.getSobrenome()) ||
                !email.equals(alunoParaEditar.getEmail()) ||
                !telefone.equals(alunoParaEditar.getTelefone()) ||
                !matricula.equals(alunoParaEditar.getMatricula()) ||
                idade != (alunoParaEditar.getIdade()) ||
                periodo != (alunoParaEditar.getPeriodo()) ||
                (cursoSelecionado != null && cursoSelecionado.getId() != alunoParaEditar.getFk_curso());

        if (!houveMudanca) {
            dispose();
            return;
        }
        

        alunoParaEditar.setNome(nome);
        alunoParaEditar.setSobrenome(sobrenome);
        alunoParaEditar.setEmail(email);
        alunoParaEditar.setTelefone(telefone);
        alunoParaEditar.setMatricula(matricula);
        alunoParaEditar.setIdade(idade);
        alunoParaEditar.setPeriodo(periodo);
        if (cursoSelecionado != null) {
            alunoParaEditar.setFk_curso(cursoSelecionado.getId());
        }

        try {
            alunoController.atualizar(alunoParaEditar);
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
    
    private void carregarComboBoxCursos() {
        this.alunoController.getAllCursos().forEach(cbCurso::addItem);
    }
    
    private void carregarComboBoxPeriodos(CursoModel curso) {
        cbPeriodo.removeAllItems();
        for(int p = 1; p <= curso.getN_periodos(); p++ ) {
            cbPeriodo.addItem(p);
        }
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
