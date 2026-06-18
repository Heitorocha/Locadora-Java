package locadora.interfaces;

import locadora.model.Cliente;
import locadora.model.Veiculo;
import locadora.model.Aluguel;

import java.util.List;

public interface ILocadora {

    void adicionarVeiculo(Veiculo veiculo);

    void adicionarCliente(Cliente cliente);

    List<Veiculo> listarVeiculos();

    List<Cliente> listarClientes();

    Aluguel alugarVeiculo(Cliente cliente, Veiculo veiculo, int dias);

    List<Aluguel> listarAlugueis();
}
