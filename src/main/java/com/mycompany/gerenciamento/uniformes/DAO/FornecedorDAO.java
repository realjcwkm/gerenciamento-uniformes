/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.FornecedorModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author rober
 */
public class FornecedorDAO {
    private final Connection conn;
    
    public FornecedorDAO() {
        this.conn = Conexao.getConexao();
    }

    public List<FornecedorModel> listarTodos() {
        List<FornecedorModel> fornecedores = new ArrayList<>();
        String sql = "SELECT * FROM Fornecedor ORDER BY nome";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while(rs.next()) {
                FornecedorModel fornecedor = new FornecedorModel();
                
                fornecedor.setId(rs.getInt("id"));
                fornecedor.setNome(rs.getString("nome"));
                
                fornecedores.add(fornecedor);
            }
        } catch(SQLException error) {
            System.err.println("Erro ao listar fornecedores: " + error.getMessage());
            error.printStackTrace();
        }
        
        return fornecedores;
    }
    
}
