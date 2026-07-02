package locadora.app.ui;

import locadora.model.Carro;
import locadora.model.Moto;
import locadora.model.Veiculo;
import locadora.service.Locadora;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VeiculoPanel extends JPanel {

    private final Locadora locadora;
    private DefaultTableModel model;
    private JTable tabela;
    private JComboBox<String> tipoCombo;
    private JTextField placaField;
    private JTextField modeloField;
    private JTextField marcaField;
    private JTextField corField;
    private JTextField diariaField;
    private JCheckBox seguroCheck;
    private Veiculo veiculoSelecionado;

    public VeiculoPanel(Locadora locadora) {

        this.locadora = locadora;

        setLayout(new BorderLayout(10, 10));

        criarFormulario();
        criarTabela();

        atualizarTabela();
    }

    private void criarFormulario() {

        JPanel formulario =
                new JPanel(new GridBagLayout());

        formulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Cadastro de Veículos"));

        GridBagConstraints c =
                new GridBagConstraints();

        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        tipoCombo =
                new JComboBox<>(
                        new String[]{"Carro", "Moto"});

        placaField = new JTextField(20);
        modeloField = new JTextField(20);
        marcaField = new JTextField(20);
        corField = new JTextField(20);
        diariaField = new JTextField(20);

        seguroCheck =
                new JCheckBox(
                        "Disponibilizar seguro para locação (+25%)");

        JButton salvarBtn =
                new JButton("Salvar");

        JButton limparBtn =
                new JButton("Limpar");

        JButton excluirBtn =
                new JButton("Excluir");

        c.gridx = 0;
        c.gridy = 0;
        formulario.add(
                new JLabel("Tipo:"), c);

        c.gridx = 1;
        formulario.add(
                tipoCombo, c);

        c.gridx = 0;
        c.gridy = 1;
        formulario.add(
                new JLabel("Placa:"), c);

        c.gridx = 1;
        formulario.add(
                placaField, c);

        c.gridx = 0;
        c.gridy = 2;
        formulario.add(
                new JLabel("Modelo:"), c);

        c.gridx = 1;
        formulario.add(
                modeloField, c);

        c.gridx = 0;
        c.gridy = 3;
        formulario.add(
                new JLabel("Marca:"), c);

        c.gridx = 1;
        formulario.add(
                marcaField, c);

        c.gridx = 0;
        c.gridy = 4;
        formulario.add(
                new JLabel("Cor:"), c);

        c.gridx = 1;
        formulario.add(
                corField, c);

        c.gridx = 0;
        c.gridy = 5;
        formulario.add(
                new JLabel("Diária:"), c);

        c.gridx = 1;
        formulario.add(
                diariaField, c);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        formulario.add(
                seguroCheck, c);

        JPanel botoes =
                new JPanel(new FlowLayout());

        botoes.add(salvarBtn);
        botoes.add(limparBtn);
        botoes.add(excluirBtn);

        c.gridy = 7;
        formulario.add(
                botoes, c);

        add(formulario,
                BorderLayout.NORTH);

        salvarBtn.addActionListener(
                e -> salvarVeiculo());

        limparBtn.addActionListener(
                e -> limparCampos());

        excluirBtn.addActionListener(
                e -> excluirVeiculo());
    }

    private void criarTabela() {

        model = new DefaultTableModel();

        model.addColumn("Tipo");
        model.addColumn("Placa");
        model.addColumn("Modelo");
        model.addColumn("Marca");
        model.addColumn("Diária");
        model.addColumn("Seguro");
        model.addColumn("Status");

        tabela = new JTable(model);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabela.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (e.getValueIsAdjusting()) {
                        return;
                    }

                    int linha =
                            tabela.getSelectedRow();

                    if (linha >= 0) {

                        Veiculo veiculo =
                                locadora.getVeiculos().get(linha);

                        veiculoSelecionado = veiculo;
                        tipoCombo.setSelectedItem(veiculo.getTipo());
                        placaField.setText(veiculo.getPlaca());
                        modeloField.setText(veiculo.getModelo());
                        marcaField.setText(veiculo.getMarca());
                        corField.setText(veiculo.getCor());
                        diariaField.setText(String.format("%.2f", veiculo.getValorDiaria()));
                        seguroCheck.setSelected(veiculo.temSeguro());
                    }
                });

        add(new JScrollPane(tabela),
                BorderLayout.CENTER);
    }

    private void salvarVeiculo() {

        try {

            String tipo =
                    (String) tipoCombo.getSelectedItem();

            String placa =
                    placaField.getText().trim();

            String modelo =
                    modeloField.getText().trim();

            String marca =
                    marcaField.getText().trim();

            String cor =
                    corField.getText().trim();

            double diaria =
                    Double.parseDouble(
                            diariaField.getText());

            boolean seguro = seguroCheck.isSelected();

            if (veiculoSelecionado != null) {

                if (!veiculoSelecionado.getTipo().equals(tipo)) {
                    locadora.getVeiculos().remove(veiculoSelecionado);
                    locadora.adicionarVeiculo(
                            criarVeiculo(tipo, placa, modelo, diaria, cor, marca, seguro));
                } else {
                    veiculoSelecionado.setPlaca(placa);
                    veiculoSelecionado.setModelo(modelo);
                    veiculoSelecionado.setMarca(marca);
                    veiculoSelecionado.setCor(cor);
                    veiculoSelecionado.setValorDiaria(diaria);
                    veiculoSelecionado.setSeguro(seguro);
                }

                locadora.salvarVeiculos();

                JOptionPane.showMessageDialog(
                        this,
                        "Veículo atualizado com sucesso!");

            } else {

                locadora.adicionarVeiculo(
                        criarVeiculo(tipo, placa, modelo, diaria, cor, marca, seguro));

                JOptionPane.showMessageDialog(
                        this,
                        "Veículo cadastrado com sucesso!");
            }

            atualizarTabela();
            limparCampos();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Dados inválidos.");
        }
    }

    private Veiculo criarVeiculo(String tipo,
                                 String placa,
                                 String modelo,
                                 double diaria,
                                 String cor,
                                 String marca,
                                 boolean seguro) {

        if ("Carro".equals(tipo)) {
            return new Carro(placa, modelo, diaria, cor, marca, seguro);
        }

        return new Moto(placa, modelo, diaria, cor, marca, seguro);
    }

    private void excluirVeiculo() {

        int linha =
                tabela.getSelectedRow();

        if (linha < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um veículo.");

            return;
        }

        Veiculo veiculo =
                locadora.getVeiculos()
                        .get(linha);

        locadora.getVeiculos()
                .remove(veiculo);

        locadora.salvarVeiculos();

        atualizarTabela();

        limparCampos();

        JOptionPane.showMessageDialog(
                this,
                "Veículo removido.");
    }

    private void limparCampos() {

        placaField.setText("");
        modeloField.setText("");
        marcaField.setText("");
        corField.setText("");
        diariaField.setText("");
        seguroCheck.setSelected(false);
        tipoCombo.setSelectedIndex(0);
        veiculoSelecionado = null;
        tabela.clearSelection();
    }

    public void atualizarTabela() {

        model.setRowCount(0);

        for (Veiculo veiculo :
                locadora.getVeiculos()) {

            model.addRow(new Object[]{

                    veiculo.getTipo(),
                    veiculo.getPlaca(),
                    veiculo.getModelo(),
                    veiculo.getMarca(),
                    String.format(
                            "R$ %.2f",
                            veiculo.getValorDiaria()),
                    veiculo.temSeguro() ? "Sim" : "Nao",
                    veiculo.isDisponivel()
                            ? "Disponível"
                            : "Alugado"
            });
        }
    }
}