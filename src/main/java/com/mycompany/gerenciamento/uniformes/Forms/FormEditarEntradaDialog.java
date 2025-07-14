/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Forms;

import com.mycompany.gerenciamento.uniformes.Controllers.ServidorController;
import com.mycompany.gerenciamento.uniformes.Controllers.UniformeController;
import com.mycompany.gerenciamento.uniformes.DAO.TamanhoDAO;
import com.mycompany.gerenciamento.uniformes.DAO.TipoUniformeDAO;
import com.mycompany.gerenciamento.uniformes.Models.DepartamentoModel;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeEstoqueModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
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
import java.util.List;
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
 * @author rober
 */
public class FormEditarEntradaDialog extends JDialog {
    private JTextField tfQuantidade;
    private JComboBox<TipoUniformeModel> cbTipoUniforme;
    private JComboBox<TamanhoModel> cbTamanho;


    private final UniformeController uniformeController ;
    private final UniformeModel uniformeParaEditar ;
    private boolean salvo = false;

    public FormEditarEntradaDialog(ViewsSistema parent, UniformeModel uniformes) {
        super(parent, "Editar Uniforme", true);
        
        this.uniformeController = new UniformeController();
        this.uniformeParaEditar = uniformes;

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
        cbTipoUniforme = new JComboBox<>();
        cbTipoUniforme.setPreferredSize(tamanhoCampo);
        cbTamanho = new JComboBox<>();
        cbTamanho.setPreferredSize(tamanhoCampo);
        tfQuantidade = new JTextField();
        tfQuantidade.setPreferredSize(tamanhoCampo);
                

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel lblTitulo = new JLabel("Editar Informações do Uniforme");
        lblTitulo.setFont(fonteTitulo);
        panelPai.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        // Formulário
        addFormField(panelPai, gbc, "Selecione o tipo:", cbTipoUniforme, fonteLabel, 0, 1);
        addFormField(panelPai, gbc, "Tamanho:", cbTamanho, fonteLabel, 1, 1);
        addFormField(panelPai, gbc, "Quantidade:", tfQuantidade, fonteLabel, 0, 3);
                
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
        carregarComboBoxUniformes();
        preencherFormulario();
        pack();
        setLocationRelativeTo(parent);
    }

    public FormEditarEntradaDialog(ViewsSistema aThis, UniformeEstoqueModel entradaParaEditar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    /**
 * Preenche os campos do formulário com os dados da entrada que está sendo editada.
 * Este método é chamado no construtor do formulário, após os componentes
 * e os ComboBoxes serem inicializados.
 */
private void preencherFormulario() {
    tfQuantidade.setText(String.valueOf(uniformeParaEditar.getQuantidade()));

    // seleciona o tipo de uniforme
    TipoUniformeModel tipoAtual = null;
    for (int i = 0; i < cbTipoUniforme.getItemCount(); i++) {
        if (cbTipoUniforme.getItemAt(i).getId() == uniformeParaEditar.getFk_tipo_uniforme()) {
            tipoAtual = cbTipoUniforme.getItemAt(i);
            break;
        }
    }
    // Se encontrou, seleciona o item
    if (tipoAtual != null) {
        cbTipoUniforme.setSelectedItem(tipoAtual);
    }
    
    
    // seleciona o tamanho
    TamanhoModel tamanhoAtual = null;
    for (int i = 0; i < cbTamanho.getItemCount(); i++) {
        if (cbTamanho.getItemAt(i).getId() == uniformeParaEditar.getFk_tamanho()) {
            tamanhoAtual = cbTamanho.getItemAt(i);
            break; 
        }
    }
    // Se encontrou, seleciona o item
    if (tamanhoAtual != null) {
        cbTamanho.setSelectedItem(tamanhoAtual);
    }
}
    private void salvar() {
    try {
        int quantidadeNova = Integer.parseInt(tfQuantidade.getText().trim());
        TipoUniformeModel tipoSelecionado = (TipoUniformeModel) cbTipoUniforme.getSelectedItem();
        TamanhoModel tamanhoSelecionado = (TamanhoModel) cbTamanho.getSelectedItem();

        boolean houveMudanca =
                quantidadeNova != uniformeParaEditar.getQuantidade() ||
                (tipoSelecionado != null && tipoSelecionado.getId() != uniformeParaEditar.getTipoUniforme().getId()) ||
                (tamanhoSelecionado != null && tamanhoSelecionado.getId() != uniformeParaEditar.getTamanho().getId());

        if (!houveMudanca) {
            dispose();
            return;
        }
        
        
        if (tipoSelecionado == null) {
            uniformeParaEditar.setFk_tipo_uniforme(tipoSelecionado.getId());
        }
        
        if (tamanhoSelecionado == null) {
           uniformeParaEditar.setFk_tamanho(tamanhoSelecionado.getId());
        }
        
        try {
            uniformeController.atualizarUniforme(uniformeParaEditar);
            this.salvo = true;  
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao salvar no banco de dados","",
                JOptionPane.ERROR_MESSAGE
            );
        }


    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "A quantidade deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    private void carregarComboBoxUniformes() {

        cbTipoUniforme.removeAllItems();
        cbTamanho.removeAllItems();

        TipoUniformeDAO tipoDAO = new TipoUniformeDAO();
        TamanhoDAO tamanhoDAO = new TamanhoDAO();
        
        tipoDAO.listarTodos().forEach(cbTipoUniforme::addItem);
        tamanhoDAO.listarTodos().forEach(cbTamanho::addItem);
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
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return button;
    }
}
    
