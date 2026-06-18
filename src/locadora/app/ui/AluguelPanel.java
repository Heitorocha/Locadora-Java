package locadora.app.ui;

import locadora.exception.LocadoraException;
import locadora.model.Cliente;
import locadora.model.Veiculo;
import locadora.service.Locadora;

import javax.swing.*;
import java.awt.*;

public class AluguelPanel extends JPanel {

    public AluguelPanel(Locadora locadora) {

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);

        JLabel clienteLabel = new JLabel("Cliente:");
        JComboBox<Cliente> clienteCombo =
                new JComboBox<>(
                        locadora.getClientes().toArray(new Cliente[0])
                );

        JLabel veiculoLabel = new JLabel("Veículo:");
        JComboBox<Veiculo> veiculoCombo =
                new JComboBox<>(
                        locadora.getVeiculos().toArray(new Veiculo[0])
                );

        JLabel diasLabel = new JLabel("Dias:");
        JTextField diasField = new JTextField(10);

        JButton aluguelButton =
                new JButton("Realizar Aluguel");

        c.gridx = 0;
        c.gridy = 0;
        add(clienteLabel, c);

        c.gridx = 1;
        add(clienteCombo, c);

        c.gridx = 0;
        c.gridy = 1;
        add(veiculoLabel, c);

        c.gridx = 1;
        add(veiculoCombo, c);

        c.gridx = 0;
        c.gridy = 2;
        add(diasLabel, c);

        c.gridx = 1;
        add(diasField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        add(aluguelButton, c);

        aluguelButton.addActionListener(e -> {

            try {

                Cliente cliente =
                        (Cliente) clienteCombo.getSelectedItem();

                Veiculo veiculo =
                        (Veiculo) veiculoCombo.getSelectedItem();

                int dias =
                        Integer.parseInt(diasField.getText());

                locadora.alugarVeiculo(
                        cliente,
                        veiculo,
                        dias
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Aluguel realizado com sucesso!"
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Digite um número válido de dias."
                );

            } catch (LocadoraException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        });
    }
}