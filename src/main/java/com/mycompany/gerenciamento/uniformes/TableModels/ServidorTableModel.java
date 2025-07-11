/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.TableModels;

import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author 70094534209
 */
public class ServidorTableModel extends AbstractTableModel {
    private List<ServidorModel> servidores;
    private String[] colunas = {"Nome", "Matrícula", "Departamento","Status","Ações"};

    public ServidorTableModel(List<ServidorModel> servidores) {
        this.servidores = servidores;
    }

    @Override
    public int getRowCount() {
        return servidores.size();
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
        ServidorModel servidor = servidores.get(rowIndex);

        switch (columnIndex) {
            case 0: return servidor.getNome() + " " + servidor.getSobrenome();
            case 1: return servidor.getMatricula();
            case 2: return servidor.getNomeDepartamento();
            case 3: return servidor.isAtivo() ? "Ativo" : "Inativo";
            case 4: return "Editar";
            default: return null;
        }
    }

    public void setServidores(List<ServidorModel> novosServidores) {
        this.servidores = novosServidores;
        fireTableDataChanged();
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
    }
    
    public ServidorModel getServidorAt(int row) {
        return servidores.get(row);
    }
}
