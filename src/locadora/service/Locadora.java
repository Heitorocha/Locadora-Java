package locadora.service;

import locadora.exception.LocadoraException;
import locadora.interfaces.ILocadora;
import locadora.model.Aluguel;
import locadora.model.Cliente;
import locadora.model.Funcionario;
import locadora.model.Veiculo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que gerencia a locadora, seus clientes, veículos e alugueis.
 */
public class Locadora implements ILocadora {
    private final List<Veiculo> veiculos;
    private final List<Cliente> clientes;
    private final List<Funcionario> funcionarios;
    private final List<Aluguel> alugueis;

    public Locadora() {
        veiculos = new ArrayList<>();
        clientes = new ArrayList<>();
        funcionarios = new ArrayList<>();
        alugueis = new ArrayList<>();
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public List<Veiculo> listarVeiculos() {
        for (Veiculo veiculo : veiculos) {
            System.out.println(veiculo);
        }
        return new ArrayList<>(veiculos);
    }

    public List<Cliente> listarClientes() {
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
        return new ArrayList<>(clientes);
    }

    public List<Funcionario> listarFuncionarios() {
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }
        return new ArrayList<>(funcionarios);
    }

    public List<Veiculo> getVeiculos() {
        return new ArrayList<>(veiculos);
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    public List<Funcionario> getFuncionarios() {
        return new ArrayList<>(funcionarios);
    }

    public List<Aluguel> getAlugueis() {
        return new ArrayList<>(alugueis);
    }

    public Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                return veiculo;
            }
        }
        return null;
    }

    public Aluguel alugarVeiculo(Cliente cliente, Veiculo veiculo, int dias) throws LocadoraException {
        if (cliente == null) {
            throw new LocadoraException("Cliente inválido.");
        }
        if (veiculo == null) {
            throw new LocadoraException("Veículo inválido.");
        }
        if (dias <= 0) {
            throw new LocadoraException("O número de dias deve ser maior que zero.");
        }

        double valorTotal = veiculo.getValorDiaria() * dias;
        if (valorTotal > cliente.getSaldo()) {
            throw new LocadoraException("Saldo insuficiente para este aluguel.");
        }

        cliente.debitar(valorTotal);
        Aluguel aluguel = new Aluguel(cliente, veiculo, dias);
        alugueis.add(aluguel);
        return aluguel;
    }

    public List<Aluguel> listarAlugueis() {
        for (Aluguel aluguel : alugueis) {
            System.out.println(aluguel);
        }
        return new ArrayList<>(alugueis);
    }
}
