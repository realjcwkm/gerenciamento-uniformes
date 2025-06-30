/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gerenciamento.uniformes;
import com.mycompany.gerenciamento.uniformes.View.ViewsSistema;

/**
 *
 * @author barba
 */
public class GerenciamentoUniformes {
        
    public static void main(String[] args) {
        System.out.println("Método main iniciado!");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ViewsSistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewsSistema().setVisible(true); 
            }
        });
    }
}
