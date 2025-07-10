package com.mycompany.gerenciamento.uniformes.View;

import com.mycompany.gerenciamento.uniformes.Controllers.AuthController;
import com.mycompany.gerenciamento.uniformes.Controllers.EntregaController;
import com.mycompany.gerenciamento.uniformes.Forms.FormEntregaDialog;
import com.mycompany.gerenciamento.uniformes.Models.EntregaModel;
import com.mycompany.gerenciamento.uniformes.Models.ServidorModel;
import com.mycompany.gerenciamento.uniformes.Controllers.ServidorController;
import com.mycompany.gerenciamento.uniformes.TableModels.EntregaTableModel;
import com.mycompany.gerenciamento.uniformes.TableModels.ServidorTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.mycompany.gerenciamento.uniformes.Controllers.GraficosController;
import com.mycompany.gerenciamento.uniformes.Controllers.UniformeController;
import com.mycompany.gerenciamento.uniformes.Models.UniformeEstoqueModel;
import com.mycompany.gerenciamento.uniformes.TableModels.UniformeTableModel;
import com.mycompany.gerenciamento.uniformes.Controllers.TrocaController;
import com.mycompany.gerenciamento.uniformes.Forms.ConfirmacaoTroca;
import com.mycompany.gerenciamento.uniformes.Forms.FormSelecaoUniforme;
import com.mycompany.gerenciamento.uniformes.Forms.FormServidorDialog;
import com.mycompany.gerenciamento.uniformes.Models.UniformeModel;
import com.mycompany.gerenciamento.uniformes.View.Utils.ButtonColumnRendererEditor;
import com.mycompany.gerenciamento.uniformes.View.Utils.CustomCellRenderer;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

/**
 * @author barbara
 */

public class ViewsSistema extends javax.swing.JFrame {
    private final CardLayout mainCardLayout;
    private final CardLayout authCardLayout;
    private final CardLayout appCardLayout;
    private final EntregaTableModel entregaTableModel; 
    private final ServidorTableModel servidorTableModel;
    private final UniformeTableModel uniformeTableModel;
    private final AuthController authController;
    private final EntregaController entregaController;
    private final ServidorController servidorController;
    private final UniformeController uniformeController; 
    private final TrocaController trocaController;
    private GraficosController graficosController;
    private String matriculaUpdate;
    private String termoBuscaAtualServidores = "";
    private String termoBuscaAtualDistribuicao = "";
    
    // === PAGINAÇÃO ENTREGAS ===
    private int paginaAtual = 1;
    private int totalDePaginas = 0;
    
    // === PAGINAÇÃO SERVIDORES === 
    private int paginaAtualServidores = 1;
    private int totalDePaginasServidores = 0;
    
    // === PADRÃO 10 LINHAS POR PÁGINA ===
    private final int ITENS_POR_PAGINA = 10;
    
    public ViewsSistema() {
        initComponents();
<<<<<<< HEAD
        
        // Campo pesquisa servidor
//        btn_buscar_serv.addActionListener(e -> realizarBuscaServidores());
//        tx_pesquisa_serv.addActionListener(e -> realizarBuscaServidores());
        
//        // Campo pesquisa Entrega
//        btn_buscar_dis_pd.addActionListener(e -> realizarBuscaDistribuicao());
//        tx_pesquisa_dis_pd.addActionListener(e -> realizarBuscaDistribuicao());
=======

        // === CAMPO PESQUISA SERVIDOR ===
        btn_buscar_serv.addActionListener(e -> realizarBuscaServidores());
        tx_pesquisa_serv.addActionListener(e -> realizarBuscaServidores());
        
        // === CAMPO PESQUISA ENTREGA ===
        btn_buscar_dis_pd.addActionListener(e -> realizarBuscaDistribuicao());
        tx_pesquisa_dis_pd.addActionListener(e -> realizarBuscaDistribuicao());
>>>>>>> develop
        

        carregarGraficoPizza(); // === GRÁFICO PIZZA ===
        carregarGraficoBarras(); // === GRÁFICO BARRAS ===
        
        this.appCardLayout = (CardLayout) panel_telaInicial.getLayout();
        this.mainCardLayout = (CardLayout) main_container.getLayout();
        this.authCardLayout = (CardLayout) panel_autenticacao.getLayout();
        
        this.authController = new AuthController();
        this.entregaController = new EntregaController();
        this.servidorController = new ServidorController();
        this.uniformeController = new UniformeController();
        this.trocaController = new TrocaController();
                
        this.entregaTableModel = new EntregaTableModel(new ArrayList<>()); 
        this.servidorTableModel = new ServidorTableModel(new ArrayList<>());
        this.uniformeTableModel = new UniformeTableModel();
        
        // === INICIO ADICIONA TROCA ICON NA TABELA ===
        this.tb_distribuicao.setModel(entregaTableModel);
        this.tb_distribuicao.setDefaultRenderer(Object.class, new CustomCellRenderer());
        tb_distribuicao.setFillsViewportHeight(true);         
        
        final int TROCA_COLUMN_INDEX = 8; 
        
        ImageIcon trocaIcon = null;
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/troca-icon.png"));

            Image image = originalIcon.getImage();

            Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);

            trocaIcon = new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Erro ao carregar ou redimensionar o ícone de troca: " + e.getMessage());
        }

        ButtonColumnRendererEditor trocaButtonEditor = new ButtonColumnRendererEditor(this.tb_distribuicao, trocaIcon);

        trocaButtonEditor.getButton().addActionListener(e -> {
            if (tb_distribuicao.isEditing()) {
                tb_distribuicao.getCellEditor().stopCellEditing();
            }
            int modelRow = tb_distribuicao.convertRowIndexToModel(tb_distribuicao.getSelectedRow());
            if (modelRow == -1) return; 

            EntregaModel entregaAntiga = entregaTableModel.getEntregaAt(modelRow);

            if (entregaAntiga.isTrocado()) {
                JOptionPane.showMessageDialog(this, "Esta entrega já foi trocada.");
                return;
            }

            iniciaFluxoDeTroca(entregaAntiga);
        });

        this.tb_distribuicao.getColumnModel().getColumn(TROCA_COLUMN_INDEX).setCellRenderer(trocaButtonEditor);
        this.tb_distribuicao.getColumnModel().getColumn(TROCA_COLUMN_INDEX).setCellEditor(trocaButtonEditor);

        this.tb_distribuicao.getColumnModel().getColumn(TROCA_COLUMN_INDEX).setPreferredWidth(40);
        this.tb_distribuicao.getColumnModel().getColumn(TROCA_COLUMN_INDEX).setMaxWidth(40);        
        // === FIM ADICIONA TROCA ICON NA TABELA ===
        
        this.tabela_uniformes.setModel(uniformeTableModel);
        
        // === INICIO ADICIONA EDIT ICON NA TABELA ===
        this.tb_servidores.setModel(servidorTableModel);
        this.tb_servidores.setDefaultRenderer(Object.class, new CustomCellRenderer());
        tb_servidores.setFillsViewportHeight(true); 
        
        final int EDIT_COLUMN_INDEX = 5;
        
        ImageIcon editIcon = null;
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/edit-icon.png"));

            Image image = originalIcon.getImage();

            Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);

            editIcon = new ImageIcon(scaledImage);

        } catch (Exception e) {
            System.err.println("Erro ao carregar ou redimensionar o ícone de troca: " + e.getMessage());
        }
        
        ButtonColumnRendererEditor editButtonEditor = new ButtonColumnRendererEditor(this.tb_servidores, editIcon);

        editButtonEditor.getButton().addActionListener(e -> {
            if (tb_servidores.isEditing()) {
                tb_servidores.getCellEditor().stopCellEditing();
            }
            int modelRow = tb_servidores.convertRowIndexToModel(tb_servidores.getSelectedRow());
            if (modelRow == -1) return; 
        });

        this.tb_servidores.getColumnModel().getColumn(EDIT_COLUMN_INDEX).setCellRenderer(editButtonEditor);
        this.tb_servidores.getColumnModel().getColumn(EDIT_COLUMN_INDEX).setCellEditor(editButtonEditor);

        this.tb_servidores.getColumnModel().getColumn(EDIT_COLUMN_INDEX).setPreferredWidth(40);
        this.tb_servidores.getColumnModel().getColumn(EDIT_COLUMN_INDEX).setMaxWidth(40);        
        // === FIM ADICIONA EDIT ICON NA TABELA ===
        
        
        System.out.println("Painéis disponíveis:");
        for (Component comp : panel_telaInicial.getComponents()) {
            System.out.println("- " + comp.getName() + " (" + comp.getClass().getSimpleName() + ")");
        }
        
        mainCardLayout.show(main_container, "card_autenticacao");
        authCardLayout.show(panel_autenticacao, "card_login");
    }
        
    private void carregaDadosUniformes() {
    try {
        // Chama o Controller, que por sua vez chama o DAO
        List<UniformeEstoqueModel> listaUniformes = this.uniformeController.TabelaEstoque();
        
        // Passa a lista de dados para o TableModel, que irá atualizar a JTable
        uniformeTableModel.setUniformes(listaUniformes); // Use o método que você criou no seu TableModel
        
    } catch (Exception error) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar os dados de uniformes.", "Erro", JOptionPane.ERROR_MESSAGE);
        error.printStackTrace();
    }
}

    // === CARREGA GRÁFICO PIZZA === 
    private void carregarGraficoPizza() {
        this.graficosController = new GraficosController();
        JFreeChart graficoPizza = graficosController.criarGraficoPizzaPorTipo();
        int totalDeUniformes = graficosController.getTotalUniformesDistribuidos();

        panelGraficoPizza.removeAll(); 
        panelGraficoPizza.setLayout(new BoxLayout(panelGraficoPizza, BoxLayout.Y_AXIS));
       
        JLabel lb_titulo_inicio = new JLabel("Saída de Uniformes");
        lb_titulo_inicio.setFont(new Font("SansSerif", Font.BOLD, 14));
        lb_titulo_inicio.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lb_total_inicio = new JLabel(String.valueOf(totalDeUniformes));
        lb_total_inicio.setFont(new Font("SansSerif", Font.BOLD, 18));
        lb_total_inicio.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lb_descricao_inicio = new JLabel("Total de uniformes distribuídos");
        lb_descricao_inicio.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lb_descricao_inicio.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator separador_inicio = new JSeparator();

        ChartPanel chartPanel = new ChartPanel(graficoPizza);
        chartPanel.setOpaque(false);
        chartPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelGraficoPizza.add(Box.createRigidArea(new Dimension(10, 10)));
        panelGraficoPizza.add(lb_titulo_inicio);
        panelGraficoPizza.add(Box.createRigidArea(new Dimension(0, 5)));
        panelGraficoPizza.add(lb_total_inicio);
        panelGraficoPizza.add(Box.createRigidArea(new Dimension(0, 15)));
        panelGraficoPizza.add(lb_descricao_inicio);
        panelGraficoPizza.add(Box.createRigidArea(new Dimension(0, 5)));
        panelGraficoPizza.add(separador_inicio);
        panelGraficoPizza.add(Box.createRigidArea(new Dimension(0, 15)));
        panelGraficoPizza.add(chartPanel);
        panelGraficoPizza.add(Box.createVerticalGlue());

        panelGraficoPizza.revalidate();
        panelGraficoPizza.repaint();
    }
    
    // === CARREGA GRÁFICO BARRA ===
    private void carregarGraficoBarras() {
        JFreeChart graficoBarras = graficosController.criarGraficoBarrasPorTurma();
        List<String> porcentagens = graficosController.getPorcentagensPorCurso();

        // Panel geral
        panelGraficoBarras.removeAll();
        panelGraficoBarras.setLayout(new BoxLayout(panelGraficoBarras, BoxLayout.Y_AXIS));
        panelGraficoBarras.setBackground(Color.WHITE);
        panelGraficoBarras.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Panel títulos
        JPanel panelTitulos = new JPanel(new BorderLayout());
        panelTitulos.setOpaque(false);

        JLabel lblTitulo = new JLabel("Saída de Uniformes");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        panelTitulos.add(lblTitulo, BorderLayout.NORTH);

        JLabel lblDescricao = new JLabel("Total de uniformes distribuídos por turma");
        lblDescricao.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblDescricao.setForeground(Color.GRAY);
        panelTitulos.add(lblDescricao, BorderLayout.CENTER);

        // Panel porcentagens
        JPanel panelContainerPorcentagens = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelContainerPorcentagens.setOpaque(false);

        for (String linhaPorcentagem : porcentagens) {
            JPanel cardPanel = new JPanel();
            cardPanel.setBackground(Color.WHITE);
            cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));

            JLabel lblLinha = new JLabel(linhaPorcentagem);
            lblLinha.setFont(new Font("SansSerif", Font.BOLD, 12));
            cardPanel.add(lblLinha);

            panelContainerPorcentagens.add(cardPanel);
        }

        // Panel gráfico
        ChartPanel chartPanel = new ChartPanel(graficoBarras);
        chartPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adiciona ao panel geral
        panelGraficoBarras.add(panelTitulos);
        panelGraficoBarras.add(Box.createRigidArea(new Dimension(0, 15)));

        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        panelGraficoBarras.add(separador);

        panelGraficoBarras.add(Box.createRigidArea(new Dimension(0, 15)));
        panelGraficoBarras.add(panelContainerPorcentagens);
        panelGraficoBarras.add(Box.createRigidArea(new Dimension(0, 15)));
        panelGraficoBarras.add(chartPanel);

        panelGraficoBarras.revalidate();
        panelGraficoBarras.repaint();
    }
    
    // === FLUXO DE TROCA ===
    private void iniciaFluxoDeTroca(EntregaModel entregaAntiga) {
        FormSelecaoUniforme selecaoDialog = new FormSelecaoUniforme(this);
        selecaoDialog.setVisible(true);
    
        UniformeModel uniformeNovo = selecaoDialog.getUniformeSelecionado();

        if (uniformeNovo != null) {
            ConfirmacaoTroca confirmacaoDialog = new ConfirmacaoTroca(this, entregaAntiga, uniformeNovo);
            confirmacaoDialog.setVisible(true);
            
            if (confirmacaoDialog.isConfirmado()) {
                boolean sucesso = trocaController.realizarTroca(entregaAntiga, uniformeNovo);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Troca de Uniforme realizada com sucesso!");
                    atualizarTabelaEControles();
                } else {
                    JOptionPane.showMessageDialog(this, "Ocorreu um erro ao realizar a troca.", "Erro de Transação", JOptionPane.ERROR_MESSAGE);
                }
            }
        } 
    }
    
    // === APLICA PAGINAÇÃO ENTREGAS ===
    private void atualizarTabelaEControles() {
        List<EntregaModel> listaPaginada = entregaController.listarPagina(paginaAtual, ITENS_POR_PAGINA, termoBuscaAtualDistribuicao);
        
        entregaTableModel.setEntregas(listaPaginada);
        
        lb_status_paginacao_pd.setText("Página " + paginaAtual + " de " + totalDePaginas);
        
        btn_anterior_pd.setEnabled(paginaAtual > 1);
        btn_proximo_pd.setEnabled(paginaAtual < totalDePaginas);
    }
    
    // === APLICA PAGINAÇÃO SERVIDORES ===
    private void atualizarTabelaServidoresEControles() {
        List<ServidorModel> listaPaginada = servidorController.listarPagina(paginaAtualServidores, ITENS_POR_PAGINA, termoBuscaAtualServidores);
        servidorTableModel.setServidores(listaPaginada);

        lb_status_paginacao_serv.setText("Página " + paginaAtualServidores + " de " + totalDePaginasServidores);

        btn_anterior_serv.setEnabled(paginaAtualServidores > 1);
        btn_proximo_serv.setEnabled(paginaAtualServidores < totalDePaginasServidores);
    }
    
    // === BUSCA SERVIDORES ===
    private void realizarBuscaServidores() {
        termoBuscaAtualServidores = tx_pesquisa_serv.getText(); // Texto do campo de pesquisa

        paginaAtualServidores = 1;
        totalDePaginasServidores = servidorController.getTotalDePaginas(ITENS_POR_PAGINA, termoBuscaAtualServidores);

        atualizarTabelaServidoresEControles();
    }
    
    // === BUSCA DISTRIBUIÇÃO ===
    private void realizarBuscaDistribuicao() {
        termoBuscaAtualDistribuicao = tx_pesquisa_dis_pd.getText();
        
        paginaAtual = 1;
        totalDePaginas = entregaController.getTotalDeEntregas(ITENS_POR_PAGINA, termoBuscaAtualDistribuicao);
        
        atualizarTabelaEControles();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog = new javax.swing.JDialog();
        main_container = new javax.swing.JPanel();
        panel_aplicacao = new javax.swing.JPanel();
        panel_navbar = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(80, 80), new java.awt.Dimension(25, 800));
        jLabel7 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(80, 80), new java.awt.Dimension(100, 800));
        nome_sistema = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(80, 80), new java.awt.Dimension(450, 800));
        btn_nav_Inicio = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(80, 80), new java.awt.Dimension(20, 800));
        btn_nav_distribuicao = new javax.swing.JButton();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(80, 80), new java.awt.Dimension(10, 800));
        btn_nav_alunos = new javax.swing.JButton();
        btn_nav_servidores = new javax.swing.JButton();
        btn_nav_uniformes = new javax.swing.JButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(80, 80), new java.awt.Dimension(20, 800));
        btn_sair_pn = new javax.swing.JButton();
        panel_telaInicial = new javax.swing.JPanel();
        Inicio = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelGraficoPizza = new javax.swing.JPanel();
        panelGraficoBarras = new javax.swing.JPanel();
        panel_distribuicao = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_distribuicao = new javax.swing.JTable();
        btn_cad_distribuicao_pd = new javax.swing.JButton();
        lb_titulo_pd = new javax.swing.JLabel();
        lb_subtitulo_pd = new javax.swing.JLabel();
        panel1 = new java.awt.Panel();
        lb_status_paginacao_pd = new javax.swing.JLabel();
        btn_proximo_pd = new javax.swing.JButton();
        btn_anterior_pd = new javax.swing.JButton();
        tx_pesquisa_dis_pd = new javax.swing.JTextField();
        btn_buscar_dis_pd = new javax.swing.JButton();
        Alunos = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Servidores = new javax.swing.JPanel();
        lb_titulo_serv = new javax.swing.JLabel();
        lb_sub_serv = new javax.swing.JLabel();
        btn_cadastrar_serv = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_servidores = new javax.swing.JTable();
        panel2 = new java.awt.Panel();
        lb_status_paginacao_serv = new javax.swing.JLabel();
        btn_proximo_serv = new javax.swing.JButton();
        btn_anterior_serv = new javax.swing.JButton();
        tx_pesquisa_serv = new javax.swing.JTextField();
        btn_buscar_serv = new javax.swing.JButton();
        Uniformes = new javax.swing.JPanel();
        Titulo = new javax.swing.JLabel();
        subtitulo = new javax.swing.JLabel();
        tx_pesquisa = new javax.swing.JTextField();
        btn_buscar = new javax.swing.JButton();
        jcb_filtros = new javax.swing.JComboBox<>();
        btn_Add_Uniforme = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabela_uniformes = new javax.swing.JTable();
        btn_editar = new javax.swing.JButton();
        panel_autenticacao = new javax.swing.JPanel();
        panel_login = new javax.swing.JPanel();
        img_pl = new javax.swing.JLabel();
        card_form_pl = new com.mycompany.gerenciamento.uniformes.Components.CardPanel();
        lb_login_pl = new javax.swing.JLabel();
        lb_matricula_pl = new javax.swing.JLabel();
        input_matricula_pl = new javax.swing.JTextField();
        lb_senha_pl = new javax.swing.JLabel();
        input_senha_pl = new javax.swing.JPasswordField();
        separador_pl = new javax.swing.JSeparator();
        btn_login_pl = new javax.swing.JButton();
        btn_esq_senha_pl = new javax.swing.JButton();
        panel_primeiro_acesso = new javax.swing.JPanel();
        img_ppa = new javax.swing.JLabel();
        card_form_ppa = new com.mycompany.gerenciamento.uniformes.Components.CardPanel();
        lb_redefinir_senha_ppa = new javax.swing.JLabel();
        lb_senha_ppa = new javax.swing.JLabel();
        input_senha_ppa = new javax.swing.JPasswordField();
        lb_confirmar_senha_ppa = new javax.swing.JLabel();
        input_confirmar_senha_ppa = new javax.swing.JPasswordField();
        separador_ppa = new javax.swing.JSeparator();
        btn_salvar_ppa = new javax.swing.JButton();
        btn_voltar_ppa = new javax.swing.JButton();
        panel_solicitacao_codigo = new javax.swing.JPanel();
        img_psc = new javax.swing.JLabel();
        card_form_psc = new com.mycompany.gerenciamento.uniformes.Components.CardPanel();
        lb_solicitar_codigo_psc = new javax.swing.JLabel();
        lb_matricula_psc = new javax.swing.JLabel();
        input_matricula_psc = new javax.swing.JTextField();
        lb_email_psc = new javax.swing.JLabel();
        input_email_psc = new javax.swing.JTextField();
        separador_psc = new javax.swing.JSeparator();
        btn_enviar_psc = new javax.swing.JButton();
        btn_voltar_psc = new javax.swing.JButton();
        panel_redefinir_senha = new javax.swing.JPanel();
        img_prs = new javax.swing.JLabel();
        card_form_prs = new com.mycompany.gerenciamento.uniformes.Components.CardPanel();
        lb_redefinir_senha_prs = new javax.swing.JLabel();
        lb_codigo_prs = new javax.swing.JLabel();
        input_codigo_prs = new javax.swing.JTextField();
        lb_senha_prs = new javax.swing.JLabel();
        input_senha_prs = new javax.swing.JPasswordField();
        lb_confirmar_senha_prs = new javax.swing.JLabel();
        input_confirmar_senha_prs = new javax.swing.JPasswordField();
        separador_prs = new javax.swing.JSeparator();
        btn_salvar_prs = new javax.swing.JButton();
        btn_voltar_prs = new javax.swing.JButton();

        javax.swing.GroupLayout jDialogLayout = new javax.swing.GroupLayout(jDialog.getContentPane());
        jDialog.getContentPane().setLayout(jDialogLayout);
        jDialogLayout.setHorizontalGroup(
            jDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialogLayout.setVerticalGroup(
            jDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GERENCIAMENTO DE UNIFORMES ACADÊMICOS");
        setMaximumSize(new java.awt.Dimension(1360, 760));
        setMinimumSize(new java.awt.Dimension(1360, 760));
        setName("FrameServidores"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1360, 760));
        setSize(new java.awt.Dimension(1360, 760));

        main_container.setMaximumSize(new java.awt.Dimension(1360, 760));
        main_container.setMinimumSize(new java.awt.Dimension(1360, 760));
        main_container.setPreferredSize(new java.awt.Dimension(1360, 760));
        main_container.setLayout(new java.awt.CardLayout());

        panel_aplicacao.setMaximumSize(new java.awt.Dimension(1360, 760));
        panel_aplicacao.setMinimumSize(new java.awt.Dimension(1360, 760));
        panel_aplicacao.setPreferredSize(new java.awt.Dimension(1360, 760));
        panel_aplicacao.setLayout(new java.awt.BorderLayout());

        panel_navbar.setBackground(new java.awt.Color(35, 91, 88));
        panel_navbar.setMaximumSize(new java.awt.Dimension(1360, 80));
        panel_navbar.setMinimumSize(new java.awt.Dimension(1360, 80));
        panel_navbar.setPreferredSize(new java.awt.Dimension(1360, 80));
        panel_navbar.setLayout(new javax.swing.BoxLayout(panel_navbar, javax.swing.BoxLayout.LINE_AXIS));
        panel_navbar.add(filler4);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-IFRO-PNG-branco.png"))); // NOI18N
        panel_navbar.add(jLabel7);
        panel_navbar.add(filler1);

        nome_sistema.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nome_sistema.setForeground(new java.awt.Color(255, 255, 255));
        nome_sistema.setText("GERENCIAMENTO DE UNIFORMES ACADÊMICOS");
        panel_navbar.add(nome_sistema);
        panel_navbar.add(filler3);

        btn_nav_Inicio.setBackground(new java.awt.Color(35, 91, 88));
        btn_nav_Inicio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_nav_Inicio.setForeground(new java.awt.Color(255, 255, 255));
        btn_nav_Inicio.setText("Inicio");
        btn_nav_Inicio.setBorder(null);
        btn_nav_Inicio.setBorderPainted(false);
        btn_nav_Inicio.setContentAreaFilled(false);
        btn_nav_Inicio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_nav_Inicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nav_InicioActionPerformed(evt);
            }
        });
        panel_navbar.add(btn_nav_Inicio);
        panel_navbar.add(filler6);

        btn_nav_distribuicao.setBackground(new java.awt.Color(35, 91, 88));
        btn_nav_distribuicao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_nav_distribuicao.setForeground(new java.awt.Color(255, 255, 255));
        btn_nav_distribuicao.setText("Distribuição");
        btn_nav_distribuicao.setBorder(null);
        btn_nav_distribuicao.setBorderPainted(false);
        btn_nav_distribuicao.setContentAreaFilled(false);
        btn_nav_distribuicao.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_nav_distribuicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nav_distribuicaoActionPerformed(evt);
            }
        });
        panel_navbar.add(btn_nav_distribuicao);
        panel_navbar.add(filler7);

        btn_nav_alunos.setBackground(new java.awt.Color(35, 91, 88));
        btn_nav_alunos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_nav_alunos.setForeground(new java.awt.Color(255, 255, 255));
        btn_nav_alunos.setText("Alunos");
        btn_nav_alunos.setBorderPainted(false);
        btn_nav_alunos.setContentAreaFilled(false);
        btn_nav_alunos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_nav_alunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nav_alunosActionPerformed(evt);
            }
        });
        panel_navbar.add(btn_nav_alunos);

        btn_nav_servidores.setBackground(new java.awt.Color(35, 91, 88));
        btn_nav_servidores.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_nav_servidores.setForeground(new java.awt.Color(255, 255, 255));
        btn_nav_servidores.setText("Servidores");
        btn_nav_servidores.setBorderPainted(false);
        btn_nav_servidores.setContentAreaFilled(false);
        btn_nav_servidores.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_nav_servidores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nav_servidoresActionPerformed(evt);
            }
        });
        panel_navbar.add(btn_nav_servidores);

        btn_nav_uniformes.setBackground(new java.awt.Color(35, 91, 88));
        btn_nav_uniformes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_nav_uniformes.setForeground(new java.awt.Color(255, 255, 255));
        btn_nav_uniformes.setText("Uniformes");
        btn_nav_uniformes.setBorderPainted(false);
        btn_nav_uniformes.setContentAreaFilled(false);
        btn_nav_uniformes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_nav_uniformes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nav_uniformesActionPerformed(evt);
            }
        });
        panel_navbar.add(btn_nav_uniformes);
        panel_navbar.add(filler5);

        btn_sair_pn.setBackground(new java.awt.Color(0, 164, 55));
        btn_sair_pn.setForeground(new java.awt.Color(255, 255, 255));
        btn_sair_pn.setText("SAIR");
        btn_sair_pn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sair_pnActionPerformed(evt);
            }
        });
        panel_navbar.add(btn_sair_pn);

        panel_aplicacao.add(panel_navbar, java.awt.BorderLayout.NORTH);

        panel_telaInicial.setBackground(new java.awt.Color(204, 255, 102));
        panel_telaInicial.setMaximumSize(new java.awt.Dimension(1360, 680));
        panel_telaInicial.setMinimumSize(new java.awt.Dimension(1360, 680));
        panel_telaInicial.setPreferredSize(new java.awt.Dimension(1360, 680));
        panel_telaInicial.setLayout(new java.awt.CardLayout());

        Inicio.setBackground(new java.awt.Color(255, 255, 255));
        Inicio.setMaximumSize(new java.awt.Dimension(1360, 680));
        Inicio.setMinimumSize(new java.awt.Dimension(1360, 680));
        Inicio.setPreferredSize(new java.awt.Dimension(1360, 680));

        jLabel1.setText("Inicio");

        panelGraficoPizza.setBackground(new java.awt.Color(255, 255, 255));
        panelGraficoPizza.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 51), 1, true));
        panelGraficoPizza.setForeground(new java.awt.Color(255, 255, 255));
        panelGraficoPizza.setMaximumSize(new java.awt.Dimension(450, 450));
        panelGraficoPizza.setMinimumSize(new java.awt.Dimension(450, 450));
        panelGraficoPizza.setPreferredSize(new java.awt.Dimension(450, 450));

        javax.swing.GroupLayout panelGraficoPizzaLayout = new javax.swing.GroupLayout(panelGraficoPizza);
        panelGraficoPizza.setLayout(panelGraficoPizzaLayout);
        panelGraficoPizzaLayout.setHorizontalGroup(
            panelGraficoPizzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );
        panelGraficoPizzaLayout.setVerticalGroup(
            panelGraficoPizzaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );

        panelGraficoBarras.setBackground(new java.awt.Color(255, 255, 255));
        panelGraficoBarras.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        panelGraficoBarras.setMaximumSize(new java.awt.Dimension(600, 450));
        panelGraficoBarras.setMinimumSize(new java.awt.Dimension(600, 450));
        panelGraficoBarras.setPreferredSize(new java.awt.Dimension(600, 450));

        javax.swing.GroupLayout panelGraficoBarrasLayout = new javax.swing.GroupLayout(panelGraficoBarras);
        panelGraficoBarras.setLayout(panelGraficoBarrasLayout);
        panelGraficoBarrasLayout.setHorizontalGroup(
            panelGraficoBarrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 618, Short.MAX_VALUE)
        );
        panelGraficoBarrasLayout.setVerticalGroup(
            panelGraficoBarrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout InicioLayout = new javax.swing.GroupLayout(Inicio);
        Inicio.setLayout(InicioLayout);
        InicioLayout.setHorizontalGroup(
            InicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InicioLayout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(panelGraficoPizza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(panelGraficoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1215, 1215, 1215)
                .addComponent(jLabel1)
                .addGap(0, 828, Short.MAX_VALUE))
        );
        InicioLayout.setVerticalGroup(
            InicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InicioLayout.createSequentialGroup()
                .addGap(0, 332, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(0, 332, Short.MAX_VALUE))
            .addGroup(InicioLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(InicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelGraficoBarras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelGraficoPizza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(180, Short.MAX_VALUE))
        );

        panel_telaInicial.add(Inicio, "inicio");

        panel_distribuicao.setBackground(new java.awt.Color(255, 255, 255));
        panel_distribuicao.setMaximumSize(new java.awt.Dimension(1360, 680));
        panel_distribuicao.setMinimumSize(new java.awt.Dimension(1360, 680));
        panel_distribuicao.setPreferredSize(new java.awt.Dimension(1360, 680));

        tb_distribuicao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nome", "Tamanho", "Uniforme", "Matrícula", "Servidor Responsável", "Quantidade", "Data de Troca", ""
            }
        ));
        tb_distribuicao.setAutoscrolls(false);
        tb_distribuicao.setMaximumSize(new java.awt.Dimension(1360, 0));
        tb_distribuicao.setMinimumSize(new java.awt.Dimension(1360, 0));
        tb_distribuicao.setRowHeight(27);
        tb_distribuicao.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(tb_distribuicao);

        btn_cad_distribuicao_pd.setBackground(new java.awt.Color(4, 120, 87));
        btn_cad_distribuicao_pd.setForeground(new java.awt.Color(255, 255, 255));
        btn_cad_distribuicao_pd.setText("+ Cadastrar Distribuição");
        btn_cad_distribuicao_pd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cad_distribuicao_pdActionPerformed(evt);
            }
        });

        lb_titulo_pd.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        lb_titulo_pd.setText("Controle de Distribuição");

        lb_subtitulo_pd.setForeground(new java.awt.Color(35, 91, 88));
        lb_subtitulo_pd.setText("Tenha controle em tempo real das distribuições de uniformes.");

        lb_status_paginacao_pd.setText("jLabel2");
        lb_status_paginacao_pd.setPreferredSize(new java.awt.Dimension(18, 18));

        btn_proximo_pd.setText("Próxima");
        btn_proximo_pd.setMargin(new java.awt.Insets(8, 14, 8, 14));
        btn_proximo_pd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximo_pdActionPerformed(evt);
            }
        });

        btn_anterior_pd.setText("Anterior");
        btn_anterior_pd.setMargin(new java.awt.Insets(8, 14, 8, 14));
        btn_anterior_pd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anterior_pdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(lb_status_paginacao_pd, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_anterior_pd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_proximo_pd)
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_proximo_pd, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_anterior_pd, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_status_paginacao_pd, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        tx_pesquisa_dis_pd.setText("Busque uma entrega...");
        tx_pesquisa_dis_pd.setPreferredSize(new java.awt.Dimension(130, 30));
        tx_pesquisa_dis_pd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_pesquisa_dis_pdActionPerformed(evt);
            }
        });

        btn_buscar_dis_pd.setFont(new java.awt.Font("Liberation Sans", 1, 12)); // NOI18N
        btn_buscar_dis_pd.setText("Buscar");
        btn_buscar_dis_pd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_dis_pdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_distribuicaoLayout = new javax.swing.GroupLayout(panel_distribuicao);
        panel_distribuicao.setLayout(panel_distribuicaoLayout);
        panel_distribuicaoLayout.setHorizontalGroup(
            panel_distribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_distribuicaoLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(panel_distribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_distribuicaoLayout.createSequentialGroup()
                        .addGroup(panel_distribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tx_pesquisa_dis_pd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb_subtitulo_pd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addComponent(btn_buscar_dis_pd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 568, Short.MAX_VALUE)
                        .addComponent(btn_cad_distribuicao_pd))
                    .addComponent(lb_titulo_pd)
                    .addComponent(jScrollPane1)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(79, 79, 79))
        );
        panel_distribuicaoLayout.setVerticalGroup(
            panel_distribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_distribuicaoLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(lb_titulo_pd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_subtitulo_pd)
                .addGap(18, 18, 18)
                .addGroup(panel_distribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx_pesquisa_dis_pd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_buscar_dis_pd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cad_distribuicao_pd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        panel_telaInicial.add(panel_distribuicao, "distribuicao");

        Alunos.setBackground(new java.awt.Color(255, 204, 255));
        Alunos.setMaximumSize(new java.awt.Dimension(1360, 680));
        Alunos.setMinimumSize(new java.awt.Dimension(1360, 680));
        Alunos.setPreferredSize(new java.awt.Dimension(1360, 680));

        jLabel3.setText("Alunos");

        javax.swing.GroupLayout AlunosLayout = new javax.swing.GroupLayout(Alunos);
        Alunos.setLayout(AlunosLayout);
        AlunosLayout.setHorizontalGroup(
            AlunosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AlunosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(630, 630, 630))
        );
        AlunosLayout.setVerticalGroup(
            AlunosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlunosLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_telaInicial.add(Alunos, "alunos");

        Servidores.setBackground(new java.awt.Color(255, 255, 255));
        Servidores.setMaximumSize(new java.awt.Dimension(1360, 680));
        Servidores.setMinimumSize(new java.awt.Dimension(1360, 680));
        Servidores.setPreferredSize(new java.awt.Dimension(1360, 680));

        lb_titulo_serv.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        lb_titulo_serv.setText("Controle de Servidores");
        lb_titulo_serv.setMaximumSize(new java.awt.Dimension(257, 28));
        lb_titulo_serv.setMinimumSize(new java.awt.Dimension(257, 28));
        lb_titulo_serv.setPreferredSize(new java.awt.Dimension(257, 28));

        lb_sub_serv.setForeground(new java.awt.Color(35, 91, 88));
        lb_sub_serv.setText("Tenha um controle em tempo real dos funcionários com acesso ao sistema.");

        btn_cadastrar_serv.setBackground(new java.awt.Color(4, 120, 87));
        btn_cadastrar_serv.setForeground(new java.awt.Color(255, 255, 255));
        btn_cadastrar_serv.setText("+ Cadastrar Servidor");
        btn_cadastrar_serv.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_cadastrar_serv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cadastrar_servActionPerformed(evt);
            }
        });

        tb_servidores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Setor", "Matrícula", "Status"
            }
        ));
        tb_servidores.setAutoscrolls(false);
        tb_servidores.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tb_servidores.setName(""); // NOI18N
        tb_servidores.setRowHeight(27);
        tb_servidores.setShowGrid(false);
        tb_servidores.setShowHorizontalLines(true);
        jScrollPane2.setViewportView(tb_servidores);

        lb_status_paginacao_serv.setText("jLabel2");
        lb_status_paginacao_serv.setPreferredSize(new java.awt.Dimension(18, 18));

        btn_proximo_serv.setText("Próxima");
        btn_proximo_serv.setMargin(new java.awt.Insets(8, 14, 8, 14));
        btn_proximo_serv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proximo_servActionPerformed(evt);
            }
        });

        btn_anterior_serv.setText("Anterior");
        btn_anterior_serv.setMargin(new java.awt.Insets(8, 14, 8, 14));
        btn_anterior_serv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anterior_servActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_status_paginacao_serv, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_anterior_serv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_proximo_serv)
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_proximo_serv, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_anterior_serv, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_status_paginacao_serv, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        tx_pesquisa_serv.setText("Busque um servidor...");
        tx_pesquisa_serv.setMaximumSize(new java.awt.Dimension(130, 30));
        tx_pesquisa_serv.setMinimumSize(new java.awt.Dimension(130, 30));
        tx_pesquisa_serv.setPreferredSize(new java.awt.Dimension(130, 30));
        tx_pesquisa_serv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_pesquisa_servActionPerformed(evt);
            }
        });

        btn_buscar_serv.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_buscar_serv.setText("Buscar");
        btn_buscar_serv.setMaximumSize(new java.awt.Dimension(70, 30));
        btn_buscar_serv.setMinimumSize(new java.awt.Dimension(70, 30));
        btn_buscar_serv.setPreferredSize(new java.awt.Dimension(70, 30));
        btn_buscar_serv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_servActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ServidoresLayout = new javax.swing.GroupLayout(Servidores);
        Servidores.setLayout(ServidoresLayout);
        ServidoresLayout.setHorizontalGroup(
            ServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServidoresLayout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(ServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(ServidoresLayout.createSequentialGroup()
                        .addGroup(ServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb_sub_serv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb_titulo_serv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tx_pesquisa_serv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_buscar_serv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 568, Short.MAX_VALUE)
                        .addComponent(btn_cadastrar_serv)))
                .addGap(79, 79, 79))
        );
        ServidoresLayout.setVerticalGroup(
            ServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServidoresLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(lb_titulo_serv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_sub_serv)
                .addGap(18, 18, 18)
                .addGroup(ServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx_pesquisa_serv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_buscar_serv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cadastrar_serv, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        panel_telaInicial.add(Servidores, "servidores");

        Uniformes.setBackground(new java.awt.Color(255, 255, 255));
        Uniformes.setMaximumSize(new java.awt.Dimension(1360, 680));
        Uniformes.setMinimumSize(new java.awt.Dimension(1360, 680));
        Uniformes.setPreferredSize(new java.awt.Dimension(1360, 680));

        Titulo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Titulo.setText("Controle de Uniformes");

        subtitulo.setForeground(new java.awt.Color(0, 102, 102));
        subtitulo.setText("Tenha um controle em tempo real do estoque de uniformes");

        tx_pesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_pesquisaActionPerformed(evt);
            }
        });

        btn_buscar.setText("BUSCAR");

        jcb_filtros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Camisa", "Calça", "Bermuda", "Tamanho P", "Tamanho M", "Tamanho G" }));
        jcb_filtros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_filtrosActionPerformed(evt);
            }
        });

        btn_Add_Uniforme.setBackground(new java.awt.Color(0, 153, 102));
        btn_Add_Uniforme.setForeground(new java.awt.Color(255, 255, 255));
        btn_Add_Uniforme.setText("+ Adicionar Uniforme");

        tabela_uniformes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Tipo", "Status", "Entrada", "Saída", "Tamanho", "Data Entrada"
            }
        ));
        jScrollPane3.setViewportView(tabela_uniformes);

        btn_editar.setText("EDITAR");

        javax.swing.GroupLayout UniformesLayout = new javax.swing.GroupLayout(Uniformes);
        Uniformes.setLayout(UniformesLayout);
        UniformesLayout.setHorizontalGroup(
            UniformesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UniformesLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(UniformesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UniformesLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(46, Short.MAX_VALUE))
                    .addGroup(UniformesLayout.createSequentialGroup()
                        .addGroup(UniformesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(UniformesLayout.createSequentialGroup()
                                .addComponent(btn_buscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tx_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92)
                                .addComponent(jcb_filtros, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_editar))
                            .addGroup(UniformesLayout.createSequentialGroup()
                                .addGroup(UniformesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(subtitulo)
                                    .addComponent(Titulo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_Add_Uniforme)))
                        .addGap(35, 35, 35))))
        );
        UniformesLayout.setVerticalGroup(
            UniformesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UniformesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(UniformesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(UniformesLayout.createSequentialGroup()
                        .addComponent(Titulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(subtitulo))
                    .addComponent(btn_Add_Uniforme))
                .addGap(52, 52, 52)
                .addGroup(UniformesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_editar)
                    .addComponent(btn_buscar)
                    .addComponent(tx_pesquisa)
                    .addComponent(jcb_filtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );

        panel_telaInicial.add(Uniformes, "uniformes");

        panel_aplicacao.add(panel_telaInicial, java.awt.BorderLayout.CENTER);

        main_container.add(panel_aplicacao, "card_aplicacao");

        panel_autenticacao.setMaximumSize(new java.awt.Dimension(1360, 760));
        panel_autenticacao.setMinimumSize(new java.awt.Dimension(1360, 760));
        panel_autenticacao.setPreferredSize(new java.awt.Dimension(1360, 760));
        panel_autenticacao.setLayout(new java.awt.CardLayout());

        panel_login.setBackground(new java.awt.Color(35, 91, 88));
        panel_login.setMaximumSize(new java.awt.Dimension(1360, 760));
        panel_login.setMinimumSize(new java.awt.Dimension(1360, 760));
        panel_login.setPreferredSize(new java.awt.Dimension(1360, 760));

        img_pl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/imagem-tela-login-resize.png"))); // NOI18N

        card_form_pl.setBackground(new java.awt.Color(255, 255, 255));
        card_form_pl.setMaximumSize(new java.awt.Dimension(503, 633));
        card_form_pl.setMinimumSize(new java.awt.Dimension(503, 633));
        card_form_pl.setPreferredSize(new java.awt.Dimension(503, 633));

        lb_login_pl.setBackground(new java.awt.Color(255, 255, 255));
        lb_login_pl.setFont(new java.awt.Font("Noto Sans", 0, 32)); // NOI18N
        lb_login_pl.setText("LOGIN");

        lb_matricula_pl.setBackground(new java.awt.Color(255, 255, 255));
        lb_matricula_pl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_matricula_pl.setLabelFor(input_matricula_pl);
        lb_matricula_pl.setText("Matrícula:");

        input_matricula_pl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        input_matricula_pl.setPreferredSize(new java.awt.Dimension(331, 28));
        input_matricula_pl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_matricula_plActionPerformed(evt);
            }
        });

        lb_senha_pl.setBackground(new java.awt.Color(255, 255, 255));
        lb_senha_pl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_senha_pl.setLabelFor(input_senha_pl);
        lb_senha_pl.setText("Senha:");

        input_senha_pl.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        input_senha_pl.setMinimumSize(new java.awt.Dimension(331, 28));
        input_senha_pl.setPreferredSize(new java.awt.Dimension(331, 28));

        separador_pl.setPreferredSize(new java.awt.Dimension(260, 10));

        btn_login_pl.setBackground(new java.awt.Color(0, 164, 55));
        btn_login_pl.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_login_pl.setForeground(new java.awt.Color(255, 255, 255));
        btn_login_pl.setText("ACESSAR");
        btn_login_pl.setBorderPainted(false);
        btn_login_pl.setPreferredSize(new java.awt.Dimension(163, 34));
        btn_login_pl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_login_plActionPerformed(evt);
            }
        });

        btn_esq_senha_pl.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        btn_esq_senha_pl.setText("Esqueceu ou deseja alterar sua senha?");
        btn_esq_senha_pl.setBorder(null);
        btn_esq_senha_pl.setBorderPainted(false);
        btn_esq_senha_pl.setContentAreaFilled(false);
        btn_esq_senha_pl.setFocusPainted(false);
        btn_esq_senha_pl.setIconTextGap(0);
        btn_esq_senha_pl.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn_esq_senha_pl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_esq_senha_plActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout card_form_plLayout = new javax.swing.GroupLayout(card_form_pl);
        card_form_pl.setLayout(card_form_plLayout);
        card_form_plLayout.setHorizontalGroup(
            card_form_plLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card_form_plLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(card_form_plLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lb_login_pl)
                    .addComponent(input_matricula_pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(separador_pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_login_pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_esq_senha_pl)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, card_form_plLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lb_matricula_pl))
                    .addComponent(input_senha_pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_senha_pl, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        card_form_plLayout.setVerticalGroup(
            card_form_plLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card_form_plLayout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(lb_login_pl)
                .addGap(33, 33, 33)
                .addComponent(lb_matricula_pl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_matricula_pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_senha_pl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_senha_pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(separador_pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_login_pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_esq_senha_pl)
                .addContainerGap(158, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_loginLayout = new javax.swing.GroupLayout(panel_login);
        panel_login.setLayout(panel_loginLayout);
        panel_loginLayout.setHorizontalGroup(
            panel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_loginLayout.createSequentialGroup()
                .addComponent(img_pl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(card_form_pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        panel_loginLayout.setVerticalGroup(
            panel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(img_pl)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_loginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(card_form_pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        panel_autenticacao.add(panel_login, "card_login");

        panel_primeiro_acesso.setBackground(new java.awt.Color(35, 91, 88));
        panel_primeiro_acesso.setMaximumSize(new java.awt.Dimension(1360, 760));
        panel_primeiro_acesso.setMinimumSize(new java.awt.Dimension(1360, 760));
        panel_primeiro_acesso.setPreferredSize(new java.awt.Dimension(1360, 760));

        img_ppa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/imagem-tela-login-resize.png"))); // NOI18N

        card_form_ppa.setBackground(new java.awt.Color(255, 255, 255));
        card_form_ppa.setMaximumSize(new java.awt.Dimension(503, 633));
        card_form_ppa.setMinimumSize(new java.awt.Dimension(503, 633));
        card_form_ppa.setPreferredSize(new java.awt.Dimension(503, 633));

        lb_redefinir_senha_ppa.setBackground(new java.awt.Color(255, 255, 255));
        lb_redefinir_senha_ppa.setFont(new java.awt.Font("Noto Sans", 0, 32)); // NOI18N
        lb_redefinir_senha_ppa.setText("REDEFINIR SENHA");

        lb_senha_ppa.setBackground(new java.awt.Color(255, 255, 255));
        lb_senha_ppa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_senha_ppa.setLabelFor(input_senha_ppa);
        lb_senha_ppa.setText("Senha:");

        input_senha_ppa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        input_senha_ppa.setPreferredSize(new java.awt.Dimension(331, 28));
        input_senha_ppa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_senha_ppaActionPerformed(evt);
            }
        });

        lb_confirmar_senha_ppa.setBackground(new java.awt.Color(255, 255, 255));
        lb_confirmar_senha_ppa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_confirmar_senha_ppa.setLabelFor(input_senha_pl);
        lb_confirmar_senha_ppa.setText("Confirmar Senha:");

        input_confirmar_senha_ppa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        input_confirmar_senha_ppa.setMinimumSize(new java.awt.Dimension(331, 28));
        input_confirmar_senha_ppa.setPreferredSize(new java.awt.Dimension(331, 28));
        input_confirmar_senha_ppa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_confirmar_senha_ppaActionPerformed(evt);
            }
        });

        separador_ppa.setPreferredSize(new java.awt.Dimension(260, 10));

        btn_salvar_ppa.setBackground(new java.awt.Color(0, 164, 55));
        btn_salvar_ppa.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_salvar_ppa.setForeground(new java.awt.Color(255, 255, 255));
        btn_salvar_ppa.setText("SALVAR");
        btn_salvar_ppa.setBorderPainted(false);
        btn_salvar_ppa.setPreferredSize(new java.awt.Dimension(148, 34));
        btn_salvar_ppa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvar_ppaActionPerformed(evt);
            }
        });

        btn_voltar_ppa.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        btn_voltar_ppa.setText("Voltar a tela de login");
        btn_voltar_ppa.setBorder(null);
        btn_voltar_ppa.setBorderPainted(false);
        btn_voltar_ppa.setContentAreaFilled(false);
        btn_voltar_ppa.setFocusPainted(false);
        btn_voltar_ppa.setIconTextGap(0);
        btn_voltar_ppa.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn_voltar_ppa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_voltar_ppaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout card_form_ppaLayout = new javax.swing.GroupLayout(card_form_ppa);
        card_form_ppa.setLayout(card_form_ppaLayout);
        card_form_ppaLayout.setHorizontalGroup(
            card_form_ppaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card_form_ppaLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(card_form_ppaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lb_redefinir_senha_ppa)
                    .addComponent(input_senha_ppa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input_confirmar_senha_ppa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(separador_ppa, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_salvar_ppa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, card_form_ppaLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lb_senha_ppa))
                    .addComponent(lb_confirmar_senha_ppa, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_voltar_ppa))
                .addGap(86, 86, 86))
        );
        card_form_ppaLayout.setVerticalGroup(
            card_form_ppaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card_form_ppaLayout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(lb_redefinir_senha_ppa)
                .addGap(33, 33, 33)
                .addComponent(lb_senha_ppa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_senha_ppa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_confirmar_senha_ppa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_confirmar_senha_ppa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(separador_ppa, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_salvar_ppa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(btn_voltar_ppa)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_primeiro_acessoLayout = new javax.swing.GroupLayout(panel_primeiro_acesso);
        panel_primeiro_acesso.setLayout(panel_primeiro_acessoLayout);
        panel_primeiro_acessoLayout.setHorizontalGroup(
            panel_primeiro_acessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_primeiro_acessoLayout.createSequentialGroup()
                .addComponent(img_ppa)
                .addGap(55, 55, 55)
                .addComponent(card_form_ppa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        panel_primeiro_acessoLayout.setVerticalGroup(
            panel_primeiro_acessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_primeiro_acessoLayout.createSequentialGroup()
                .addComponent(img_ppa)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_primeiro_acessoLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(card_form_ppa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_autenticacao.add(panel_primeiro_acesso, "card_primeiro_acesso");

        panel_solicitacao_codigo.setBackground(new java.awt.Color(35, 91, 88));
        panel_solicitacao_codigo.setMaximumSize(new java.awt.Dimension(1360, 760));
        panel_solicitacao_codigo.setMinimumSize(new java.awt.Dimension(1360, 760));

        img_psc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/imagem-tela-login-resize.png"))); // NOI18N

        card_form_psc.setBackground(new java.awt.Color(255, 255, 255));
        card_form_psc.setMaximumSize(new java.awt.Dimension(503, 633));
        card_form_psc.setMinimumSize(new java.awt.Dimension(503, 633));

        lb_solicitar_codigo_psc.setBackground(new java.awt.Color(255, 255, 255));
        lb_solicitar_codigo_psc.setFont(new java.awt.Font("Noto Sans", 0, 32)); // NOI18N
        lb_solicitar_codigo_psc.setText("SOLICITAR CÓDIGO");

        lb_matricula_psc.setBackground(new java.awt.Color(255, 255, 255));
        lb_matricula_psc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_matricula_psc.setLabelFor(input_matricula_pl);
        lb_matricula_psc.setText("Matrícula:");

        input_matricula_psc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        input_matricula_psc.setPreferredSize(new java.awt.Dimension(331, 28));
        input_matricula_psc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_matricula_pscActionPerformed(evt);
            }
        });

        lb_email_psc.setBackground(new java.awt.Color(255, 255, 255));
        lb_email_psc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_email_psc.setLabelFor(input_senha_pl);
        lb_email_psc.setText("E-mail:");

        input_email_psc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        input_email_psc.setPreferredSize(new java.awt.Dimension(331, 28));
        input_email_psc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_email_pscActionPerformed(evt);
            }
        });

        separador_psc.setPreferredSize(new java.awt.Dimension(260, 10));

        btn_enviar_psc.setBackground(new java.awt.Color(0, 164, 55));
        btn_enviar_psc.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_enviar_psc.setForeground(new java.awt.Color(255, 255, 255));
        btn_enviar_psc.setText("ENVIAR");
        btn_enviar_psc.setBorderPainted(false);
        btn_enviar_psc.setPreferredSize(new java.awt.Dimension(148, 34));
        btn_enviar_psc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_enviar_pscActionPerformed(evt);
            }
        });

        btn_voltar_psc.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        btn_voltar_psc.setText("Voltar a tela de login");
        btn_voltar_psc.setBorder(null);
        btn_voltar_psc.setBorderPainted(false);
        btn_voltar_psc.setContentAreaFilled(false);
        btn_voltar_psc.setFocusPainted(false);
        btn_voltar_psc.setIconTextGap(0);
        btn_voltar_psc.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn_voltar_psc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_voltar_pscActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout card_form_pscLayout = new javax.swing.GroupLayout(card_form_psc);
        card_form_psc.setLayout(card_form_pscLayout);
        card_form_pscLayout.setHorizontalGroup(
            card_form_pscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card_form_pscLayout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addGroup(card_form_pscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lb_solicitar_codigo_psc)
                    .addComponent(input_matricula_psc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(separador_psc, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_enviar_psc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input_email_psc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_voltar_psc))
                .addGap(86, 86, 86))
            .addGroup(card_form_pscLayout.createSequentialGroup()
                .addGroup(card_form_pscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card_form_pscLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(lb_email_psc))
                    .addGroup(card_form_pscLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(lb_matricula_psc)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        card_form_pscLayout.setVerticalGroup(
            card_form_pscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card_form_pscLayout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(lb_solicitar_codigo_psc)
                .addGap(33, 33, 33)
                .addComponent(lb_matricula_psc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_matricula_psc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_email_psc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_email_psc, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(separador_psc, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_enviar_psc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_voltar_psc)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_solicitacao_codigoLayout = new javax.swing.GroupLayout(panel_solicitacao_codigo);
        panel_solicitacao_codigo.setLayout(panel_solicitacao_codigoLayout);
        panel_solicitacao_codigoLayout.setHorizontalGroup(
            panel_solicitacao_codigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_solicitacao_codigoLayout.createSequentialGroup()
                .addComponent(img_psc)
                .addGap(55, 55, 55)
                .addComponent(card_form_psc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        panel_solicitacao_codigoLayout.setVerticalGroup(
            panel_solicitacao_codigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(img_psc)
            .addGroup(panel_solicitacao_codigoLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(card_form_psc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panel_autenticacao.add(panel_solicitacao_codigo, "card_solicitacao_codigo");

        panel_redefinir_senha.setBackground(new java.awt.Color(35, 91, 88));
        panel_redefinir_senha.setMaximumSize(new java.awt.Dimension(1360, 760));
        panel_redefinir_senha.setMinimumSize(new java.awt.Dimension(1360, 760));

        img_prs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/imagem-tela-login-resize.png"))); // NOI18N

        card_form_prs.setBackground(new java.awt.Color(255, 255, 255));
        card_form_prs.setMaximumSize(new java.awt.Dimension(503, 633));
        card_form_prs.setMinimumSize(new java.awt.Dimension(503, 633));

        lb_redefinir_senha_prs.setBackground(new java.awt.Color(255, 255, 255));
        lb_redefinir_senha_prs.setFont(new java.awt.Font("Noto Sans", 0, 32)); // NOI18N
        lb_redefinir_senha_prs.setText("REDEFINIR SENHA");

        lb_codigo_prs.setBackground(new java.awt.Color(255, 255, 255));
        lb_codigo_prs.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_codigo_prs.setLabelFor(input_senha_ppa);
        lb_codigo_prs.setText("Código:");

        input_codigo_prs.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        input_codigo_prs.setPreferredSize(new java.awt.Dimension(331, 28));
        input_codigo_prs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_codigo_prsinput_codigo_pssActionPerformed(evt);
            }
        });

        lb_senha_prs.setBackground(new java.awt.Color(255, 255, 255));
        lb_senha_prs.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_senha_prs.setLabelFor(input_senha_ppa);
        lb_senha_prs.setText("Senha:");

        input_senha_prs.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        input_senha_prs.setPreferredSize(new java.awt.Dimension(331, 28));
        input_senha_prs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_senha_prsinput_senha_prsActionPerformed(evt);
            }
        });

        lb_confirmar_senha_prs.setBackground(new java.awt.Color(255, 255, 255));
        lb_confirmar_senha_prs.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_confirmar_senha_prs.setLabelFor(input_senha_pl);
        lb_confirmar_senha_prs.setText("Confirmar Senha:");

        input_confirmar_senha_prs.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        input_confirmar_senha_prs.setMinimumSize(new java.awt.Dimension(331, 28));
        input_confirmar_senha_prs.setPreferredSize(new java.awt.Dimension(331, 28));
        input_confirmar_senha_prs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_confirmar_senha_prsinput_confirmar_senha_prsActionPerformed(evt);
            }
        });

        separador_prs.setPreferredSize(new java.awt.Dimension(260, 10));

        btn_salvar_prs.setBackground(new java.awt.Color(0, 164, 55));
        btn_salvar_prs.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btn_salvar_prs.setForeground(new java.awt.Color(255, 255, 255));
        btn_salvar_prs.setText("SALVAR");
        btn_salvar_prs.setBorderPainted(false);
        btn_salvar_prs.setPreferredSize(new java.awt.Dimension(148, 34));
        btn_salvar_prs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvar_prsbtn_salvar_prsActionPerformed(evt);
            }
        });

        btn_voltar_prs.setFont(new java.awt.Font("Noto Sans", 1, 12)); // NOI18N
        btn_voltar_prs.setText("Voltar a tela de login");
        btn_voltar_prs.setBorder(null);
        btn_voltar_prs.setBorderPainted(false);
        btn_voltar_prs.setContentAreaFilled(false);
        btn_voltar_prs.setFocusPainted(false);
        btn_voltar_prs.setIconTextGap(0);
        btn_voltar_prs.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btn_voltar_prs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_voltar_prsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout card_form_prsLayout = new javax.swing.GroupLayout(card_form_prs);
        card_form_prs.setLayout(card_form_prsLayout);
        card_form_prsLayout.setHorizontalGroup(
            card_form_prsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card_form_prsLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(card_form_prsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lb_redefinir_senha_prs)
                    .addComponent(separador_prs, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_salvar_prs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(card_form_prsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, card_form_prsLayout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(lb_codigo_prs))
                        .addComponent(input_codigo_prs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(card_form_prsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(input_senha_prs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, card_form_prsLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(lb_senha_prs))
                            .addGroup(card_form_prsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(input_confirmar_senha_prs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lb_confirmar_senha_prs, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addComponent(btn_voltar_prs))
                .addGap(86, 86, 86))
        );
        card_form_prsLayout.setVerticalGroup(
            card_form_prsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card_form_prsLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(lb_redefinir_senha_prs)
                .addGap(48, 48, 48)
                .addComponent(lb_codigo_prs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_codigo_prs, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_senha_prs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_senha_prs, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_confirmar_senha_prs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_confirmar_senha_prs, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(separador_prs, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_salvar_prs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_voltar_prs)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_redefinir_senhaLayout = new javax.swing.GroupLayout(panel_redefinir_senha);
        panel_redefinir_senha.setLayout(panel_redefinir_senhaLayout);
        panel_redefinir_senhaLayout.setHorizontalGroup(
            panel_redefinir_senhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_redefinir_senhaLayout.createSequentialGroup()
                .addComponent(img_prs)
                .addGap(55, 55, 55)
                .addComponent(card_form_prs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        panel_redefinir_senhaLayout.setVerticalGroup(
            panel_redefinir_senhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_redefinir_senhaLayout.createSequentialGroup()
                .addComponent(img_prs)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panel_redefinir_senhaLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(card_form_prs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_autenticacao.add(panel_redefinir_senha, "card_redefinir_senha");

        main_container.add(panel_autenticacao, "card_autenticacao");

        getContentPane().add(main_container, java.awt.BorderLayout.CENTER);

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_nav_InicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nav_InicioActionPerformed
        // TODO add your handling code here:
        appCardLayout.show(panel_telaInicial, "inicio");
        System.out.println("Mostrando painel Inicio");        
        carregarGraficoPizza();
        carregarGraficoBarras();
    }//GEN-LAST:event_btn_nav_InicioActionPerformed

    private void btn_nav_distribuicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nav_distribuicaoActionPerformed
        // TODO add your handling code here:
        termoBuscaAtualDistribuicao = "";
        tx_pesquisa_dis_pd.setText("");
        
        totalDePaginas = entregaController.getTotalDeEntregas(ITENS_POR_PAGINA, termoBuscaAtualDistribuicao);
        
        paginaAtual = 1;
        
        atualizarTabelaEControles();
        
        // carregaDadosDistribuicao();
        appCardLayout.show(panel_telaInicial, "distribuicao");
        System.out.println("Mostrando painel Distribuicao");
    }//GEN-LAST:event_btn_nav_distribuicaoActionPerformed

    private void btn_nav_alunosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nav_alunosActionPerformed
        // TODO add your handling code here:
        appCardLayout.show(panel_telaInicial, "alunos");
        System.out.println("Mostrando painel Alunos");
        //JOptionPane.showMessageDialog(this, "Painel de Alunos ainda não implementado");
    }//GEN-LAST:event_btn_nav_alunosActionPerformed

    private void btn_nav_servidoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nav_servidoresActionPerformed
        // TODO add your handling code here:
        termoBuscaAtualServidores = "";
        tx_pesquisa_serv.setText("");

        totalDePaginasServidores = servidorController.getTotalDePaginas(ITENS_POR_PAGINA, termoBuscaAtualServidores);
        paginaAtualServidores = 1;
        atualizarTabelaServidoresEControles();

        appCardLayout.show(panel_telaInicial, "servidores");
        //JOptionPane.showMessageDialog(this, "Painel de Servidores ainda não implementado");
    }//GEN-LAST:event_btn_nav_servidoresActionPerformed

    private void btn_nav_uniformesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nav_uniformesActionPerformed
        // TODO add your handling code here:
        carregaDadosUniformes();
        appCardLayout.show(panel_telaInicial, "uniformes");
        System.out.println("Mostrando painel Uniformes");
        //JOptionPane.showMessageDialog(this, "Painel de Uniformes ainda não implementado");
    }//GEN-LAST:event_btn_nav_uniformesActionPerformed

    private void btn_cad_distribuicao_pdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cad_distribuicao_pdActionPerformed
        // TODO add your handling code here:
        FormEntregaDialog dialog = new FormEntregaDialog(this);
        dialog.setVisible(true);
        
        if (dialog.isSalvo()) {
            JOptionPane.showMessageDialog(this, "Distribuição realizada com sucesso!");
            atualizarTabelaEControles();
        }      
    }//GEN-LAST:event_btn_cad_distribuicao_pdActionPerformed

    private void input_matricula_plActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_matricula_plActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_matricula_plActionPerformed

    private void btn_login_plActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_login_plActionPerformed
        String matriculaLogin = input_matricula_pl.getText();
        String senhaLogin = new String(input_senha_pl.getPassword());

        String login = this.authController.autenticar(matriculaLogin, senhaLogin);
        
        input_senha_pl.setText("");
        
        switch (login) {
            case "autenticado":
                mainCardLayout.show(main_container, "card_aplicacao");
                input_matricula_pl.setText("");
                JOptionPane.showMessageDialog(this, "Login efetuado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "p_acesso":
                this.matriculaUpdate = matriculaLogin;
                authCardLayout.show(panel_autenticacao, "card_primeiro_acesso");
                JOptionPane.showMessageDialog(this, "Identificamos que esse é seu primeiro acesso ao sistema, por favor, altere sua senha!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "n_autenticado":
                JOptionPane.showMessageDialog(this, "Matrícula ou senha inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }//GEN-LAST:event_btn_login_plActionPerformed

    private void btn_salvar_ppaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvar_ppaActionPerformed
        String senhaUpdate = new String(input_senha_ppa.getPassword());
        String senhaUpdateConf = new String(input_confirmar_senha_ppa.getPassword());
        
        if (senhaUpdate.equals(senhaUpdateConf)) {
            boolean confirm = this.authController.redefinirSenha(this.matriculaUpdate, senhaUpdate);
            if (confirm) {
                this.matriculaUpdate = null;
                authCardLayout.show(panel_autenticacao, "card_login");
                input_senha_ppa.setText("");
                input_confirmar_senha_ppa.setText("");
                JOptionPane.showMessageDialog(this, "Senha alterada com sucesso!\nEfetue o login", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao redefinir a senha!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "As senhas não são iguais!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_salvar_ppaActionPerformed

    private void btn_cadastrar_servActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cadastrar_servActionPerformed
        // TODO add your handling code here:                                                 
        FormServidorDialog dialog = new FormServidorDialog(this);

        dialog.setVisible(true); 

        if (dialog.isSalvo()) {
            JOptionPane.showMessageDialog(this, "Servidor cadastrado com sucesso!");
            atualizarTabelaServidoresEControles();
        }

        System.out.println("Atualizando a tabela de servidores...");
        atualizarTabelaServidoresEControles();
    }//GEN-LAST:event_btn_cadastrar_servActionPerformed
    private void tx_pesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_pesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_pesquisaActionPerformed

    private void jcb_filtrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_filtrosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcb_filtrosActionPerformed

    private void btn_sair_pnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sair_pnActionPerformed
        boolean confirm = this.authController.sair();
        if (confirm) {
            mainCardLayout.show(main_container, "card_autenticacao");
            authCardLayout.show(panel_autenticacao, "card_login");
            JOptionPane.showMessageDialog(this, "Sessão encerrada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao encerrar sessão!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_sair_pnActionPerformed

    private void input_confirmar_senha_ppaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_confirmar_senha_ppaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_confirmar_senha_ppaActionPerformed

    private void input_senha_ppaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_senha_ppaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_senha_ppaActionPerformed
  
    private void input_matricula_pscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_matricula_pscActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_matricula_pscActionPerformed

    private void btn_enviar_pscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_enviar_pscActionPerformed
        // TODO add your handling code here:
        String matriculaRecuperacao = input_matricula_psc.getText();
        String emailRecuperacao = input_email_psc.getText();
        
        boolean recuperacao = this.authController.solicitarCodigo(matriculaRecuperacao, emailRecuperacao);
        
        if (recuperacao) {
            this.matriculaUpdate = matriculaRecuperacao;
            authCardLayout.show(panel_autenticacao, "card_redefinir_senha");
            input_matricula_psc.setText("");
            input_email_psc.setText("");
            JOptionPane.showMessageDialog(this, "O código de recuperação de 6 digitos foi enviado a seu E-mail!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Usuário inexistente ou E-mail inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_enviar_pscActionPerformed

    private void input_email_pscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_email_pscActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_email_pscActionPerformed

    private void input_codigo_prsinput_codigo_pssActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_codigo_prsinput_codigo_pssActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_codigo_prsinput_codigo_pssActionPerformed

    private void input_senha_prsinput_senha_prsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_senha_prsinput_senha_prsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_senha_prsinput_senha_prsActionPerformed

    private void input_confirmar_senha_prsinput_confirmar_senha_prsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_confirmar_senha_prsinput_confirmar_senha_prsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_confirmar_senha_prsinput_confirmar_senha_prsActionPerformed

    private void btn_salvar_prsbtn_salvar_prsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvar_prsbtn_salvar_prsActionPerformed
        // TODO add your handling code here:
        String codigo = input_codigo_prs.getText();
        String senhaRedefinir = new String(input_senha_prs.getPassword());
        String senhaRedefinirConf = new String(input_confirmar_senha_prs.getPassword());
        
        if (senhaRedefinir.equals(senhaRedefinirConf)) {
            String confirmCodigo = this.authController.verificarCodigoRecuperacao(this.matriculaUpdate, codigo);
            switch (confirmCodigo) {
                case "n_codigo":
                    JOptionPane.showMessageDialog(this, "Código de recuperação não registrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
                case "exp_codigo":
                    JOptionPane.showMessageDialog(this, "Código de recuperação expirado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
                case "sucesso":
                    boolean confirm = this.authController.redefinirSenha(this.matriculaUpdate, senhaRedefinir);
                    if (confirm) {
                        this.matriculaUpdate = null;
                        authCardLayout.show(panel_autenticacao, "card_login");
                        input_codigo_prs.setText("");
                        input_senha_prs.setText("");
                        input_confirmar_senha_prs.setText("");
                        JOptionPane.showMessageDialog(this, "Senha alterada com sucesso!\nEfetue o login", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao redefinir a senha!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "err_codigo":
                    JOptionPane.showMessageDialog(this, "Código de recuperação incorreto!", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } else {
            input_senha_prs.setText("");
            input_confirmar_senha_prs.setText("");
            JOptionPane.showMessageDialog(this, "As senhas não são iguais!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_salvar_prsbtn_salvar_prsActionPerformed

    private void btn_esq_senha_plActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_esq_senha_plActionPerformed
        // TODO add your handling code here:
        authCardLayout.show(panel_autenticacao, "card_solicitacao_codigo");
    }//GEN-LAST:event_btn_esq_senha_plActionPerformed

    private void btn_voltar_prsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_voltar_prsActionPerformed
        // TODO add your handling code here:
        input_senha_pl.setText("");
        authCardLayout.show(panel_autenticacao, "card_login");
    }//GEN-LAST:event_btn_voltar_prsActionPerformed

    private void btn_voltar_pscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_voltar_pscActionPerformed
        // TODO add your handling code here:
        input_senha_pl.setText("");
        authCardLayout.show(panel_autenticacao, "card_login");
    }//GEN-LAST:event_btn_voltar_pscActionPerformed

    private void btn_voltar_ppaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_voltar_ppaActionPerformed
        // TODO add your handling code here:
        authCardLayout.show(panel_autenticacao, "card_login");
    }//GEN-LAST:event_btn_voltar_ppaActionPerformed

    private void btn_anterior_pdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anterior_pdActionPerformed
        // TODO add your handling code here:
        if (paginaAtual > 1) {
                paginaAtual--; 
                atualizarTabelaEControles(); 
        }
    }//GEN-LAST:event_btn_anterior_pdActionPerformed

    private void btn_anterior_servActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anterior_servActionPerformed
        // TODO add your handling code here:
        if (paginaAtualServidores > 1) {
                paginaAtualServidores--;
                atualizarTabelaServidoresEControles();
        }
    }//GEN-LAST:event_btn_anterior_servActionPerformed

    private void btn_proximo_pdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximo_pdActionPerformed
        // TODO add your handling code here:
        if (paginaAtual < totalDePaginas) {
                paginaAtual++; 
                atualizarTabelaEControles(); 
        }
    }//GEN-LAST:event_btn_proximo_pdActionPerformed

    private void btn_proximo_servActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proximo_servActionPerformed
        // TODO add your handling code here:
        if (paginaAtualServidores < totalDePaginasServidores) {
                paginaAtualServidores++;
                atualizarTabelaServidoresEControles();
        }
    }//GEN-LAST:event_btn_proximo_servActionPerformed

    private void btn_buscar_servActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_servActionPerformed
        // TODO add your handling code here
        realizarBuscaServidores();
    }//GEN-LAST:event_btn_buscar_servActionPerformed

    private void tx_pesquisa_servActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_pesquisa_servActionPerformed
        // TODO add your handling code here:
        realizarBuscaServidores();
    }//GEN-LAST:event_tx_pesquisa_servActionPerformed

    private void tx_pesquisa_dis_pdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_pesquisa_dis_pdActionPerformed
        // TODO add your handling code here:
        realizarBuscaDistribuicao();

    }//GEN-LAST:event_tx_pesquisa_dis_pdActionPerformed

    private void btn_buscar_dis_pdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_dis_pdActionPerformed
        // TODO add your handling code here:
        realizarBuscaDistribuicao();
    }//GEN-LAST:event_btn_buscar_dis_pdActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Alunos;
    private javax.swing.JPanel Inicio;
    private javax.swing.JPanel Servidores;
    private javax.swing.JLabel Titulo;
    private javax.swing.JPanel Uniformes;
    private javax.swing.JButton btn_Add_Uniforme;
    private javax.swing.JButton btn_anterior_pd;
    private javax.swing.JButton btn_anterior_serv;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_buscar_dis_pd;
    private javax.swing.JButton btn_buscar_serv;
    private javax.swing.JButton btn_cad_distribuicao_pd;
    private javax.swing.JButton btn_cadastrar_serv;
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_enviar_psc;
    private javax.swing.JButton btn_esq_senha_pl;
    private javax.swing.JButton btn_login_pl;
    private javax.swing.JButton btn_nav_Inicio;
    private javax.swing.JButton btn_nav_alunos;
    private javax.swing.JButton btn_nav_distribuicao;
    private javax.swing.JButton btn_nav_servidores;
    private javax.swing.JButton btn_nav_uniformes;
    private javax.swing.JButton btn_proximo_pd;
    private javax.swing.JButton btn_proximo_serv;
    private javax.swing.JButton btn_sair_pn;
    private javax.swing.JButton btn_salvar_ppa;
    private javax.swing.JButton btn_salvar_prs;
    private javax.swing.JButton btn_voltar_ppa;
    private javax.swing.JButton btn_voltar_prs;
    private javax.swing.JButton btn_voltar_psc;
    private com.mycompany.gerenciamento.uniformes.Components.CardPanel card_form_pl;
    private com.mycompany.gerenciamento.uniformes.Components.CardPanel card_form_ppa;
    private com.mycompany.gerenciamento.uniformes.Components.CardPanel card_form_prs;
    private com.mycompany.gerenciamento.uniformes.Components.CardPanel card_form_psc;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.JLabel img_pl;
    private javax.swing.JLabel img_ppa;
    private javax.swing.JLabel img_prs;
    private javax.swing.JLabel img_psc;
    private javax.swing.JTextField input_codigo_prs;
    private javax.swing.JPasswordField input_confirmar_senha_ppa;
    private javax.swing.JPasswordField input_confirmar_senha_prs;
    private javax.swing.JTextField input_email_psc;
    private javax.swing.JTextField input_matricula_pl;
    private javax.swing.JTextField input_matricula_psc;
    private javax.swing.JPasswordField input_senha_pl;
    private javax.swing.JPasswordField input_senha_ppa;
    private javax.swing.JPasswordField input_senha_prs;
    private javax.swing.JDialog jDialog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox<String> jcb_filtros;
    private javax.swing.JLabel lb_codigo_prs;
    private javax.swing.JLabel lb_confirmar_senha_ppa;
    private javax.swing.JLabel lb_confirmar_senha_prs;
    private javax.swing.JLabel lb_email_psc;
    private javax.swing.JLabel lb_login_pl;
    private javax.swing.JLabel lb_matricula_pl;
    private javax.swing.JLabel lb_matricula_psc;
    private javax.swing.JLabel lb_redefinir_senha_ppa;
    private javax.swing.JLabel lb_redefinir_senha_prs;
    private javax.swing.JLabel lb_senha_pl;
    private javax.swing.JLabel lb_senha_ppa;
    private javax.swing.JLabel lb_senha_prs;
    private javax.swing.JLabel lb_solicitar_codigo_psc;
    private javax.swing.JLabel lb_status_paginacao_pd;
    private javax.swing.JLabel lb_status_paginacao_serv;
    private javax.swing.JLabel lb_sub_serv;
    private javax.swing.JLabel lb_subtitulo_pd;
    private javax.swing.JLabel lb_titulo_pd;
    private javax.swing.JLabel lb_titulo_serv;
    private javax.swing.JPanel main_container;
    private javax.swing.JLabel nome_sistema;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private javax.swing.JPanel panelGraficoBarras;
    private javax.swing.JPanel panelGraficoPizza;
    private javax.swing.JPanel panel_aplicacao;
    private javax.swing.JPanel panel_autenticacao;
    private javax.swing.JPanel panel_distribuicao;
    private javax.swing.JPanel panel_login;
    private javax.swing.JPanel panel_navbar;
    private javax.swing.JPanel panel_primeiro_acesso;
    private javax.swing.JPanel panel_redefinir_senha;
    private javax.swing.JPanel panel_solicitacao_codigo;
    private javax.swing.JPanel panel_telaInicial;
    private javax.swing.JSeparator separador_pl;
    private javax.swing.JSeparator separador_ppa;
    private javax.swing.JSeparator separador_prs;
    private javax.swing.JSeparator separador_psc;
    private javax.swing.JLabel subtitulo;
    private javax.swing.JTable tabela_uniformes;
    private javax.swing.JTable tb_distribuicao;
    private javax.swing.JTable tb_servidores;
    private javax.swing.JTextField tx_pesquisa;
    private javax.swing.JTextField tx_pesquisa_dis_pd;
    private javax.swing.JTextField tx_pesquisa_serv;
    // End of variables declaration//GEN-END:variables
}
