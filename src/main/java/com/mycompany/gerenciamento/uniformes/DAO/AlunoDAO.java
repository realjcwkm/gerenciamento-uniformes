/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Interfaces.AlunoInterface;
import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import com.mycompany.gerenciamento.uniformes.Models.CursoModel;
import com.mycompany.gerenciamento.uniformes.Models.FiltroModel;
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
    public List<AlunoModel> listarTodos(int pagina, int itensPorPagina, String busca, FiltroModel filtro) {
        List<AlunoModel> alunos = new ArrayList<>();
        
        StringBuilder sqlBuilder = new StringBuilder(
         "SELECT a.*, "
            + "c.id AS id_curso, "
            + "c.nome AS nome_curso, "
            + "c.n_periodos AS periodos_curso "
         + "FROM Aluno AS a "
         + "LEFT JOIN Curso AS c "
         + "ON a.fk_curso = c.id "
        );
        
        boolean hasSearchTerm = busca != null && !busca.trim().isEmpty();
        boolean hasFilter = filtro != null && filtro.getIdFiltro() > 0;
        
        if(hasSearchTerm || hasFilter) {
            sqlBuilder.append(" WHERE ");
        }
        
        if(hasSearchTerm) {
            sqlBuilder.append(
                " (UPPER(a.nome) LIKE ?"
             + " OR UPPER(a.sobrenome) LIKE ?"
             + " OR UPPER(a.matricula) LIKE ?"
             + " OR UPPER(c.nome) LIKE ?)"
            );
        }
        
        if(hasSearchTerm && hasFilter) {
            sqlBuilder.append(" AND ");
        }
        
        if(hasFilter) {
            if("CURSO".equals(filtro.getTipoFiltro())) {
                sqlBuilder.append("c.id = ?");
            } else if("PERIODO".equals(filtro.getTipoFiltro())){
                sqlBuilder.append("c.n_periodo = ?");
            }
        }
        
        sqlBuilder.append(" ORDER BY a.id DESC LIMIT ? OFFSET ?");
        
        String sql = sqlBuilder.toString();
        
        int offset = (pagina - 1) * itensPorPagina;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            if (hasSearchTerm) {
                busca = "%" + busca.toUpperCase() + "%";
                ps.setString(index++, busca);
                ps.setString(index++, busca);
                ps.setString(index++, busca);
                ps.setString(index++, busca);
            }
            
            if (hasFilter) {
                ps.setInt(index++, filtro.getIdFiltro());
            }
            
            ps.setInt(index++, itensPorPagina);
            ps.setInt(index++, offset);
            
            ResultSet rs = ps.executeQuery();
            
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
    public int getTotal(String busca, FiltroModel filtro) {
        StringBuilder sqlBuilder = new StringBuilder(
         "SELECT COUNT(*) FROM Aluno AS a "
         + "LEFT JOIN Curso AS c "
         + "ON a.fk_curso = c.id"
        );
        
        boolean hasSearchTerm = busca != null && !busca.trim().isEmpty();
        boolean hasFilter = filtro != null && filtro.getIdFiltro() > 0;
        
        if(hasSearchTerm || hasFilter) {
            sqlBuilder.append(" WHERE ");
        }
        
        if(hasSearchTerm) {
            sqlBuilder.append("(UPPER(a.nome) LIKE ?"
             + " OR UPPER(a.sobrenome) LIKE ?"
             + " OR UPPER(a.matricula) LIKE ?"
             + " OR UPPER(c.nome) LIKE ?)"
            );
        }
        
        if(hasSearchTerm && hasFilter) {
            sqlBuilder.append(" AND ");
        }
        
        if(hasFilter) {
            if("CURSO".equals(filtro.getTipoFiltro())) {
                sqlBuilder.append("c.id = ?");
            } else if("PERIODO".equals(filtro.getTipoFiltro())){
                sqlBuilder.append("c.n_periodo = ?");
            }
        }
        
       
        
        String sql = sqlBuilder.toString();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int index = 1;
            
            if (hasSearchTerm) {
                busca = "%" + busca.toUpperCase() + "%";
                ps.setString(index++, busca);
                ps.setString(index++, busca);
                ps.setString(index++, busca);
                ps.setString(index++, busca);
            }
            
            if (hasFilter) {
                ps.setInt(index++, filtro.getIdFiltro());
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException error) {
            System.err.println("Erro ao contar o total de alunos: " + error.getMessage());
        }
        return 0;
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
            ps.setInt(8, aluno.getCurso().getId());
            
            int linhasAfetadas = ps.executeUpdate();
            
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
    
    @Override
    public boolean editar(AlunoModel aluno) {
        String sql = "UPDATE Aluno SET nome = ?, sobrenome = ?, email = ?, telefone = ?, matricula = ?, idade = ?, periodo = ?, fk_curso = ? WHERE id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getSobrenome());
            ps.setString(3, aluno.getEmail());
            ps.setString(4, aluno.getTelefone());
            ps.setString(5, aluno.getMatricula());
            ps.setInt(6, aluno.getIdade());
            ps.setInt(7, aluno.getPeriodo());
            ps.setInt(8, aluno.getCurso().getId());
            ps.setInt(9, aluno.getId());
            
            int linhasAfetadas = ps.executeUpdate();
            
            if (linhasAfetadas == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        aluno.setId(rs.getInt(1));
                        System.out.println("Aluno Atualizado com ID: " + aluno.getId());
                        return true;
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno:");
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean excluir(int id) {
        String sql = "DELETE FROM Aluno WHERE id = ?";
        try(PreparedStatement ps = this.conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException error) {
            System.err.println("Erro ao excluir aluno: " + error.getMessage());
            error.printStackTrace();
            return false;
        }
    }
}
