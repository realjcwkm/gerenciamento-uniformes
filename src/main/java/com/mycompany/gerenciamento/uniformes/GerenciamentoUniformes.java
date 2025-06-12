/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gerenciamento.uniformes;

import com.mycompany.gerenciamento.uniformes.DAO.TamanhoDAO;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;

/**
 *
 * @author barba
 */
public class GerenciamentoUniformes {

    public static void main(String[] args) {
        TamanhoModel xg = new TamanhoModel();
        xg.setNome("XG");
        
        new TamanhoDAO().cadastrarTamanho(xg);
    }
}
