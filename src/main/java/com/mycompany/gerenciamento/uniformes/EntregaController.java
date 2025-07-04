/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes;

import com.mycompany.gerenciamento.uniformes.DAO.EntregaDAO;
import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class EntregaController {
    private final EntregaDAO entregaDAO;
    
    public EntregaController() {
        this.entregaDAO = new EntregaDAO();
    }
    
    public List<EntregaModel> listarTodos() {
        return this.entregaDAO.listarTodos();
    }
    
    public boolean salvarEntrega(EntregaModel entrega) {
        if(entrega.getQuantidade() <= 0) {
            return false;
        }
        try {
            entregaDAO.cadastrarEntrega(entrega);
            return true;
        } catch(Exception error) {
            error.printStackTrace();
            return false;
        }
    } 
}
