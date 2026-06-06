package locadora;

public class Cliente {
    private String cpf;
    private String nome;
    private double saldo;

    public Cliente(String cpf, String nome, double saldo) {
        this.cpf = cpf;
        this.nome = nome;
        this.saldo = saldo;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public void depositar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        this.saldo += valor;
    }

    public void debitar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor deve ser positivo");
        if (valor > saldo) throw new IllegalArgumentException("Saldo insuficiente");
        this.saldo -= valor;
    }

    @Override
    public String toString() {
        return String.format("Cliente{cpf=%s, nome=%s, saldo=%.2f}", cpf, nome, saldo);
    }
}
