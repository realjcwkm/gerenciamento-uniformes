/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes;

import com.mycompany.gerenciamento.uniformes.DAO.EntregaDAO;
import com.mycompany.gerenciamento.uniformes.DAO.TamanhoDAO;
import com.mycompany.gerenciamento.uniformes.DAO.TipoUniformeDAO;
import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class EntregaController {
    private final EntregaDAO entregaDAO;
    private final TipoUniformeDAO tipoUniformeDAO;
    private final TamanhoDAO tamanhoDAO;
    
    public EntregaController() {
        this.entregaDAO = new EntregaDAO();
        this.tipoUniformeDAO = new TipoUniformeDAO();
        this.tamanhoDAO = new TamanhoDAO();
    }
    
    public List<EntregaModel> listarTodos() {
        return this.entregaDAO.listarTodos();
    }
    
    public List<TipoUniformeModel> getAllTipos() {
        return this.tipoUniformeDAO.listarTodos();
    }
    
    public List<TamanhoModel> getAllTamanhos() {
        return this.tamanhoDAO.listarTodos();
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
