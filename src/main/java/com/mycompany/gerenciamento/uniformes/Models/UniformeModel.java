/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Models;

/**
 *
 * @author rober
 */
public class UniformeModel {
    private int id;
    private int quantidade;
    private int fk_tipo_uniforme;
    private int fk_tamanho;
    
    
    private TamanhoModel tamanho;
    private TipoUniformeModel tipoUniforme;
    
//
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getFk_tipo_uniforme() {
        return fk_tipo_uniforme;
    }

    public void setFk_tipo_uniforme(int fk_tipo_uniforme) {
        this.fk_tipo_uniforme = fk_tipo_uniforme;
    }

    public int getFk_tamanho() {
        return fk_tamanho;
    }

    public void setFk_tamanho(int fk_tamanho) {
        this.fk_tamanho = fk_tamanho;
    }

    public TamanhoModel getTamanho() {
        return tamanho;
    }

    public void setTamanho(TamanhoModel tamanho) {
        this.tamanho = tamanho;
    }

    public TipoUniformeModel getTipoUniforme() {
        return tipoUniforme;
    }

    public void setTipoUniforme(TipoUniformeModel tipoUniforme) {
        this.tipoUniforme = tipoUniforme;
    }

}