/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes;

import com.mycompany.gerenciamento.uniformes.DAO.AlunoDAO;
import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class AlunoController {
    private final AlunoDAO alunoDAO;
    
    public AlunoController() {
        this.alunoDAO = new AlunoDAO();
    }
    
    public List<AlunoModel> listarTodos() {
        return this.alunoDAO.listarTodos();
    }
}
