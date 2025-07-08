/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.TrocaModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class TrocaDAO {
    private Connection conn;
    
    public TrocaDAO() {
        this.conn = Conexao.getConexao();
    }
    
    public List<TrocaModel> listarTodos(){
        List<TrocaModel> trocas = new ArrayList<>();
        String sql = "SELECT * FROM Trocas";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery(sql);
            
            while(rs.next()) {
                TrocaModel troca = new TrocaModel();
                
                troca.setId(rs.getInt("id"));
                troca.setData_troca(rs.getDate("data_troca").toLocalDate());
                
                trocas.add(troca);
            }
        } catch (SQLException error) {
            System.err.println("Erro ao listar trocas: ");
            error.printStackTrace();
        }
        
        return trocas;
    } 
    
    public void cadastrarTroca(TrocaModel troca) throws SQLException{
        String sql = "INSERT INTO Troca (data_troca, fk_entrega_antiga, fk_entrega_nova) VALUES (?, ?, ?)";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(troca.getData_troca()));
            ps.setInt(2, troca.getEntregaAntiga().getId());
            ps.setInt(3, troca.getEntregaNova().getId());
            ps.executeUpdate();
        }
    }
}
