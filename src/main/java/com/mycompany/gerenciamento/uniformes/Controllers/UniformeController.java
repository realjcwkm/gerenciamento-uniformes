/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.UniformeDAO;
import com.mycompany.gerenciamento.uniformes.Models.UniformeEstoqueModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import java.util.List;

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
    
    public List<UniformeEstoqueModel> TabelaEstoque (){
        return this.uniformeDAO.TabelaEstoque();
    }
    public void editarEntrada(UniformeModel uniformes ){      
        uniformeDAO.editarEntrada(uniformes);
    }
    
    public void atualizarUniforme(UniformeModel uniformes) {      
        uniformeDAO.editarEntrada(uniformes);
    }
    
    public List<UniformeModel> getAllUniformes() {
        return this.uniformeDAO.listarTodos(); 
    }

}
