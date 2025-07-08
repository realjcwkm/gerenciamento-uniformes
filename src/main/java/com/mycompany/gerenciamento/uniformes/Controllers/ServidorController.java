package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.DepartamentoDAO;
import com.mycompany.gerenciamento.uniformes.DAO.ServidorDAO;
import com.mycompany.gerenciamento.uniformes.Models.DepartamentoModel;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class ServidorController {
    private final ServidorDAO servidorDAO;
    private final DepartamentoDAO departamentoDAO;

    public ServidorController() {
        this.servidorDAO = new ServidorDAO();
        this.departamentoDAO = new DepartamentoDAO(); 
    }

    public List<ServidorModel> listarTodos() {
        return this.servidorDAO.listarTodos();
    }

    public List<DepartamentoModel> getAllDepartamentos() {
        return this.departamentoDAO.listarTodos(); 
    }

    public boolean cadastrarNovoServidor(String nome, String sobrenome, String email, String telefone, String matricula, boolean isAtivo, DepartamentoModel departamento) {
        if (nome == null || nome.trim().isEmpty() ||
            matricula == null || matricula.trim().isEmpty() ||
            departamento == null) {
            System.err.println("Erro de validação: Nome, matrícula e departamento são obrigatórios.");
            return false;
        }

        try {
            String senhaPadraoPura = "senha" + matricula;

            String senhaComHash = BCrypt.hashpw(senhaPadraoPura, BCrypt.gensalt());

            ServidorModel novoServidor = new ServidorModel();
            novoServidor.setNome(nome);
            novoServidor.setSobrenome(sobrenome);
            novoServidor.setEmail(email);
            novoServidor.setTelefone(telefone);
            novoServidor.setMatricula(matricula);
            novoServidor.setSenha(senhaComHash);
            novoServidor.setAtivo(isAtivo);
            novoServidor.setAcesso(true);
            novoServidor.setFk_departamento(departamento.getId());

            return this.servidorDAO.cadastrarServidor(novoServidor);

        } catch (Exception error) {
            error.printStackTrace();
            return false;
        }
    }
}