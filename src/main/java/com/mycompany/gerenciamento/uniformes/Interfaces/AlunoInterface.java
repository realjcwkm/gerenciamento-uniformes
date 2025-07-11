/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Interfaces;

import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import java.util.List;

/**
 *
 * @author geinfo
 */
public interface AlunoInterface {
    public List<AlunoModel> listarTodos(int pagina, int intesPorPagina, String busca);
    public int getTotal(String busca);
    public AlunoModel getByMatricula(String matricula);
    public boolean cadastrar(AlunoModel aluno);
    public void editar(AlunoModel aluno);
}
