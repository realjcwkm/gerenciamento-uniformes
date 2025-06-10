/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author w
 */
public class Conexao {
    private static final String url = "jdbc:mysql://localhost:3306/uniformes";
    private static final String user = "root";
    private static final String password = "banco123";
    
    private static Connection conexao;
    
    public static Connection getConexao() {
        try {
            if (conexao == null) {
                conexao = DriverManager.getConnection(url, user, password);
            }
            return conexao;
        } catch (SQLException error) {
            error.printStackTrace();
            return null;
        }
        
    }
}
