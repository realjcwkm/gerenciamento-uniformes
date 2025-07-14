/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Models;

/**
 *
 * @author geinfo
 */
public class AlunoModel {
    private int id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String matricula;
    private int idade;
    private int periodo;
    private int fk_curso;
    
    private CursoModel curso;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade;}

    public int getPeriodo() { return periodo; }
    public void setPeriodo(int periodo) { this.periodo = periodo; }

    public int getFk_curso() { return fk_curso; }
    public void setFk_curso(int fk_curso) { this.fk_curso = fk_curso; }

    public CursoModel getCurso() { return curso; }
    public void setCurso(CursoModel curso) { this.curso = curso; }
    
}
