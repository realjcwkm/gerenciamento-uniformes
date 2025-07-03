/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Session;

import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;

/**
 *
 * @author w
 */
public class AuthSession {
    
    private static AuthSession instancia;
    
    private int id;
    private String nome;
    private String matricula;
    
    private AuthSession() {}
    
    public static AuthSession getInstance() {
        if (instancia == null) {
            instancia = new AuthSession();
        }
        
        return instancia;
    }
    
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    
    public void iniciarSessao(ServidorModel servidor) {
        this.id = servidor.getId();
        this.nome = servidor.getNome();
        this.matricula = servidor.getMatricula();
    }
    
    public void encerrarSessao() {
        this.id = 0;
        this.nome = null;
        this.matricula = null;
    }
}
