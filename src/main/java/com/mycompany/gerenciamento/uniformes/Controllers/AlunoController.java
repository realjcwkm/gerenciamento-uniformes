/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.AlunoDAO;
import com.mycompany.gerenciamento.uniformes.DAO.CursoDAO;
import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import com.mycompany.gerenciamento.uniformes.Models.CursoModel;
import com.mycompany.gerenciamento.uniformes.Models.FiltroModel;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class AlunoController {
    private final AlunoDAO alunoDAO;
    private final CursoDAO cursoDAO;
    
    public AlunoController() {
        this.alunoDAO = new AlunoDAO();
        this.cursoDAO = new CursoDAO();
    }
    
    public List<AlunoModel> listarTodos(int pagina, int itensPorPagina, String busca, FiltroModel filtro) {
        return this.alunoDAO.listarTodos(pagina, itensPorPagina, busca, filtro);
    }
    
    public int getTotalDePaginas(int itensPorPagina, String busca, FiltroModel filtro) {
        int totalDeItens = this.alunoDAO.getTotal(busca, filtro);
        int totalPaginas = (int) Math.ceil((double) totalDeItens / itensPorPagina);
        return Math.max(totalPaginas, 1);
    }
    
    public AlunoModel getByMatricula(String matricula) {
        return this.alunoDAO.getByMatricula(matricula);
    }
    
    public List<CursoModel> getAllCursos() {
        return this.cursoDAO.listarTodos();
    }
    
    public String cadastrar(String nome, String sobrenome, String email, String telefone, String matricula, int idade, CursoModel curso, int periodo) {
        try {
            AlunoModel aluno = new AlunoModel();
            
            aluno.setNome(nome);
            aluno.setSobrenome(sobrenome);
            aluno.setEmail(email);
            aluno.setTelefone(telefone);
            aluno.setMatricula(matricula);
            aluno.setIdade(idade);
            aluno.setCurso(curso);
            aluno.setPeriodo(periodo);
            
            AlunoModel repetido = this.alunoDAO.getByMatricula(matricula);
            
            if (repetido != null) {
                return "repetido";
            }
            
            if (this.alunoDAO.cadastrar(aluno)) {
                return "sucesso";
            }
            return "erro";
        } catch (Exception e) {
            e.printStackTrace();
            return "erro";
        }
    }
    
    public void atualizar(AlunoModel aluno) {
        alunoDAO.editar(aluno);
    }
    
    public boolean excluir(int id) {
        return this.alunoDAO.excluir(id);
    }
}
