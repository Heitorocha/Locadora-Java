package locadora.model;

/**
 * Classe que representa um funcionário da locadora.
 */
public class Funcionario extends Pessoa {

    private double salario;

    public Funcionario(String cpf, String nome, double salario) {
        super(cpf, nome);
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return String.format("Funcionario{cpf=%s, nome=%s, salario=%.2f}",
                getCpf(), getNome(), salario);
    }
}
