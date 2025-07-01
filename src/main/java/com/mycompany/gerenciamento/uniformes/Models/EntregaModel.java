/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Models;

import java.time.LocalDate;

/**
 *
 * @author geinfo
 */
public class EntregaModel {
    private int id;
    private LocalDate data_entrega;
    private int semestre;
    private int ano;
    private boolean trocado;
    private int quantidade;
    private int fk_servidor;
    private int fk_aluno;
    private int fk_uniforme;
    
    //Getters e Setters 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(LocalDate data_entrega) {
        this.data_entrega = data_entrega;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
    
    public boolean isTrocado() {
        return this.trocado;
    }

    public void setTrocado(boolean trocado) {
        this.trocado = trocado;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getFk_servidor() {
        return fk_servidor;
    }

    public void setFk_servidor(int fk_servidor) {
        this.fk_servidor = fk_servidor;
    }

    public int getFk_aluno() {
        return fk_aluno;
    }

    public void setFk_aluno(int fk_aluno) {
        this.fk_aluno = fk_aluno;
    }

    public int getFk_uniforme() {
        return fk_uniforme;
    }

    public void setFk_uniforme(int fk_uniforme) {
        this.fk_uniforme = fk_uniforme;
    }
    
}
