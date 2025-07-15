/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import java.sql.Connection;
import java.sql.Date;
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
    
    public List<UniformeModel> listarTodos() {
        List<UniformeModel> uniformes = new ArrayList<>();
        String sql = "SELECT " +
            " u.id AS uniforme_id, " +
            " u.quantidade AS uniforme_quantidade, " +
            " t.id AS tamanho_id, " +
            " t.nome AS tamanho_nome, " + 
            " tp.id AS tipo_id, " +
            " tp.nome AS tipo_nome " + 
            "FROM Uniforme AS u " +
            "JOIN Tamanho AS t ON u.fk_tamanho = t.id " +
            "JOIN TipoUniforme AS tp ON u.fk_tipo_uniforme = tp.id";

        try (PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UniformeModel uniforme = new UniformeModel();
                TamanhoModel tamanho = new TamanhoModel();
                TipoUniformeModel tipoUniforme = new TipoUniformeModel();

                tamanho.setId(rs.getInt("tamanho_id"));
                tamanho.setNome(rs.getString("tamanho_nome"));

                tipoUniforme.setId(rs.getInt("tipo_id"));
                tipoUniforme.setNome(rs.getString("tipo_nome"));

                uniforme.setId(rs.getInt("uniforme_id"));
                uniforme.setQuantidade(rs.getInt("uniforme_quantidade"));
                uniforme.setTamanho(tamanho); 
                uniforme.setTipoUniforme(tipoUniforme); 
                uniformes.add(uniforme);
            }
        } catch (SQLException error) {
            System.err.println("Erro ao listar uniformes: ");
            error.printStackTrace();

        }
        
        return uniformes;
    }
    
    public UniformeModel buscarPorTipoETamanho(int idTipo, int idTamanho) {
        String sql = "SELECT u.id AS id_uniforme, u.quantidade AS quantidade_estoque, "
                + "tu.id AS id_tipo, tu.nome AS tipo, "
                + "t.id AS id_tamanho, t.nome AS tamanho "
                + "FROM Uniforme AS u "
                + "LEFT JOIN TipoUniforme AS tu ON u.fk_tipo_uniforme = tu.id "
                + "LEFT JOIN Tamanho AS t ON u.fk_tamanho = t.id "
                + "WHERE u.fk_tipo_uniforme = ? AND u.fk_tamanho = ?";
        
        UniformeModel uniforme = null;
        
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTipo);
            ps.setInt(2, idTamanho); 
            
            try (ResultSet rs = ps.executeQuery()) { 
                if(rs.next()) {
                    uniforme = new UniformeModel();
                    TipoUniformeModel tipo = new TipoUniformeModel();
                    TamanhoModel tamanho = new TamanhoModel();
                    
                    tipo.setId(rs.getInt("id_tipo"));
                    tipo.setNome(rs.getString("tipo"));
                    
                    tamanho.setId(rs.getInt("id_tamanho"));
                    tamanho.setNome(rs.getString("tamanho"));
                    
                    uniforme.setId(rs.getInt("id_uniforme"));
                    uniforme.setQuantidade(rs.getInt("quantidade_estoque"));
                    
                    uniforme.setTipoUniforme(tipo);
                    uniforme.setTamanho(tamanho);
                }
                
            }
        } catch (SQLException error) {
            System.err.println("Erro ao buscar uniforme por tipo e tamanho: " + error.getMessage());
            error.printStackTrace();
        }
        
        return uniforme;
    }
    
    public int getTotal() {
        String sql = "SELECT COUNT(*) FROM Uniforme";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getTotalQuantidade() {
        String sql = "SELECT SUM(quantidade) FROM Uniforme";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1); 
            }
        } catch (SQLException e) {
            System.err.println("Erro ao calcular o estoque total geral: " + e.getMessage());
            e.printStackTrace();
        }
        return 0; 
    }
}