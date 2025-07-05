/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Models;

/**
 *
 * @author rober
 */
public class EntradaModel {
    private int id;
    private int data_entrada;
    private  int quantidade;
    private int fk_fornecedor;
    private int fk_uniforme;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getData_entrada() {
        return data_entrada;
    }

    public void setData_entrada(int data_entrada) {
        this.data_entrada = data_entrada;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getFk_fornecedor() {
        return fk_fornecedor;
    }

    public void setFk_fornecedor(int fk_fornecedor) {
        this.fk_fornecedor = fk_fornecedor;
    }

    public int getFk_uniforme() {
        return fk_uniforme;
    }

    public void setFk_uniforme(int fk_uniforme) {
        this.fk_uniforme = fk_uniforme;
    }
}
