package locadora;

public class Carro {
    private String placa;
    private String modelo;
    private double valorDiaria;
    private int portas;

    public Carro(String placa, String modelo, double valorDiaria, int portas) {
        this.placa = placa;
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
        this.portas = portas;
    }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public double getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(double valorDiaria) { this.valorDiaria = valorDiaria; }
    public int getPortas() { return portas; }
    public void setPortas(int portas) { this.portas = portas; }

    @Override
    public String toString() {
        return String.format("Carro{placa=%s, modelo=%s, valorDiaria=%.2f, portas=%d}", placa, modelo, valorDiaria, portas);
    }
}
