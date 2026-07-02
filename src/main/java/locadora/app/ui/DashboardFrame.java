package locadora.app.ui;

import java.awt.*;
import javax.swing.*;
import locadora.service.Locadora;

public class DashboardFrame extends JFrame {

    public DashboardFrame(Locadora locadora) {

        setTitle("Locadora de Veículos");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        DashboardPanel dashboardPanel = new DashboardPanel(locadora);

        final ClientePanel[] clientePanelRef = new ClientePanel[1];
        final VeiculoPanel[] veiculoPanelRef = new VeiculoPanel[1];
        final RelatorioPanel[] relatorioPanelRef = new RelatorioPanel[1];
        Locadora.TipoUsuario tipoUsuario = locadora.getTipoUsuarioLogado();

        AluguelPanel aluguelPanel = new AluguelPanel(locadora);

        JTabbedPane abas = new JTabbedPane();

        abas.addTab("Dashboard", dashboardPanel);

        if (tipoUsuario == Locadora.TipoUsuario.FUNCIONARIO) {
            clientePanelRef[0] = new ClientePanel(locadora);
            veiculoPanelRef[0] = new VeiculoPanel(locadora);
            relatorioPanelRef[0] = new RelatorioPanel(locadora);

            abas.addTab("Clientes", clientePanelRef[0]);
            abas.addTab("Veículos", veiculoPanelRef[0]);
        }

        abas.addTab("Aluguéis", aluguelPanel);

        if (tipoUsuario == Locadora.TipoUsuario.FUNCIONARIO) {
            abas.addTab("Relatórios", relatorioPanelRef[0]);
        }

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botaoSair = new JButton("Sair");
        botaoSair.addActionListener(e -> {
            locadora.logout();
            dispose();
            new LoginFrame(locadora);
        });
        topo.add(botaoSair);

        add(topo, BorderLayout.NORTH);
        add(abas, BorderLayout.CENTER);

        abas.addChangeListener(e -> {
            if (clientePanelRef[0] != null) {
                clientePanelRef[0].atualizarTabela();
            }
            if (veiculoPanelRef[0] != null) {
                veiculoPanelRef[0].atualizarTabela();
            }
            dashboardPanel.atualizarDashboard();
        });

        Timer timer = new Timer(1000, e -> {
            if (clientePanelRef[0] != null) {
                clientePanelRef[0].atualizarTabela();
            }
            if (veiculoPanelRef[0] != null) {
                veiculoPanelRef[0].atualizarTabela();
            }
            dashboardPanel.atualizarDashboard();
        });

        timer.start();

        setVisible(true);
    }
}