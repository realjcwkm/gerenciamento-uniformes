/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.AlunoDAO;
import com.mycompany.gerenciamento.uniformes.DAO.CursoDAO;
import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import com.mycompany.gerenciamento.uniformes.Models.CursoModel;
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
    
    public List<AlunoModel> listarTodos() {
        return this.alunoDAO.listarTodos();
    }
    
    public AlunoModel getByMatricula(String matricula) {
        return this.alunoDAO.getByMatricula(matricula);
    }
    
    public List<CursoModel> getAllCursos() {
        return this.cursoDAO.listarTodos();
    }
    
    public boolean cadastrar(String nome, String sobrenome, String email, String telefone, String matricula, int idade, CursoModel curso, int periodo) {
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
            
            return this.alunoDAO.cadastrar(aluno);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
