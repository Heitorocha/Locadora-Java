package locadora.interfaces;

import locadora.exception.LocadoraException;
import locadora.model.Aluguel;
import locadora.model.Cliente;
import locadora.model.Funcionario;
import locadora.model.Veiculo;

import java.util.List;

public interface ILocadora {

    void adicionarVeiculo(Veiculo veiculo);

    void adicionarCliente(Cliente cliente);

    void adicionarFuncionario(Funcionario funcionario);

    List<Veiculo> listarVeiculos();

    List<Cliente> listarClientes();

    List<Funcionario> listarFuncionarios();

    List<Veiculo> getVeiculos();

    List<Cliente> getClientes();

    List<Funcionario> getFuncionarios();

    List<Aluguel> getAlugueis();

    Cliente buscarClientePorCpf(String cpf);

    Veiculo buscarVeiculoPorPlaca(String placa);

    Aluguel alugarVeiculo(Cliente cliente, Veiculo veiculo, int dias) throws LocadoraException;

    List<Aluguel> listarAlugueis();
}
