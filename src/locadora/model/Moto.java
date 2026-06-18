package locadora.model;

/**
 * Classe que representa uma moto disponível para locação.
 */
public class Moto extends Veiculo {

    public Moto(String placa,
                String modelo,
                double valorDiaria,
                String cor,
                String marca,
                boolean seguro) {

        super(placa, modelo, valorDiaria, cor, marca, seguro);
    }

    @Override
    public String getTipo() {
        return "Moto";
    }
}
