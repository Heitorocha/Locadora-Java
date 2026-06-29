package locadora.app.ui;

import locadora.model.Aluguel;
import locadora.model.Veiculo;
import locadora.service.Locadora;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private final Locadora locadora;

    private JLabel clientesLabel;
    private JLabel veiculosLabel;
    private JLabel disponiveisLabel;
    private JLabel alugadosLabel;
    private JLabel alugueisLabel;

    private JTextArea historicoArea;

    public DashboardPanel(Locadora locadora) {

        this.locadora = locadora;

        setLayout(new BorderLayout(15, 15));

        criarPainelResumo();
        criarPainelHistorico();

        atualizarDashboard();
    }

    /**
     * Cria os indicadores principais do sistema.
     */
    private void criarPainelResumo() {

        JPanel resumoPanel = new JPanel(
                new GridLayout(5, 1, 10, 10));

        resumoPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Resumo da Locadora"));

        clientesLabel = new JLabel();
        veiculosLabel = new JLabel();
        disponiveisLabel = new JLabel();
        alugadosLabel = new JLabel();
        alugueisLabel = new JLabel();

        resumoPanel.add(clientesLabel);
        resumoPanel.add(veiculosLabel);
        resumoPanel.add(disponiveisLabel);
        resumoPanel.add(alugadosLabel);
        resumoPanel.add(alugueisLabel);

        add(resumoPanel, BorderLayout.NORTH);
    }

    /**
     * Cria a área que exibe os últimos aluguéis.
     */
    private void criarPainelHistorico() {

        historicoArea = new JTextArea();

        historicoArea.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(historicoArea);

        scroll.setBorder(
                BorderFactory.createTitledBorder(
                        "Últimos Aluguéis"));

        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Atualiza todas as informações do dashboard.
     */
    public void atualizarDashboard() {

        int disponiveis = 0;
        int alugados = 0;

        for (Veiculo veiculo : locadora.getVeiculos()) {

            if (veiculo.isDisponivel()) {
                disponiveis++;
            } else {
                alugados++;
            }
        }

        clientesLabel.setText(
                "[P] Clientes cadastrados: "
                        + locadora.getClientes().size());

        veiculosLabel.setText(
                "[Car] Veiculos cadastrados: "
                        + locadora.getVeiculos().size());

        disponiveisLabel.setText(
                "[OK] Veiculos disponiveis: "
                        + disponiveis);

        alugadosLabel.setText(
                "[X] Veiculos alugados: "
                        + alugados);

        alugueisLabel.setText(
                "[PDF] Total de alugueis: "
                        + locadora.getAlugueis().size());

        historicoArea.setText("");

        if (locadora.getAlugueis().isEmpty()) {

            historicoArea.setText(
                    "Nenhum aluguel realizado.");
            return;
        }

        int inicio = Math.max(
                0,
                locadora.getAlugueis().size() - 5);

        for (int i = inicio;
             i < locadora.getAlugueis().size();
             i++) {

            Aluguel aluguel =
                    locadora.getAlugueis().get(i);

            historicoArea.append(
                    aluguel.toString() + "\n");
        }
    }
}