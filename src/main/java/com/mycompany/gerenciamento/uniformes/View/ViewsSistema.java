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
import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
    private final EntregaTableModel entregaTableModel; // Declara a tableModel
    private final ServidorTableModel servidorTableModel;
    private final AuthController authController;
    private final EntregaController entregaController;
    private final ServidorController servidorController;
    private GraficosController graficosController;
    private String matriculaUpdate;
    
    public ViewsSistema() {
        initComponents();
        carregarGraficoPizza();
        carregarGraficoBarras();
        
        this.appCardLayout = (CardLayout) panel_telaInicial.getLayout();
        this.mainCardLayout = (CardLayout) main_container.getLayout();
        this.authCardLayout = (CardLayout) panel_autenticacao.getLayout();
        
        this.authController = new AuthController();
        this.entregaController = new EntregaController();
        this.servidorController = new ServidorController();
                
        this.entregaTableModel = new EntregaTableModel(new ArrayList<>()); //Cria modelo com uma lista vazia
        this.servidorTableModel = new ServidorTableModel(new ArrayList<>());
        
        this.tb_distribuicao.setModel(entregaTableModel); //Conecta o Jtable ao criado pelo netbeans
        this.tb_servidores.setModel(servidorTableModel);
        
        System.out.println("Painéis disponíveis:");
        for (Component comp : panel_telaInicial.getComponents()) {
            System.out.println("- " + comp.getName() + " (" + comp.getClass().getSimpleName() + ")");
        }
        
        mainCardLayout.show(main_container, "card_autenticacao");
        authCardLayout.show(panel_autenticacao, "card_login");
    }
    
    private void carregaDadosDistribuicao() {
        try {
            List<EntregaModel> listaDeEntregas = this.entregaController.listarTodos();
            
            entregaTableModel.setEntregas(listaDeEntregas);
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os dados de distribuição.", "Erro", JOptionPane.ERROR_MESSAGE);
            error.printStackTrace();
        }
    }

    private void carregaDadosServidores() {
        try {
            List<ServidorModel> listaDeServidores = this.servidorController.listarTodos();

            servidorTableModel.setServidores(listaDeServidores);
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os dados de servidores.", "Erro", JOptionPane.ERROR_MESSAGE);
            error.printStackTrace();
        }
    }

    // Carrega Gráfico Pizza
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
    
    // Carrega Gráfico Barra
    private void carregarGraficoBarras() {
        this.graficosController = new GraficosController();
        JFreeChart graficoBarras = graficosController.criarGraficoBarrasPorTurma();

        ChartPanel chartPanelBarras = new ChartPanel(graficoBarras);

        panelGraficoBarras.removeAll();
        panelGraficoBarras.setLayout(new BorderLayout());
        panelGraficoBarras.add(chartPanelBarras, BorderLayout.CENTER);

        panelGraficoBarras.revalidate();
        panelGraficoBarras.repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jd_cadServ_Serv = new javax.swing.JDialog();
        panel_cadServ = new javax.swing.JPanel();
        lb_titulo_pcadServ = new javax.swing.JLabel();
        lb_nome_pcadServ = new javax.swing.JLabel();
        tf_nome_pcadServ = new javax.swing.JTextField();
        lb_sobrenome_pcadServ = new javax.swing.JLabel();
        tf_sobrenome_pcadServ = new javax.swing.JTextField();
        lb_email_pcadServ = new javax.swing.JLabel();
        tf_email_pcadServ = new javax.swing.JTextField();
        lb_telefone_pcadServ = new javax.swing.JLabel();
        tf_telefone_pcadServ = new javax.swing.JTextField();
        lb_dep_pcadServ = new javax.swing.JLabel();
        jc_dep_pcadServ = new javax.swing.JComboBox<>();
        lb_matricula_pcadServ = new javax.swing.JLabel();
        tf_matricula_pcadServ = new javax.swing.JTextField();
        lb_satus_pcadServ = new javax.swing.JLabel();
        jbtn_ativo_pcadServ = new javax.swing.JRadioButton();
        jbtn_inativo_pcadServ = new javax.swing.JRadioButton();
        btn_cancelar_pcadServ = new javax.swing.JButton();
        btn_salvar_pcadServ = new javax.swing.JButton();
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
        Alunos = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Servidores = new javax.swing.JPanel();
        lb_titulo_serv = new javax.swing.JLabel();
        lb_sub_serv = new javax.swing.JLabel();
        btn_cadastrar_serv = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_servidores = new javax.swing.JTable();
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
        btn_esq_senh_pl = new javax.swing.JButton();
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
        btn_voltar_login_ppa = new javax.swing.JButton();

        jd_cadServ_Serv.setForeground(java.awt.Color.white);
        jd_cadServ_Serv.setMinimumSize(new java.awt.Dimension(600, 425));
        jd_cadServ_Serv.setModal(true);
        jd_cadServ_Serv.setResizable(false);

        panel_cadServ.setBackground(new java.awt.Color(255, 255, 255));
        panel_cadServ.setAutoscrolls(true);
        panel_cadServ.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panel_cadServ.setMaximumSize(new java.awt.Dimension(600, 425));
        panel_cadServ.setMinimumSize(new java.awt.Dimension(600, 425));
        panel_cadServ.setPreferredSize(new java.awt.Dimension(600, 425));

        lb_titulo_pcadServ.setFont(new java.awt.Font("Segoe UI", 1, 19)); // NOI18N
        lb_titulo_pcadServ.setText("Cadastrar Servidor");

        lb_nome_pcadServ.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lb_nome_pcadServ.setText("Nome:");

        tf_nome_pcadServ.setPreferredSize(new java.awt.Dimension(64, 30));
        tf_nome_pcadServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_nome_pcadServActionPerformed(evt);
            }
        });

        lb_sobrenome_pcadServ.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lb_sobrenome_pcadServ.setText("Sobrenome:");

        tf_sobrenome_pcadServ.setPreferredSize(new java.awt.Dimension(64, 30));

        lb_email_pcadServ.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lb_email_pcadServ.setText("E-mail:");

        tf_email_pcadServ.setPreferredSize(new java.awt.Dimension(64, 30));

        lb_telefone_pcadServ.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lb_telefone_pcadServ.setText("Telefone:");

        tf_telefone_pcadServ.setPreferredSize(new java.awt.Dimension(64, 30));
        tf_telefone_pcadServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_telefone_pcadServActionPerformed(evt);
            }
        });

        lb_dep_pcadServ.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lb_dep_pcadServ.setText("Departamento:");

        jc_dep_pcadServ.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jc_dep_pcadServ.setPreferredSize(new java.awt.Dimension(64, 30));

        lb_matricula_pcadServ.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lb_matricula_pcadServ.setText("Matrícula:");

        tf_matricula_pcadServ.setPreferredSize(new java.awt.Dimension(64, 30));

        lb_satus_pcadServ.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lb_satus_pcadServ.setText("Status:");

        jbtn_ativo_pcadServ.setText("Ativo");
        jbtn_ativo_pcadServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_ativo_pcadServActionPerformed(evt);
            }
        });

        jbtn_inativo_pcadServ.setText("Inativo");
        jbtn_inativo_pcadServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn_inativo_pcadServActionPerformed(evt);
            }
        });

        btn_cancelar_pcadServ.setBackground(new java.awt.Color(238, 63, 63));
        btn_cancelar_pcadServ.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_cancelar_pcadServ.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar_pcadServ.setText("Cancelar");
        btn_cancelar_pcadServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelar_pcadServActionPerformed(evt);
            }
        });

        btn_salvar_pcadServ.setBackground(new java.awt.Color(0, 164, 55));
        btn_salvar_pcadServ.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_salvar_pcadServ.setForeground(new java.awt.Color(255, 255, 255));
        btn_salvar_pcadServ.setText("Salvar");
        btn_salvar_pcadServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvar_pcadServActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_cadServLayout = new javax.swing.GroupLayout(panel_cadServ);
        panel_cadServ.setLayout(panel_cadServLayout);
        panel_cadServLayout.setHorizontalGroup(
            panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cadServLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_cadServLayout.createSequentialGroup()
                        .addComponent(lb_email_pcadServ)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lb_telefone_pcadServ)
                        .addGap(232, 232, 232))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_cadServLayout.createSequentialGroup()
                        .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tf_email_pcadServ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tf_nome_pcadServ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jc_dep_pcadServ, 0, 250, Short.MAX_VALUE))
                            .addComponent(lb_nome_pcadServ)
                            .addComponent(lb_titulo_pcadServ)
                            .addComponent(lb_dep_pcadServ))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_matricula_pcadServ)
                            .addComponent(lb_sobrenome_pcadServ)
                            .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(panel_cadServLayout.createSequentialGroup()
                                    .addComponent(btn_cancelar_pcadServ, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(28, 28, 28)
                                    .addComponent(btn_salvar_pcadServ, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tf_telefone_pcadServ, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tf_matricula_pcadServ, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                    .addComponent(tf_sobrenome_pcadServ, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(36, 36, 36))
                    .addGroup(panel_cadServLayout.createSequentialGroup()
                        .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_satus_pcadServ)
                            .addGroup(panel_cadServLayout.createSequentialGroup()
                                .addComponent(jbtn_ativo_pcadServ)
                                .addGap(18, 18, 18)
                                .addComponent(jbtn_inativo_pcadServ)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panel_cadServLayout.setVerticalGroup(
            panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_cadServLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lb_titulo_pcadServ)
                .addGap(18, 18, 18)
                .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_nome_pcadServ)
                    .addComponent(lb_sobrenome_pcadServ))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_nome_pcadServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_sobrenome_pcadServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_email_pcadServ)
                    .addComponent(lb_telefone_pcadServ))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_email_pcadServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tf_telefone_pcadServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_cadServLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lb_matricula_pcadServ))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_cadServLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_dep_pcadServ)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_matricula_pcadServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jc_dep_pcadServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 25, Short.MAX_VALUE)
                .addComponent(lb_satus_pcadServ)
                .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_cadServLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtn_ativo_pcadServ)
                            .addComponent(jbtn_inativo_pcadServ))
                        .addGap(65, 109, Short.MAX_VALUE))
                    .addGroup(panel_cadServLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(panel_cadServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_cancelar_pcadServ, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_salvar_pcadServ, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jd_cadServ_ServLayout = new javax.swing.GroupLayout(jd_cadServ_Serv.getContentPane());
        jd_cadServ_Serv.getContentPane().setLayout(jd_cadServ_ServLayout);
        jd_cadServ_ServLayout.setHorizontalGroup(
            jd_cadServ_ServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_cadServ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jd_cadServ_ServLayout.setVerticalGroup(
            jd_cadServ_ServLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_cadServ, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );

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

        panelGraficoBarras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
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

        lb_subtitulo_pd.setText("Tenha controle em tempo real das distribuições de uniformes.");

        javax.swing.GroupLayout panel_distribuicaoLayout = new javax.swing.GroupLayout(panel_distribuicao);
        panel_distribuicao.setLayout(panel_distribuicaoLayout);
        panel_distribuicaoLayout.setHorizontalGroup(
            panel_distribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_distribuicaoLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(panel_distribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_titulo_pd)
                    .addGroup(panel_distribuicaoLayout.createSequentialGroup()
                        .addComponent(lb_subtitulo_pd)
                        .addGap(665, 665, 665)
                        .addComponent(btn_cad_distribuicao_pd))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        panel_distribuicaoLayout.setVerticalGroup(
            panel_distribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_distribuicaoLayout.createSequentialGroup()
                .addContainerGap(222, Short.MAX_VALUE)
                .addComponent(lb_titulo_pd)
                .addGap(18, 18, 18)
                .addGroup(panel_distribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_cad_distribuicao_pd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_subtitulo_pd))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
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

        tb_servidores.setAutoCreateRowSorter(true);
        tb_servidores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Setor", "Matrícula", "Status"
            }
        ));
        tb_servidores.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tb_servidores.setFocusable(false);
        tb_servidores.setMaximumSize(new java.awt.Dimension(1360, 0));
        tb_servidores.setMinimumSize(new java.awt.Dimension(1360, 0));
        tb_servidores.setName(""); // NOI18N
        tb_servidores.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tb_servidores.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tb_servidores.setShowGrid(true);
        jScrollPane2.setViewportView(tb_servidores);

        javax.swing.GroupLayout ServidoresLayout = new javax.swing.GroupLayout(Servidores);
        Servidores.setLayout(ServidoresLayout);
        ServidoresLayout.setHorizontalGroup(
            ServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServidoresLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(ServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(ServidoresLayout.createSequentialGroup()
                        .addGroup(ServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_sub_serv)
                            .addComponent(lb_titulo_serv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 659, Short.MAX_VALUE)
                        .addComponent(btn_cadastrar_serv)))
                .addGap(79, 79, 79))
        );
        ServidoresLayout.setVerticalGroup(
            ServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ServidoresLayout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addGroup(ServidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_cadastrar_serv, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ServidoresLayout.createSequentialGroup()
                        .addComponent(lb_titulo_serv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_sub_serv)))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(141, Short.MAX_VALUE))
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
                "Tipo", "Status", "Entrada", "Saída", "Tamanho", "Data Entrega"
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

        btn_esq_senh_pl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_esq_senh_pl.setText("Esqueceu ou deseja alterar sua senha?");
        btn_esq_senh_pl.setBorder(null);
        btn_esq_senh_pl.setBorderPainted(false);
        btn_esq_senh_pl.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout card_form_plLayout = new javax.swing.GroupLayout(card_form_pl);
        card_form_pl.setLayout(card_form_plLayout);
        card_form_plLayout.setHorizontalGroup(
            card_form_plLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card_form_plLayout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addGroup(card_form_plLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lb_login_pl)
                    .addComponent(input_matricula_pl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(input_senha_pl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(separador_pl, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_login_pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_esq_senh_pl))
                .addGap(86, 86, 86))
            .addGroup(card_form_plLayout.createSequentialGroup()
                .addGroup(card_form_plLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card_form_plLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(lb_senha_pl))
                    .addGroup(card_form_plLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(lb_matricula_pl)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        card_form_plLayout.setVerticalGroup(
            card_form_plLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card_form_plLayout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(lb_login_pl)
                .addGap(33, 33, 33)
                .addComponent(lb_matricula_pl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_matricula_pl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lb_senha_pl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input_senha_pl, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(separador_pl, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_login_pl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_esq_senh_pl)
                .addContainerGap(149, Short.MAX_VALUE))
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

        btn_voltar_login_ppa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_voltar_login_ppa.setText("Voltar a tela de login");
        btn_voltar_login_ppa.setBorder(null);
        btn_voltar_login_ppa.setBorderPainted(false);
        btn_voltar_login_ppa.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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
                    .addComponent(btn_voltar_login_ppa)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, card_form_ppaLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lb_senha_ppa))
                    .addComponent(lb_confirmar_senha_ppa, javax.swing.GroupLayout.Alignment.LEADING))
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
                .addGap(18, 18, 18)
                .addComponent(btn_voltar_login_ppa)
                .addContainerGap(149, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_primeiro_acessoLayout = new javax.swing.GroupLayout(panel_primeiro_acesso);
        panel_primeiro_acesso.setLayout(panel_primeiro_acessoLayout);
        panel_primeiro_acessoLayout.setHorizontalGroup(
            panel_primeiro_acessoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_primeiro_acessoLayout.createSequentialGroup()
                .addComponent(img_ppa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        carregaDadosDistribuicao();
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
        carregaDadosServidores(); 
    
        appCardLayout.show(panel_telaInicial, "servidores");
        System.out.println("Mostrando painel Servidores");
        //JOptionPane.showMessageDialog(this, "Painel de Servidores ainda não implementado");
    }//GEN-LAST:event_btn_nav_servidoresActionPerformed

    private void btn_nav_uniformesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nav_uniformesActionPerformed
        // TODO add your handling code here:
        appCardLayout.show(panel_telaInicial, "uniformes");
        System.out.println("Mostrando painel Uniformes");
        //JOptionPane.showMessageDialog(this, "Painel de Uniformes ainda não implementado");
    }//GEN-LAST:event_btn_nav_uniformesActionPerformed

    private void btn_cad_distribuicao_pdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cad_distribuicao_pdActionPerformed
        // TODO add your handling code here:
        FormEntregaDialog dialog = new FormEntregaDialog(this);
        dialog.setVisible(true);
        
        EntregaModel novaEntrega = dialog.getEntregaCriada();
        
        if(novaEntrega != null) {
            boolean sucesso = entregaController.salvarEntrega(novaEntrega);
            
            if(sucesso) {
                JOptionPane.showMessageDialog(this, "Distribuição cadastrada com sucesso!");
                carregaDadosDistribuicao();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao cadastrar a distribuição. Verifique os dados e tente novamente.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            }
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
        this.jd_cadServ_Serv.pack();
        this.jd_cadServ_Serv.setLocationRelativeTo(this);
        this.jd_cadServ_Serv.setVisible(true);
        
        System.out.println("Atualizando a tabela de servidores...");
        carregaDadosServidores();
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
  
    private void btn_salvar_pcadServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvar_pcadServActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salvar_pcadServActionPerformed

    private void tf_nome_pcadServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_nome_pcadServActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_nome_pcadServActionPerformed

    private void tf_telefone_pcadServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_telefone_pcadServActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_telefone_pcadServActionPerformed

    private void btn_cancelar_pcadServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelar_pcadServActionPerformed
        // TODO add your handling code here:
        jd_cadServ_Serv.dispose();
    }//GEN-LAST:event_btn_cancelar_pcadServActionPerformed

    private void jbtn_ativo_pcadServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_ativo_pcadServActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtn_ativo_pcadServActionPerformed

    private void jbtn_inativo_pcadServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn_inativo_pcadServActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtn_inativo_pcadServActionPerformed

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
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_cad_distribuicao_pd;
    private javax.swing.JButton btn_cadastrar_serv;
    private javax.swing.JButton btn_cancelar_pcadServ;
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_esq_senh_pl;
    private javax.swing.JButton btn_login_pl;
    private javax.swing.JButton btn_nav_Inicio;
    private javax.swing.JButton btn_nav_alunos;
    private javax.swing.JButton btn_nav_distribuicao;
    private javax.swing.JButton btn_nav_servidores;
    private javax.swing.JButton btn_nav_uniformes;
    private javax.swing.JButton btn_sair_pn;
    private javax.swing.JButton btn_salvar_pcadServ;
    private javax.swing.JButton btn_salvar_ppa;
    private javax.swing.JButton btn_voltar_login_ppa;
    private com.mycompany.gerenciamento.uniformes.Components.CardPanel card_form_pl;
    private com.mycompany.gerenciamento.uniformes.Components.CardPanel card_form_ppa;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.JLabel img_pl;
    private javax.swing.JLabel img_ppa;
    private javax.swing.JPasswordField input_confirmar_senha_ppa;
    private javax.swing.JTextField input_matricula_pl;
    private javax.swing.JPasswordField input_senha_pl;
    private javax.swing.JPasswordField input_senha_ppa;
    private javax.swing.JDialog jDialog;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JRadioButton jbtn_ativo_pcadServ;
    private javax.swing.JRadioButton jbtn_inativo_pcadServ;
    private javax.swing.JComboBox<String> jc_dep_pcadServ;
    private javax.swing.JComboBox<String> jcb_filtros;
    private javax.swing.JDialog jd_cadServ_Serv;
    private javax.swing.JLabel lb_confirmar_senha_ppa;
    private javax.swing.JLabel lb_dep_pcadServ;
    private javax.swing.JLabel lb_email_pcadServ;
    private javax.swing.JLabel lb_login_pl;
    private javax.swing.JLabel lb_matricula_pcadServ;
    private javax.swing.JLabel lb_matricula_pl;
    private javax.swing.JLabel lb_nome_pcadServ;
    private javax.swing.JLabel lb_redefinir_senha_ppa;
    private javax.swing.JLabel lb_satus_pcadServ;
    private javax.swing.JLabel lb_senha_pl;
    private javax.swing.JLabel lb_senha_ppa;
    private javax.swing.JLabel lb_sobrenome_pcadServ;
    private javax.swing.JLabel lb_sub_serv;
    private javax.swing.JLabel lb_subtitulo_pd;
    private javax.swing.JLabel lb_telefone_pcadServ;
    private javax.swing.JLabel lb_titulo_pcadServ;
    private javax.swing.JLabel lb_titulo_pd;
    private javax.swing.JLabel lb_titulo_serv;
    private javax.swing.JPanel main_container;
    private javax.swing.JLabel nome_sistema;
    private javax.swing.JPanel panelGraficoBarras;
    private javax.swing.JPanel panelGraficoPizza;
    private javax.swing.JPanel panel_aplicacao;
    private javax.swing.JPanel panel_autenticacao;
    private javax.swing.JPanel panel_cadServ;
    private javax.swing.JPanel panel_distribuicao;
    private javax.swing.JPanel panel_login;
    private javax.swing.JPanel panel_navbar;
    private javax.swing.JPanel panel_primeiro_acesso;
    private javax.swing.JPanel panel_telaInicial;
    private javax.swing.JSeparator separador_pl;
    private javax.swing.JSeparator separador_ppa;
    private javax.swing.JLabel subtitulo;
    private javax.swing.JTable tabela_uniformes;
    private javax.swing.JTable tb_distribuicao;
    private javax.swing.JTable tb_servidores;
    private javax.swing.JTextField tf_email_pcadServ;
    private javax.swing.JTextField tf_matricula_pcadServ;
    private javax.swing.JTextField tf_nome_pcadServ;
    private javax.swing.JTextField tf_sobrenome_pcadServ;
    private javax.swing.JTextField tf_telefone_pcadServ;
    private javax.swing.JTextField tx_pesquisa;
    // End of variables declaration//GEN-END:variables
}
