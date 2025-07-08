/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Forms;

import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author geinfo
 */
public class ConfirmacaoTroca extends JDialog {
    private boolean confirmado = false;

    public ConfirmacaoTroca(Frame parent, EntregaModel entregaAntiga, UniformeModel uniformeNovo) {
        super(parent, "Confirma Troca de Uniforme?", true);

        JPanel panel = new JPanel(new BorderLayout(40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(70, 70, 70, 70));
        
        String infoAluno = entregaAntiga.getAluno().getNome() + " (" + entregaAntiga.getAluno().getMatricula() + ")";
        JLabel lblAluno = new JLabel(infoAluno, SwingConstants.CENTER);
        lblAluno.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(lblAluno, BorderLayout.NORTH);
        
        JPanel trocaPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        
        String descUniformeAntigo = "<html><div style='text-align: center;'>DE:<br>" 
                                  + entregaAntiga.getUniforme().getTipoUniforme().getNome() + "<br>" 
                                  + entregaAntiga.getUniforme().getTamanho().getNome() + "</div></html>";
        trocaPanel.add(new JLabel(descUniformeAntigo, SwingConstants.CENTER));

        JLabel seta = new JLabel("→", SwingConstants.CENTER);
        seta.setFont(new Font("Segoe UI", Font.BOLD, 36));
        trocaPanel.add(seta);

        String descUniformeNovo = "<html><div style='text-align: center;'>PARA:<br>"  
                                + uniformeNovo.getTipoUniforme().getNome() + "<br>"
                                + uniformeNovo.getTamanho().getNome() + "</div></html>";
        trocaPanel.add(new JLabel(descUniformeNovo, SwingConstants.CENTER));
        
        panel.add(trocaPanel, BorderLayout.CENTER);

        JButton btnConfirmar = new JButton("Confirmar Troca");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnConfirmar.addActionListener(e -> {
            this.confirmado = true;
            dispose();
        });
        btnCancelar.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnConfirmar);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmado() {
        return confirmado;
    }
}
