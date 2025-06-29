package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.DepartamentoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepartamentoDAO {
    private Connection conn;
    
    public DepartamentoDAO() {
        this.conn = Conexao.getConexao();
    }
    
    public void cadastrarDepartamento(DepartamentoModel departamento) {
        String sql = "INSERT INTO departamento (nome) VALUES (?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departamento.getNome());
            ps.execute();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
}