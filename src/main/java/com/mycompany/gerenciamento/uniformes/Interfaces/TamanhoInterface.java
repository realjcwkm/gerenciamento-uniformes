/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Interfaces;

import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import java.util.List;

/**
 *
 * @author geinfo
 */
public interface TamanhoInterface {
    public List<TamanhoModel> listarTodos();
    public void cadastrarTamanho(TamanhoModel tamanho);
}
