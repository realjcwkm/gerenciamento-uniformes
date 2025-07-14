/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.EntradaDAO;
import com.mycompany.gerenciamento.uniformes.DAO.EntregaDAO;
import com.mycompany.gerenciamento.uniformes.DAO.UniformeDAO;
import com.mycompany.gerenciamento.uniformes.Models.EntradaModel;
import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author geinfo
 */
public class UniformeController {
    private final UniformeDAO uniformeDAO;
    private final EntradaDAO entradaDAO;
    private final EntregaDAO entregaDAO;
    
    public UniformeController() {
        this.uniformeDAO = new UniformeDAO();
        this.entradaDAO = new EntradaDAO();
        this.entregaDAO = new EntregaDAO();
    }
    
    public UniformeModel buscarPorTipoETamanho(int idTipo, int idTamanho) {
        return this.uniformeDAO.buscarPorTipoETamanho(idTipo, idTamanho);
    }

    public List<UniformeModel> getAllUniformes() {
        return this.uniformeDAO.listarTodos(); 
    }
    
    public List<Object[]> gerarDadosParaTabelaEstoque() {
       
        List<EntradaModel> todasEntradas = entradaDAO.listarTodos();
        List<EntregaModel> todasSaidas = entregaDAO.listarTodos(); 
        List<UniformeModel> todosUniformes = uniformeDAO.listarTodos(); 

        Map<Integer, Integer> entradasPorUniformeId = new HashMap<>();
        for (EntradaModel entrada : todasEntradas) {
            int id = entrada.getUniforme().getId();
            entradasPorUniformeId.put(id, entradasPorUniformeId.getOrDefault(id, 0) + entrada.getQuantidade());
        }

        Map<Integer, Integer> saidasPorUniformeId = new HashMap<>();
        for (EntregaModel saida : todasSaidas) {
            int id = saida.getUniforme().getId();
            saidasPorUniformeId.put(id, saidasPorUniformeId.getOrDefault(id, 0) + saida.getQuantidade());
        }
        
        List<Object[]> dadosParaTabela = new ArrayList<>();
        for (UniformeModel uniforme : todosUniformes) {
            int idUniforme = uniforme.getId();
            
            int totalEntradas = entradasPorUniformeId.getOrDefault(idUniforme, 0);
            int totalSaidas = saidasPorUniformeId.getOrDefault(idUniforme, 0);
            int estoqueAtual = totalEntradas - totalSaidas;
            String status = estoqueAtual > 0 ? "Em Estoque" : "Fora de Estoque";
            
            Object[] linha = new Object[]{
                uniforme.getTipoUniforme().getNome(),
                uniforme.getTamanho().getNome(),
                status,
                totalEntradas,
                totalSaidas,
                estoqueAtual,
            };
            
            dadosParaTabela.add(linha);
        }
        
        return dadosParaTabela;
    }
}
