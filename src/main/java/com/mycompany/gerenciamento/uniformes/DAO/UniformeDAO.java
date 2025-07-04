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

            tamanho.setId(rs.getInt("id_tamanho"));
//            tamanho.setDescricao(rs.getString("tamanho")); 

            tipoUniforme.setId(rs.getInt("id_tipo"));
            tipoUniforme.setNome(rs.getString("tipo"));

            uniforme.setId(rs.getInt("id_uniforme"));
            uniforme.setQuantidade(rs.getInt("quantidade_uniforme"));
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
    
}