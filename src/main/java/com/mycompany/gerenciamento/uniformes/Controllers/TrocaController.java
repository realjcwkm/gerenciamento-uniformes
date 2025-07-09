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
import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.*;
import java.sql.*;
import java.time.LocalDate;
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
        Connection conn = Conexao.getConexao();
        
        try {
            
            conn.setAutoCommit(false);
            
            EntregaModel entregaNova = new EntregaModel();
            entregaNova.setAluno(entregaAntiga.getAluno());
            entregaNova.setServidor(entregaAntiga.getServidor()); 
            entregaNova.setUniforme(uniformeNovo);
            entregaNova.setQuantidade(entregaAntiga.getQuantidade()); 
            entregaNova.setData_entrega(LocalDate.now()); 
            entregaNova.setAno(LocalDate.now().getYear());
            entregaNova.setSemestre(LocalDate.now().getMonthValue() <= 6 ? 1 : 2);
            entregaNova.setTrocado(false); 
            
            int idNovaEntrega = entregaDAO.cadastrarEntrega(entregaNova);
            entregaNova.setId(idNovaEntrega);

            TrocaModel novaTroca = new TrocaModel();
            novaTroca.setData_troca(LocalDate.now());
            novaTroca.setEntregaAntiga(entregaAntiga);
            novaTroca.setEntregaNova(entregaNova);
            trocaDAO.cadastrarTroca(novaTroca);
            
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro na transação de troca. Desfazendo operações.");
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException finalEx) {
                finalEx.printStackTrace();
            }
        }
    }
}
