package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.DepartamentoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {
    private Connection conn;

    public DepartamentoDAO() {
        this.conn = Conexao.getConexao();
    }
    
    public List<DepartamentoModel> listarTodos() {
        List<DepartamentoModel> departamentos = new ArrayList<>();
        String sql = "SELECT id, nome FROM Departamento ORDER BY nome";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DepartamentoModel depto = new DepartamentoModel();
                depto.setId(rs.getInt("id"));
                depto.setNome(rs.getString("nome"));
                departamentos.add(depto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar departamentos:");
            e.printStackTrace();
        }
        return departamentos;
    }
}