package locadora.app;

import javax.swing.SwingUtilities;
import locadora.app.ui.LoginFrame;
import locadora.model.*;
import locadora.service.Locadora;

public class Main {

    public static void main(String[] args) {

        Locadora locadora = new Locadora();

        if (locadora.getClientes().isEmpty()
                && locadora.getFuncionarios().isEmpty()
                && locadora.getVeiculos().isEmpty()) {

            locadora.adicionarCliente(
                    new Cliente("12345678900", "Fernanda", 1200.0));

            locadora.adicionarCliente(
                    new Cliente("98765432100", "Ricardo", 850.0));

            locadora.adicionarFuncionario(
                    new Funcionario("11223344556", "Patrícia", 3200.0));

            locadora.adicionarFuncionario(
                    new Funcionario("22334455667", "Lucas", 2800.0));

            locadora.adicionarVeiculo(
                    new Carro("ABC-1234", "Sedan", 150.0,
                            "Preto", "Toyota", true));

            locadora.adicionarVeiculo(
                    new Moto("XYZ-4321", "Sport", 90.0,
                            "Vermelho", "Honda", false));
        }

        SwingUtilities.invokeLater(() ->
                new LoginFrame(locadora));
    }
}