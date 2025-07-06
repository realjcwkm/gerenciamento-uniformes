/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.EntregaDAO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

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
            "Saída de Uniformes", // Título do gráfico
            dataset, // Conjunto de dados
            true, // Exibir legenda
            true, // Gerar tooltips
            false // Gerar URLs
        );
        
        customizarGrafico(graficoPizza);   
        return graficoPizza;
    }
    
    private void customizarGrafico(JFreeChart grafico) {
        PiePlot plot = (PiePlot) grafico.getPlot();
        
        plot.setLabelGenerator(null);  // Remove as etiquetas        
        plot.setBackgroundPaint(null); // Fundo transparente
        plot.setShadowPaint(null);     // Remove a sombra
        plot.setOutlineVisible(false); // Remove a borda
        
        
        LegendTitle legenda = grafico.getLegend();
        if (legenda != null) {
            legenda.setPosition(RectangleEdge.RIGHT);
            
            legenda.setItemFont(new Font("SansSerif", Font.BOLD, 12));
            legenda.setMargin(0, 20, 0, 0);
        }
        
        Shape itemQuadrado = new Rectangle(20, 20); 
        plot.setLegendItemShape(itemQuadrado);
        
        // Cores do gráfico pizza
        Color[] verdes = new Color[] {
            new Color(102, 255, 102),
            new Color(51, 204, 51),
            new Color(25, 102, 25),
            new Color(60, 179, 113),
            new Color(144, 238, 144),
            new Color(34, 139, 34)
        };

        // Aplica as cores
        PieDataset dataset = plot.getDataset();
        List<Comparable> chaves = dataset.getKeys();
        for (int i = 0; i < chaves.size(); i++) {
            plot.setSectionPaint(chaves.get(i), verdes[i % verdes.length]);
        }
    }
}
