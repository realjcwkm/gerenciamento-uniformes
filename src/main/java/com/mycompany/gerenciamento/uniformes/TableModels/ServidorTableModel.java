/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.TableModels;

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
    private String[] colunas = {"ID","Nome", "Matrícula", "Departamento","Status"};

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
            case 0: return servidor.getId();
            case 1: return servidor.getNome() + " " + servidor.getSobrenome();
            case 2: return servidor.getMatricula();
            case 3: return servidor.getNomeDepartamento();
            case 4: return servidor.isAtivo() ? "Ativo" : "Inativo";
            default: return null;
        }
    }
}
