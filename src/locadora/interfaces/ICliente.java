package locadora.interfaces;

// Interface que define as operações de um cliente
public interface ICliente {

    String getCpf();
    void setCpf(String cpf);

    String getNome();
    void setNome(String nome);

    double getSaldo();
    void setSaldo(double saldo);

    void depositar(double valor);

    void debitar(double valor);
}
