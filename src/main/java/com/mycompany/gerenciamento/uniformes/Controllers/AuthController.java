/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.ServidorDAO;
import com.mycompany.gerenciamento.uniformes.EmailService.EmailService;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import com.mycompany.gerenciamento.uniformes.Session.AuthSession;
import java.util.HashMap;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;
import java.time.Instant;
import java.security.SecureRandom;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author w
 */
public class AuthController {
    private final AuthSession session;
    private final EmailService emailService;
    private final ServidorDAO servidorDAO;
    private final Map<String, ResetInfo> codigosEmMemoria = new HashMap<>();
    private record ResetInfo(String codigo, Instant dataExpiracao) {}
    
    public AuthController() {
        this.session = AuthSession.getInstance();
        this.emailService = new EmailService();
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
    
//    Alteração de Senha
    public boolean solicitarCodigo(String matricula, String email) {
        ServidorModel servidor = servidorDAO.getByMatricula(matricula);
        if (servidor == null || servidor.getEmail() == null || !servidor.getEmail().equals(email) || servidor.getEmail().isEmpty()) {
            return false;
        }

        String codigo = gerarCodigoAleatorio(6);
        Instant dataExpiracao = Instant.now().plus(10, ChronoUnit.MINUTES);

        codigosEmMemoria.put(matricula, new ResetInfo(codigo, dataExpiracao));
        
        System.out.println("Código gerado para " + matricula + ": " + codigo);
        
        return emailService.enviarEmailReset(servidor.getEmail(), codigo);
    }

    public String verificarCodigoRecuperacao(String matricula, String codigoEnviado) {
        ResetInfo info = codigosEmMemoria.get(matricula);

        if (info == null) {
            System.err.println("Nenhum código de reset encontrado para a matrícula: " + matricula);
            return "n_codigo";
        }

        if (Instant.now().isAfter(info.dataExpiracao())) {
            System.err.println("Código para " + matricula + " expirou.");
            codigosEmMemoria.remove(matricula);
            return "exp_codigo";
        }
        
        if (info.codigo().equals(codigoEnviado)) {
            System.out.println("Código verificado com sucesso para " + matricula);
            codigosEmMemoria.remove(matricula);
            return "sucesso";
        }

        System.err.println("Código incorreto para a matrícula: " + matricula);
        return "err_codigo";
    }

    private String gerarCodigoAleatorio(int tamanho) {
        SecureRandom random = new SecureRandom();
        StringBuilder codigo = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++) {
            codigo.append(random.nextInt(10));
        }
        return codigo.toString();
    }
    
}
