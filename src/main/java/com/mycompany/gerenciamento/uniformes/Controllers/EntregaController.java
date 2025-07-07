/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.AlunoDAO;
import com.mycompany.gerenciamento.uniformes.DAO.EntregaDAO;
import com.mycompany.gerenciamento.uniformes.DAO.TamanhoDAO;
import com.mycompany.gerenciamento.uniformes.DAO.TipoUniformeDAO;
import com.mycompany.gerenciamento.uniformes.DAO.UniformeDAO;
import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import com.mycompany.gerenciamento.uniformes.Session.AuthSession;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class EntregaController {
    private final EntregaDAO entregaDAO;
    private final TipoUniformeDAO tipoUniformeDAO;
    private final TamanhoDAO tamanhoDAO;
    
    public EntregaController() {
        this.entregaDAO = new EntregaDAO();
        this.tipoUniformeDAO = new TipoUniformeDAO();
        this.tamanhoDAO = new TamanhoDAO();
    }
    
    public List<EntregaModel> listarTodos() {
        return this.entregaDAO.listarTodos();
    }
    
    public AlunoModel getAlunoByMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            return null;
        }
        AlunoDAO dao = new AlunoDAO();
        return dao.getByMatricula(matricula);
    }
    
    public List<TipoUniformeModel> getAllTipos() {
        return this.tipoUniformeDAO.listarTodos();
    }
    
    public List<TamanhoModel> getAllTamanhos() {
        return this.tamanhoDAO.listarTodos();
    }
    
    public boolean cadastrarNovaEntrega(AlunoModel aluno, TipoUniformeModel tipo, TamanhoModel tamanho, int quantidade) {
        if (aluno == null || tipo == null || tamanho == null || quantidade <= 0) {
            System.err.println("Erro de validação: Dados de entrada incompletos.");
            return false;
        }

        try {
            UniformeDAO uniformeDAO = new UniformeDAO();
            UniformeModel uniformeCompleto = uniformeDAO.buscarPorTipoETamanho(tipo.getId(), tamanho.getId());
            if (uniformeCompleto == null) {
                System.err.println("Não existe um uniforme para o tipo e tamanho selecionado.");
                return false;
            }
            
            LocalDate hoje = LocalDate.now(); 
            int anoAtual = hoje.getYear();
            int mesAtual = hoje.getMonthValue();
            int semestreAtual = (mesAtual <= 6) ? 1 : 2; 

            AuthSession sessao = AuthSession.getInstance();
            ServidorModel servidorLogado = new ServidorModel();
            servidorLogado.setId(sessao.getId());
            servidorLogado.setNome(sessao.getNome());

            EntregaModel novaEntrega = new EntregaModel();
            novaEntrega.setAluno(aluno);
            novaEntrega.setUniforme(uniformeCompleto);
            novaEntrega.setServidor(servidorLogado);
            novaEntrega.setQuantidade(quantidade);
            novaEntrega.setData_entrega(hoje);
            novaEntrega.setAno(anoAtual);
            novaEntrega.setSemestre(semestreAtual);
            novaEntrega.setTrocado(false);

            EntregaDAO entregaDAO = new EntregaDAO();
            entregaDAO.cadastrarEntrega(novaEntrega);
            
            return true; 

        } catch (Exception error) {
            error.printStackTrace();
            return false;
        }
    }
}
