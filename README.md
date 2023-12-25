![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1.3-green)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.puc%3Aedificad&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.puc%3Aedificad)


## Edificad
API desenvolvida como trabalho extensionista na disciplinda (Trabalho Interdisciplinar: Aplicações para Sustentabilidade).
Como um dos objetivos da disciplina é o desenvolvimento de soluções/artefatos aplicados a ações sustentáveis, o grupo optou por 
trabalhar em parceria com a ONG Edificando Vidas. 

Após identificar um problema relacionado à sustentabilidade, foi proposto o desenvolvimento de um sistema de cadastro e distribuição de cestas que permita ao parceiro reduzir o consumo excessivo de papel(ação ligada à sustentabilidade) e centralizar todas as informações dispersas atualmente - dados de beneficiários e voluntários em arquivos físicos, planilhas e mensagens de whatsapp.

--------------------------------------------------------------------------------
### ⚙ Configurar ambiente de desenvolvimento

1. Instalar java 17+;  Desconsidere caso já tenha a versão mais recente instalada. 
   [Link oficial](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. Instalar e configurar o maven; [Link oficial](https://maven.apache.org/download.cgi)
3. Instalar postgreSQL e configuar o SGBD; [Link oficial](https://www.postgresql.org/download/)
4. Para criação do database e schemas configurados no profile default, rodar os comandos abaixo
    ````sql
    create database java;
    use database java;
    create user sa with password 'root'
    create schema edificad authorization sa;
    grant select, insert, update, delete on all TABLES in schema edificad  TO sa;
    ````
5. Clone o projeto com o comando em ambiente local com o comando abaixo com `cmd, powershell...`
    ```shell  
    git clone git@github.com:jarnunes/edificad.git
    ```
6. Abra o diretório do projeto em um terminal `cmd, powershell...` e rode o comando ``mvn clean package`` para compilar o projeto;
7. No mesmo terminal do passo anterior, rodar o comando ``java -jar .\target\edificad.jar`` para iniciar a aplicação;
8. Acesse o URL [http://localhost:5000/api](http://localhost:5000/api)

--------------------------------------------------------------------------------

### Documentação da API ###
 - Para consultar todos os endpoints, bem como outras informações, acesse: [Documentação API](https://edificad-api.jnunesc.com.br/)
--------------------------------------------------------------------------------

### 🛠 Tecnologias

- [Java 17+](https://www.oracle.com/news/announcement/oracle-releases-java-20-2023-03-21/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.html)
- [PostgreSQL](https://www.postgresql.org/#)
- [jUnit](https://junit.org/junit5/docs/current/user-guide/)