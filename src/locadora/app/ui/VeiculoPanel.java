package locadora.app.ui;

import locadora.model.Carro;
import locadora.model.Moto;
import locadora.model.Veiculo;
import locadora.service.Locadora;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Painel responsável pelo gerenciamento de veículos.
 * Permite cadastrar, visualizar e excluir veículos.
 */
public class VeiculoPanel extends JPanel {

    // Referência para a locadora
    private final Locadora locadora;

    // Componentes da tabela
    private DefaultTableModel model;
    private JTable tabela;

    // Campos do formulário
    private JComboBox<String> tipoCombo;

    private JTextField placaField;
    private JTextField modeloField;
    private JTextField marcaField;
    private JTextField corField;
    private JTextField diariaField;

    private JCheckBox opcionalCheck;

    /**
     * Construtor do painel.
     */
    public VeiculoPanel(Locadora locadora) {

        this.locadora = locadora;

        setLayout(new BorderLayout(10, 10));

        criarFormulario();
        criarTabela();

        atualizarTabela();
    }

    /**
     * Cria o formulário de cadastro.
     */
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

        opcionalCheck =
                new JCheckBox(
                        "Possui Seguro / Capacete");

        JButton cadastrarBtn =
                new JButton("Cadastrar");

        JButton limparBtn =
                new JButton("Limpar");

        JButton excluirBtn =
                new JButton("Excluir");

        // Tipo
        c.gridx = 0;
        c.gridy = 0;
        formulario.add(
                new JLabel("Tipo:"), c);

        c.gridx = 1;
        formulario.add(
                tipoCombo, c);

        // Placa
        c.gridx = 0;
        c.gridy = 1;
        formulario.add(
                new JLabel("Placa:"), c);

        c.gridx = 1;
        formulario.add(
                placaField, c);

        // Modelo
        c.gridx = 0;
        c.gridy = 2;
        formulario.add(
                new JLabel("Modelo:"), c);

        c.gridx = 1;
        formulario.add(
                modeloField, c);

        // Marca
        c.gridx = 0;
        c.gridy = 3;
        formulario.add(
                new JLabel("Marca:"), c);

        c.gridx = 1;
        formulario.add(
                marcaField, c);

        // Cor
        c.gridx = 0;
        c.gridy = 4;
        formulario.add(
                new JLabel("Cor:"), c);

        c.gridx = 1;
        formulario.add(
                corField, c);

        // Diária
        c.gridx = 0;
        c.gridy = 5;
        formulario.add(
                new JLabel("Diária:"), c);

        c.gridx = 1;
        formulario.add(
                diariaField, c);

        // Seguro / Capacete
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;

        formulario.add(
                opcionalCheck, c);

        // Painel dos botões
        JPanel botoes =
                new JPanel(new FlowLayout());

        botoes.add(cadastrarBtn);
        botoes.add(limparBtn);
        botoes.add(excluirBtn);

        c.gridy = 7;

        formulario.add(
                botoes, c);

        add(formulario,
                BorderLayout.NORTH);

        // Eventos
        cadastrarBtn.addActionListener(
                e -> cadastrarVeiculo());

        limparBtn.addActionListener(
                e -> limparCampos());

        excluirBtn.addActionListener(
                e -> excluirVeiculo());
    }

    /**
     * Cria a tabela de veículos.
     */
    private void criarTabela() {

        model = new DefaultTableModel();

        model.addColumn("Tipo");
        model.addColumn("Placa");
        model.addColumn("Modelo");
        model.addColumn("Marca");
        model.addColumn("Diária");
        model.addColumn("Status");

        tabela = new JTable(model);

        tabela.getSelectionModel()
                .addListSelectionListener(e -> {

                    int linha =
                            tabela.getSelectedRow();

                    if (linha >= 0) {

                        placaField.setText(
                                model.getValueAt(
                                        linha, 1).toString());

                        modeloField.setText(
                                model.getValueAt(
                                        linha, 2).toString());

                        marcaField.setText(
                                model.getValueAt(
                                        linha, 3).toString());

                        String diaria =
                                model.getValueAt(
                                        linha, 4).toString()
                                        .replace("R$ ", "")
                                        .replace(",", ".");

                        diariaField.setText(diaria);
                    }
                });

        add(new JScrollPane(tabela),
                BorderLayout.CENTER);
    }

    /**
     * Cadastra um novo veículo.
     */
    private void cadastrarVeiculo() {

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

            if (tipo.equals("Carro")) {

                locadora.adicionarVeiculo(

                        new Carro(
                                placa,
                                modelo,
                                diaria,
                                cor,
                                marca,
                                opcionalCheck.isSelected()
                        )
                );

            } else {

                locadora.adicionarVeiculo(

                        new Moto(
                                placa,
                                modelo,
                                diaria,
                                cor,
                                marca,
                                opcionalCheck.isSelected()
                        )
                );
            }

            atualizarTabela();

            limparCampos();

            JOptionPane.showMessageDialog(
                    this,
                    "Veículo cadastrado com sucesso!");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Dados inválidos.");
        }
    }

    /**
     * Remove o veículo selecionado.
     */
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

        atualizarTabela();

        limparCampos();

        JOptionPane.showMessageDialog(
                this,
                "Veículo removido.");
    }

    /**
     * Limpa os campos do formulário.
     */
    private void limparCampos() {

        placaField.setText("");
        modeloField.setText("");
        marcaField.setText("");
        corField.setText("");
        diariaField.setText("");

        opcionalCheck.setSelected(false);

        tabela.clearSelection();
    }

    /**
     * Atualiza a tabela com os dados atuais.
     */
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

                    veiculo.isDisponivel()
                            ? "Disponível"
                            : "Alugado"
            });
        }
    }
}