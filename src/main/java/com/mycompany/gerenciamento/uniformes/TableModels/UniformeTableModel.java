/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.TableModels;

import com.mycompany.gerenciamento.uniformes.Models.UniformeEstoqueModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rober
 */
public class UniformeTableModel extends AbstractTableModel {
    private List<UniformeEstoqueModel> listaUniformes;
    private String[] colunas = {"Tipo", "Status", "Entrada", "Saída", "Tamanho", "Data Entrada", ""}; 
    private final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public UniformeTableModel() {
        this.listaUniformes = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return listaUniformes.size();
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
        UniformeEstoqueModel uniforme = listaUniformes.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return uniforme.getTipo();
            case 1: return uniforme.getStatus();
            case 2: return uniforme.getTotalEntrada();
            case 3: return uniforme.getTotalSaida();
            case 4: return uniforme.getTamanho();
            case 5:
                LocalDate data = uniforme.getDataUltimaEntrada(); 
                if (data != null) {
                    return data.format(formatadorData);
                }
                return "";
            case 6: return "Editar";
            default: return null;
        }
    }
    
    public void setUniformes(List<UniformeEstoqueModel> novosUniformes) {
        this.listaUniformes = novosUniformes;
        this.fireTableDataChanged(); 
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 6;
    }
    
    public UniformeEstoqueModel getUniformeAt(int row) {
        return this.listaUniformes.get(row);
    }
}