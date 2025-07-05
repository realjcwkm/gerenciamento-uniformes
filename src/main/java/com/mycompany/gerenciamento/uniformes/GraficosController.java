/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes;

import com.mycompany.gerenciamento.uniformes.DAO.EntregaDAO; // Importe seu DAO
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author 70094534209
 */
public class GraficosController {

    private final EntregaDAO entregaDAO;

    public GraficosController() {
        this.entregaDAO = new EntregaDAO();
    }

    public JFreeChart criarGraficoPizzaPorTipo() {
        // dados do DAO
        Map<String, Integer> dados = this.entregaDAO.getContagemEntregaPorTipo();

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : dados.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        // Fábrica de gráficos (ChartFactory)
        JFreeChart graficoPizza = ChartFactory.createPieChart(
            "Entregas por Tipo de Uniforme", // Título do gráfico
            dataset, // Conjunto de dados
            true, // Exibir legenda
            true, // Gerar tooltips
            false // Gerar URLs? Não
        );
        
        return graficoPizza;
    }
}
