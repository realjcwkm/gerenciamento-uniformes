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
public class TrocaModel {
    private int id;
    private LocalDate data_troca;
    private int fk_entrega_antiga;
    private int fk_entrega_nova;
    
    private EntregaModel entregaAntiga;
    private EntregaModel entregaNova;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData_troca() {
        return data_troca;
    }

    public void setData_troca(LocalDate data_troca) {
        this.data_troca = data_troca;
    }

    public int getFk_entrega_antiga() {
        return fk_entrega_antiga;
    }

    public void setFk_entrega_antiga(int fk_entrega_antiga) {
        this.fk_entrega_antiga = fk_entrega_antiga;
    }

    public int getFk_entrega_nova() {
        return fk_entrega_nova;
    }

    public void setFk_entrega_nova(int fk_entrega_nova) {
        this.fk_entrega_nova = fk_entrega_nova;
    }

    public EntregaModel getEntregaAntiga() {
        return entregaAntiga;
    }

    public void setEntregaAntiga(EntregaModel entregaAntiga) {
        this.entregaAntiga = entregaAntiga;
    }

    public EntregaModel getEntregaNova() {
        return entregaNova;
    }

    public void setEntregaNova(EntregaModel entregaNova) {
        this.entregaNova = entregaNova;
    }
    
    
}
