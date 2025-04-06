# Sobre
Projeto criado como prova de proficiência no componente disciplinar de Programação em Orientação à Objetos, ministrada pelo professor Fernando Kenji Kamei.

## Aluno

Eike Fabrício da Silva

# Critérios
```
Critérios de Avaliação dos Projetos
Os seguintes critérios serão adotados na avaliação dos projetos:

- Utilizar classes abstratas, classes não abstratas, e interfaces;
- Uso adequado da OO: classes e encapsulamento;
- Uso de construtores;
- Aplicação do conceito de herança;
- Uso de Polimorfismo;
- Uso de Collections;
- Uso de Testes Unitários automatizados para cada regra de negócio (BO) e acesso ao BD (DAO);
- Utilizar ao menos 2 padrões de projeto e explicar onde está sendo aplicado;
- Uso de banco de dados (insert, update, delete, select);
- Estruturação do projeto em camadas (View, BO, VO, DAO);
- Uso de exceções (deve-se cria exceções próprias e tratá-las corretamente).

Outras informações:
* Projeto estar no projeto no GitHub (não é para colocar o projeto final apenas, mas o projeto desde o início, para que possa ver os commits que cada integrante fez);
* O uso de interface mobile ou web é OPCIONAL.
```

# Adequação ao critério

> [!WARNING]
> O projeto utiliza do banco de dados `H2`, sendo um banco relacional em memória, utilizei para facilitar a implementação e maior garantia de que irá funcionar nos computadores do IFAL, vez que não depende de conexão à um banco externo de fato.
> Foram implementadas as quatro operações (CRUD).

No projeto, utilizei de classe abstrata `Account`, com suas respectivas implementações sendo `SavingsAccount` e `CheckingAccount`. Construtores foram usados e classes devidamente encapsuladas.

Uso de `interface` e `enum` pode ser observado em `br.edu.ifal.eikefab.account.repository.AccountRepository` e `br.edu.ifal.eikefab.account.AccountType`, respectivamente.

Como padrão de projeto, implementei o MVC, onde temos como

`Model`

- Account
  - Implementado utilizando design pattern o conceito de Builder.
    - Utilizado em momentos em que há muitos parâmetros no construtor, como uma maneira de enxugá-los.
- SavingsAccount
- CheckingAccount

`View`

- Menu
- MenuHandler
- MenuOption

`Controller`

- AccountController
  - Utiliza como design pattern o conceito de Singleton;
  - Conectado ao banco via `AccountRepository`.

Temos exceções específicas para cada acesso e, ao todo, 46 testes, espalhados por todo o código obtendo 88% de coverage.