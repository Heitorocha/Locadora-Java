package locadora.model;

/**
 * Classe que representa um aluguel realizado pela locadora.
 */
public class Aluguel {

    private final Cliente cliente;
    private final Veiculo veiculo;
    private final int dias;
    private final boolean comSeguro;
    private final double valorTotal;

    public Aluguel(Cliente cliente, Veiculo veiculo, int dias, boolean comSeguro) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dias = dias;
        this.comSeguro = comSeguro;
        this.valorTotal = veiculo.calcularValorLocacao(dias, comSeguro);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public int getDias() {
        return dias;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public boolean isComSeguro() {
        return comSeguro;
    }

    @Override
    public String toString() {
        return String.format("Aluguel{cliente=%s, veiculo=%s, dias=%d, seguro=%s, total=%.2f}",
                cliente.getNome(), veiculo.getTipo(), dias, comSeguro ? "sim" : "nao", valorTotal);
    }
}
