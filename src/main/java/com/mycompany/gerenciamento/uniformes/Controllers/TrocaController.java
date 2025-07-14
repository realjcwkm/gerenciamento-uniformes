/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.EntregaDAO;
import com.mycompany.gerenciamento.uniformes.DAO.TamanhoDAO;
import com.mycompany.gerenciamento.uniformes.DAO.TipoUniformeDAO;
import com.mycompany.gerenciamento.uniformes.DAO.TrocaDAO;
import com.mycompany.gerenciamento.uniformes.DAO.UniformeDAO;
import com.mycompany.gerenciamento.uniformes.Models.*;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class TrocaController {
    private final TrocaDAO trocaDAO;
    private final EntregaDAO entregaDAO;
    
    public TrocaController() {
        this.trocaDAO = new TrocaDAO();
        this.entregaDAO = new EntregaDAO();
    }
    
    public List<TipoUniformeModel> getAllTipos() {
        return new TipoUniformeDAO().listarTodos(); 
    }
    
    public List<TamanhoModel> getAllTamanhos() {
        return new TamanhoDAO().listarTodos();
    }
    
    public UniformeModel buscarUniformePorTipoETamanho(TipoUniformeModel tipo, TamanhoModel tamanho) {
        if (tipo == null || tamanho == null) {
            return null;
        }
        return new UniformeDAO().buscarPorTipoETamanho(tipo.getId(), tamanho.getId());
    }
    
    
    public boolean realizarTroca(EntregaModel entregaAntiga, UniformeModel uniformeNovo) {
        return this.trocaDAO.realizarTroca(entregaAntiga, uniformeNovo);
    }
}
