# Documentação do Projeto Locadora em Java

## 1. Objetivo do Sistema

Este projeto implementa um sistema de locadora de veículos em Java com interface gráfica Swing. O sistema exemplifica conceitos de Programação Orientada a Objetos e arquitetura em camadas, incluindo:

- herança
- polimorfismo
- coleções (`ArrayList`)
- classe abstrata
- interface
- tratamento de exceções
- arquitetura em camadas (`app`, `service`, `model`, `interfaces`, `exception`)
- interface gráfica com Swing
- cadastro de clientes e veículos
- realização de aluguéis com opção de seguro

---

## 2. Estrutura do Projeto

A estrutura do repositório foi organizada da seguinte forma:

```text
src/
  main/
    java/
      locadora/
        app/
          Main.java
          ui/
            AluguelPanel.java
            ClientePanel.java
            DashboardFrame.java
            DashboardPanel.java
            LoginFrame.java
            RelatorioPanel.java
            VeiculoPanel.java
        exception/
          LocadoraException.java
        interfaces/
          ICliente.java
          ILocadora.java
          IVeiculo.java
        model/
          Aluguel.java
          Carro.java
          Cliente.java
          Funcionario.java
          Pessoa.java
          Veiculo.java
          Moto.java
        service/
          Locadora.java

docs/
  Documentacao.md
```

### Responsabilidade dos pacotes

- `locadora.interfaces`
  - contém as interfaces do domínio: `ICliente`, `IVeiculo` e `ILocadora`
- `locadora.model`
  - contém as entidades do domínio: `Pessoa`, `Cliente`, `Funcionario`, `Veiculo`, `Carro`, `Moto`, `Aluguel`
- `locadora.service`
  - contém a classe `Locadora` com a lógica de negócio
- `locadora.app`
  - contém a aplicação principal e os painéis da interface gráfica
- `locadora.exception`
  - contém a exceção customizada `LocadoraException`

---

## 3. Funcionamento do Sistema

A aplicação permite:

- autenticar usuários como cliente ou funcionário
- cadastrar veículos e editar seus dados por meio da aba de funcionário
- habilitar ou não a opção de seguro no cadastro de veículo
- realizar aluguéis com opção de seguro, adicionando 25% ao valor total
- gerar relatórios com veículos, clientes e aluguéis
- validar dados de entrada (CPF, nome, saldo, placa, diária, cor, marca, dias)
- tratar exceções de negócio, como saldo insuficiente e dados inválidos

---

## 4. Descrição dos Componentes

### 4.1 `locadora.interfaces.ICliente`

Define as operações que um cliente deve implementar:

- `String getCpf()`
- `void setCpf(String cpf)`
- `String getNome()`
- `void setNome(String nome)`
- `double getSaldo()`
- `void setSaldo(double saldo)`
- `void depositar(double valor)`
- `void debitar(double valor)`

### 4.2 `locadora.interfaces.IVeiculo`

Define as operações que um veículo deve implementar:

- `String getPlaca()`
- `void setPlaca(String placa)`
- `String getModelo()`
- `void setModelo(String modelo)`
- `double getValorDiaria()`
- `void setValorDiaria(double valorDiaria)`
- `String getCor()`
- `void setCor(String cor)`
- `String getMarca()`
- `void setMarca(String marca)`
- `boolean temSeguro()`
- `void setSeguro(boolean seguro)`
- `String getTipo()`

### 4.3 `locadora.interfaces.ILocadora`

Define o contrato para a classe de serviço `Locadora`:

- `void adicionarVeiculo(Veiculo veiculo)`
- `void adicionarCliente(Cliente cliente)`
- `void adicionarFuncionario(Funcionario funcionario)`
- `List<Veiculo> listarVeiculos()`
- `List<Cliente> listarClientes()`
- `List<Funcionario> listarFuncionarios()`
- `List<Veiculo> getVeiculos()`
- `List<Cliente> getClientes()`
- `List<Funcionario> getFuncionarios()`
- `List<Aluguel> getAlugueis()`
- `Cliente buscarClientePorCpf(String cpf)`
- `Veiculo buscarVeiculoPorPlaca(String placa)`
- `Aluguel alugarVeiculo(Cliente cliente, Veiculo veiculo, int dias) throws LocadoraException`
- `Aluguel alugarVeiculo(Cliente cliente, Veiculo veiculo, int dias, boolean comSeguro) throws LocadoraException`
- `List<Aluguel> listarAlugueis()`

### 4.4 `locadora.model.Pessoa`

Classe abstrata base para `Cliente` e `Funcionario`.

Atributos:

- `cpf`
- `nome`

### 4.5 `locadora.model.Cliente`

Herda de `Pessoa` e implementa `ICliente`.

Atributos:

- `saldo`

Responsabilidades:

- armazenar dados do cliente
- gerenciar saldo
- tratar depósitos e débitos

### 4.6 `locadora.model.Funcionario`

Herda de `Pessoa`.

Atributos:

- `salario`

Responsabilidade:

- representar funcionário da locadora

### 4.7 `locadora.model.Veiculo`

Classe abstrata que implementa `IVeiculo`.

Atributos:

- `placa`
- `modelo`
- `valorDiaria`
- `cor`
- `marca`
- `seguro`

Responsabilidade:

- definir atributos comuns a todos os veículos
- exigir que subclasses implementem `getTipo()`

### 4.8 `locadora.model.Carro`

Subclasse de `Veiculo` que representa um carro.

### 4.9 `locadora.model.Moto`

Subclasse de `Veiculo` que representa uma moto.

### 4.10 `locadora.model.Aluguel`

Classe que armazena um aluguel realizado.

Atributos:

- `cliente`
- `veiculo`
- `dias`
- `comSeguro`
- `valorTotal`

### 4.11 `locadora.service.Locadora`

Implementa a lógica de negócio.

Responsabilidades:

- armazenar listas de clientes, veículos, funcionários e aluguéis
- cadastrar clientes, veículos e funcionários
- realizar buscas por CPF e placa
- autenticar usuários como cliente ou funcionário
- efetuar aluguéis com validação de saldo, dias e seguro
- listar registros do sistema

### 4.12 `locadora.exception.LocadoraException`

Exceção customizada usada para erros de negócio.

### 4.13 `locadora.app.Main`

Ponto de entrada da aplicação. Inicia a interface gráfica em Swing e abre a tela de login.

### 4.14 `locadora.app.ui`

Pacote com os painéis e telas da interface:

- `LoginFrame` para autenticação
- `DashboardFrame` para navegação por perfil
- `VeiculoPanel` para cadastro e edição de veículos
- `AluguelPanel` para realização de aluguéis com seguro
- `RelatorioPanel` para geração de relatórios

---

## 5. Como Executar

1. Compile o projeto:

```powershell
javac -d bin $(Get-ChildItem -Recurse -Filter *.java -Path src/main/java | ForEach-Object { $_.FullName })
```

2. Execute a aplicação:

```powershell
java -cp bin locadora.app.Main
```

---

## 6. Guia rápido de uso

- Abra a aplicação e faça login com um CPF cadastrado.
- Use a senha `cliente` para acessar o perfil de cliente ou `funcionario` para o perfil de funcionário.
- No painel de funcionário, utilize a aba de veículos para cadastrar ou editar um veículo.
- O funcionário pode habilitar a opção de seguro no cadastro do veículo.
- Na aba de aluguel, selecione um cliente, um veículo e informe a quantidade de dias.
- Marque a opção de seguro para somar 25% ao valor total do aluguel.
- Clique em "Realizar aluguel" para concluir a reserva; o sistema desconta o valor do saldo do cliente.
- Clique em "Gerar relatório" para ver a lista atual de veículos, clientes e aluguéis.

## 7. Observações

- A interface separa permissões entre cliente e funcionário.
- O cadastro de veículo pode ser feito e editado pela área do funcionário.
- O seguro é opcional no aluguel e somente está disponível para veículos que possuem essa opção habilitada.
- O sistema trata entradas inválidas e exibe mensagens de erro apropriadas.
