package locadora.model;

/**
 * Classe abstrata que representa uma pessoa no sistema.
 * Esta classe é utilizada como base para clientes e funcionários.
 */
public abstract class Pessoa {

    private String cpf;
    private String nome;

    public Pessoa(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return String.format("Pessoa{cpf=%s, nome=%s}", cpf, nome);
    }
}
