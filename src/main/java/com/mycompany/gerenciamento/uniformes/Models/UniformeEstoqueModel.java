/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Models;
 import java.time.LocalDate;
/**
 *
 * @author rober
 */
public class UniformeEstoqueModel {
    private String tipo;
    private String tamanho;
    private String status;
    private int totalEntrada;
    private int totalSaida;
    private LocalDate dataUltimaEntrada;

    // Getters e Setters correspondentes (também em camelCase)
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTamanho() { return tamanho; }
    public void setTamanho(String tamanho) { this.tamanho = tamanho; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getTotalEntrada() { return totalEntrada; }
    public void setTotalEntrada(int totalEntrada) { this.totalEntrada = totalEntrada; }

    public int getTotalSaida() { return totalSaida; }
    public void setTotalSaida(int totalSaida) { this.totalSaida = totalSaida; }

    public LocalDate getDataUltimaEntrada() { return dataUltimaEntrada; }
    public void setDataUltimaEntrada(LocalDate dataUltimaEntrada) { this.dataUltimaEntrada = dataUltimaEntrada; }
}
