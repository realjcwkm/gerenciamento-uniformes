/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes;

import com.mycompany.gerenciamento.uniformes.DAO.ServidorDAO;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author w
 */
public class AuthController {
    private final ServidorDAO servidorDAO;
    
    public AuthController() {
        this.servidorDAO = new ServidorDAO();
    }
    
    public String autenticar(String matricula, String senha) {
        ServidorModel servidor = this.servidorDAO.getByMatricula(matricula);
        
        if (servidor != null) {
            String hash = servidor.getSenha();
            if (BCrypt.checkpw(senha, hash)) {
                if (servidor.isPrimeiroAcesso()) {
                    return "p_acesso";
                }
                return "autenticado";
            }
        }
        
        return "n_autenticado";
    }
    
}
