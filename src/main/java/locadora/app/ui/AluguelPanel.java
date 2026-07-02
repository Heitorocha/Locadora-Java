package locadora.app.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import locadora.exception.LocadoraException;
import locadora.model.Cliente;
import locadora.model.Veiculo;
import locadora.service.Locadora;

public class AluguelPanel extends JPanel {

    private final Locadora locadora;
    private JComboBox<Cliente> comboCliente;
    private JComboBox<Veiculo> comboVeiculo;
    private JCheckBox checkSeguro;
    private JTextField campoDias;
    private JLabel labelValor;

    public AluguelPanel(Locadora locadora) {

        this.locadora = locadora;
        setLayout(new GridBagLayout());

        locadora.adicionarListener(this::atualizarDados);
        criarComponentes();
    }

    private void criarComponentes() {

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        JLabel labelCliente = new JLabel("Cliente:");
        comboCliente = new JComboBox<>(locadora.getClientes().toArray(new Cliente[0]));

        JLabel labelVeiculo = new JLabel("Veículo:");
        comboVeiculo = new JComboBox<>(locadora.getVeiculos().toArray(new Veiculo[0]));

        JLabel labelDias = new JLabel("Dias:");
        campoDias = new JTextField(10);

        checkSeguro = new JCheckBox("Adicionar seguro (+25%)");
        JLabel labelInfoSeguro = new JLabel("O seguro aumenta o valor total em 25%.");
        labelValor = new JLabel("Valor estimado: R$ 0,00");

        JButton botaoAluguel = new JButton("Realizar Aluguel");

        c.gridx = 0;
        c.gridy = 0;
        add(labelCliente, c);

        c.gridx = 1;
        add(comboCliente, c);

        c.gridx = 0;
        c.gridy = 1;
        add(labelVeiculo, c);

        c.gridx = 1;
        add(comboVeiculo, c);

        c.gridx = 0;
        c.gridy = 2;
        add(labelDias, c);

        c.gridx = 1;
        add(campoDias, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        add(checkSeguro, c);

        c.gridy = 4;
        add(labelInfoSeguro, c);

        c.gridy = 5;
        add(labelValor, c);

        c.gridy = 6;
        add(botaoAluguel, c);

        aplicarPermissaoCliente();
        checkSeguro.setEnabled(false);
        atualizarValorEstimado();

        comboVeiculo.addActionListener(e -> {
            atualizarEstadoSeguro();
            atualizarValorEstimado();
        });

        checkSeguro.addActionListener(e -> atualizarValorEstimado());

        campoDias.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                atualizarValorEstimado();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                atualizarValorEstimado();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                atualizarValorEstimado();
            }
        });

        botaoAluguel.addActionListener(e -> {
            try {
                Cliente cliente = obterClienteSelecionado();
                Veiculo veiculo = (Veiculo) comboVeiculo.getSelectedItem();
                int dias = Integer.parseInt(campoDias.getText());

                boolean seguroSelecionado = checkSeguro.isSelected() && veiculo.temSeguro();

                locadora.alugarVeiculo(cliente, veiculo, dias, seguroSelecionado);

                String mensagem = seguroSelecionado
                        ? String.format("Aluguel realizado com sucesso! Total com seguro: R$ %.2f", veiculo.calcularValorLocacao(dias, true))
                        : String.format("Aluguel realizado com sucesso! Total: R$ %.2f", veiculo.calcularValorLocacao(dias, false));

                JOptionPane.showMessageDialog(this, mensagem);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um número válido de dias.");
            } catch (LocadoraException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
    }

    private void atualizarDados() {
        atualizarCombos();
        aplicarPermissaoCliente();
        atualizarEstadoSeguro();
        atualizarValorEstimado();
    }

    private void atualizarCombos() {
        comboCliente.setModel(new DefaultComboBoxModel<>(locadora.getClientes().toArray(new Cliente[0])));
        comboVeiculo.setModel(new DefaultComboBoxModel<>(locadora.getVeiculos().toArray(new Veiculo[0])));
    }

    private void aplicarPermissaoCliente() {
        if (locadora.getTipoUsuarioLogado() == Locadora.TipoUsuario.CLIENTE) {
            comboCliente.setEnabled(false);
            comboCliente.setSelectedItem(locadora.getClienteLogado());
        } else {
            comboCliente.setEnabled(true);
        }
    }

    private Cliente obterClienteSelecionado() {
        if (locadora.getTipoUsuarioLogado() == Locadora.TipoUsuario.CLIENTE) {
            return locadora.getClienteLogado();
        }

        return (Cliente) comboCliente.getSelectedItem();
    }

    private void atualizarEstadoSeguro() {
        Veiculo veiculo = (Veiculo) comboVeiculo.getSelectedItem();
        if (veiculo == null) {
            checkSeguro.setEnabled(false);
            checkSeguro.setSelected(false);
        } else {
            checkSeguro.setEnabled(veiculo.temSeguro());
            if (!veiculo.temSeguro()) {
                checkSeguro.setSelected(false);
            }
        }
    }

    private void atualizarValorEstimado() {
        try {
            Veiculo veiculo = (Veiculo) comboVeiculo.getSelectedItem();
            int dias = Integer.parseInt(campoDias.getText().trim());

            if (veiculo == null || dias <= 0) {
                labelValor.setText("Valor estimado: R$ 0,00");
                return;
            }

            boolean seguroSelecionado = checkSeguro.isSelected() && veiculo.temSeguro();
            double total = veiculo.calcularValorLocacao(dias, seguroSelecionado);
            labelValor.setText(String.format("Valor estimado: R$ %.2f", total));

        } catch (NumberFormatException ex) {
            labelValor.setText("Valor estimado: R$ 0,00");
        }
    }
}