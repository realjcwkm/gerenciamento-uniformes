# Sistema de Gerenciamento de Uniformes - IFRO Calama

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)

## 🎯 Sobre o Projeto

Este projeto é um **Sistema de Gerenciamento de Uniformes Escolares** desenvolvido para o **DEPAE** do **Instituto Federal de Educação, Ciência e Tecnologia de Rondônia (IFRO), Campus Porto Velho Calama**.

O principal objetivo do sistema é otimizar e modernizar o controle de distribuição e o gerenciamento do estoque de uniformes para os alunos. A ferramenta busca solucionar problemas comuns no processo manual, como a falta de controle sobre as entregas, a dificuldade em gerenciar o inventário disponível e a ausência de um registro centralizado e confiável das informações.

Com este sistema, a gestão de uniformes se torna mais **eficiente, transparente e organizada**.

***

## ⚙️ Manual de Instalação

Para executar o projeto em sua máquina, siga os passos abaixo.

### 1. Configuração do Banco de Dados

O sistema utiliza um banco de dados MySQL para o gerenciamento das informações.

* Execute o arquivo `uniformes.sql`, que se encontra na raiz do projeto, em um SGBD (Sistema de Gerenciamento de Banco de Dados) MySQL de sua preferência (MySQL Workbench, DBeaver, etc.). Este script criará o banco de dados e todas as tabelas necessárias para o funcionamento do sistema, além de popular o banco com dados iniciais.

### 2. Configuração da Conexão

Para que o sistema possa se comunicar com o banco de dados que você acabou de criar, é preciso configurar as credenciais de acesso.

* Dentro do pacote `DBConnection` do projeto, localize o arquivo `DBConfigExample`.
* Crie uma cópia deste arquivo no mesmo diretório e renomeie-a para `DBConfig`.
* Abra o novo arquivo `DBConfig` e preencha os valores dos atributos estáticos `user` e `password` com o usuário e a senha do seu banco de dados MySQL.

**Exemplo do arquivo `DBConfig`:**

```java
package com.mycompany.gerenciamento.uniformes.DBConnection;

public class DBConfig {
    final static String user = "seu_usuario_mysql";
    final static String password = "sua_senha_mysql";
}
```

## 🔑 Acesso ao Sistema

### 1. Acesso Inicial (Administrador)

Após a instalação, você pode acessar o sistema pela primeira vez utilizando um usuário administrador, criado automaticamente pelo script `uniformes.sql`.

Use as seguintes credenciais para fazer o login:

* **Matrícula:** `admin01`
* **Senha:** `admin`

### 2. Cadastro de Novos Servidores:

Após o acesso inicial, você pode cadastrar novos servidores:

* A senha padrão para o primeiro acesso de um novo servidor segue o formato: `"ifro+matricula"`.
* **Exemplo:** Para um servidor cadastrado com a matrícula `100123`, sua senha de primeiro acesso será `ifro100123`.

**Após seguir estes passos, o sistema estará pronto para ser utilizado.**
