/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Forms;




// Imports necessários para a lógica
import com.mycompany.gerenciamento.uniformes.DAO.*;
import com.mycompany.gerenciamento.uniformes.Models.*;
import com.mycompany.gerenciamento.uniformes.Controllers.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;


/**
 * @author rober
 */

public class FormUniforme extends JDialog {
    // Campos do formulário
    private final JTextField tfQuantidade;
    private final JTextField tfDataEntrada;
    private final JComboBox<TipoUniformeModel> cbTipoUniforme;
    private final JComboBox<TamanhoModel> cbTamanho;
    private final JComboBox<FornecedorModel> cbFornecedor;

    private final EntradaDAO entradaDAO;
    private final UniformeDAO uniformeDAO;
    private final TipoUniformeDAO tipoUniformeDAO;
    private final TamanhoDAO tamanhoDAO;
    private final FornecedorDAO fornecedorDAO;
    
    private boolean salvo = false;

    public FormUniforme(Frame parent) {
        super(parent, "Cadastrar Uniforme", true);
        
        this.entradaDAO = new EntradaDAO();
        this.uniformeDAO = new UniformeDAO();
        this.tipoUniformeDAO = new TipoUniformeDAO();
        this.tamanhoDAO = new TamanhoDAO();
        this.fornecedorDAO = new FornecedorDAO();
        
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
        tfQuantidade = new JTextField();
        tfQuantidade.setPreferredSize(tamanhoCampo);
        tfDataEntrada = new JTextField();
        tfDataEntrada.setPreferredSize(tamanhoCampo);
        cbTipoUniforme = new JComboBox<>();
        cbTipoUniforme.setPreferredSize(tamanhoCampo);
        cbTamanho = new JComboBox<>();
        cbTamanho.setPreferredSize(tamanhoCampo);
        cbFornecedor = new JComboBox<>();     
        cbFornecedor.setPreferredSize(tamanhoCampo);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel lblTitulo = new JLabel("Cadastrar Uniformes");
        lblTitulo.setFont(fonteTitulo);
        panelPai.add(lblTitulo, gbc);
        
        gbc.gridwidth = 1;

        // Formulário
        addFormField(panelPai, gbc, "Selecione o tipo:", cbTipoUniforme,fonteLabel, 0, 1);
        addFormField(panelPai, gbc, "Tamanho:", cbTamanho,fonteLabel, 1, 1);
        addFormField(panelPai, gbc, "Quantidade:", tfQuantidade,fonteLabel, 1, 3);
        addFormField(panelPai, gbc, "Fornecedor:", cbFornecedor,fonteLabel, 0, 3);
        addFormField(panelPai, gbc, "Data de entrada (AAAA-MM-DD):", tfDataEntrada,fonteLabel, 0, 5);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        Dimension tamanhoBotao = new Dimension(120, 40);
        JButton btnSalvar = createStyledButton("Salvar", corBotaoSalvar, fonteBotao, tamanhoBotao);
        //btnSalvar.addActionListener(e -> salvar());

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

        carregarComboBoxUniformes();
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

    // Método para carregar os ComboBoxes
    private void carregarComboBoxUniformes() {
        tipoUniformeDAO.listarTodos().forEach(cbTipoUniforme::addItem);
        tamanhoDAO.listarTodos().forEach(cbTamanho::addItem);
        fornecedorDAO.listarTodos().forEach(cbFornecedor::addItem);
    }
    
}

