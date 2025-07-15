/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Interfaces;

import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import com.mycompany.gerenciamento.uniformes.Models.FiltroModel;
import java.util.List;

/**
 *
 * @author geinfo
 */
public interface AlunoInterface {
    public List<AlunoModel> listarTodos(int pagina, int intesPorPagina, String busca, FiltroModel filtro);
    public int getTotal(String busca, FiltroModel filtro);
    public AlunoModel getByMatricula(String matricula);
    public boolean cadastrar(AlunoModel aluno);
    public boolean editar(AlunoModel aluno);
    public boolean excluir(int id);
}
