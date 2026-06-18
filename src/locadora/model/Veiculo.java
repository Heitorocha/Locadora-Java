package locadora.model;

import locadora.interfaces.IVeiculo;

/**
 * Classe abstrata que representa um veículo da locadora.
 * Utiliza polimorfismo para permitir carros e motos como tipos concretos.
 */
public abstract class Veiculo implements IVeiculo {

    private String placa;
    private String modelo;
    private double valorDiaria;
    private String cor;
    private String marca;
    private boolean seguro;

    // Controle de disponibilidade
    private boolean disponivel;

    public Veiculo(String placa,
                   String modelo,
                   double valorDiaria,
                   String cor,
                   String marca,
                   boolean seguro) {

        this.placa = placa;
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
        this.cor = cor;
        this.marca = marca;
        this.seguro = seguro;

        // Todo veículo inicia disponível
        this.disponivel = true;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public boolean temSeguro() {
        return seguro;
    }

    public void setSeguro(boolean seguro) {
        this.seguro = seguro;
    }

    /**
     * Verifica se o veículo está disponível para locação.
     */
    public boolean isDisponivel() {
        return disponivel;
    }

    /**
     * Atualiza a disponibilidade do veículo.
     */
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public abstract String getTipo();

    @Override
    public String toString() {

        return String.format(
                "%s{placa=%s, modelo=%s, diaria=%.2f, cor=%s, marca=%s, seguro=%s, status=%s}",
                getTipo(),
                placa,
                modelo,
                valorDiaria,
                cor,
                marca,
                seguro ? "sim" : "não",
                disponivel ? "Disponível" : "Alugado"
        );
    }
}