/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DAO;

import com.mycompany.gerenciamento.uniformes.DBConnection.Conexao;
import com.mycompany.gerenciamento.uniformes.Models.EntradaModel;
import com.mycompany.gerenciamento.uniformes.Models.FornecedorModel;
import com.mycompany.gerenciamento.uniformes.Models.TamanhoModel;
import com.mycompany.gerenciamento.uniformes.Models.TipoUniformeModel;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author rober
 */
public class EntradaDAO {
    private final Connection conn;

    public EntradaDAO() {
        this.conn = Conexao.getConexao();
    }
    
    public int getQuantidadeTotal() {
        String sql = "SELECT SUM(quantidade) FROM Entradas";
        int total = 0;
        try (PreparedStatement ps = this.conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void cadastrar(EntradaModel entrada) {
        String sql = "INSERT INTO Entradas (data_entrada, quantidade, fk_fornecedor, fk_uniforme) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(entrada.getData_entrada()));
            ps.setInt(2, entrada.getQuantidade());
            ps.setInt(3, entrada.getFornecedor().getId()); 
            ps.setInt(4, entrada.getUniforme().getId());     

            ps.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar entrada: " + e.getMessage());
            e.printStackTrace();
        }
    }
        
      
    public List<EntradaModel> listarTodos() {
        List<EntradaModel> listaDeEntradas = new ArrayList<>();
        String sql = "SELECT " +
            "e.id AS entrada_id, e.data_entrada, e.quantidade, " +
            "f.id AS fornecedor_id, f.nome AS fornecedor_nome, " +
            "u.id AS uniforme_id, " +
            "tu.nome AS tipo_uniforme_nome, " +
            "t.nome AS tamanho_nome " +
            "FROM " +
            "Entradas e " +
            "LEFT JOIN Fornecedor f ON e.fk_fornecedor = f.id " +
            "LEFT JOIN Uniforme u ON e.fk_uniforme = u.id " +
            "LEFT JOIN TipoUniforme tu ON u.fk_tipo_uniforme = tu.id " +
            "LEFT JOIN Tamanho t ON u.fk_tamanho = t.id " +
            "ORDER BY e.data_entrada DESC";

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) { 
                    EntradaModel entrada = new EntradaModel();
                    FornecedorModel fornecedor = new FornecedorModel();
                    UniformeModel uniforme = new UniformeModel();
                    TipoUniformeModel tipo = new TipoUniformeModel();
                    TamanhoModel tamanho = new TamanhoModel();

                    fornecedor.setId(rs.getInt("fornecedor_id"));
                    fornecedor.setNome(rs.getString("fornecedor_nome"));

                    tipo.setNome(rs.getString("tipo_uniforme_nome"));
                    tamanho.setNome(rs.getString("tamanho_nome"));

                    uniforme.setId(rs.getInt("uniforme_id"));
                    uniforme.setTipoUniforme(tipo);
                    uniforme.setTamanho(tamanho);

                    entrada.setId(rs.getInt("entrada_id"));
                    entrada.setData_entrada(rs.getDate("data_entrada").toLocalDate());
                    entrada.setQuantidade(rs.getInt("quantidade"));
                    entrada.setFornecedor(fornecedor);
                    entrada.setUniforme(uniforme);

                    listaDeEntradas.add(entrada);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao listar entradas: " + e.getMessage());
                e.printStackTrace();
            }
            return listaDeEntradas;
        }

    public boolean registrarEntrada(TipoUniformeModel tipo, TamanhoModel tamanho, FornecedorModel fornecedor, int quantidade, LocalDate dataEntrada) { 
        try {
            UniformeDAO uniformeDAO = new UniformeDAO();
            UniformeModel uniforme = uniformeDAO.buscarPorTipoETamanho(tipo.getId(), tamanho.getId());
            if (uniforme == null) {
                System.err.println("Este tipo/tamanho de uniforme não está cadastrado.");
                return false;
            }
            
            EntradaModel novaEntrada = new EntradaModel();
            novaEntrada.setUniforme(uniforme);
            novaEntrada.setQuantidade(quantidade);
            novaEntrada.setFornecedor(fornecedor);
            novaEntrada.setData_entrada(dataEntrada);

 
            this.cadastrar(novaEntrada);

            return true; 

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
