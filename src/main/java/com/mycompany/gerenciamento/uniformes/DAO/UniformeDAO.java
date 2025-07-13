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
    private Object EntregaSql;
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
    
 
    public List<UniformeEstoqueModel> TabelaEstoque() {
        List<UniformeEstoqueModel> relatorio = new ArrayList<>();
        
        // Query ajustada para
        String sql = """
            SELECT tu.nome AS Tipo,
            CASE
            	WHEN (COALESCE(SUM(entradas.quantidade), 0) - COALESCE(SUM(entrega.quantidade), 0)) > 0
            	THEN CONCAT('Disponível (', (COALESCE(SUM(entradas.quantidade), 0) - COALESCE(SUM(entrega.quantidade), 0)), ')')
            	ELSE 'Indisponível'
            END AS Status,
            entradas.quantidade AS Entradas,
            COALESCE(SUM(entrega.quantidade), 0) AS Saida,
            t.nome AS Tamanho,
            MAX(entradas.data_entrada) AS DataEntrada
            FROM Uniforme u
            JOIN TipoUniforme tu ON u.fk_tipo_uniforme = tu.id
            JOIN Tamanho t ON u.fk_tamanho = t.id
            LEFT JOIN Entradas entradas ON u.id = entradas.fk_uniforme
            LEFT JOIN Entrega entrega ON u.id = entrega.fk_uniforme
            GROUP BY u.id, tu.nome, t.nome
            ORDER BY tu.nome, t.nome;
                     
            """;


        try (Statement stmt = this.conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UniformeEstoqueModel item = new UniformeEstoqueModel();

                item.setTipo(rs.getString("Tipo"));
                item.setStatus(rs.getString("Status"));
                item.setTotalEntrada(rs.getInt("Entradas"));
                item.setTotalSaida(rs.getInt("Saida"));
                item.setTamanho(rs.getString("Tamanho"));
                
                Date dataEntradaSql = rs.getDate("DataEntrada");
                if (dataEntradaSql != null) {
                    item.setDataUltimaEntrada(dataEntradaSql.toLocalDate());
                }

                relatorio.add(item);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório de estoque: " + e.getMessage());
            e.printStackTrace();
        }
        
        return relatorio;
    }
    
        
        public void editarEntrada(UniformeModel uniformes){
        String sql = "UPDATE Uniforme SET fk_tipo_uniforme = ?, fk_tamanho = ?, quantidade = ? WHERE id = ?";

        try (PreparedStatement ps = this.conn.prepareStatement(sql)) {

            ps.setInt(1, uniformes.getFk_tipo_uniforme()); 
            ps.setInt(2, uniformes.getFk_tamanho());  
            ps.setInt(3, uniformes.getQuantidade());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar entrada no banco de dados: " + e.getMessage(), e);
        }
    }
}