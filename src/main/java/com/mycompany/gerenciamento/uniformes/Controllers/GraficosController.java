package com.mycompany.gerenciamento.uniformes.Controllers;

import com.mycompany.gerenciamento.uniformes.DAO.EntregaDAO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.CategoryDataset;

public class GraficosController {

    private final EntregaDAO entregaDAO;
    private final Map<String, Color> coresPorTipo;

    public GraficosController() {
        this.entregaDAO = new EntregaDAO();
        this.coresPorTipo = new HashMap<>();

        // Paleta de cores para serem as mesmas nos dois gráficos
        Color[] paletaVerdes = new Color[]{
            new Color(46, 139, 87), 
            new Color(36, 143, 45),
            new Color(60, 179, 113), 
            new Color(47, 106, 54),
            new Color(144, 238, 144), 
            new Color(34, 139, 34)
        };
        
        List<String> todosOsTipos = this.entregaDAO.getTodosOsTiposDeUniforme();
        for (int i = 0; i < todosOsTipos.size(); i++) {
            String tipo = todosOsTipos.get(i);
            Color cor = paletaVerdes[i % paletaVerdes.length];
            this.coresPorTipo.put(tipo, cor);
        }
    }

    // Gráfico Pizza
    public JFreeChart criarGraficoPizzaPorTipo() {
        // dados do DAO
        Map<String, Integer> dados = this.entregaDAO.getContagemEntregaPorTipo();
        
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : dados.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        
        // Fábrica de gráficos (ChartFactory)
        JFreeChart graficoPizza = ChartFactory.createPieChart(
            null,       // Título do gráfico
            dataset,    // Conjunto de dados
            true,       // Exibir legenda
            true,       // Gerar tooltips
            false       // Gerar URLs
        );
        
        customizarGraficoPizza(graficoPizza);
        return graficoPizza;
    }

    // Customizar gráfico pizza
    private void customizarGraficoPizza(JFreeChart grafico) {
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

        PieDataset dataset = plot.getDataset();
        for (Object chaveObj : dataset.getKeys()) {
            String chave = (String) chaveObj;
            Color cor = this.coresPorTipo.get(chave); // Busca a cor
            if (cor != null) {
                plot.setSectionPaint(chave, cor);
            }
        }
    }

    // Gráfico Barra
    public JFreeChart criarGraficoBarrasPorTurma() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();        
        List<Map<String, Object>> dados = this.entregaDAO.getContagemPorTurmaETipo();
        for (Map<String, Object> linha : dados) {
            Number quantidade = (Number) linha.get("quantidade");
            String tipoUniforme = (String) linha.get("tipo_uniforme");
            String turma = (String) linha.get("turma");
            dataset.addValue(quantidade, tipoUniforme, turma);
        }
        
        // Fábrica de gráficos (ChartFactory)
        JFreeChart graficoBarras = ChartFactory.createStackedBarChart(
            null,
            "Turma", // Label do eixo X
            "Quantidade", // Label do eixo Y
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        );
        
        customizarGraficoBarras(graficoBarras);
        return graficoBarras;
    }

    // Customizar gráfico barra
    private void customizarGraficoBarras(JFreeChart grafico) {
        CategoryPlot plot = grafico.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // Layout do gráfico
        plot.setBackgroundPaint(Color.WHITE);
        plot.setInsets(new RectangleInsets(10.0, 20.0, 10.0, 20.0));
        plot.setOutlineVisible(false);
        
        // Estilização das barras
        renderer.setMaximumBarWidth(0.10);

        // Números nas barras
        //renderer.setDefaultItemLabelGenerator(new org.jfree.chart.labels.StandardCategoryItemLabelGenerator());
        //renderer.setDefaultItemLabelsVisible(true);

        // Estilização dos tooltips que aparecem ao passar o mouse
        String formatoTooltip = "<html><b>Turma:</b> {1}<br><b>Uniforme:</b> {0}<br><b>Quantidade:</b> {2}</html>";
        StandardCategoryToolTipGenerator geradorDeTooltip = new StandardCategoryToolTipGenerator(
            formatoTooltip, new DecimalFormat("0")
        );
        renderer.setDefaultToolTipGenerator(geradorDeTooltip);

        LegendTitle legenda = grafico.getLegend();
        if (legenda != null) {
            legenda.setPosition(RectangleEdge.RIGHT);
        }

        CategoryDataset dataset = plot.getDataset();
        if (dataset != null) {
            for (int i = 0; i < dataset.getRowCount(); i++) {
                String tipoUniforme = (String) dataset.getRowKey(i);
                Color cor = this.coresPorTipo.get(tipoUniforme); // Busca a cor
                if (cor != null) {
                    renderer.setSeriesPaint(i, cor);
                }
            }
        }
    }
    
    // Porcentagem por curso do gráfico em barra
    public List<String> getPorcentagensPorCurso() {
        List<String> resultadoFormatado = new ArrayList<>();
        int totalGeral = this.entregaDAO.getQuantidadeTotalGeral();
        Map<String, Integer> totaisPorCurso = this.entregaDAO.getContagemPorCurso();

        System.out.println("Dados de contagem por curso recebidos do DAO: " + totaisPorCurso);
        
        if (totalGeral == 0) {
            resultadoFormatado.add("Nenhuma entrega registrada.");
            return resultadoFormatado;
        }

        DecimalFormat formatador = new DecimalFormat("0.0'%'");

        for (Map.Entry<String, Integer> entry : totaisPorCurso.entrySet()) {
            String nomeCurso = entry.getKey();
            int totalCurso = entry.getValue();

            double porcentagem = (double) totalCurso / totalGeral; 

            String linha = nomeCurso + ": " + formatador.format(porcentagem * 100);
            resultadoFormatado.add(linha);
        }

        return resultadoFormatado;
    }
    
    public int getTotalUniformesDistribuidos() {
       return this.entregaDAO.getQuantidadeTotalGeral();
    }
}