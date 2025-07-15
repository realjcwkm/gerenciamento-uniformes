package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.Interfaces.ServidorInterface;
import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.FiltroModel;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServidorDAO implements ServidorInterface {
    private Connection conn;
    
    public ServidorDAO() {
        this.conn = Conexao.getConexao();
    }
    
    @Override
    public int getTotal(String termoBusca, FiltroModel filtroDepto, FiltroModel filtroStatus) {
        StringBuilder sqlBuilder = new StringBuilder(
            "SELECT COUNT(*) FROM Servidor s JOIN Departamento d ON s.fk_departamento = d.id"
        );

        boolean hasSearchTerm = termoBusca != null && !termoBusca.trim().isEmpty();
        boolean hasDeptoFilter = filtroDepto != null && filtroDepto.getIdFiltro() > 0;
        boolean hasStatusFilter = filtroStatus != null && !filtroStatus.getTipoFiltro().equals("TODOS"); 

        if(hasSearchTerm || hasDeptoFilter || hasStatusFilter) {
            sqlBuilder.append(" WHERE ");
        }

        if(hasSearchTerm) {
            sqlBuilder.append("(LOWER(s.nome) LIKE ? OR LOWER(s.sobrenome) LIKE ? OR LOWER(s.matricula) LIKE ? OR LOWER(d.nome) LIKE ?)");
        }
        
        if(hasSearchTerm && (hasDeptoFilter || hasStatusFilter)) {
            sqlBuilder.append(" AND ");
        }

        if(hasDeptoFilter) {
            sqlBuilder.append("d.id = ?");
        }
        
        if(hasDeptoFilter && hasStatusFilter) {
            sqlBuilder.append(" AND ");
        }
        
        if(hasStatusFilter) {
            sqlBuilder.append("s.ativo = ?");
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlBuilder.toString())) {
            int index = 1;
            if (hasSearchTerm) {
                String termoLike = "%" + termoBusca.toLowerCase() + "%";
                ps.setString(index++, termoLike);
                ps.setString(index++, termoLike);
                ps.setString(index++, termoLike);
                ps.setString(index++, termoLike);
            }
            if (hasDeptoFilter) {
                ps.setInt(index++, filtroDepto.getIdFiltro());
            }
            if(hasStatusFilter) {
                ps.setBoolean(index++, filtroStatus.getIdFiltro() == 1);
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException error) {
            System.err.println("Erro ao contar o total de servidores com filtro: " + error.getMessage());
        }
        return 0;
    }

    @Override
    public List<ServidorModel> listarPagina(int pagina, int itensPorPagina, String termoBusca, FiltroModel filtroDepto, FiltroModel filtroStatus) {
        List<ServidorModel> servidores = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(
            "SELECT s.*, d.nome AS nome_departamento " +
            "FROM Servidor s " +
            "JOIN Departamento d ON s.fk_departamento = d.id"
        );

        boolean hasSearchTerm = termoBusca != null && !termoBusca.trim().isEmpty();
        boolean hasDeptoFilter = filtroDepto != null && filtroDepto.getIdFiltro() > 0;
        boolean hasStatusFilter = filtroStatus != null && !filtroStatus.getTipoFiltro().equals("TODOS");

        if(hasSearchTerm || hasDeptoFilter || hasStatusFilter) {
            sqlBuilder.append(" WHERE ");
        }

        if(hasSearchTerm) {
            sqlBuilder.append("(LOWER(s.nome) LIKE ? OR LOWER(s.sobrenome) LIKE ? OR LOWER(s.matricula) LIKE ? OR LOWER(d.nome) LIKE ?)");
        }
        
        if(hasSearchTerm && (hasDeptoFilter || hasStatusFilter)) {
            sqlBuilder.append(" AND ");
        }

        if(hasDeptoFilter) {
            sqlBuilder.append("d.id = ?");
        }
        
        if(hasDeptoFilter && hasStatusFilter) {
            sqlBuilder.append(" AND ");
        }
        
        if(hasStatusFilter) {
            sqlBuilder.append("s.ativo = ?");
        }

        sqlBuilder.append(" ORDER BY s.id ASC LIMIT ? OFFSET ?");
        int offset = (pagina - 1) * itensPorPagina;

        try (PreparedStatement ps = conn.prepareStatement(sqlBuilder.toString())) {
            int index = 1;
            if (hasSearchTerm) {
                String termoLike = "%" + termoBusca.toLowerCase() + "%";
                ps.setString(index++, termoLike);
                ps.setString(index++, termoLike);
                ps.setString(index++, termoLike);
                ps.setString(index++, termoLike);
            }
            if (hasDeptoFilter) {
                ps.setInt(index++, filtroDepto.getIdFiltro());
            }
            if (hasStatusFilter) {
                ps.setBoolean(index++, filtroStatus.getIdFiltro() == 1);
            }

            ps.setInt(index++, itensPorPagina);
            ps.setInt(index++, offset);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ServidorModel servidor = new ServidorModel();
                    servidor.setId(rs.getInt("id"));
                    servidor.setNome(rs.getString("nome"));
                    servidor.setSobrenome(rs.getString("sobrenome"));
                    servidor.setEmail(rs.getString("email"));
                    servidor.setTelefone(rs.getString("telefone"));
                    servidor.setMatricula(rs.getString("matricula"));
                    servidor.setSenha(rs.getString("senha"));
                    servidor.setAtivo(rs.getBoolean("ativo"));
                    servidor.setAcesso(rs.getBoolean("primeiro_acesso"));
                    servidor.setFk_departamento(rs.getInt("fk_departamento"));
                    servidor.setNomeDepartamento(rs.getString("nome_departamento"));
                    servidores.add(servidor);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar servidores por página com filtro: " + e.getMessage());
        }
        return servidores;
    }
    
    @Override
    public ServidorModel getByMatricula(String matricula) {
        String sql = "SELECT * FROM Servidor WHERE matricula = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            
            try (ResultSet rs = ps.executeQuery()) {
            
                if (rs.next()) {
                    ServidorModel servidor = new ServidorModel();
                    servidor.setId(rs.getInt("id"));
                    servidor.setNome(rs.getString("nome"));
                    servidor.setSobrenome(rs.getString("sobrenome"));
                    servidor.setEmail(rs.getString("email"));
                    servidor.setTelefone(rs.getString("telefone"));
                    servidor.setMatricula(rs.getString("matricula"));
                    servidor.setSenha(rs.getString("senha"));
                    servidor.setAtivo(rs.getBoolean("ativo"));
                    servidor.setAcesso(rs.getBoolean("primeiro_acesso"));
                    servidor.setFk_departamento(rs.getInt("fk_departamento"));
                    
                    return servidor;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar servidor:");
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public boolean updateSenha(String matricula, String hashSenha) {
        String sql = "UPDATE Servidor "
                   + "SET senha = ?, primeiro_acesso = 0 "
                   + "WHERE matricula = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashSenha);
            ps.setString(2, matricula);
            
            int linhasAfetadas = ps.executeUpdate();
            
            return linhasAfetadas == 1;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar a senha:");
            e.printStackTrace();
        }
        
        return false;
    }
    
    @Override
    public boolean verificarDepartamento(int idDepartamento) throws SQLException {
        String sql = "SELECT id FROM Departamento WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDepartamento);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    @Override
    public boolean cadastrar(ServidorModel servidor) {
        String sql = "INSERT INTO Servidor (nome, sobrenome, email, telefone, matricula, senha, ativo, fk_departamento) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            // Verifica a conexão
            if (conn == null || conn.isClosed()) {
                conn = Conexao.getConexao();
            }
            
            // Verifica o departamento
            if (!verificarDepartamento(servidor.getFk_departamento())) {
                System.out.println("Departamento não encontrado!");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, servidor.getNome());
                ps.setString(2, servidor.getSobrenome());
                ps.setString(3, servidor.getEmail());
                ps.setString(4, servidor.getTelefone());
                ps.setString(5, servidor.getMatricula());
                ps.setString(6, servidor.getSenha());
                ps.setBoolean(7, servidor.isAtivo());
                ps.setInt(8, servidor.getFk_departamento());
                
                // Executa a inserção
                int linhasAfetadas = ps.executeUpdate();
                
                if (linhasAfetadas > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            servidor.setId(rs.getInt(1));
                            System.out.println("Servidor cadastrado com ID: " + servidor.getId());
                            return true;
                        }
                    }
                }
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar servidor:");
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public void editar(ServidorModel servidor) {
        String sql = "UPDATE Servidor SET nome = ?, sobrenome = ?, email = ?, telefone = ?, fk_departamento = ?, ativo = ? WHERE id = ?";

        try (PreparedStatement ps = this.conn.prepareStatement(sql)) {

            ps.setString(1, servidor.getNome());
            ps.setString(2, servidor.getSobrenome());
            ps.setString(3, servidor.getEmail());
            ps.setString(4, servidor.getTelefone());
            ps.setInt(5, servidor.getFk_departamento());
            ps.setBoolean(6, servidor.isAtivo());
            ps.setInt(7, servidor.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar servidor no banco de dados: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean excluir(int id) {
        String sql = "DELETE FROM Servidor WHERE id = ?";
        try {
            if (this.conn == null || this.conn.isClosed()) {
                this.conn = Conexao.getConexao();
            }
            try (PreparedStatement ps = this.conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                int linhasAfetadas = ps.executeUpdate();
                return linhasAfetadas > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir servidor: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}