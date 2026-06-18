package locadora.app.ui;

import locadora.model.Cliente;
import locadora.service.Locadora;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Painel responsável pelo gerenciamento de clientes.
 * Permite cadastrar, visualizar e excluir clientes.
 */
public class ClientePanel extends JPanel {

    // Referência para a locadora
    private final Locadora locadora;

    // Modelo e tabela de exibição dos clientes
    private DefaultTableModel model;
    private JTable tabela;

    // Campos do formulário
    private JTextField cpfField;
    private JTextField nomeField;
    private JTextField saldoField;

    /**
     * Construtor do painel de clientes.
     */
    public ClientePanel(Locadora locadora) {

        this.locadora = locadora;

        setLayout(new BorderLayout(10, 10));

        criarFormulario();
        criarTabela();

        atualizarTabela();
    }

    /**
     * Cria o formulário superior para cadastro.
     */
    private void criarFormulario() {

        JPanel painelFormulario =
                new JPanel(new GridBagLayout());

        painelFormulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Cadastro de Clientes"));

        GridBagConstraints c =
                new GridBagConstraints();

        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Campos de entrada
        cpfField = new JTextField(20);
        nomeField = new JTextField(20);
        saldoField = new JTextField(20);

        // Botões de ação
        JButton cadastrarBtn =
                new JButton("Cadastrar");

        JButton limparBtn =
                new JButton("Limpar");

        JButton excluirBtn =
                new JButton("Excluir");

        // CPF
        c.gridx = 0;
        c.gridy = 0;
        painelFormulario.add(
                new JLabel("CPF:"), c);

        c.gridx = 1;
        painelFormulario.add(
                cpfField, c);

        // Nome
        c.gridx = 0;
        c.gridy = 1;
        painelFormulario.add(
                new JLabel("Nome:"), c);

        c.gridx = 1;
        painelFormulario.add(
                nomeField, c);

        // Saldo
        c.gridx = 0;
        c.gridy = 2;
        painelFormulario.add(
                new JLabel("Saldo:"), c);

        c.gridx = 1;
        painelFormulario.add(
                saldoField, c);

        // Painel dos botões
        JPanel botoes =
                new JPanel(new FlowLayout());

        botoes.add(cadastrarBtn);
        botoes.add(limparBtn);
        botoes.add(excluirBtn);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;

        painelFormulario.add(
                botoes, c);

        add(painelFormulario,
                BorderLayout.NORTH);

        // Eventos dos botões
        cadastrarBtn.addActionListener(
                e -> cadastrarCliente());

        limparBtn.addActionListener(
                e -> limparCampos());

        excluirBtn.addActionListener(
                e -> excluirCliente());
    }

    /**
     * Cria a tabela de clientes.
     */
    private void criarTabela() {

        model = new DefaultTableModel();

        model.addColumn("CPF");
        model.addColumn("Nome");
        model.addColumn("Saldo");

        tabela = new JTable(model);

        // Quando selecionar uma linha,
        // os dados são carregados nos campos.
        tabela.getSelectionModel()
                .addListSelectionListener(e -> {

                    int linha =
                            tabela.getSelectedRow();

                    if (linha >= 0) {

                        cpfField.setText(
                                model.getValueAt(
                                        linha, 0).toString());

                        nomeField.setText(
                                model.getValueAt(
                                        linha, 1).toString());

                        saldoField.setText(
                                model.getValueAt(
                                        linha, 2).toString());
                    }
                });

        add(new JScrollPane(tabela),
                BorderLayout.CENTER);
    }

    /**
     * Realiza o cadastro de um novo cliente.
     */
    private void cadastrarCliente() {

        try {

            String cpf =
                    cpfField.getText().trim();

            String nome =
                    nomeField.getText().trim();

            double saldo =
                    Double.parseDouble(
                            saldoField.getText());

            // Verifica se os campos obrigatórios foram preenchidos
            if (cpf.isEmpty()
                    || nome.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos.");

                return;
            }

            Cliente cliente =
                    new Cliente(
                            cpf,
                            nome,
                            saldo);

            locadora.adicionarCliente(
                    cliente);

            atualizarTabela();

            limparCampos();

            JOptionPane.showMessageDialog(
                    this,
                    "Cliente cadastrado com sucesso!");

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Saldo inválido.");
        }
    }

    /**
     * Remove o cliente selecionado na tabela.
     */
    private void excluirCliente() {

        int linha =
                tabela.getSelectedRow();

        if (linha < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um cliente.");

            return;
        }

        Cliente cliente =
                locadora.getClientes()
                        .get(linha);

        locadora.getClientes()
                .remove(cliente);

        atualizarTabela();

        limparCampos();

        JOptionPane.showMessageDialog(
                this,
                "Cliente removido.");
    }

    /**
     * Limpa os campos do formulário.
     */
    private void limparCampos() {

        cpfField.setText("");
        nomeField.setText("");
        saldoField.setText("");

        tabela.clearSelection();
    }

    /**
     * Recarrega os dados da tabela.
     */
    public void atualizarTabela() {

        model.setRowCount(0);

        for (Cliente cliente :
                locadora.getClientes()) {

            model.addRow(new Object[]{

                    cliente.getCpf(),
                    cliente.getNome(),
                    cliente.getSaldo()
            });
        }
    }
}