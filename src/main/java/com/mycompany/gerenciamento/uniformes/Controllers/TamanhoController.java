/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.TamanhoDAO;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class TamanhoController {
    private final TamanhoDAO tamanhoDAO;
    
    public TamanhoController() {
        this.tamanhoDAO = new TamanhoDAO();
    }
    
    public List<TamanhoModel> listarTodos() {
        return this.tamanhoDAO.listarTodos();
    }
}
