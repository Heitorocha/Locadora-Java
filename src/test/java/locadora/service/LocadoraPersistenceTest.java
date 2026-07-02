package locadora.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import locadora.model.Carro;
import locadora.model.Cliente;
import locadora.model.Funcionario;
import locadora.model.Veiculo;

public class LocadoraPersistenceTest {

    public static void main(String[] args) throws Exception {
        apagarArquivos();

        Locadora locadora = new Locadora();
        Cliente cliente = new Cliente("12345678900", "Ana", 1000.0);
        Funcionario funcionario = new Funcionario("10987654321", "Bruno", 3000.0);
        Veiculo veiculo = new Carro("XYZ-9999", "Civic", 120.0, "Azul", "Honda", true);

        locadora.adicionarCliente(cliente);
        locadora.adicionarFuncionario(funcionario);
        locadora.adicionarVeiculo(veiculo);

        locadora.alugarVeiculo(cliente, veiculo, 2, true);

        Locadora recarregada = new Locadora();

        if (recarregada.getClientes().size() != 1
                || recarregada.getFuncionarios().size() != 1
                || recarregada.getVeiculos().size() != 1
                || recarregada.getAlugueis().size() != 1) {
            throw new AssertionError("Persistência incompleta após reinicialização.");
        }

        System.out.println("Teste de persistência executado com sucesso.");
    }

    private static void apagarArquivos() throws Exception {
        for (String nome : new String[]{"veiculos.txt", "clientes.txt", "funcionarios.txt", "alugueis.txt"}) {
            Path caminho = Paths.get(nome);
            Files.deleteIfExists(caminho);
        }
    }
}
