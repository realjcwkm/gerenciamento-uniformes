/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
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
public class UniformeDAO {
    private Connection conn;
    
    public UniformeDAO(){
    this.conn = Conexao.getConexao();
    }
    public void cadastrarUniforme(UniformeModel uniforme){
        String sql = "INSERT INTO uniforme(quantidade, fk_tipo_uniforme, fk_tamanho) VALUES (?,?,?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, uniforme.getQuantidade());
            ps.setInt(2, uniforme.getFk_tipo_uniforme());
            ps.setInt(3, uniforme.getFk_tamanho());
            
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Uniforme cadastrado com sucesso!");

        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar uniforme: " + e.getMessage());
            e.printStackTrace();
        }
    }  
}