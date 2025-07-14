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
import com.mycompany.gerenciamento.uniformes.Models.FiltroModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author geinfo
 */
public class UniformeController {
    private List<Object[]> relatorioEntradas; 
    private List<Object[]> relatorioEntradasFiltrado;
    
    public UniformeController() {
        this.relatorioEntradas = new ArrayList<>();
        this.relatorioEntradasFiltrado = new ArrayList<>();
    }
    
    public UniformeModel buscarPorTipoETamanho(int idTipo, int idTamanho) {
        UniformeDAO uniformeDAO = new UniformeDAO();
        return uniformeDAO.buscarPorTipoETamanho(idTipo, idTamanho);
    }

    public List<UniformeModel> getAllUniformes() {
        UniformeDAO uniformeDAO = new UniformeDAO();
        return uniformeDAO.listarTodos(); 
    }
    
    public List<Object[]> gerarDadosParaTabelaEstoque() {
        UniformeDAO uniformeDAO = new UniformeDAO();
        EntradaDAO entradaDAO = new EntradaDAO();
        EntregaDAO entregaDAO = new EntregaDAO();
       
        List<EntradaModel> todasEntradas = entradaDAO.listarTodos();
        List<EntregaModel> todasSaidas = entregaDAO.listarTodasAsEntregas(); 
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
            int estoqueAtual = uniforme.getQuantidade();
           
            String status = estoqueAtual > 0 ? "Em Estoque" : "Fora de Estoque";
            
            Object[] linha = new Object[]{
                uniforme.getTipoUniforme().getNome(),
                uniforme.getTamanho().getNome(),
                status,
                totalEntradas,
                totalSaidas,
                estoqueAtual,
                uniforme.getTipoUniforme().getId(),    
                uniforme.getTamanho().getId() 
            };
            
            dadosParaTabela.add(linha);
        }
        
        return this.relatorioEntradas = dadosParaTabela;
    }
    
    public void filtrarRelatorio(String termoBusca, FiltroModel filtro) {
        String termo = termoBusca.toLowerCase().trim();
        
        List<Object[]> resultadoParcial = new ArrayList<>(this.relatorioEntradas);
        
        
        if (filtro != null && filtro.getIdFiltro() > 0) {
            resultadoParcial = resultadoParcial.stream()
                .filter(linha -> {
                    if ("TIPO".equals(filtro.getTipoFiltro())) {
                        return (Integer) linha[6] == filtro.getIdFiltro(); 
                    } else if ("TAMANHO".equals(filtro.getTipoFiltro())) {
                        return (Integer) linha[7] == filtro.getIdFiltro();
                    }
                    return true;
                })
                .collect(Collectors.toList());
        }
        
        if (!termo.isEmpty()) {
            resultadoParcial = resultadoParcial.stream()
                .filter(linha -> 
                    Arrays.stream(linha, 0, 6) 
                          .anyMatch(celula -> celula != null && celula.toString().toLowerCase().contains(termo))
                )
                .collect(Collectors.toList());
        }
        
        this.relatorioEntradasFiltrado = resultadoParcial;
    }
    
    public int getTotal(int itensPorPagina) {
        int totalItens = (this.relatorioEntradasFiltrado != null) ? this.relatorioEntradas.size() : 0;
        int totalPaginas = (int) Math.ceil((double) totalItens / itensPorPagina);
        return Math.max(totalPaginas, 1);
    }
    
    public List<Object[]> getPaginaDoRelatorio(int pagina, int itensPorPagina) {
        if (this.relatorioEntradasFiltrado == null) {
            return new ArrayList<>();
        }
        int startIndex = (pagina - 1) * itensPorPagina;
        int endIndex = Math.min(startIndex + itensPorPagina, this.relatorioEntradasFiltrado.size());
        
        if (startIndex >= endIndex) {
            return new ArrayList<>(); 
        }
        
        return this.relatorioEntradasFiltrado.subList(startIndex, endIndex);
    }
}
