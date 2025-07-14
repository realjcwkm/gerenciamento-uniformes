/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Interfaces;

import com.mycompany.gerenciamento.uniformes.Models.FiltroModel;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author w
 */
public interface ServidorInterface {
    public ServidorModel getByMatricula(String matricula);
    public boolean cadastrar(ServidorModel servidor);
    public boolean updateSenha(String matricula, String senha);
    public boolean verificarDepartamento(int idDepartamento) throws SQLException;
    void editar(ServidorModel servidor);
    public boolean excluir(int id);
    List<ServidorModel> listarPagina(int pagina, int itensPorPagina, String termoBusca, FiltroModel filtroDepto, FiltroModel filtroStatus);
    int getTotal(String termoBusca, FiltroModel filtroDepto, FiltroModel filtroStatus);
}
