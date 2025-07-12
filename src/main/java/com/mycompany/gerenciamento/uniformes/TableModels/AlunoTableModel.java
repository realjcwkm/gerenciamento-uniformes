/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.TableModels;

import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author geinfo
 */
public class AlunoTableModel extends AbstractTableModel {
    private List<AlunoModel> alunos;
    private String[] colunas = {"Nome", "Turma", "Matricula", "Periodo", ""};
    
    public AlunoTableModel(List<AlunoModel> alunos) {
        this.alunos = alunos;
    }
    
    @Override
    public int getRowCount() {
        return alunos.size();
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
        AlunoModel aluno = alunos.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return aluno.getNome() + " " + aluno.getSobrenome();
            case 1: return aluno.getCurso().getNome();
            case 2: return aluno.getMatricula();
            case 3: return aluno.getPeriodo();
            case 4: return "Editar";
            default: return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
    }
    
    public AlunoModel getAlunoAt(int row) {
        return alunos.get(row);
    }
    
    public void setAlunos(List<AlunoModel> alunos) {
        this.alunos = alunos;
        fireTableDataChanged();
    }
}
