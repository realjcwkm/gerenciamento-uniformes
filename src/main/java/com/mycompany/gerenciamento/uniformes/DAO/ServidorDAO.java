package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
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
    public List<ServidorModel> listarTodos() {
        List<ServidorModel> servidores = new ArrayList<>();
        String sql = "SELECT * FROM servidor";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
                servidor.setFk_departamento(rs.getInt("fk_departamento"));

                servidores.add(servidor);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar servidores:");
            e.printStackTrace();
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
    public boolean cadastrarServidor(ServidorModel servidor) {
        String sql = "INSERT INTO servidor (nome, sobrenome, email, telefone, matricula, senha, ativo, fk_departamento) "
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
    public boolean verificarDepartamento(int idDepartamento) throws SQLException {
        String sql = "SELECT id FROM departamento WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDepartamento);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}