/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Models;

import java.util.Objects;

/**
 *
 * @author geinfo
 */
public class FiltroModel {
    private String tipoFiltro;
    private int idFiltro;
    private String textoDisplay;
    
    public FiltroModel(String tipo, int id, String texto) {
        this.tipoFiltro = tipo;
        this.idFiltro = id;
        this.textoDisplay = texto;
    }

    public int getIdFiltro() {
        return idFiltro;
    }

    public String getTipoFiltro() {
        return tipoFiltro;
    }

    @Override
    public String toString() {
        return textoDisplay; 
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiltroModel that = (FiltroModel) o;
        return idFiltro == that.idFiltro && Objects.equals(tipoFiltro, that.tipoFiltro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoFiltro, idFiltro);
    }
    
    
}
