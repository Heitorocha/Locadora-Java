package locadora.service;

import locadora.interfaces.ILocadora;
import locadora.model.Aluguel;
import locadora.model.Cliente;
import locadora.model.Veiculo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que gerencia a locadora, seus clientes, veículos e alugueis.
 */
public class Locadora implements ILocadora {
    private final List<Veiculo> veiculos;
    private final List<Cliente> clientes;
    private final List<Aluguel> alugueis;

    public Locadora() {
        veiculos = new ArrayList<>();
        clientes = new ArrayList<>();
        alugueis = new ArrayList<>();
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
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

    public Aluguel alugarVeiculo(Cliente cliente, Veiculo veiculo, int dias) {
        double valorTotal = veiculo.getValorDiaria() * dias;
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
