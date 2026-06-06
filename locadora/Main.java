package locadora;

public class Main {
    public static void main(String[] args) {
        Locadora loc = new Locadora();

        Cliente cli = new Cliente("12345678900", "João Silva", 500.0);
        Carro car = new Carro("ABC-1234", "Gol", 80.0, 4);

        loc.adicionarCliente(cli);
        loc.adicionarCarro(car);

        System.out.println("Clientes:");
        for (Cliente c : loc.listarClientes()) System.out.println(c);

        System.out.println("\nCarros:");
        for (Carro c : loc.listarCarros()) System.out.println(c);
    }
}
