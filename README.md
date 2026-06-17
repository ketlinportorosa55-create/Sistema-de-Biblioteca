# Sistema-de-Biblioteca

O objetivo principal deste projeto é consolidar e aplicar os pilares da Programação Orientada a Objetos em um cenário prático do mundo real. O sistema simula as operações do dia a dia de uma biblioteca, garantindo consistência no fluxo de dados desde o cadastro até a finalização e gravação das informações.

A arquitetura do sistema divide-se em camadas bem definidas seguindo o modelo MVC:

Model: Representação das entidades de negócio e contratos.
View: Interface de interação textual via terminal com o usuário.
Controller: Camada intermediária que orquestra as regras de negócio e manipula os dados.

===== Conceitos de POO Aplicados e Relações =====


Herança e Classes Abstratas:

Pessoa é uma classe abstrata que serve como base genérica compartilhada. Ela implementa as interfaces de persistência e força as subclasses a definirem sua forma de identificação pelo método abstrato getDocumentoPrincipal().

Usuario e Autor estendem a classe Pessoa, herdando seus atributos base (id, nome, email) e especializando seus comportamentos.


Polimorfismo e Interfaces:

Interface Persistivel: Garante que todas as classes de modelo possuam obrigatoriamente um comportamento de identificação (getId e setId).

Sobrecarga de Métodos: A classe Livro utiliza sobrecarga no método atualizarDados(), permitindo alterar opcionalmente apenas o título ou o título em conjunto com o objeto Autor.


Associações entre Objetos:

Associação (Agregação) entre Livro e Autor: Um Livro possui uma referência para um objeto Autor. Se o livro for deletado, o autor correspondente continua existindo de forma independente no sistema.

Associação (Composição/Dependência) em Emprestimo: A classe Emprestimo associa diretamente as instâncias de Usuario e Livro. A existência de um registro de empréstimo depende obrigatoriamente da coexistência prévia dessas duas entidades ativas.

===== Como Executar o Projeto =====

.....

===== Por que e como a IA foi abordada:=====

  Refatoração de Arquitetura: Auxílio na estruturação modular das camadas para garantir a separação estrita de responsabilidades exigida pelo padrão MVC.

  Tratamento de Exceções: Apoio no desenho da robustez de capturas, como na criação da exceção personalizada EntidadeNaoEncontradaException e tratamento de NumberFormatException na camada de View.

  Mentoria em Boas Práticas: Revisão de código focada em legibilidade, boas práticas de encapsulamento e implementação da interface Serializable para persistência robusta em arquivos locais.

