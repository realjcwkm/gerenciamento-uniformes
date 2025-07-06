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

    /*Cadastra uma nova entrada de uniforme no banco de dados.*/

    public void cadastrarEntrada(EntradaModel entrada) {
        String sql = "INSERT INTO Entrada (data_entrada, quantidade, fk_fornecedor, fk_uniforme) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(entrada.getData_entrada()));
            ps.setInt(2, entrada.getQuantidade());
            ps.setInt(3, entrada.getFornecedor().getId()); 
            ps.setInt(4, entrada.getUniforme().getId());     

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Entrada cadastrada com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar entrada: " + e.getMessage());
            e.printStackTrace();
        }
    } 
}
