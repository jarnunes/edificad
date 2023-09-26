## Edificad

<!--ts-->
### 📖 Menu
* [Features](#-features)
* [Running project](#-executar-em-ambiente-de-desenvolvimento)
* [Technologies](#-tecnologias)
<!--te-->

--------------------------------------------------------------------------------
### 💡 Features
- [x] ....
- [x] ....
- [x] ....

--------------------------------------------------------------------------------
### ⚙ Executar em ambiente de desenvolvimento

1. Faça a instalação do java 20 ou superior. Desconsidere caso já tenha a versão mais recente instalada. 
   [Link para download java 20+](https://www.oracle.com/br/java/technologies/downloads/)
2. Faça a instalação do postgreSQL e em seguida acesse o banco como superusuario. [Link para download](https://www.postgresql.org/download/)
3. Para criação do database e schemas configurados no profile default, rode os comandos abaixo (um de cada vez)
    ````sql
    create database java;
    use database java;
    create user sa with password 'root'
    create schema edificad authorization sa;
    grant select, insert, update, delete on all TABLES in schema edificad  TO sa;
    ````
4. Clone o projeto com o comando em ambiente local com o comando abaixo com `cmd, powershell ou outra ferramenta`
    ```shell  
    git clone git@github.com:jarnunes/edificad.git
    ```
5. Abra o projeto em uma IDE (neste exemplo foi utilizado o `Intellij IDEA` e execute o projeto;
6. Acesse o endpoint de teste: [http://localhost:5000/api/demo/person/list](http://localhost:5000/api/demo/person/list)

--------------------------------------------------------------------------------

### Documentação da API

1. Para consultar a documentação da API, acesse o link [Edificad API](http://edificad-api.jnunesc.com.br/)
--------------------------------------------------------------------------------

### 🛠 Tecnologias

- [Java 20+](https://www.oracle.com/news/announcement/oracle-releases-java-20-2023-03-21/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Hibernate](https://hibernate.org/)
- [PostgreSQL](https://www.postgresql.org/#)
