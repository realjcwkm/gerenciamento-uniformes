/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.View.Utils;

/**
 *
 * @author 70094534209
 */
@FunctionalInterface
public interface ColumnFormatter {
    
    String format(Object value, int row, int col);
}
