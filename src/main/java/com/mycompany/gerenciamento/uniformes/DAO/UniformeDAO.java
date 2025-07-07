/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeEstoqueModel;
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
    
    public List<UniformeEstoqueModel> TabelaEstoqueUniforme () {
        List<UniformeEstoqueModel> relatorio = new ArrayList<>();
        
        // Query que junta todas as tabelas e calcula os totais
        String sql = "SELECT " +
        "    tu.nome AS tipo_uniforme, " +
        "    t.nome AS tamanho_uniforme, " +
        "    COALESCE(SUM(entradas.quantidade), 0) AS total_entradas, " +
        "    COALESCE(SUM(entregas.quantidade), 0) AS total_saidas, " +
        "    MAX(entregas.data_entrega) as ultima_data_entrega " +
        "FROM " +
        "    Uniforme u " +
        "JOIN TipoUniforme tu ON u.fk_tipo_uniforme = tu.id " +
        "JOIN Tamanho t ON u.fk_tamanho = t.id " +
        "LEFT JOIN Entradas entradas ON u.id = entradas.fk_uniforme " +
        "LEFT JOIN Entrega entregas ON u.id = entregas.fk_uniforme " +
        "GROUP BY " +
        "    u.id, tu.nome, t.nome " +
        "ORDER BY " +
        "    tu.nome, t.nome";

        try (Statement stmt = this.conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UniformeEstoqueModel item = new UniformeEstoqueModel();
                
                int totalEntradas = rs.getInt("total_entradas");
                int totalSaidas = rs.getInt("total_saidas");
                int estoqueAtual = totalEntradas - totalSaidas;
                Date EntradaSql = rs.getDate("data_entrada");

                item.setTipo(rs.getString("tipo_uniforme"));
                item.setTamanho(rs.getString("tamanho_uniforme"));
                item.setEntrada(totalEntradas);
                item.setSaida(totalSaidas);

                if (estoqueAtual > 0) {
                    item.setStatus("Disponível");
                } else {
                    item.setStatus("Indisponível");
                }
                
                if (EntradaSql != null) {
                    item.setData_entrada(EntradaSql.toLocalDate());
                }

                relatorio.add(item);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório de estoque: " + e.getMessage());
            e.printStackTrace();
        }
        
        return relatorio;
    }
    
}