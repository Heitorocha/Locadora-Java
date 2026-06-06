package locadora;

import java.util.ArrayList;
import java.util.List;

public class Locadora {
    private final List<Carro> carros = new ArrayList<>();
    private final List<Cliente> clientes = new ArrayList<>();

    public void adicionarCarro(Carro c) { carros.add(c); }
    public void adicionarCliente(Cliente c) { clientes.add(c); }
    public List<Carro> listarCarros() { return new ArrayList<>(carros); }
    public List<Cliente> listarClientes() { return new ArrayList<>(clientes); }
}
