/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.ServidorDAO;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import com.mycompany.gerenciamento.uniformes.Session.AuthSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author w
 */
public class AuthController {
    private final AuthSession session;
    private final ServidorDAO servidorDAO;
    
    public AuthController() {
        this.session = AuthSession.getInstance();
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
                this.session.iniciarSessao(servidor);
                return "autenticado";
            }
        }
        
        return "n_autenticado";
    }
    
    public boolean sair() {
        this.session.encerrarSessao();
        return true;
    }
    
    public boolean redefinirSenha(String matricula, String novaSenha) {
        String hash = BCrypt.hashpw(novaSenha, BCrypt.gensalt(12));
        return this.servidorDAO.updateSenha(matricula, hash);
    }
    
}
