package locadora.model;

/**
 * Classe que representa um carro disponível para locação.
 */
public class Carro extends Veiculo {

    public Carro(String placa, String modelo, double valorDiaria, String cor, String marca, boolean seguro) {
        super(placa, modelo, valorDiaria, cor, marca, seguro);
    }

    @Override
    public String getTipo() {
        return "Carro";
    }
}
