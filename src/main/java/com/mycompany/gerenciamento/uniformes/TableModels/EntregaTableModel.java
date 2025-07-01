/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.TableModels;

import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author geinfo
 */
public class EntregaTableModel extends AbstractTableModel{
    private List<EntregaModel> entregas;
    private String [] colunas = {"ID", "Nome", "Tamanho", "Uniforme", "Matrícula", "Servidor Responsável", "Quantidade", "Data de Troca"};
    
    public EntregaTableModel() {
        this.entregas = new ArrayList<>();
    }
    
    public EntregaTableModel(List<EntregaModel> entregas) {
        this.entregas = entregas;
    }
    
    @Override
    public int getRowCount() {
        return entregas.size();
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
        EntregaModel entrega = entregas.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return entrega.getId();
            case 1: return entrega.getFk_aluno();
            case 2: return entrega.getFk_uniforme();
            case 3: return entrega.getFk_uniforme();
            case 4: return entrega.getFk_aluno();
            case 5: return entrega.getFk_servidor();
            case 6: return entrega.getQuantidade();
            case 7: return entrega.getData_entrega(); //Trocar para data troca
            default: return null;
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }
    
    public void setEntregas(List<EntregaModel> novasEntregas) {
        this.entregas = novasEntregas;
        fireTableDataChanged();
    }
    
}
