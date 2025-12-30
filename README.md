# 🗄️ Projeto de análise e revisão de diversos sistemas de bancos de dados

Este projeto é uma aplicação Java Swing que demonstra a implementação prática do **Design Pattern Strategy**.

O sistema simula um gerenciador de banco de dados universal que altera seu comportamento, estrutura de dados e regras de negócio dinamicamente (em tempo de execução) com base na tecnologia de banco de dados selecionada (SQL vs NoSQL vs Real-time).

## 🎯 Objetivo

O objetivo principal é didático: mostrar como desacoplar a interface do usuário da lógica de persistência. A aplicação permite alternar entre simulações de **PostgreSQL**, **MongoDB**, **Firebase** e **Couchbase**, explicando o cenário ideal de uso para cada um.

## 🚀 Funcionalidades

- **Troca de Estratégia em Tempo Real:** Mude do PostgreSQL para o MongoDB e veja as colunas da tabela e o contexto mudarem instantaneamente.
- **CRUD Simulado:** Operações de Create, Read, Update e Delete mantidas em memória (Listas) para demonstrar a persistência volátil.
- **Interface Dinâmica:** A `JTable` se reconstrói baseada nos metadados fornecidos pela estratégia selecionada.
- **Contexto Educativo:** Cada banco possui um botão de informação explicando *por que* aquela tecnologia seria usada no mundo real (ex: ACID para financeiro, Documentos para catálogos).

## 🏗️ Arquitetura (Strategy Pattern)

O projeto segue estritamente o padrão Strategy:

1.  **A Interface (`DatabaseStrategy`)**: Define o contrato (Create, Read, Update, Delete, GetColumnNames, etc.).
2.  **As Estratégias Concretas**:
    * `PostgresImplementation`: Simula transações financeiras (SQL).
    * `MongoImplementation`: Simula catálogo de produtos (NoSQL Document).
    * `FirebaseImplementation`: Simula gestão de funcionários em tempo real.
    * `CouchbaseImplementation`: Simula cache de sessão de alta performance.
3.  **O Contexto**: A classe `GerenciadorBancoDados` (GUI) que consome a estratégia sem saber qual implementação está rodando.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (JDK 8+)
* **GUI:** Swing (JFrame, JTable, DefaultTableModel)
* **Conceitos:** Polimorfismo, Interfaces, Collections API.

