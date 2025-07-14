/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.CursoDAO;
import com.mycompany.gerenciamento.uniformes.Models.CursoModel;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class CursoController {
   private final CursoDAO cursoDAO;
    
    public CursoController() {
        this.cursoDAO = new CursoDAO();
    }
    
    public List<CursoModel> listarTodos() {
        return this.cursoDAO.listarTodos();
    } 
}
