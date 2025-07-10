/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.TipoUniformeDAO;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class TipoUniformeController {
    private final TipoUniformeDAO tipoUniformeDAO;
    
    public TipoUniformeController() {
        this.tipoUniformeDAO = new TipoUniformeDAO();
    }
    
    public List<TipoUniformeModel> listarTodos() {
        return this.tipoUniformeDAO.listarTodos();
    }
}
