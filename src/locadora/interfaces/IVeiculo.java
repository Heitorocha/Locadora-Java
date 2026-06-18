package locadora.interfaces;

public interface IVeiculo {

    String getPlaca();
    void setPlaca(String placa);

    String getModelo();
    void setModelo(String modelo);

    double getValorDiaria();
    void setValorDiaria(double valorDiaria);

    String getCor();
    void setCor(String cor);

    String getMarca();
    void setMarca(String marca);

    boolean temSeguro();
    void setSeguro(boolean seguro);

    String getTipo();
}
