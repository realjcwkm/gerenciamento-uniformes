package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.DepartamentoDAO;
import com.mycompany.gerenciamento.uniformes.DAO.ServidorDAO;
import com.mycompany.gerenciamento.uniformes.Models.DepartamentoModel;
import com.mycompany.gerenciamento.uniformes.Models.FiltroModel;
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

    public List<DepartamentoModel> getAllDepartamentos() {
        return this.departamentoDAO.listarTodos(); 
    }

    public List<ServidorModel> listarPagina(int pagina, int itensPorPagina, String termoBusca, FiltroModel filtroDepto, FiltroModel filtroStatus) {
        return this.servidorDAO.listarPagina(pagina, itensPorPagina, termoBusca, filtroDepto, filtroStatus);
    }

    public int getTotalDePaginas(int itensPorPagina, String termoBusca, FiltroModel filtroDepto, FiltroModel filtroStatus) {
        int totalDeItens = this.servidorDAO.getTotal(termoBusca, filtroDepto, filtroStatus);
        int totalPaginas = (int) Math.ceil((double) totalDeItens / itensPorPagina);
        return Math.max(totalPaginas, 1);
    }
    
    public boolean cadastrar(String nome, String sobrenome, String email, String telefone, String matricula, boolean isAtivo, DepartamentoModel departamento) {
        if (nome == null || nome.trim().isEmpty() ||
            sobrenome == null || sobrenome.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            telefone == null || telefone.trim().isEmpty() ||
            matricula == null || matricula.trim().isEmpty() ||
            departamento == null) {

            System.err.println("Tentativa de cadastrar servidor com dados nulos ou vazios.");
            return false;
        }

        try {
            String senhaPadraoPura = "ifro" + matricula;
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

            return this.servidorDAO.cadastrar(novoServidor);

        } catch (Exception error) {
            error.printStackTrace();
            return false;
        }
    }
    
    public void editar(ServidorModel servidor) {      
        servidorDAO.editar(servidor);
    }

    public String validarExclusao(ServidorModel servidor) {
        if (servidor.isAtivo()) {
            return "Não é possível excluir um servidor com status 'Ativo'.";
        }
        return "";
    }

    public boolean excluir(int id) {
        return this.servidorDAO.excluir(id);
    }
}