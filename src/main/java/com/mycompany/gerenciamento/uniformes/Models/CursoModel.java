/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Models;

/**
 *
 * @author geinfo
 */
public class CursoModel {
    private int id;
    private String nome;
    private int n_periodos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getN_periodos() {
        return n_periodos;
    }

    public void setN_periodos(int n_periodos) {
        this.n_periodos = n_periodos;
    }
    
    @Override
    public String toString() {
        return this.nome;
    }
    
}
