
package locadora.src.javaapplication10;

import java.util.ArrayList;
import java.util.List;

public class Locadora {
    private final List<Veiculo> veiculos;
    private final List<Cliente> clientes;

    public Locadora(){
        veiculos = new ArrayList<>();
        clientes = new ArrayList<>();
    }
    
    public void adicionarVeiculo(Veiculo v) {
        veiculos.add(v); 
    }
    
    public void adicionarCliente(Cliente c) {
        clientes.add(c); 
    }
    
    public void listarCarros() { 
       for(Veiculo v : veiculos){
           System.out.println(v.toString());
       }
    }
    
    public List<Cliente> listarClientes() {
        for(Cliente c: clientes){
            System.out.println(c.toString());
        }
        return clientes;
    }
}
