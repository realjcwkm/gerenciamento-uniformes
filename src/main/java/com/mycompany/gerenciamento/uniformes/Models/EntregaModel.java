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
    private boolean trocado = false;
    private int quantidade;
    private int fk_servidor;
    private int fk_aluno;
    private int fk_uniforme;
}
