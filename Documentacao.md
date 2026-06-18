# Documentação do Projeto Locadora em Java

## 1. Objetivo do Sistema

Este projeto implementa um sistema de locadora de veículos em Java. O objetivo é demonstrar princípios de Programação Orientada a Objetos (POO) em uma aplicação simples, permitindo:

- cadastro de clientes;
- cadastro de veículos (carros e motos);
- gerenciamento de saldo do cliente com depósitos e débitos;
- realização de locações de veículos;
- registro e exibição de aluguéis;
- demonstração de herança, abstração, polimorfismo, encapsulamento e interfaces.

O sistema foi organizado para facilitar o entendimento e a manutenção, separando contratos (interfaces), classes de modelo, serviços de negócio e a aplicação principal.

---

## 2. Organização de Pacotes

O projeto é dividido em pacotes com responsabilidades claras:

- `locadora.interfaces`
  - contém as interfaces do sistema;
  - define contratos para comportamento de cliente, veículo e locadora.

- `locadora.model`
  - contém as classes de domínio do sistema;
  - representa entidades como cliente, veículo, pessoa, funcionário e aluguel.

- `locadora.service`
  - contém a classe `Locadora`, responsável pela lógica de negócio;
  - gerencia listas de veículos, clientes e aluguéis.

- `locadora.app`
  - contém a classe `Main`;
  - controla o fluxo da aplicação e demonstra o funcionamento do sistema.

---

## 3. Descrição das Interfaces

### 3.1 `locadora.interfaces.ICliente`

Interface que define os comportamentos de um cliente da locadora.

Atributos esperados (acessados por métodos):

- `cpf`
- `nome`
- `saldo`

Métodos:

- `String getCpf()`
- `void setCpf(String cpf)`
- `String getNome()`
- `void setNome(String nome)`
- `double getSaldo()`
- `void setSaldo(double saldo)`
- `void depositar(double valor)`
- `void debitar(double valor)`

Esta interface permite que qualquer classe que represente cliente implemente as operações necessárias para manipular dados pessoais e saldo.

### 3.2 `locadora.interfaces.IVeiculo`

Interface que define o comportamento de veículos no sistema.

Métodos:

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

Esta interface define os comportamentos comuns a todos os veículos, permitindo polimorfismo entre carros e motos.

### 3.3 `locadora.interfaces.ILocadora`

Interface que define os comportamentos da classe de serviço que gerencia a locadora.

Métodos:

- `void adicionarVeiculo(Veiculo veiculo)`
- `void adicionarCliente(Cliente cliente)`
- `List<Veiculo> listarVeiculos()`
- `List<Cliente> listarClientes()`
- `Aluguel alugarVeiculo(Cliente cliente, Veiculo veiculo, int dias)`
- `List<Aluguel> listarAlugueis()`

Esta interface estabelece o contrato para o serviço de gerenciamento da locadora, permitindo que a classe `Locadora` implemente a lógica de negócio necessária.

---

## 4. Descrição das Classes

### 4.1 `locadora.model.Pessoa`

Classe abstrata que representa uma pessoa no sistema.

Atributos:

- `cpf` - identifica a pessoa;
- `nome` - nome completo da pessoa.

Métodos:

- `getCpf()` / `setCpf(String cpf)`
- `getNome()` / `setNome(String nome)`
- `toString()` - retorna uma representação textual da pessoa.

Responsabilidade:

- fornecer atributos e comportamentos comuns para pessoas do sistema;
- ser base para classes concretas `Cliente` e `Funcionario`.

### 4.2 `locadora.model.Cliente`

Classe que representa um cliente da locadora.

Herança e interfaces:

- herda de `Pessoa`;
- implementa `ICliente`.

Atributos:

- `saldo` - valor disponível para pagar aluguéis.

Métodos principais:

- `getSaldo()` / `setSaldo(double saldo)`
- `depositar(double valor)` - adiciona saldo;
- `debitar(double valor)` - retira saldo, lançando erro se for inválido ou insuficiente;
- `toString()` - mostra dados do cliente.

Responsabilidade:

- representar um cliente com dados pessoais e saldo financeiro;
- executar operações financeiras;
- cumprir o contrato definido por `ICliente`.

### 4.3 `locadora.model.Funcionario`

Classe que representa um funcionário da locadora.

Herança:

- estende `Pessoa`.

Atributos:

- `salario` - remuneração do funcionário.

Métodos:

- `getSalario()` / `setSalario(double salario)`
- `toString()` - retorna a representação textual do funcionário.

Responsabilidade:

- mostrar aplicação de herança para outro tipo de pessoa;
- exemplificar reutilização de atributos e métodos de `Pessoa`.

### 4.4 `locadora.model.Veiculo`

Classe abstrata que representa um veículo da locadora.

Herança e interfaces:

- implementa `IVeiculo`;
- é uma classe abstrata, portanto não pode ser instanciada diretamente.

Atributos:

- `placa`
- `modelo`
- `valorDiaria`
- `cor`
- `marca`
- `seguro`

Métodos:

- getters e setters para cada atributo;
- `public abstract String getTipo()` - método abstrato que obriga subclasses a fornecer o tipo de veículo;
- `toString()` - converte os dados do veículo em texto.

Responsabilidade:

- representar dados básicos e comportamentos comuns de qualquer veículo;
- definir o contrato que todas as subclasses de veículo devem seguir.

### 4.5 `locadora.model.Carro`

Classe concreta que representa um carro.

Herança:

- estende `Veiculo`.

Métodos:

- `getTipo()` - retorna a string `"Carro"`.

Responsabilidade:

- representar o tipo de veículo carro;
- fornecer a implementação concreta do método abstrato `getTipo()`.

### 4.6 `locadora.model.Moto`

Classe concreta que representa uma moto.

Herança:

- estende `Veiculo`.

Métodos:

- `getTipo()` - retorna a string `"Moto"`.

Responsabilidade:

- representar o tipo de veículo moto;
- fornecer a implementação concreta do método abstrato `getTipo()`.

### 4.7 `locadora.model.Aluguel`

Classe que representa um aluguel realizado.

Atributos:

- `cliente` - cliente que alugou o veículo;
- `veiculo` - veículo alugado;
- `dias` - número de dias do aluguel;
- `valorTotal` - valor calculado automaticamente.

Métodos:

- getters para todos os atributos;
- `toString()` - retorna a representação textual do aluguel.

Responsabilidade:

- armazenar os dados de uma locação;
- calcular o valor total a partir de `valorDiaria` e `dias`.

### 4.8 `locadora.service.Locadora`

Classe que representa a locadora como serviço.

Implementa:

- `ILocadora`.

Atributos:

- `veiculos` - lista de veículos disponíveis;
- `clientes` - lista de clientes cadastrados;
- `alugueis` - lista de aluguéis efetuados.

Métodos:

- `adicionarVeiculo(Veiculo veiculo)` - adiciona veículo à lista;
- `adicionarCliente(Cliente cliente)` - adiciona cliente à lista;
- `listarVeiculos()` - exibe e retorna a lista de veículos;
- `listarClientes()` - exibe e retorna a lista de clientes;
- `alugarVeiculo(Cliente cliente, Veiculo veiculo, int dias)` - realiza o aluguel, debita o cliente e registra o aluguel;
- `listarAlugueis()` - exibe e retorna a lista de aluguéis.

Responsabilidade:

- concentrar a lógica de negócio do sistema;
- orquestrar interação entre clientes, veículos e aluguel.

### 4.9 `locadora.app.Main`

Classe principal que demonstra o funcionamento do sistema.

Fluxo principal:

1. Cria uma instância de `Locadora`.
2. Cria clientes usando a interface `ICliente`.
3. Exibe dados dos clientes.
4. Realiza operações de depósito e débito.
5. Adiciona os clientes à `Locadora`.
6. Cria veículos (`Carro` e `Moto`).
7. Adiciona os veículos à `Locadora`.
8. Realiza aluguéis chamando `Locadora.alugarVeiculo(...)`.
9. Exibe listas de veículos, clientes e aluguéis.
10. Cria um `Funcionario` para demonstrar herança.

Responsabilidade:

- demonstrar como usar as classes e interfaces do sistema;
- prover um cenário completo de uso.

---

## 5. Relações entre as Classes e Interfaces

### 5.1 Herança

- `Pessoa` é classe abstrata base.
- `Cliente` estende `Pessoa`.
- `Funcionario` estende `Pessoa`.
- `Veiculo` é classe abstrata base para veículos.
- `Carro` e `Moto` estendem `Veiculo`.

### 5.2 Implementação de Interfaces

- `Cliente` implements `ICliente`.
- `Veiculo` implements `IVeiculo`.
- `Locadora` implements `ILocadora`.

### 5.3 Associação de Objetos

- `Aluguel` contém referências para `Cliente` e `Veiculo`.
- `Locadora` mantém coleções de `Cliente`, `Veiculo` e `Aluguel`.
- `Main` cria e associa objetos para formar o fluxo de locação.

### 5.4 Polimorfismo

- Clientes são manipulados por `ICliente` em `Main`.
- Veículos são manipulados por referências de tipo `Veiculo`.
- `Carro` e `Moto` são tratados de forma genérica como `Veiculo`.
- `getTipo()` é chamado em `Veiculo`, retornando valor específico de cada subclasse.

---

## 6. Conceitos de POO Aplicados

### 6.1 Encapsulamento

Atributos privados (`private`) são usados em todas as classes:

- `Pessoa`: `cpf`, `nome`
- `Cliente`: `saldo`
- `Funcionario`: `salario`
- `Veiculo`: `placa`, `modelo`, `valorDiaria`, `cor`, `marca`, `seguro`
- `Aluguel`: `cliente`, `veiculo`, `dias`, `valorTotal`
- `Locadora`: listas privadas

O acesso a esses atributos é feito por métodos públicos (`getters`/`setters`), garantindo controle sobre leitura e escrita.

### 6.2 Herança

- `Cliente` e `Funcionario` reaproveitam atributos e comportamento de `Pessoa`.
- `Carro` e `Moto` reaproveitam atributos e comportamento de `Veiculo`.

Isso evita duplicação de código e permite definir o que é comum em um lugar só.

### 6.3 Polimorfismo

- `ICliente cliente = new Cliente(...)`
- `Veiculo v = new Carro(...)`
- `Veiculo v = new Moto(...)`

Essas atribuições mostram que um objeto concreto pode ser manipulado como seu tipo mais geral.

### 6.4 Abstração

- `Pessoa` e `Veiculo` são classes abstratas.
- Elas representam conceitos gerais sem instância direta.
- Forçam subclasses a implementar comportamentos específicos.

### 6.5 Classes Abstratas

- `Pessoa`: define dados comuns a pessoas.
- `Veiculo`: define dados comuns a veículos e declara o método abstrato `getTipo()`.

### 6.6 Interfaces

- `ICliente` define contrato de cliente.
- `IVeiculo` define contrato de veículo.
- `ILocadora` define contrato de serviço.

Interfaces permitem separar definição de comportamento da implementação real.

### 6.7 Associação entre Objetos

- `Aluguel` associa um `Cliente` a um `Veiculo`.
- `Locadora` associa vários `Cliente`, `Veiculo` e `Aluguel`.
- Assim, o sistema modela relações reais de negócio entre os objetos.

### 6.8 Sobrescrita de Métodos (`@Override`)

- `Cliente`: `getCpf()`, `setCpf()`, `getNome()`, `setNome()`, `depositar()`, `debitar()`, `toString()`.
- `Veiculo`: `toString()`.
- `Carro`: `getTipo()`.
- `Moto`: `getTipo()`.
- `Funcionario`: `toString()`.

A anotação `@Override` garante que o método realmente sobrescreve a definição da superclasse ou interface.

---

## 7. Diagrama UML Simples

```
locadora.interfaces
-------------------
ICliente
IVeiculo
ILocadora

locadora.model
-------------------
Pessoa <|-- Cliente
      <|-- Funcionario

IVeiculo <|.. Veiculo <|-- Carro
                     <|-- Moto
                     |     
Cliente --> Aluguel
Veiculo --> Aluguel

locadora.service
-------------------
ILocadora <|.. Locadora

locadora.app
-------------------
Main --> Locadora
Main --> Cliente
Main --> Veiculo
Main --> Aluguel
```

Mais detalhado:

```
+----------------+      +----------------+
|    ICliente    |<-----|    Cliente     |
+----------------+      +----------------+
| +getCpf()      |      | -saldo         |
| +getNome()     |      | +depositar()   |
| +getSaldo()    |      | +debitar()     |
+----------------+      +----------------+
                           ^
                           |
                      +----+----+
                      | Pessoa  |
                      +---------+
                      | -cpf    |
                      | -nome   |
                      +---------+

+----------------+      +----------------+
|    IVeiculo    |<-----|    Veiculo     |
+----------------+      +----------------+
| +getPlaca()    |      | -placa         |
| +getModelo()   |      | -modelo        |
| +getTipo()     |      | +getTipo()     |
+----------------+      +----------------+
                             ^   ^
                             |   |
                      +------+------+   +------+
                      |  Carro    |   |  Moto |
                      +-----------+   +-------+

+----------------+      +----------------+
|   ILocadora    |<-----|   Locadora    |
+----------------+      +----------------+
| +adicionar...  |      | -veiculos      |
| +listar...     |      | -clientes      |
+----------------+      +----------------+
```

---

## 8. Funcionamento da aplicação - passo a passo

1. `Main` cria uma instância de `Locadora`.
2. Cria os clientes como `ICliente`.
3. Exibe e altera o saldo dos clientes usando `depositar()` e `debitar()`.
4. Adiciona os clientes ao serviço `Locadora`.
5. Cria os veículos `Carro` e `Moto`.
6. Adiciona veículos ao serviço `Locadora`.
7. Realiza a operação `alugarVeiculo`.
   - calcula o valor total do aluguel;
   - debita o valor do cliente;
   - registra o aluguel em uma lista.
8. Exibe listas de veículos, clientes e aluguéis.

Este fluxo demonstra como os objetos colaboram para executar a operação de locação.

---

## 9. Conclusão

O projeto é um exemplo claro de aplicação de Programação Orientada a Objetos em Java.
Ele utiliza:

- encapsulamento para proteger estados internos;
- abstração para modelar pessoas e veículos genericamente;
- herança para reutilizar código e estruturar tipos;
- polimorfismo para tratar objetos similares de forma genérica;
- interfaces para separar contrato de implementação;
- associação para ligar clientes, veículos e aluguéis;
- sobrescrita de métodos para adaptar comportamento em subclasses.

A arquitetura está organizada e permite que qualquer pessoa entenda a intenção de cada componente e como eles se relacionam.
