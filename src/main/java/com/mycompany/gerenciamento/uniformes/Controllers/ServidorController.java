/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.ServidorDAO;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import java.util.List;

/**
 *
 * @author 70094534209
 */
public class ServidorController {
    private final ServidorDAO servidorDAO;
    
    public ServidorController() {
        this.servidorDAO = new ServidorDAO();
    }
    
    public List<ServidorModel> listarTodos() {
        return this.servidorDAO.listarTodos();
    }
}
