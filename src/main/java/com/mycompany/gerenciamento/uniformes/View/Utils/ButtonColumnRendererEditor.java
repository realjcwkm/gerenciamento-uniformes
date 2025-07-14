/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.View.Utils;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author geinfo
 */
public class ButtonColumnRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
  
    private final JButton button;
    private final JTable table;
    private boolean isPushed;

    public ButtonColumnRendererEditor(JTable table, Icon icon) {
        this.table = table;
        
        this.button = new JButton(icon);
        this.button.setBorderPainted(false);
        this.button.setContentAreaFilled(false);
        this.button.setFocusPainted(false);
        this.button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.button.addActionListener(this);
        
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setBackground(UIManager.getColor("Button.background"));
        }
        return button;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        isPushed = false;
        return ""; 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
    }
    
    public JButton getButton() {
        return button;
    }
}
