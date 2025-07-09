/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Interfaces.AlunoInterface;
import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import com.mycompany.gerenciamento.uniformes.Models.CursoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class AlunoDAO implements AlunoInterface {
    private Connection conn;
    
    public AlunoDAO() {
        this.conn = Conexao.getConexao();
    }
    
    @Override
    public List<AlunoModel> listarTodos() {
        List<AlunoModel> alunos = new ArrayList<>();
        String sql = "SELECT a.*, c.id AS id_curso, c.nome AS nome_curso, c.n_periodos AS periodos_curso FROM Aluno AS a "
                   + "LEFT JOIN Curso AS c ON a.fk_curso = c.id";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery(sql);
            
            while (rs.next()) {
                AlunoModel aluno = new AlunoModel();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setSobrenome(rs.getString("sobrenome"));
                aluno.setEmail(rs.getString("email"));
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setMatricula(rs.getString("matricula"));
                aluno.setIdade(rs.getInt("idade"));
                aluno.setPeriodo(rs.getInt("periodo"));
                aluno.setFk_curso(rs.getInt("fk_curso"));
                
                CursoModel curso = new CursoModel();
                curso.setId(rs.getInt("id_curso"));
                curso.setNome(rs.getString("nome_curso"));
                curso.setN_periodos(rs.getInt("periodos_curso"));
                
                aluno.setCurso(curso);
                
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos:");
            e.printStackTrace();
        }
        
        return alunos;
    }
    
    @Override
    public AlunoModel getByMatricula(String matricula) {
        String sql = "SELECT a.id, a.nome, a.sobrenome, a.email, a.telefone, a.matricula, a.idade, a.periodo,"
                + "c.id AS id_curso, c.nome AS curso, c.n_periodos AS n_periodos "
                + "FROM Aluno AS a "
                + "LEFT JOIN Curso AS c ON a.fk_curso = c.id "
                + "WHERE a.matricula = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AlunoModel aluno = new AlunoModel();
                    CursoModel curso = new CursoModel();
                    
                    curso.setId(rs.getInt("id"));
                    curso.setNome(rs.getString("curso"));
                    curso.setN_periodos(rs.getInt("n_periodos"));
                    
                    
                    aluno.setId(rs.getInt("id"));
                    aluno.setNome(rs.getString("nome"));
                    aluno.setSobrenome(rs.getString("sobrenome"));
                    aluno.setEmail(rs.getString("email"));
                    aluno.setTelefone(rs.getString("telefone"));
                    aluno.setMatricula(rs.getString("matricula"));
                    aluno.setIdade(rs.getInt("idade"));
                    aluno.setPeriodo(rs.getInt("periodo"));
                    
                    aluno.setCurso(curso);
                    
                    return aluno;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar aluno:");
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public boolean cadastrar(AlunoModel aluno) {
        String sql = "INSERT INTO Aluno (nome, sobrenome, email, telefone, matricula, idade, periodo, fk_curso) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getSobrenome());
            ps.setString(3, aluno.getEmail());
            ps.setString(4, aluno.getTelefone());
            ps.setString(5, aluno.getMatricula());
            ps.setInt(6, aluno.getIdade());
            ps.setInt(7, aluno.getPeriodo());
            ps.setInt(8, aluno.getFk_curso());
            
            int linhasAfetadas = ps.executeUpdate(sql);
            
            if (linhasAfetadas == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        aluno.setId(rs.getInt(1));
                        System.out.println("Aluno Cadastrado com ID: " + aluno.getId());
                        return true;
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar aluno:");
            e.printStackTrace();
            return false;
        }
    }
}
