/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Interfaces;

import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author geinfo
 */
public interface EntregaInterface {
    public List<EntregaModel> listarTodos();
    public int cadastrarEntrega(EntregaModel entrega) throws SQLException;
}
