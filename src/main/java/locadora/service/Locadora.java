package locadora.service;

import locadora.exception.LocadoraException;
import locadora.interfaces.ILocadora;
import locadora.model.Aluguel;
import locadora.model.Carro;
import locadora.model.Cliente;
import locadora.model.Funcionario;
import locadora.model.Moto;
import locadora.model.Veiculo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por gerenciar toda a lógica da locadora.
 * Armazena clientes, veículos, funcionários e aluguéis.
 */
public class Locadora implements ILocadora {

    public enum TipoUsuario {
        CLIENTE,
        FUNCIONARIO
    }

    private static final String ARQUIVO_VEICULOS = "veiculos.txt";

    // Listas principais do sistema
    private final List<Veiculo> veiculos;
    private final List<Cliente> clientes;
    private final List<Funcionario> funcionarios;
    private final List<Aluguel> alugueis;

    /**
     * Inicializa as listas da locadora.
     */
    public Locadora() {

        veiculos = new ArrayList<>();
        clientes = new ArrayList<>();
        funcionarios = new ArrayList<>();
        alugueis = new ArrayList<>();

        carregarVeiculosDoArquivo();
    }

    /**
     * Adiciona um veículo ao sistema.
     */
    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
        salvarVeiculos();
    }

    /**
     * Adiciona um cliente ao sistema.
     */
    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Adiciona um funcionário ao sistema.
     */
    public void adicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    /**
     * Lista todos os veículos cadastrados.
     */
    public List<Veiculo> listarVeiculos() {

        for (Veiculo veiculo : veiculos) {
            System.out.println(veiculo);
        }

        return new ArrayList<>(veiculos);
    }

    /**
     * Lista todos os clientes cadastrados.
     */
    public List<Cliente> listarClientes() {

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }

        return new ArrayList<>(clientes);
    }

    /**
     * Lista todos os funcionários cadastrados.
     */
    public List<Funcionario> listarFuncionarios() {

        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }

        return new ArrayList<>(funcionarios);
    }

    /**
     * Retorna a lista de veículos.
     */
    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    /**
     * Retorna a lista de clientes.
     */
    public List<Cliente> getClientes() {
        return clientes;
    }

    /**
     * Retorna a lista de funcionários.
     */
    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    /**
     * Retorna a lista de aluguéis realizados.
     */
    public List<Aluguel> getAlugueis() {
        return alugueis;
    }

    /**
     * Busca um cliente pelo CPF.
     */
    public Cliente buscarClientePorCpf(String cpf) {

        for (Cliente cliente : clientes) {

            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }

        return null;
    }

    /**
     * Busca um funcionário pelo CPF.
     */
    public Funcionario buscarFuncionarioPorCpf(String cpf) {

        for (Funcionario funcionario : funcionarios) {

            if (funcionario.getCpf().equals(cpf)) {
                return funcionario;
            }
        }

        return null;
    }

    /**
     * Autentica um usuário e identifica se ele é cliente ou funcionário.
     */
    public TipoUsuario autenticar(String usuario, String senha)
            throws LocadoraException {

        if (usuario == null || usuario.trim().isEmpty()) {
            throw new LocadoraException(
                    "Informe o CPF de acesso.");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new LocadoraException(
                    "Informe a senha.");
        }

        String cpf = usuario.trim();
        String senhaInformada = senha.trim();

        if (buscarClientePorCpf(cpf) != null
                && "cliente".equalsIgnoreCase(senhaInformada)) {
            return TipoUsuario.CLIENTE;
        }

        if (buscarFuncionarioPorCpf(cpf) != null
                && "funcionario".equalsIgnoreCase(senhaInformada)) {
            return TipoUsuario.FUNCIONARIO;
        }

        throw new LocadoraException(
                "Usuário ou senha inválidos.");
    }

    /**
     * Busca um veículo pela placa.
     */
    public Veiculo buscarVeiculoPorPlaca(String placa) {

        for (Veiculo veiculo : veiculos) {

            if (veiculo.getPlaca()
                    .equalsIgnoreCase(placa)) {

                return veiculo;
            }
        }

        return null;
    }

    /**
     * Realiza o aluguel de um veículo.
     *
     * Regras:
     * - Cliente deve existir
     * - Veículo deve existir
     * - Veículo deve estar disponível
     * - Dias devem ser maiores que zero
     * - Cliente deve possuir saldo suficiente
     */
    public Aluguel alugarVeiculo(
            Cliente cliente,
            Veiculo veiculo,
            int dias)
            throws LocadoraException {
        return alugarVeiculo(cliente, veiculo, dias, false);
    }

    public Aluguel alugarVeiculo(
            Cliente cliente,
            Veiculo veiculo,
            int dias,
            boolean comSeguro)
            throws LocadoraException {

        if (cliente == null) {
            throw new LocadoraException(
                    "Cliente inválido.");
        }

        if (veiculo == null) {
            throw new LocadoraException(
                    "Veículo inválido.");
        }

        // Impede alugar um veículo já locado
        if (!veiculo.isDisponivel()) {
            throw new LocadoraException(
                    "Este veículo já está alugado.");
        }

        if (dias <= 0) {
            throw new LocadoraException(
                    "O número de dias deve ser maior que zero.");
        }

        boolean seguroSelecionado = comSeguro && veiculo.temSeguro();

        // Calcula o valor total da locação
        double valorTotal =
                veiculo.calcularValorLocacao(dias, seguroSelecionado);

        // Verifica saldo disponível do cliente
        if (valorTotal > cliente.getSaldo()) {
            throw new LocadoraException(
                    "Saldo insuficiente para este aluguel.");
        }

        // Debita o valor do aluguel do saldo do cliente
        cliente.debitar(valorTotal);

        // Marca o veículo como alugado
        veiculo.setDisponivel(false);

        // Cria e registra o aluguel
        Aluguel aluguel =
                new Aluguel(
                        cliente,
                        veiculo,
                        dias,
                        seguroSelecionado);

        alugueis.add(aluguel);

        return aluguel;
    }

    /**
     * Realiza a devolução do veículo.
     * O veículo volta a ficar disponível.
     */
    public void devolverVeiculo(Veiculo veiculo)
            throws LocadoraException {

        if (veiculo == null) {
            throw new LocadoraException(
                    "Veículo inválido.");
        }

        veiculo.setDisponivel(true);
    }

    /**
     * Lista todos os aluguéis registrados.
     */
    public List<Aluguel> listarAlugueis() {

        for (Aluguel aluguel : alugueis) {
            System.out.println(aluguel);
        }

        return new ArrayList<>(alugueis);
    }

    public void salvarVeiculos() {

        Path caminho = Paths.get(ARQUIVO_VEICULOS);

        try {
            List<String> linhas = new ArrayList<>();

            for (Veiculo veiculo : veiculos) {
                linhas.add(String.join("|",
                        veiculo.getTipo(),
                        veiculo.getPlaca(),
                        veiculo.getModelo(),
                        String.valueOf(veiculo.getValorDiaria()),
                        veiculo.getCor(),
                        veiculo.getMarca(),
                        String.valueOf(veiculo.temSeguro()),
                        String.valueOf(veiculo.isDisponivel())));
            }

            Files.write(caminho, linhas, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            System.err.println("Não foi possível salvar os veículos: " + ex.getMessage());
        }
    }

    private void carregarVeiculosDoArquivo() {

        Path caminho = Paths.get(ARQUIVO_VEICULOS);

        if (!Files.exists(caminho)) {
            return;
        }

        try {
            List<String> linhas = Files.readAllLines(caminho, StandardCharsets.UTF_8);

            for (String linha : linhas) {
                if (linha == null || linha.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linha.split("\\|");

                if (partes.length < 8) {
                    continue;
                }

                String tipo = partes[0];
                String placa = partes[1];
                String modelo = partes[2];
                double diaria = Double.parseDouble(partes[3]);
                String cor = partes[4];
                String marca = partes[5];
                boolean seguro = Boolean.parseBoolean(partes[6]);
                boolean disponivel = Boolean.parseBoolean(partes[7]);

                Veiculo veiculo = criarVeiculo(tipo, placa, modelo, diaria, cor, marca, seguro);
                veiculo.setDisponivel(disponivel);
                veiculos.add(veiculo);
            }

        } catch (IOException ex) {
            System.err.println("Não foi possível carregar os veículos: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.err.println("Formato inválido no arquivo de veículos: " + ex.getMessage());
        }
    }

    private Veiculo criarVeiculo(String tipo,
                                 String placa,
                                 String modelo,
                                 double diaria,
                                 String cor,
                                 String marca,
                                 boolean seguro) {

        if ("Carro".equalsIgnoreCase(tipo)) {
            return new Carro(placa, modelo, diaria, cor, marca, seguro);
        }

        return new Moto(placa, modelo, diaria, cor, marca, seguro);
    }
}