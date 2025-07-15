/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.EntradaDAO;
import com.mycompany.gerenciamento.uniformes.DAO.FornecedorDAO;
import com.mycompany.gerenciamento.uniformes.DAO.TamanhoDAO;
import com.mycompany.gerenciamento.uniformes.DAO.TipoUniformeDAO;
import com.mycompany.gerenciamento.uniformes.DAO.UniformeDAO;
import com.mycompany.gerenciamento.uniformes.Models.FornecedorModel;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class EntradaController {
    private final EntradaDAO entradaDAO;
    private final TipoUniformeDAO tipoUniformeDAO;
    private final TamanhoDAO tamanhoDAO;
    private final UniformeDAO uniformeDAO;
    private final FornecedorDAO fornecedorDAO;
    
    public EntradaController() {
        this.entradaDAO = new EntradaDAO();
        this.tipoUniformeDAO = new TipoUniformeDAO();
        this.tamanhoDAO = new TamanhoDAO();
        this.uniformeDAO = new UniformeDAO();
        this.fornecedorDAO = new FornecedorDAO();
    }
    
     public List<TipoUniformeModel> getAllTipos() {
        return this.tipoUniformeDAO.listarTodos();
    }
    
    public List<TamanhoModel> getAllTamanhos() {
        return this.tamanhoDAO.listarTodos();
    }
    
    public List<FornecedorModel> getAllFornecedores() {
        return this.fornecedorDAO.listarTodos();
    }
    
    public boolean regristrarEntrada(TipoUniformeModel tipo, TamanhoModel tamanho, FornecedorModel fornecedor, int quantidade, LocalDate data_entrega) {
        return this.entradaDAO.registrarEntrada(tipo, tamanho, fornecedor, quantidade, data_entrega);
    }
    
}
