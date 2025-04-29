# NarutoCrud

Este projeto é uma API RESTful simples construída com Spring Boot (versão 3.4.4) e tem como objetivo a gestão de personagens do universo Naruto. A API permite criar, atualizar, deletar e listar personagens, além de permitir o uso de jutsus e desvios.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.4.4
- Spring Security (JWT, autenticação básica)
- Swagger (documentação da API)
- H2 (banco de dados em memória)

## Funcionalidades

- **Personagens**: Cadastro, atualização e remoção de personagens.
- **Jutsus**: Uso de poder específicos para cada personagem.
- **Desvios**: Definição de estratégias de desvio para os personagens.

## Endpoints

- **POST /personagens**: Criação de um novo personagem.
- **GET /personagens**: Listagem de todos os personagens.
- **POST /personagens/{id}/jutsus**: Uso de jutsu por um personagem.
- **POST /personagens/{id}/desviar**: Estratégia de desvio de um personagem.

## Como Executar

1. Clone este repositório:
   ```bash
   git clone https://github.com/gabrielhoffmannhub/NarutoCrud-Parte1.git
2. Acesse o diretório do projeto:
   ```bash
    cd NarutoCrud-Parte1
3. Compile e rode o projeto:
    ```bash
    ./mvnw spring-boot:run
4. Acesse a documentação da API através do Swagger:
    ```bash
    http://localhost:8080/swagger-ui/

## Testes
Os testes podem ser executados com:
  ```bash
  ./mvnw test

