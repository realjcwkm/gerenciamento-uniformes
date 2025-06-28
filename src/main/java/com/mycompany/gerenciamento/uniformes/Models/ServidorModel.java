package com.mycompany.gerenciamento.uniformes.Models;

public class ServidorModel {
    private int id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String matricula;
    private String senha;
    private boolean ativo;
    private int fk_departamento;

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
    
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    
    public int getFk_departamento() { return fk_departamento; }
    public void setFk_departamento(int fk_departamento) { 
        this.fk_departamento = fk_departamento; 
    }
}