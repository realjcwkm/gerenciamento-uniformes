/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Interfaces.TamanhoInterface;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author w
 */
public class TamanhoDAO implements TamanhoInterface{
    private Connection conn;
    
    public TamanhoDAO() {
        this.conn = Conexao.getConexao();
    }
    @Override
    public List<TamanhoModel> listarTodos() {
        List<TamanhoModel> tamanhos = new ArrayList<>();
        
        String sql = "SELECT * FROM Tamanho";
        
        try(PreparedStatement ps = conn.prepareStatement(sql); 
                ResultSet rs = ps.executeQuery()) {
           while(rs.next()) {
               TamanhoModel tamanho = new TamanhoModel();
               
               tamanho.setId(rs.getInt("id"));
               tamanho.setNome(rs.getString("nome"));
               
               tamanhos.add(tamanho);
           }
        } catch(SQLException error) {
            System.err.println("Erro ao listar tamanhos: ");
            error.printStackTrace();
        }
        
        return tamanhos;
    }
    
    @Override
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
