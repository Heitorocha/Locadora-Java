package locadora.app.ui;

import locadora.model.Aluguel;
import locadora.model.Cliente;
import locadora.model.Veiculo;
import locadora.service.Locadora;

import javax.swing.*;
import java.awt.*;

public class RelatorioPanel extends JPanel {

    private final Locadora locadora;
    private final JComboBox<String> tipoRelatorio;
    private final JComboBox<Object> filtroCombo;
    private final JTextArea resultadoArea;

    public RelatorioPanel(Locadora locadora) {

        this.locadora = locadora;

        setLayout(new BorderLayout(10, 10));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT));

        tipoRelatorio = new JComboBox<>(new String[]{
                "Resumo Geral",
                "Todos os Aluguéis",
                "Aluguéis por Cliente",
                "Aluguéis por Veículo",
                "Clientes Cadastrados",
                "Veículos Cadastrados"
        });

        filtroCombo = new JComboBox<>();
        filtroCombo.setPreferredSize(new Dimension(250, 25));

        JButton gerarBtn = new JButton("Gerar Relatório");

        topo.add(new JLabel("Tipo:"));
        topo.add(tipoRelatorio);

        topo.add(new JLabel("Filtro:"));
        topo.add(filtroCombo);

        topo.add(gerarBtn);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        atualizarFiltro();

        tipoRelatorio.addActionListener(e -> atualizarFiltro());

        gerarBtn.addActionListener(e -> gerarRelatorio());
    }

    private void atualizarFiltro() {

        filtroCombo.removeAllItems();

        String tipo = (String) tipoRelatorio.getSelectedItem();

        if ("Aluguéis por Cliente".equals(tipo)) {

            for (Cliente cliente : locadora.getClientes()) {
                filtroCombo.addItem(cliente);
            }

            filtroCombo.setEnabled(true);

        } else if ("Aluguéis por Veículo".equals(tipo)) {

            for (Veiculo veiculo : locadora.getVeiculos()) {
                filtroCombo.addItem(veiculo);
            }

            filtroCombo.setEnabled(true);

        } else {

            filtroCombo.setEnabled(false);
        }
    }

    private void gerarRelatorio() {

        resultadoArea.setText("");

        String tipo = (String) tipoRelatorio.getSelectedItem();

        switch (tipo) {

            case "Resumo Geral":

                resultadoArea.append("=== RESUMO GERAL ===\n\n");

                resultadoArea.append("Clientes cadastrados: "
                        + locadora.getClientes().size() + "\n");

                resultadoArea.append("Veículos cadastrados: "
                        + locadora.getVeiculos().size() + "\n");

                resultadoArea.append("Aluguéis realizados: "
                        + locadora.getAlugueis().size() + "\n");

                break;

            case "Todos os Aluguéis":

                resultadoArea.append("=== TODOS OS ALUGUÉIS ===\n\n");

                for (Aluguel aluguel : locadora.getAlugueis()) {
                    resultadoArea.append(aluguel + "\n");
                }

                break;

            case "Clientes Cadastrados":

                resultadoArea.append("=== CLIENTES ===\n\n");

                for (Cliente cliente : locadora.getClientes()) {
                    resultadoArea.append(cliente + "\n");
                }

                break;

            case "Veículos Cadastrados":

                resultadoArea.append("=== VEICULOS ===\n\n");

                for (Veiculo veiculo : locadora.getVeiculos()) {
                    resultadoArea.append(veiculo + "\n");
                }

                break;

            case "Aluguéis por Cliente":

                Cliente cliente =
                        (Cliente) filtroCombo.getSelectedItem();

                resultadoArea.append(
                        "=== ALUGUÉIS DO CLIENTE ===\n\n");

                for (Aluguel aluguel : locadora.getAlugueis()) {

                    if (aluguel.getCliente().equals(cliente)) {

                        resultadoArea.append(
                                aluguel + "\n");
                    }
                }

                break;

            case "Aluguéis por Veículo":

                Veiculo veiculo =
                        (Veiculo) filtroCombo.getSelectedItem();

                resultadoArea.append(
                        "=== ALUGUEIS DO VEICULO ===\n\n");

                for (Aluguel aluguel : locadora.getAlugueis()) {

                    if (aluguel.getVeiculo().equals(veiculo)) {

                        resultadoArea.append(
                                aluguel + "\n");
                    }
                }

                break;
        }
    }
}