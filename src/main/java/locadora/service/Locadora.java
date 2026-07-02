package locadora.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import locadora.exception.LocadoraException;
import locadora.interfaces.ILocadora;
import locadora.model.Aluguel;
import locadora.model.Carro;
import locadora.model.Cliente;
import locadora.model.Funcionario;
import locadora.model.Moto;
import locadora.model.Veiculo;

/**
 * Classe responsável por gerenciar toda a lógica da locadora.
 * Armazena clientes, veículos, funcionários e aluguéis.
 */
public class Locadora implements ILocadora {

    public enum TipoUsuario {
        CLIENTE,
        FUNCIONARIO
    }

    public interface LocadoraListener {
        void dadosAtualizados();
    }

    private static final String ARQUIVO_VEICULOS = "veiculos.txt";
    private static final String ARQUIVO_CLIENTES = "clientes.txt";
    private static final String ARQUIVO_FUNCIONARIOS = "funcionarios.txt";
    private static final String ARQUIVO_ALUGUEIS = "alugueis.txt";

    private final List<Veiculo> veiculos;
    private final List<Cliente> clientes;
    private final List<Funcionario> funcionarios;
    private final List<Aluguel> alugueis;
    private final List<LocadoraListener> listeners;
    private Cliente clienteLogado;
    private Funcionario funcionarioLogado;
    private TipoUsuario tipoUsuarioLogado;

    /**
     * Inicializa as listas da locadora.
     */
    public Locadora() {

        veiculos = new ArrayList<>();
        clientes = new ArrayList<>();
        funcionarios = new ArrayList<>();
        alugueis = new ArrayList<>();
        listeners = new ArrayList<>();
        clienteLogado = null;
        funcionarioLogado = null;
        tipoUsuarioLogado = null;

        carregarClientesDoArquivo();
        carregarFuncionariosDoArquivo();
        carregarVeiculosDoArquivo();
        carregarAlugueisDoArquivo();
    }

    public void adicionarListener(LocadoraListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removerListener(LocadoraListener listener) {
        listeners.remove(listener);
    }

    private void notificarMudanca() {
        for (LocadoraListener listener : new ArrayList<>(listeners)) {
            listener.dadosAtualizados();
        }
    }

    /**
     * Adiciona um veículo ao sistema.
     */
    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            return;
        }

        veiculos.add(veiculo);
        salvarVeiculos();
        notificarMudanca();
    }

    public void removerVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            return;
        }

        veiculos.remove(veiculo);
        removerAlugueisDoVeiculo(veiculo);
        salvarVeiculos();
        salvarAlugueis();
        notificarMudanca();
    }

    /**
     * Adiciona um cliente ao sistema.
     */
    public void adicionarCliente(Cliente cliente) {
        if (cliente == null) {
            return;
        }

        clientes.add(cliente);
        salvarClientes();
        notificarMudanca();
    }

    public void removerCliente(Cliente cliente) {
        if (cliente == null) {
            return;
        }

        clientes.remove(cliente);
        removerAlugueisDoCliente(cliente);
        salvarClientes();
        salvarAlugueis();
        notificarMudanca();
    }

    /**
     * Adiciona um funcionário ao sistema.
     */
    public void adicionarFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            return;
        }

        funcionarios.add(funcionario);
        salvarFuncionarios();
        notificarMudanca();
    }

    public void removerFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            return;
        }

        funcionarios.remove(funcionario);
        salvarFuncionarios();
        notificarMudanca();
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

    public Cliente getClienteLogado() {
        return clienteLogado;
    }

    public Funcionario getFuncionarioLogado() {
        return funcionarioLogado;
    }

    public TipoUsuario getTipoUsuarioLogado() {
        return tipoUsuarioLogado;
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

        clienteLogado = null;
        funcionarioLogado = null;
        tipoUsuarioLogado = null;

        if (buscarClientePorCpf(cpf) != null
                && "cliente".equalsIgnoreCase(senhaInformada)) {
            clienteLogado = buscarClientePorCpf(cpf);
            tipoUsuarioLogado = TipoUsuario.CLIENTE;
            return TipoUsuario.CLIENTE;
        }

        if (buscarFuncionarioPorCpf(cpf) != null
                && "funcionario".equalsIgnoreCase(senhaInformada)) {
            funcionarioLogado = buscarFuncionarioPorCpf(cpf);
            tipoUsuarioLogado = TipoUsuario.FUNCIONARIO;
            return TipoUsuario.FUNCIONARIO;
        }

        throw new LocadoraException(
                "Usuário ou senha inválidos.");
    }

    public void logout() {
        clienteLogado = null;
        funcionarioLogado = null;
        tipoUsuarioLogado = null;
        notificarMudanca();
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

        if (tipoUsuarioLogado == TipoUsuario.CLIENTE) {
            if (clienteLogado == null
                    || !clienteLogado.getCpf().equals(cliente.getCpf())) {
                throw new LocadoraException(
                        "Você só pode realizar aluguéis para si mesmo.");
            }
        }

        if (!veiculo.isDisponivel()) {
            throw new LocadoraException(
                    "Este veículo já está alugado.");
        }

        if (dias <= 0) {
            throw new LocadoraException(
                    "O número de dias deve ser maior que zero.");
        }

        boolean seguroSelecionado = comSeguro && veiculo.temSeguro();

        double valorTotal =
                veiculo.calcularValorLocacao(dias, seguroSelecionado);

        if (valorTotal > cliente.getSaldo()) {
            throw new LocadoraException(
                    "Saldo insuficiente para este aluguel.");
        }

        cliente.debitar(valorTotal);
        veiculo.setDisponivel(false);

        Aluguel aluguel =
                new Aluguel(
                        cliente,
                        veiculo,
                        dias,
                        seguroSelecionado);

        alugueis.add(aluguel);
        salvarClientes();
        salvarVeiculos();
        salvarAlugueis();
        notificarMudanca();

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
        salvarVeiculos();
        notificarMudanca();
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

    public void salvarClientes() {
        Path caminho = Paths.get(ARQUIVO_CLIENTES);

        try {
            List<String> linhas = new ArrayList<>();

            for (Cliente cliente : clientes) {
                linhas.add(String.join("|",
                        cliente.getCpf(),
                        cliente.getNome(),
                        String.valueOf(cliente.getSaldo())));
            }

            Files.write(caminho, linhas, StandardCharsets.UTF_8);
            notificarMudanca();
        } catch (IOException ex) {
            System.err.println("Não foi possível salvar os clientes: " + ex.getMessage());
        }
    }

    public void salvarFuncionarios() {
        Path caminho = Paths.get(ARQUIVO_FUNCIONARIOS);

        try {
            List<String> linhas = new ArrayList<>();

            for (Funcionario funcionario : funcionarios) {
                linhas.add(String.join("|",
                        funcionario.getCpf(),
                        funcionario.getNome(),
                        String.valueOf(funcionario.getSalario())));
            }

            Files.write(caminho, linhas, StandardCharsets.UTF_8);
            notificarMudanca();
        } catch (IOException ex) {
            System.err.println("Não foi possível salvar os funcionários: " + ex.getMessage());
        }
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
            notificarMudanca();

        } catch (IOException ex) {
            System.err.println("Não foi possível salvar os veículos: " + ex.getMessage());
        }
    }

    public void salvarAlugueis() {
        Path caminho = Paths.get(ARQUIVO_ALUGUEIS);

        try {
            List<String> linhas = new ArrayList<>();

            for (Aluguel aluguel : alugueis) {
                linhas.add(String.join("|",
                        aluguel.getCliente() != null ? aluguel.getCliente().getCpf() : "",
                        aluguel.getVeiculo() != null ? aluguel.getVeiculo().getPlaca() : "",
                        String.valueOf(aluguel.getDias()),
                        String.valueOf(aluguel.isComSeguro()),
                        String.valueOf(aluguel.getValorTotal())));
            }

            Files.write(caminho, linhas, StandardCharsets.UTF_8);
            notificarMudanca();
        } catch (IOException ex) {
            System.err.println("Não foi possível salvar os aluguéis: " + ex.getMessage());
        }
    }

    private void carregarClientesDoArquivo() {
        Path caminho = Paths.get(ARQUIVO_CLIENTES);

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
                if (partes.length < 3) {
                    continue;
                }

                Cliente cliente = new Cliente(partes[0], partes[1], Double.parseDouble(partes[2]));
                clientes.add(cliente);
            }
        } catch (IOException ex) {
            System.err.println("Não foi possível carregar os clientes: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.err.println("Formato inválido no arquivo de clientes: " + ex.getMessage());
        }
    }

    private void carregarFuncionariosDoArquivo() {
        Path caminho = Paths.get(ARQUIVO_FUNCIONARIOS);

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
                if (partes.length < 3) {
                    continue;
                }

                Funcionario funcionario = new Funcionario(partes[0], partes[1], Double.parseDouble(partes[2]));
                funcionarios.add(funcionario);
            }
        } catch (IOException ex) {
            System.err.println("Não foi possível carregar os funcionários: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.err.println("Formato inválido no arquivo de funcionários: " + ex.getMessage());
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

    private void carregarAlugueisDoArquivo() {
        Path caminho = Paths.get(ARQUIVO_ALUGUEIS);

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
                if (partes.length < 5) {
                    continue;
                }

                Cliente cliente = buscarClientePorCpf(partes[0]);
                Veiculo veiculo = buscarVeiculoPorPlaca(partes[1]);

                if (cliente == null || veiculo == null) {
                    continue;
                }

                int dias = Integer.parseInt(partes[2]);
                boolean comSeguro = Boolean.parseBoolean(partes[3]);

                aluguelJaExiste(cliente, veiculo, dias, comSeguro);
                alugueis.add(new Aluguel(cliente, veiculo, dias, comSeguro));
            }
        } catch (IOException ex) {
            System.err.println("Não foi possível carregar os aluguéis: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.err.println("Formato inválido no arquivo de aluguéis: " + ex.getMessage());
        }
    }

    private void aluguelJaExiste(Cliente cliente, Veiculo veiculo, int dias, boolean comSeguro) {
        for (Aluguel aluguel : alugueis) {
            if (aluguel.getCliente().equals(cliente)
                    && aluguel.getVeiculo().equals(veiculo)
                    && aluguel.getDias() == dias
                    && aluguel.isComSeguro() == comSeguro) {
                return;
            }
        }
    }

    private void removerAlugueisDoCliente(Cliente cliente) {
        alugueis.removeIf(aluguel -> aluguel.getCliente() != null && aluguel.getCliente().equals(cliente));
    }

    private void removerAlugueisDoVeiculo(Veiculo veiculo) {
        alugueis.removeIf(aluguel -> aluguel.getVeiculo() != null && aluguel.getVeiculo().equals(veiculo));
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