/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.TableModels;

import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author geinfo
 */
public class EntregaTableModel extends AbstractTableModel{
    private List<EntregaModel> entregas;
    private String [] colunas = {"Nome", "Tamanho", "Uniforme", "Matrícula", "Servidor Responsável", "Quantidade", "Data de Entrega", ""};
    
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
            case 0: 
                if (entrega.getAluno().getNome() != null) {
                    return entrega.getAluno().getNome() + " " + entrega.getAluno().getSobrenome();
                } else {
                    return "<Aluno inexistente>";
                }
            case 1: return entrega.getUniforme().getTamanho().getNome();
            case 2: return entrega.getUniforme().getTipoUniforme().getNome();
            case 3: return entrega.getAluno().getMatricula();
            case 4: return entrega.getServidor().getNome() + " " + entrega.getServidor().getSobrenome();
            case 5: return entrega.getQuantidade();
            case 6:
            if (entrega.getData_entrega() != null) {
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return entrega.getData_entrega().format(formatador);
            } else {
                return "N/A"; 
            } 
            case 7: return  "Trocar";
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
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 7;
    }
    
    public EntregaModel getEntregaAt(int row) {
        return entregas.get(row);
    }
    
}
