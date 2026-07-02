package locadora.service;

import locadora.exception.LocadoraException;
import locadora.model.Carro;
import locadora.model.Cliente;

public class LocadoraTest {

    public static void main(String[] args) throws Exception {
        Locadora locadora = new Locadora();

        Cliente clienteLogado = new Cliente("11111111111", "Ana", 1000.0);
        Cliente outroCliente = new Cliente("22222222222", "Bruno", 1000.0);
        locadora.adicionarCliente(clienteLogado);
        locadora.adicionarCliente(outroCliente);
        locadora.adicionarVeiculo(new Carro("ABC1234", "Onix", 100.0, "Preto", "Chevrolet", false));

        locadora.autenticar(clienteLogado.getCpf(), "cliente");

        try {
            locadora.alugarVeiculo(outroCliente, locadora.getVeiculos().get(0), 1);
            throw new AssertionError("Cliente deveria ser impedido de alugar em nome de outro cliente.");
        } catch (LocadoraException expected) {
            System.out.println("Teste de permissão executado com sucesso.");
        }
    }
}
