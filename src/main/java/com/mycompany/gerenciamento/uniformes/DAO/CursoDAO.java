/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.CursoModel;
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
public class CursoDAO {
    private Connection conn;
    
    public CursoDAO() {
        this.conn = Conexao.getConexao();
    }
    
    public List<CursoModel> listarTodos(){
        List<CursoModel> cursos = new ArrayList<>();
        String sql = "SELECT * FROM Curso";
        
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery(sql);
            
            while(rs.next()) {
                CursoModel curso = new CursoModel();
                curso.setId(rs.getInt("id"));
                curso.setNome(rs.getString("nome"));
                curso.setN_periodos(rs.getInt("n_periodos"));
                
                cursos.add(curso);
            }
        } catch (SQLException error) {
            System.err.println("Erro ao listar cursos: ");
            error.printStackTrace();
        }
        
        return cursos;
    }
            
}
