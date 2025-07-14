/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Interfaces;

import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import com.mycompany.gerenciamento.uniformes.Models.FiltroModel;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author geinfo
 */
public interface EntregaInterface {
    public List<EntregaModel> listarPagina(int pagina, int intesPorPagina, String termoBusca, FiltroModel filtro);
    public int cadastrar(EntregaModel entrega) throws SQLException;
    public int getTotal(String termoBusca, FiltroModel filtro);
    public int getQuantidadeTotalGeral();
    public Map<String, Integer> getContagemPorCurso();
    public List<String> getTodosOsTiposDeUniforme();
    public List<Map<String, Object>> getContagemPorTurmaETipo();
    public Map<String, Integer> getContagemEntregaPorTipo();
}
