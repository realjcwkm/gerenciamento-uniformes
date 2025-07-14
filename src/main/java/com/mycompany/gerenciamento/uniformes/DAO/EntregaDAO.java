/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Interfaces.EntregaInterface;
import com.mycompany.gerenciamento.uniformes.Models.AlunoModel;
import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import com.mycompany.gerenciamento.uniformes.Models.FiltroModel;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author geinfo
 */
public class EntregaDAO implements EntregaInterface {
    private Connection conn;

    public EntregaDAO() {
        this.conn = Conexao.getConexao();
    }
   
   // Gráfico pizza
    @Override
    public Map<String, Integer> getContagemEntregaPorTipo() {
        Map<String, Integer> dados = new HashMap<>();
        String sql = "SELECT tu.nome, SUM(e.quantidade) AS quantidade " +
                     "FROM Entrega AS e " +
                     "JOIN Uniforme AS u ON e.fk_uniforme = u.id " +
                     "JOIN TipoUniforme AS tu ON u.fk_tipo_uniforme = tu.id " +
                     "GROUP BY tu.nome";

        try (PreparedStatement ps = this.conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tipo = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");
                dados.put(tipo, quantidade);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar dados para o gráfico: ");
            e.printStackTrace();
        }
        return dados;
    }
   
    // Gráfico barra
    @Override
    public List<Map<String, Object>> getContagemPorTurmaETipo() {
        List<Map<String, Object>> dados = new ArrayList<>();

        String sql = "SELECT " +
                     "CONCAT(c.nome, ' - ', a.periodo, 'º Período') AS turma, " +
                     "tu.nome AS tipo_uniforme, " +
                     "SUM(e.quantidade) AS quantidade " +
                     "FROM Entrega AS e " +
                     "JOIN Aluno AS a ON e.fk_aluno = a.id " +
                     "JOIN Curso AS c ON a.fk_curso = c.id " +
                     "JOIN Uniforme AS u ON e.fk_uniforme = u.id " +
                     "JOIN TipoUniforme AS tu ON u.fk_tipo_uniforme = tu.id " +
                     "GROUP BY c.nome, a.periodo, tu.nome " +
                     "ORDER BY turma, tipo_uniforme";

        try (PreparedStatement ps = this.conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> linha = new HashMap<>();
                linha.put("turma", rs.getString("turma"));
                linha.put("tipo_uniforme", rs.getString("tipo_uniforme"));
                linha.put("quantidade", rs.getInt("quantidade"));
                dados.add(linha);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar dados para o gráfico de barras: ");
            e.printStackTrace();
        }
        return dados;
    }
    
    // Todos os tipos de uniforme para relacionar uma cor a eles
    @Override
    public List<String> getTodosOsTiposDeUniforme() {
        List<String> tipos = new ArrayList<>();
        String sql = "SELECT nome FROM TipoUniforme ORDER BY nome";

        try (PreparedStatement ps = this.conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                tipos.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os tipos de uniforme: ");
            e.printStackTrace();
        }
        return tipos;
    }
    
    // Entregas por curso
    @Override
    public Map<String, Integer> getContagemPorCurso() {
        Map<String, Integer> dados = new HashMap<>();
        String sql = "SELECT c.nome, SUM(e.quantidade) AS quantidade " +
                     "FROM Entrega AS e " +
                     "JOIN Aluno AS a ON e.fk_aluno = a.id " +
                     "JOIN Curso AS c ON a.fk_curso = c.id " +
                     "GROUP BY c.nome";

        try (PreparedStatement ps = this.conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                dados.put(rs.getString("nome"), rs.getInt("quantidade"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar contagem por curso: ");
            e.printStackTrace();
        }
        return dados;
    }
   
   // Quantidade total de uniformes entregues
    @Override
    public int getQuantidadeTotalGeral() {
        String sql = "SELECT SUM(quantidade) FROM Entrega";
        int total = 0;
        try (PreparedStatement ps = this.conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
   
    @Override
    public List<EntregaModel> listarPagina(int pagina, int itensPorPagina, String termoBusca, FiltroModel filtro) {
        List<EntregaModel> entregas = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT e.id, e.semestre, e.ano, e.data_entrega, e.trocado, e.quantidade, "
            + "s.id AS id_servidor, s.nome AS nome_servidor, s.sobrenome AS sobrenome_servidor, "
            + "u.id AS id_uniforme, u.quantidade AS quantidade_uniforme, "
            + "a.id AS id_aluno, a.matricula AS matricula_aluno, a.nome AS nome_aluno, a.sobrenome AS sobrenome_aluno, "
            + "t.id AS id_tamanho, t.nome AS tamanho, "
            + "tu.id AS id_tipo, tu.nome AS tipo,"
            + "tr.id AS id_troca, tr.data_troca AS data_troca "
            + "FROM Entrega AS e "
            + "LEFT JOIN Servidor AS s ON e.fk_servidor = s.id "
            + "LEFT JOIN Uniforme AS u ON e.fk_uniforme = u.id "
            + "LEFT JOIN Aluno AS a ON e.fk_aluno = a.id "
            + "LEFT JOIN Tamanho AS t ON u.fk_tamanho = t.id "
            + "LEFT JOIN TipoUniforme AS tu ON u.fk_tipo_uniforme = tu.id "
            + "LEFT JOIN Troca AS tr ON tr.fk_entrega_antiga = e.id");
        
        boolean hasSearchTerm = termoBusca != null && !termoBusca.trim().isEmpty();
        boolean hasFilter = filtro != null && filtro.getIdFiltro() > 0;
        
        if(hasSearchTerm || hasFilter) {
            sql.append(" WHERE ");
        }
        
        if(hasSearchTerm) {
            sql.append("(LOWER(a.nome) LIKE ? OR LOWER(tu.nome) LIKE ? OR LOWER(a.matricula) LIKE ? OR LOWER(s.nome) LIKE ? "
                    + "OR LOWER(t.nome) LIKE ? "
                    + "OR LOWER(e.quantidade) LIKE ? "
                    + "OR LOWER(e.data_entrega) LIKE ?)");
        }
        
        if(hasSearchTerm && hasFilter) {
            sql.append(" AND ");
        }
        
        if(hasFilter) {
            if("TIPO".equals(filtro.getTipoFiltro())) {
                sql.append("tu.id = ?");
            } else if("TAMANHO".equals(filtro.getTipoFiltro())){
                sql.append("t.id = ?");
            }
        }
        
        sql.append(" ORDER BY e.id DESC LIMIT ? OFFSET ?");
       
        int offset = (pagina - 1) * itensPorPagina;
        
        try(PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            
            if(hasSearchTerm) {
                String termoLike = "%" + termoBusca.toLowerCase() + "%";
                ps.setString(paramIndex++, termoLike);
                ps.setString(paramIndex++, termoLike);
                ps.setString(paramIndex++, termoLike);
                ps.setString(paramIndex++, termoLike);
                ps.setString(paramIndex++, termoLike);
                ps.setString(paramIndex++, termoLike);
                ps.setString(paramIndex++, termoLike);
                
            }
            
            if (hasFilter) {
                ps.setInt(paramIndex++, filtro.getIdFiltro());
            }
            
            ps.setInt(paramIndex++, itensPorPagina);
            ps.setInt(paramIndex++, offset);
            
            try (ResultSet rs = ps.executeQuery()) {
                
                while(rs.next()) {
                    EntregaModel entrega = new EntregaModel();
                    ServidorModel servidor = new ServidorModel();
                    UniformeModel uniforme = new UniformeModel();
                    AlunoModel aluno = new AlunoModel();
                    TamanhoModel tamanho = new TamanhoModel();
                    TipoUniformeModel tipoUniforme = new TipoUniformeModel();

                    entrega.setId(rs.getInt("id"));
                    entrega.setSemestre(rs.getInt("semestre"));
                    entrega.setAno(rs.getInt("ano"));
                    entrega.setData_entrega(rs.getDate("data_entrega").toLocalDate());
                    entrega.setTrocado(rs.getBoolean("trocado"));
                    entrega.setQuantidade(rs.getInt("quantidade"));

                    servidor.setId(rs.getInt("id_servidor"));
                    servidor.setNome(rs.getString("nome_servidor"));
                    servidor.setSobrenome(rs.getString("sobrenome_servidor"));

                    aluno.setId(rs.getInt("id_aluno"));
                    aluno.setNome(rs.getString("nome_aluno"));
                    aluno.setSobrenome(rs.getString("sobrenome_aluno"));
                    aluno.setMatricula(rs.getString("matricula_aluno"));

                    tamanho.setId(rs.getInt("id_tamanho"));
                    tamanho.setNome(rs.getString("tamanho"));

                    tipoUniforme.setId(rs.getInt("id_tipo"));
                    tipoUniforme.setNome(rs.getString("tipo"));

                    uniforme.setId(rs.getInt("id_uniforme"));
                    uniforme.setTamanho(tamanho); 
                    uniforme.setTipoUniforme(tipoUniforme);


                    entrega.setServidor(servidor);
                    entrega.setUniforme(uniforme);
                    entrega.setAluno(aluno);

                    entregas.add(entrega);
                }
            }  
        } catch(SQLException error) {
            System.err.println("Erro ao listar entregas com filtro: ");
            error.printStackTrace();
        }
        
        return entregas;
    }
       
    @Override
    public int getTotal(String termoBusca, FiltroModel filtro) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Entrega AS e "
                + "LEFT JOIN Servidor AS s ON e.fk_servidor = s.id "
                + "LEFT JOIN Aluno AS a ON e.fk_aluno = a.id "
                + "LEFT JOIN Uniforme AS u ON e.fk_uniforme = u.id "
                + "LEFT JOIN TipoUniforme AS tu ON u.fk_tipo_uniforme = tu.id "
                + "LEFT JOIN Tamanho AS t ON u.fk_tamanho = t.id");
        
        boolean hasSearchTerm = termoBusca != null && !termoBusca.trim().isEmpty();
        boolean hasFilter = filtro != null && filtro.getIdFiltro() > 0;
        
        if(hasSearchTerm || hasFilter) {
            sql.append(" WHERE ");
        }
        
        if(hasSearchTerm) {
            sql.append("(LOWER(a.nome) LIKE ? OR LOWER(tu.nome) LIKE ? OR LOWER(a.matricula) LIKE ? OR LOWER(s.nome) LIKE ? "
                    + "OR LOWER(t.nome) LIKE ? "
                    + "OR LOWER(e.quantidade) LIKE ? "
                    + "OR LOWER(e.data_entrega) LIKE ?)");
        }
        
        if(hasSearchTerm && hasFilter) {
            sql.append(" AND ");
        }
        
        if(hasFilter) {
            if("TIPO".equals(filtro.getTipoFiltro())) {
                sql.append("tu.id = ?");
            } else if("TAMANHO".equals(filtro.getTipoFiltro())){
                sql.append("t.id = ?");
            }
        }
        
        try(PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            
            if(hasSearchTerm) {
                String termoLike = "%" + termoBusca.toLowerCase() + "%";
                ps.setString(paramIndex++, termoLike);
                ps.setString(paramIndex++, termoLike);
                ps.setString(paramIndex++, termoLike);
                ps.setString(paramIndex++, termoLike);
                ps.setString(paramIndex++, termoLike);
                ps.setString(paramIndex++, termoLike);           
                ps.setString(paramIndex++, termoLike);

            }
            
            if (hasFilter) {
                ps.setInt(paramIndex++, filtro.getIdFiltro());
            }
            
            try(ResultSet rs = ps.executeQuery()){
                 if(rs.next()) {
                     return rs.getInt(1);
                 } 
            }     
        } catch(SQLException error) {
            System.err.println("Erro ao contar o total de entregas com filtro: " + error.getMessage());
        }

        return 0;
    }
    
    @Override
    public void setNullPorAluno(int alunodId) {
        String sql = "UPDATE Entrega "
                + "SET fk_aluno = NULL "
                + "WHERE fk_aluno = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, alunodId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<EntregaModel> listarTodos() {
        List<EntregaModel> entregas = new ArrayList<>();
        String sql = "SELECT e.id, e.semestre, e.ano, e.data_entrega, e.trocado, e.quantidade, "
                + "s.id AS id_servidor, s.nome AS nome_servidor, "
                + "u.id AS id_uniforme, u.quantidade AS quantidade_uniforme, "
                + "a.id AS id_aluno, a.matricula AS matricula_aluno, a.nome AS nome_aluno, "
                + "t.id AS id_tamanho, t.nome AS tamanho, "
                + "tu.id AS id_tipo, tu.nome AS tipo,"
                + "tr.id AS id_troca, tr.data_troca AS data_troca "
                + "FROM Entrega AS e "
                + "LEFT JOIN Servidor AS s ON e.fk_servidor = s.id "
                + "LEFT JOIN Uniforme AS u ON e.fk_uniforme = u.id "
                + "LEFT JOIN Aluno AS a ON e.fk_aluno = a.id "
                + "LEFT JOIN Tamanho AS t ON u.fk_tamanho = t.id "
                + "LEFT JOIN TipoUniforme AS tu ON u.fk_tipo_uniforme = tu.id "
                + "LEFT JOIN Troca AS tr ON tr.fk_entrega_antiga = e.id";

        try (PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                EntregaModel entrega = new EntregaModel();
                ServidorModel servidor = new ServidorModel();
                UniformeModel uniforme = new UniformeModel();
                AlunoModel aluno = new AlunoModel();
                TamanhoModel tamanho = new TamanhoModel();
                TipoUniformeModel tipoUniforme = new TipoUniformeModel();

                entrega.setId(rs.getInt("id"));
                entrega.setSemestre(rs.getInt("semestre"));
                entrega.setAno(rs.getInt("ano"));
                entrega.setData_entrega(rs.getDate("data_entrega").toLocalDate());
                entrega.setTrocado(rs.getBoolean("trocado"));
                entrega.setQuantidade(rs.getInt("quantidade"));

                servidor.setId(rs.getInt("id_servidor"));
                servidor.setNome(rs.getString("nome_servidor"));

                aluno.setId(rs.getInt("id_aluno"));
                aluno.setNome(rs.getString("nome_aluno"));
                aluno.setMatricula(rs.getString("matricula_aluno"));

                tamanho.setId(rs.getInt("id_tamanho"));
                tamanho.setNome(rs.getString("tamanho"));

                tipoUniforme.setId(rs.getInt("id_tipo"));
                tipoUniforme.setNome(rs.getString("tipo"));

                uniforme.setId(rs.getInt("id_uniforme"));
                uniforme.setTamanho(tamanho); 
                uniforme.setTipoUniforme(tipoUniforme);


                entrega.setServidor(servidor);
                entrega.setUniforme(uniforme);
                entrega.setAluno(aluno);

                entregas.add(entrega);
            }

        } catch(SQLException error) {
            System.err.println("Erro ao listar entregas: ");
            error.printStackTrace();
        }

        return entregas;

    }
    
    @Override
    public int cadastrar(EntregaModel entrega) throws SQLException {
        String sql = "INSERT INTO Entrega "
               + "(semestre, ano, data_entrega, trocado, quantidade, fk_servidor, fk_uniforme, fk_aluno) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
       
        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
           ps.setInt(1, entrega.getSemestre());
           ps.setInt(2, entrega.getAno());
           ps.setDate(3, java.sql.Date.valueOf(entrega.getData_entrega()));
           ps.setBoolean(4, entrega.isTrocado());
           ps.setInt(5, entrega.getQuantidade());
           ps.setInt(6, entrega.getServidor().getId());
           ps.setInt(7, entrega.getUniforme().getId());
           ps.setInt(8, entrega.getAluno().getId());
           
           ps.executeUpdate();
           
           try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); 
                } else {
                    throw new SQLException("Falha ao obter o ID da nova entrega.");
                }
            }
           
        } catch (SQLException error) {
            System.err.println("Erro ao cadastrar entrega: " + error.getMessage());
            throw error; 
        }
    }
}
