package locadora.app.ui;

import locadora.service.Locadora;

import javax.swing.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame(Locadora locadora,
                          Locadora.TipoUsuario tipoUsuario) {

        setTitle("Locadora de Veículos");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        DashboardPanel dashboardPanel =
                new DashboardPanel(locadora);

        final ClientePanel[] clientePanelRef = new ClientePanel[1];
        final VeiculoPanel[] veiculoPanelRef = new VeiculoPanel[1];
        final RelatorioPanel[] relatorioPanelRef = new RelatorioPanel[1];

        AluguelPanel aluguelPanel =
                new AluguelPanel(locadora);

        JTabbedPane abas =
                new JTabbedPane();

        abas.addTab(
                "Dashboard",
                dashboardPanel);

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

        abas.addChangeListener(e -> {
            if (clientePanelRef[0] != null) {
                clientePanelRef[0].atualizarTabela();
            }
            if (veiculoPanelRef[0] != null) {
                veiculoPanelRef[0].atualizarTabela();
            }
            dashboardPanel.atualizarDashboard();
        });

        add(abas);

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