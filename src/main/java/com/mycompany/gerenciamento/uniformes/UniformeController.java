/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes;

import com.mycompany.gerenciamento.uniformes.DAO.UniformeDAO;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;

/**
 *
 * @author geinfo
 */
public class UniformeController {
    private final UniformeDAO uniformeDAO;
    
    public UniformeController() {
        this.uniformeDAO = new UniformeDAO();
    }
    
    public UniformeModel buscarPorTipoETamanho(int idTipo, int idTamanho) {
        return this.uniformeDAO.buscarPorTipoETamanho(idTipo, idTamanho);
    }
}
