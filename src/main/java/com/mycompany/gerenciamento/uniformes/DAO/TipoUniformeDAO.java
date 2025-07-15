/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
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
public class TipoUniformeDAO {
    private Connection conn;
    
    public TipoUniformeDAO() {
        this.conn = Conexao.getConexao();
    }
    
    public List<TipoUniformeModel> listarTodos(){
        List<TipoUniformeModel> tipoUniforme = new ArrayList<>();
        String sql = "SELECT * FROM TipoUniforme";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery(sql);
            
            while(rs.next()) {
                TipoUniformeModel tipo = new TipoUniformeModel();
                tipo.setId(rs.getInt("id"));
                tipo.setNome(rs.getString("nome"));
                
                tipoUniforme.add(tipo);
            }
        } catch (SQLException error) {
            System.err.println("Erro ao listar tipo uniforme: ");
            error.printStackTrace();
        }
        
        return tipoUniforme;
    }
}
