/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Interfaces.EntregaInterface;
import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author geinfo
 */
public class EntregaDAO implements EntregaInterface {
   private Connection conn;
   
   public EntregaDAO() {
       this.conn = Conexao.getConexao();
   }
   
   @Override
   public List<EntregaModel> listarTodos() {
    List<EntregaModel> entregas = new ArrayList<>();
    String sql = "SELECT * FROM Entrega";
    
    try (PreparedStatement ps = conn.prepareStatement(sql); 
         ResultSet rs = ps.executeQuery()) {
        
        while(rs.next()) {
            EntregaModel entrega = new EntregaModel();
            entrega.setId(rs.getInt("id"));
            entrega.setSemestre(rs.getInt("semestre"));
            entrega.setAno(rs.getInt("ano"));
            entrega.setData_entrega(rs.getDate("data_entrega").toLocalDate());
            entrega.setTrocado(rs.getBoolean("trocado"));
            entrega.setQuantidade(rs.getInt("quantidade"));
            entrega.setFk_servidor(rs.getInt("fk_servidor"));
            entrega.setFk_aluno(rs.getInt("fk_aluno"));
            entrega.setFk_uniforme(rs.getInt("fk_uniforme"));
            
            entregas.add(entrega);
        }
        
    } catch(SQLException error) {
        System.err.println("Erro ao listar entregas: ");
        error.printStackTrace();
    }
    
    return entregas;
    
   }
}
