package locadora.app;

import locadora.model.Aluguel;
import locadora.model.Carro;
import locadora.model.Cliente;
import locadora.model.Funcionario;
import locadora.interfaces.ICliente;
import locadora.model.Moto;
import locadora.model.Veiculo;
import locadora.service.Locadora;

public class Main {

    public static void main(String[] args) {
        Locadora locadora = new Locadora();

        // Cria clientes usando a interface ICliente
        ICliente cliente1 = new Cliente("12345678900", "Fernanda", 100.0);
        ICliente cliente2 = new Cliente("98765432100", "Ricardo", 200.0);

        System.out.println("== Clientes ==");
        System.out.println(cliente1);
        System.out.println(cliente2);

        System.out.println("\n== Operações de saldo ==");
        cliente1.depositar(50);
        cliente1.debitar(30);
        System.out.println(cliente1);

        // Adiciona clientes na locadora
        locadora.adicionarCliente((Cliente) cliente1);
        locadora.adicionarCliente((Cliente) cliente2);

        System.out.println("\n== Veículos ==");
        Veiculo carro = new Carro("ABC-1234", "Sedan", 150.0, "Preto", "Toyota", true);
        Veiculo moto = new Moto("XYZ-4321", "Sport", 90.0, "Vermelho", "Honda", false);

        locadora.adicionarVeiculo(carro);
        locadora.adicionarVeiculo(moto);
        locadora.listarVeiculos();

        System.out.println("\n== Aluguéis ==");
        realizarAluguel(locadora, (Cliente) cliente2, carro, 3);
        realizarAluguel(locadora, (Cliente) cliente1, moto, 2);

        System.out.println("\n== Clientes registrados ==");
        locadora.listarClientes();

        System.out.println("\n== Registros de aluguel ==");
        locadora.listarAlugueis();

        System.out.println("\n== Funcionário ==");
        Funcionario funcionario = new Funcionario("11223344556", "Patrícia", 3200.0);
        System.out.println(funcionario);
    }

    private static void realizarAluguel(Locadora locadora, Cliente cliente, Veiculo veiculo, int dias) {
        System.out.println("Alugando " + veiculo.getTipo() + " para " + cliente.getNome() + " por " + dias + " dias.");
        Aluguel aluguel = locadora.alugarVeiculo(cliente, veiculo, dias);
        System.out.println("Confirmado: " + aluguel);
    }
}
