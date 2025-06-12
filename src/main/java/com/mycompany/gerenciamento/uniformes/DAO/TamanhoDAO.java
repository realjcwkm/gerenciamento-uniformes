/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author w
 */
public class TamanhoDAO {
    private Connection conn;
    
    public TamanhoDAO() {
        this.conn = Conexao.getConexao();
    }
    
    public void cadastrarTamanho(TamanhoModel tamanho) {
        String sql = "INSERT INTO Tamanho (nome) VALUES (?)";
        
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, tamanho.getNome());
            
            ps.execute();
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }
}
