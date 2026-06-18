package locadora.model;

import locadora.interfaces.ICliente;

// Classe responsável por armazenar e gerenciar os dados de um cliente
public class Cliente extends Pessoa implements ICliente {

    private double saldo;

    public Cliente(String cpf, String nome, double saldo) {
        super(cpf, nome);
        this.saldo = saldo;
    }

    @Override
    public String getCpf() {
        return super.getCpf();
    }

    @Override
    public void setCpf(String cpf) {
        super.setCpf(cpf);
    }

    @Override
    public String getNome() {
        return super.getNome();
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    @Override
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // Adiciona um valor ao saldo do cliente
    @Override
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
        this.saldo += valor;
    }

    // Retira um valor do saldo do cliente
    @Override
    public void debitar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }

        if (valor > saldo) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        this.saldo -= valor;
    }

    // Retorna uma representação em texto do objeto
    @Override
    public String toString() {
        return String.format(
                "Cliente{cpf=%s, nome=%s, saldo=%.2f}",
                getCpf(), getNome(), saldo);
    }
}