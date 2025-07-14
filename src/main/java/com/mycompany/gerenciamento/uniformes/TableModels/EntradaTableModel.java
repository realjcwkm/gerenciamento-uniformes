/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.TableModels;

import com.mycompany.gerenciamento.uniformes.Models.EntradaModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rober
 */
public class EntradaTableModel extends AbstractTableModel {
    private List<Object[]> entradas;
    private String[] colunas = {"Tipo", "Tamanho", "Status", "Entrada", "Saída", "Estoque Atual"}; 
    private final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EntradaTableModel() {
        this.entradas = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return entradas.size();
    }
    
    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return entradas.get(rowIndex)[columnIndex];
    }
    
    public void setEntradas(List<Object[]> novasEntradas) {
        this.entradas = novasEntradas;
        this.fireTableDataChanged(); 
    }
    
//    @Override;;
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        return columnIndex == 6;
//    }
    
}