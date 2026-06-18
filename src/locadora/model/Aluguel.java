package locadora.model;

/**
 * Classe que representa um aluguel realizado pela locadora.
 */
public class Aluguel {

    private final Cliente cliente;
    private final Veiculo veiculo;
    private final int dias;
    private final double valorTotal;

    public Aluguel(Cliente cliente, Veiculo veiculo, int dias) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dias = dias;
        this.valorTotal = veiculo.getValorDiaria() * dias;
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

    @Override
    public String toString() {
        return String.format("Aluguel{cliente=%s, veiculo=%s, dias=%d, total=%.2f}",
                cliente.getNome(), veiculo.getTipo(), dias, valorTotal);
    }
}
