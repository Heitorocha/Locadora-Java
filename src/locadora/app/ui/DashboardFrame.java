package locadora.app.ui;

import locadora.service.Locadora;

import javax.swing.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame(Locadora locadora) {

        setTitle("Locadora de Veículos");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Painel Dashboard
        DashboardPanel dashboardPanel =
                new DashboardPanel(locadora);

        // Painel Clientes
        ClientePanel clientePanel =
                new ClientePanel(locadora);

        // Painel Veículos
        VeiculoPanel veiculoPanel =
                new VeiculoPanel(locadora);

        // Painel Relatórios
        RelatorioPanel relatorioPanel =
                new RelatorioPanel(locadora);

        // Painel Aluguéis
        AluguelPanel aluguelPanel =
                new AluguelPanel(locadora);

        // Criação das abas
        JTabbedPane abas =
                new JTabbedPane();

        abas.addTab(
                "Dashboard",
                dashboardPanel);

        abas.addTab(
                "Clientes",
                clientePanel);

        abas.addTab(
                "Veículos",
                veiculoPanel);

        abas.addTab(
                "Aluguéis",
                aluguelPanel);

        abas.addTab(
                "Relatórios",
                relatorioPanel);

        // Atualiza ao trocar de aba
        abas.addChangeListener(e -> {

            clientePanel.atualizarTabela();

            veiculoPanel.atualizarTabela();

            dashboardPanel.atualizarDashboard();
        });

        add(abas);

        // Atualização automática do sistema
        Timer timer = new Timer(1000, e -> {

            clientePanel.atualizarTabela();

            veiculoPanel.atualizarTabela();

            dashboardPanel.atualizarDashboard();
        });

        timer.start();

        setVisible(true);
    }
}