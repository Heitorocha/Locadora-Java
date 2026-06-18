package locadora.app;

import locadora.exception.LocadoraException;
import locadora.model.Carro;
import locadora.model.Cliente;
import locadora.model.Funcionario;
import locadora.model.Moto;
import locadora.model.Veiculo;
import locadora.model.Aluguel;
import locadora.service.Locadora;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Locadora locadora = new Locadora();
        locadora.adicionarCliente(new Cliente("12345678900", "Fernanda", 1200.0));
        locadora.adicionarCliente(new Cliente("98765432100", "Ricardo", 850.0));
        locadora.adicionarFuncionario(new Funcionario("11223344556", "Patrícia", 3200.0));
        locadora.adicionarFuncionario(new Funcionario("22334455667", "Lucas", 2800.0));
        locadora.adicionarVeiculo(new Carro("ABC-1234", "Sedan", 150.0, "Preto", "Toyota", true));
        locadora.adicionarVeiculo(new Moto("XYZ-4321", "Sport", 90.0, "Vermelho", "Honda", false));

        SwingUtilities.invokeLater(() -> createAndShowGui(locadora));
    }

    private static void createAndShowGui(Locadora locadora) {
        JFrame frame = new JFrame("Locadora de Veículos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(820, 620);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.BOTH;

        JTextArea outputArea = new JTextArea(16, 64);
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);

        DefaultComboBoxModel<Cliente> clienteModel = new DefaultComboBoxModel<>(locadora.getClientes().toArray(new Cliente[0]));
        DefaultComboBoxModel<Veiculo> veiculoModel = new DefaultComboBoxModel<>(locadora.getVeiculos().toArray(new Veiculo[0]));

        JPanel rentalPanel = new JPanel(new GridBagLayout());
        rentalPanel.setBorder(BorderFactory.createTitledBorder("Aluguel de veículo"));
        GridBagConstraints rc = new GridBagConstraints();
        rc.insets = new Insets(6, 6, 6, 6);
        rc.anchor = GridBagConstraints.WEST;
        rc.fill = GridBagConstraints.HORIZONTAL;

        rc.gridx = 0;
        rc.gridy = 0;
        rentalPanel.add(new JLabel("Cliente:"), rc);

        JComboBox<Cliente> clienteCombo = new JComboBox<>(clienteModel);
        rc.gridx = 1;
        rentalPanel.add(clienteCombo, rc);

        rc.gridx = 0;
        rc.gridy = 1;
        rentalPanel.add(new JLabel("Veículo:"), rc);

        JComboBox<Veiculo> veiculoCombo = new JComboBox<>(veiculoModel);
        rc.gridx = 1;
        rentalPanel.add(veiculoCombo, rc);

        rc.gridx = 0;
        rc.gridy = 2;
        rentalPanel.add(new JLabel("Dias de aluguel:"), rc);

        JTextField diasField = new JTextField(6);
        rc.gridx = 1;
        rentalPanel.add(diasField, rc);

        JButton rentButton = new JButton("Realizar aluguel");
        JButton reportButton = new JButton("Gerar relatório");
        JButton exitButton = new JButton("Sair");
        JPanel rentalButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        rentalButtonPanel.add(rentButton);
        rentalButtonPanel.add(reportButton);
        rentalButtonPanel.add(exitButton);

        rc.gridx = 0;
        rc.gridy = 3;
        rc.gridwidth = 2;
        rentalPanel.add(rentalButtonPanel, rc);
        rc.gridwidth = 1;

        JPanel employeePanel = new JPanel(new GridBagLayout());
        employeePanel.setBorder(BorderFactory.createTitledBorder("Operações de funcionário"));
        GridBagConstraints ec = new GridBagConstraints();
        ec.insets = new Insets(6, 6, 6, 6);
        ec.anchor = GridBagConstraints.WEST;
        ec.fill = GridBagConstraints.HORIZONTAL;

        ec.gridx = 0;
        ec.gridy = 0;
        employeePanel.add(new JLabel("Ação:"), ec);

        JComboBox<String> employeeActionCombo = new JComboBox<>(new String[]{"Cadastrar cliente", "Cadastrar veículo"});
        ec.gridx = 1;
        employeePanel.add(employeeActionCombo, ec);

        ec.gridx = 0;
        ec.gridy = 1;
        JLabel regCpfLabel = new JLabel("CPF:");
        employeePanel.add(regCpfLabel, ec);

        JTextField regCpfField = new JTextField(18);
        ec.gridx = 1;
        employeePanel.add(regCpfField, ec);

        ec.gridx = 0;
        ec.gridy = 2;
        JLabel regNameLabel = new JLabel("Nome:");
        employeePanel.add(regNameLabel, ec);

        JTextField regNameField = new JTextField(18);
        ec.gridx = 1;
        employeePanel.add(regNameField, ec);

        JLabel regSaldoLabel = new JLabel("Saldo inicial:");
        ec.gridx = 0;
        ec.gridy = 3;
        employeePanel.add(regSaldoLabel, ec);

        JTextField regSaldoField = new JTextField(18);
        ec.gridx = 1;
        employeePanel.add(regSaldoField, ec);

        JLabel vehicleTypeLabel = new JLabel("Tipo de veículo:");
        ec.gridx = 0;
        ec.gridy = 4;
        employeePanel.add(vehicleTypeLabel, ec);

        JComboBox<String> vehicleTypeCombo = new JComboBox<>(new String[]{"Carro", "Moto"});
        ec.gridx = 1;
        employeePanel.add(vehicleTypeCombo, ec);

        JLabel placaLabel = new JLabel("Placa:");
        ec.gridx = 0;
        ec.gridy = 5;
        employeePanel.add(placaLabel, ec);

        JTextField placaField = new JTextField(18);
        ec.gridx = 1;
        employeePanel.add(placaField, ec);

        JLabel modeloLabel = new JLabel("Modelo:");
        ec.gridx = 0;
        ec.gridy = 6;
        employeePanel.add(modeloLabel, ec);

        JTextField modeloField = new JTextField(18);
        ec.gridx = 1;
        employeePanel.add(modeloField, ec);

        JLabel diariaLabel = new JLabel("Diária:");
        ec.gridx = 0;
        ec.gridy = 7;
        employeePanel.add(diariaLabel, ec);

        JTextField diariaField = new JTextField(18);
        ec.gridx = 1;
        employeePanel.add(diariaField, ec);

        JLabel corLabel = new JLabel("Cor:");
        ec.gridx = 0;
        ec.gridy = 8;
        employeePanel.add(corLabel, ec);

        JTextField corField = new JTextField(18);
        ec.gridx = 1;
        employeePanel.add(corField, ec);

        JLabel marcaLabel = new JLabel("Marca:");
        ec.gridx = 0;
        ec.gridy = 9;
        employeePanel.add(marcaLabel, ec);

        JTextField marcaField = new JTextField(18);
        ec.gridx = 1;
        employeePanel.add(marcaField, ec);

        JCheckBox seguroCheck = new JCheckBox("Seguro");
        ec.gridx = 1;
        ec.gridy = 10;
        employeePanel.add(seguroCheck, ec);

        JButton saveButton = new JButton("Salvar cliente");
        JButton clearButton = new JButton("Limpar campos");
        JPanel employeeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        employeeButtonPanel.add(saveButton);
        employeeButtonPanel.add(clearButton);

        ec.gridx = 0;
        ec.gridy = 11;
        ec.gridwidth = 2;
        employeePanel.add(employeeButtonPanel, ec);
        ec.gridwidth = 1;

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Aluguel", rentalPanel);
        tabs.addTab("Funcionário", employeePanel);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 0.0;
        panel.add(tabs, c);

        c.gridy = 1;
        c.weighty = 1.0;
        panel.add(scrollPane, c);

        frame.setContentPane(panel);
        frame.setVisible(true);

        updateEmployeeFields(employeeActionCombo, regCpfLabel, regCpfField,
                regNameLabel, regNameField, regSaldoLabel, regSaldoField,
                vehicleTypeLabel, vehicleTypeCombo, placaLabel, placaField,
                modeloLabel, modeloField, diariaLabel, diariaField,
                corLabel, corField, marcaLabel, marcaField, seguroCheck, saveButton);

        reportButton.addActionListener(e -> appendReport(locadora, outputArea));
        exitButton.addActionListener(e -> System.exit(0));
        rentButton.addActionListener(e -> onRent(locadora, clienteCombo, veiculoCombo, diasField, outputArea));
        employeeActionCombo.addActionListener(e -> updateEmployeeFields(employeeActionCombo, regCpfLabel, regCpfField,
                regNameLabel, regNameField, regSaldoLabel, regSaldoField,
                vehicleTypeLabel, vehicleTypeCombo, placaLabel, placaField,
                modeloLabel, modeloField, diariaLabel, diariaField,
                corLabel, corField, marcaLabel, marcaField, seguroCheck, saveButton));
        saveButton.addActionListener(e -> onRegister(locadora, employeeActionCombo, clienteModel, veiculoModel,
                regCpfField, regNameField, regSaldoField, vehicleTypeCombo, placaField,
                modeloField, diariaField, corField, marcaField, seguroCheck, outputArea));
        clearButton.addActionListener(e -> {
            regCpfField.setText("");
            regNameField.setText("");
            regSaldoField.setText("");
            placaField.setText("");
            modeloField.setText("");
            diariaField.setText("");
            corField.setText("");
            marcaField.setText("");
            seguroCheck.setSelected(false);
        });

        appendInitialInfo(locadora, outputArea);
    }

    private static void appendInitialInfo(Locadora locadora, JTextArea outputArea) {
        outputArea.append("=== Bem-vindo à Locadora de Veículos ===\n");
        outputArea.append("Funcionário pode cadastrar clientes e veículos nas abas.\n");
        outputArea.append("Clientes existentes:\n");
        for (Cliente cliente : locadora.getClientes()) {
            outputArea.append(" - " + cliente + "\n");
        }
        outputArea.append("Veículos existentes:\n");
        for (Veiculo veiculo : locadora.getVeiculos()) {
            outputArea.append(" - " + veiculo + "\n");
        }
        outputArea.append("\n");
    }

    private static void updateEmployeeFields(JComboBox<String> actionCombo,
                                             JLabel regCpfLabel,
                                             JTextField regCpfField,
                                             JLabel regNameLabel,
                                             JTextField regNameField,
                                             JLabel saldoLabel,
                                             JTextField saldoField,
                                             JLabel typeLabel,
                                             JComboBox<String> vehicleTypeCombo,
                                             JLabel placaLabel,
                                             JTextField placaField,
                                             JLabel modeloLabel,
                                             JTextField modeloField,
                                             JLabel diariaLabel,
                                             JTextField diariaField,
                                             JLabel corLabel,
                                             JTextField corField,
                                             JLabel marcaLabel,
                                             JTextField marcaField,
                                             JCheckBox seguroCheck,
                                             JButton saveButton) {
        String action = (String) actionCombo.getSelectedItem();
        boolean isRegisterClient = "Cadastrar cliente".equals(action);

        regCpfLabel.setVisible(isRegisterClient);
        regCpfField.setVisible(isRegisterClient);
        regNameLabel.setVisible(isRegisterClient);
        regNameField.setVisible(isRegisterClient);
        saldoLabel.setVisible(isRegisterClient);
        saldoField.setVisible(isRegisterClient);

        typeLabel.setVisible(!isRegisterClient);
        vehicleTypeCombo.setVisible(!isRegisterClient);
        placaLabel.setVisible(!isRegisterClient);
        placaField.setVisible(!isRegisterClient);
        modeloLabel.setVisible(!isRegisterClient);
        modeloField.setVisible(!isRegisterClient);
        diariaLabel.setVisible(!isRegisterClient);
        diariaField.setVisible(!isRegisterClient);
        corLabel.setVisible(!isRegisterClient);
        corField.setVisible(!isRegisterClient);
        marcaLabel.setVisible(!isRegisterClient);
        marcaField.setVisible(!isRegisterClient);
        seguroCheck.setVisible(!isRegisterClient);

        saveButton.setText(isRegisterClient ? "Salvar cliente" : "Salvar veículo");
    }

    private static void onRegister(Locadora locadora,
                                   JComboBox<String> actionCombo,
                                   DefaultComboBoxModel<Cliente> clienteModel,
                                   DefaultComboBoxModel<Veiculo> veiculoModel,
                                   JTextField regCpfField,
                                   JTextField regNameField,
                                   JTextField regSaldoField,
                                   JComboBox<String> vehicleTypeCombo,
                                   JTextField placaField,
                                   JTextField modeloField,
                                   JTextField diariaField,
                                   JTextField corField,
                                   JTextField marcaField,
                                   JCheckBox seguroCheck,
                                   JTextArea outputArea) {
        String action = (String) actionCombo.getSelectedItem();
        if ("Cadastrar cliente".equals(action)) {
            String cpf = regCpfField.getText().trim();
            String nome = regNameField.getText().trim();
            String saldoTexto = regSaldoField.getText().trim();

            if (cpf.isEmpty() || nome.isEmpty() || saldoTexto.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha CPF, nome e saldo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double saldo;
            try {
                saldo = Double.parseDouble(saldoTexto.replace(',', '.'));
                if (saldo < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Digite um saldo válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente cliente = new Cliente(cpf, nome, saldo);
            locadora.adicionarCliente(cliente);
            clienteModel.addElement(cliente);
            outputArea.append("Cliente cadastrado: " + cliente + "\n\n");
        } else {
            String tipoVeiculo = (String) vehicleTypeCombo.getSelectedItem();
            String placa = placaField.getText().trim();
            String modelo = modeloField.getText().trim();
            String diariaTexto = diariaField.getText().trim();
            String cor = corField.getText().trim();
            String marca = marcaField.getText().trim();
            boolean seguro = seguroCheck.isSelected();

            if (placa.isEmpty() || modelo.isEmpty() || diariaTexto.isEmpty() || cor.isEmpty() || marca.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os dados do veículo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double diaria;
            try {
                diaria = Double.parseDouble(diariaTexto.replace(',', '.'));
                if (diaria <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Digite uma diária válida.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Veiculo veiculo;
            if ("Moto".equals(tipoVeiculo)) {
                veiculo = new Moto(placa, modelo, diaria, cor, marca, seguro);
            } else {
                veiculo = new Carro(placa, modelo, diaria, cor, marca, seguro);
            }

            locadora.adicionarVeiculo(veiculo);
            veiculoModel.addElement(veiculo);
            outputArea.append("Veículo cadastrado: " + veiculo + "\n\n");
        }
    }

    private static void onRent(Locadora locadora,
                               JComboBox<Cliente> clienteCombo,
                               JComboBox<Veiculo> veiculoCombo,
                               JTextField diasField,
                               JTextArea outputArea) {
        Cliente cliente = (Cliente) clienteCombo.getSelectedItem();
        Veiculo veiculo = (Veiculo) veiculoCombo.getSelectedItem();
        String diasTexto = diasField.getText().trim();

        if (cliente == null || veiculo == null || diasTexto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha cliente, veículo e dias.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int dias;
        try {
            dias = Integer.parseInt(diasTexto);
            if (dias <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Digite um número inteiro positivo de dias.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            locadora.alugarVeiculo(cliente, veiculo, dias);
            outputArea.append(String.format("Aluguel concluído: %s alugou %s por %d dias. Total: R$ %.2f\n",
                    cliente.getNome(), veiculo.getTipo(), dias, veiculo.getValorDiaria() * dias));
            outputArea.append(String.format("Saldo restante do cliente %s: R$ %.2f\n\n",
                    cliente.getNome(), cliente.getSaldo()));
        } catch (LocadoraException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro no aluguel", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void appendReport(Locadora locadora, JTextArea outputArea) {
        outputArea.append("=== Relatório da locadora ===\n");
        outputArea.append("Veículos disponíveis:\n");
        for (Veiculo veiculo : locadora.getVeiculos()) {
            outputArea.append(" - " + veiculo + "\n");
        }

        outputArea.append("Clientes cadastrados:\n");
        for (Cliente cliente : locadora.getClientes()) {
            outputArea.append(" - " + cliente + "\n");
        }

        outputArea.append("Aluguéis realizados:\n");
        for (Aluguel aluguel : locadora.getAlugueis()) {
            outputArea.append(" - " + aluguel + "\n");
        }

        outputArea.append("\n");
    }
}
