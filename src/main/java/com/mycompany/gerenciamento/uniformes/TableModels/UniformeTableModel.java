/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.TableModels;

import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeEstoqueModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author rober
 */
public class UniformeTableModel  extends AbstractTableModel{
    private List<UniformeEstoqueModel> listaUniformes;
    private String[] colunas = {"Tipo","Status","Entrada","Saída","Tamanho","Data Entrada",""};
    private final DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
     public UniformeTableModel(List<UniformeModel> uniformes) {
        this.listaUniformes = listaUniformes;
    }
    
    public int getRowCount() {
        return listaUniformes.size();
    }

    public int getColumnCount() {
        return colunas.length;
    }

    public String getColumnName(int column) {
        return colunas[column];
    }
    
    public Object getValueAt (int rowIndex, int columnIndex){
        UniformeEstoqueModel uniforme = listaUniformes.get(rowIndex);
        
        switch (columnIndex){
            case 0: return uniforme.getTipo();
            case 1: return uniforme.getStatus();
            case 2: return uniforme.getTotalEntrada();
            case 3: return uniforme.getTotalSaida();
            case 4: return uniforme.getTamanho();
            case 5: return uniforme.getDataUltimaEntrada();
            case 6: return "Editar";
            default: return null;
        }
    }
        public void setUniformes (List<UniformeEstoqueModel> novosUniformes) {
        this.listaUniformes = novosUniformes;
        this.fireTableDataChanged();
    }
        public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 6;
        }
        public UniformeEstoqueModel getUniformeAt(int row) {
            return listaUniformes.get(row);
    }
}
