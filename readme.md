# Events API

Este repositório contém o código-fonte de uma API para gerenciamento de eventos e inscrições, a API foi desenvolvida durante o NLW Connect na trilha de Java.

## Descrição

A API Events permite:

*   Criar e gerenciar eventos (título, local, data, hora, preço, etc.).
*   Permitir que usuários se inscrevam em eventos.
*   Gerenciar um sistema de indicação, onde usuários podem indicar outros para se inscreverem em eventos.
*   Gerar rankings de usuários com base no número de indicações.

## Tecnologias Utilizadas

*   **Java:** Linguagem de programação principal.
*   **Spring Boot:** Framework para desenvolvimento rápido de aplicações Java.
*   **Spring Data JPA:** Módulo do Spring para facilitar o acesso e manipulação de dados em bancos de dados relacionais.
*   **Hibernate:** ORM (Object-Relational Mapping) utilizado pelo Spring Data JPA.
*   **H2 Database (em memória):** Banco de dados utilizado para desenvolvimento e testes (pode ser facilmente substituído por outro banco de dados).
*   **Maven:** Ferramenta de gerenciamento de dependências e build.
*   **Docker:** Plataforma para empacotar, distribuir e executar aplicações em containers isolados, garantindo consistência e portabilidade.

## Estrutura do Projeto

O projeto está organizado nas seguintes pastas principais:

```
events/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── br/com/devgabrel/events/
│ │ │ ├── controller/ - Contém os controllers da API, responsáveis por receber as requisições HTTP e retornar as respostas.
│ │ │ ├── service/ - Contém os serviços, que implementam a lógica de negócios da aplicação.
│ │ │ ├── model/ - Contém as classes de modelo (entidades), que representam as tabelas do banco de dados.
│ │ │ ├── repo/ - Contém os repositórios, que são interfaces que estendem o `CrudRepository` do Spring Data JPA e permitem realizar operações de CRUD no banco de dados.
│ │ │ ├── dto/ - Contém os Data Transfer Objects (DTOs), que são classes utilizadas para transferir dados entre as camadas da aplicação.
│ │ │ └── exception/ - Contém as classes de exceção personalizadas da aplicação.
│ │ └── resources/
│ └── test/
├── pom.xml
└── README.md
```

### Endpoints da API

Esta seção descreve os endpoints da API Events. Para cada endpoint, são fornecidas as seguintes informações:

*   **Método:** O método HTTP utilizado (GET, POST, PUT, DELETE).
*   **Endpoint:** A URL do endpoint.
*   **Descrição:** Uma breve descrição do propósito do endpoint.
*   **Parâmetros:** (Se aplicável) Uma tabela com os parâmetros da requisição.
*   **Corpo da Requisição:** (Se aplicável) O formato do corpo da requisição (JSON) e uma descrição dos campos esperados.
*   **Resposta:** O formato da resposta (JSON) e uma descrição dos campos retornados.
*   **Códigos de Status:** Uma lista dos possíveis códigos de status HTTP retornados pelo endpoint e seus significados.

### EventController

#### POST /events

*   **Método:** POST
*   **Endpoint:** `/events`
*   **Descrição:** Adiciona um novo evento ao sistema.
*   **Corpo da Requisição:**

    ```json
    {
        "title": "Nome do Evento",
        "location": "Local do Evento",
        "price": 99.99,
        "startDate": "2023-12-25",
        "endDate": "2023-12-26",
        "startTime": "10:00:00",
        "endTime": "18:00:00"
    }
    ```

    *   `title`: (String, obrigatório) Título do evento.
    *   `location`: (String, obrigatório) Local do evento.
    *   `price`: (Number, obrigatório) Preço do evento.
    *   `startDate`: (Date, opcional) Data de início do evento (formato YYYY-MM-DD).
    *   `endDate`: (Date, opcional) Data de término do evento (formato YYYY-MM-DD).
    *   `startTime`: (Time, opcional) Horário de início do evento (formato HH:MM:SS).
    *   `endTime`: (Time, opcional) Horário de término do evento (formato HH:MM:SS).

*   **Resposta:**

    ```json
    {
        "eventId": 1,
        "title": "Nome do Evento",
        "prettyName": "nome-do-evento",
        "location": "Local do Evento",
        "price": 99.99,
        "startDate": "2023-12-25",
        "endDate": "2023-12-26",
        "startTime": "10:00:00",
        "endTime": "18:00:00"
    }
    ```

    *   `eventId`: (Integer) ID do evento gerado automaticamente.
    *   `title`: (String) Título do evento.
    *   `prettyName`: (String) "Pretty name" do evento (gerado a partir do título).
    *   `location`: (String) Local do evento.
    *   `price`: (Number) Preço do evento.
    *   `startDate`: (Date) Data de início do evento.
    *   `endDate`: (Date) Data de término do evento.
    *   `startTime`: (Time) Horário de início do evento.
    *   `endTime`: (Time) Horário de término do evento.

*   **Códigos de Status:**
    *   `200 OK`: Evento criado com sucesso.
    *   `400 Bad Request`: Requisição inválida (campos ausentes ou inválidos).

#### GET /events

*   **Método:** GET
*   **Endpoint:** `/events`
*   **Descrição:** Retorna todos os eventos cadastrados.
*   **Parâmetros:** Nenhum.
*   **Resposta:** Uma lista de objetos `Event` (ver formato da resposta do endpoint `POST /events`).
*   **Códigos de Status:**
    *   `200 OK`: Sucesso. Retorna uma lista de eventos (pode ser uma lista vazia).

#### GET /events/{prettyName}

*   **Método:** GET
*   **Endpoint:** `/events/{prettyName}`
*   **Descrição:** Retorna um evento específico pelo seu "pretty name".
*   **Parâmetros:**

    | Nome        | Tipo   | Descrição                                     |
    | ----------- | ------ | --------------------------------------------- |
    | `prettyName` | String | O "pretty name" do evento a ser buscado. |

*   **Resposta:** Um objeto `Event` (ver formato da resposta do endpoint `POST /events`).
*   **Códigos de Status:**
    *   `200 OK`: Sucesso. Retorna o evento correspondente.
    *   `404 Not Found`: Evento não encontrado.

### SubscriptionController

#### POST /subscription/{prettyName} ou /subscription/{prettyName}/{userId}

*   **Método:** POST
*   **Endpoint:** `/subscription/{prettyName}` ou `/subscription/{prettyName}/{userId}`
*   **Descrição:** Cria uma nova inscrição para um evento.
*   **Parâmetros:**

    | Nome        | Tipo   | Descrição                                                                            |
    | ----------- | ------ | ------------------------------------------------------------------------------------ |
    | `prettyName` | String | O "pretty name" do evento.                                                       |
    | `userId`     | Integer| (Opcional) O ID do usuário que indicou a inscrição. Se omitido, não há indicação. |

*   **Corpo da Requisição:**

    ```json
    {
        "name": "Nome do Usuário",
        "email": "email@example.com"
    }
    ```

    *   `name`: (String, obrigatório) Nome do usuário.
    *   `email`: (String, obrigatório) Endereço de e-mail do usuário.

*   **Resposta:**

    ```json
    {
        "subscriptionNumber": 123,
        "designation": "http://codecraft.com/subscription/nome-do-evento/456"
    }
    ```

    *   `subscriptionNumber`: (Integer) Número da inscrição.
    *   `designation`: (String) URL de designação da inscrição.

*   **Códigos de Status:**
    *   `200 OK`: Inscrição criada com sucesso.
    *   `404 Not Found`: Evento ou usuário indicador não encontrado.
    *   `409 Conflict`: Usuário já inscrito no evento.
    *   `400 Bad Request`: Requisição inválida.

#### GET /subscription/{prettyName}/ranking

*   **Método:** GET
*   **Endpoint:** `/subscription/{prettyName}/ranking`
*   **Descrição:** Obtém o ranking dos 3 primeiros usuários de um evento.
*   **Parâmetros:**

    | Nome        | Tipo   | Descrição                    |
    | ----------- | ------ | ----------------------------- |
    | `prettyName` | String | O "pretty name" do evento. |

*   **Resposta:** Uma lista de objetos `SubscriptionRankingItem`:

    ```json
    [
        {
            "subscribers": 10,
            "userId": 1,
            "name": "Usuário 1"
        },
        {
            "subscribers": 5,
            "userId": 2,
            "name": "Usuário 2"
        },
        {
            "subscribers": 2,
            "userId": 3,
            "name": "Usuário 3"
        }
    ]
    ```

    *   `subscribers`: (Long) Número de inscritos que este usuário indicou.
    *   `userId`: (Integer) ID do usuário.
    *   `name`: (String) Nome do usuário.

*   **Códigos de Status:**
    *   `200 OK`: Sucesso. Retorna a lista de ranking.
    *   `404 Not Found`: Evento não encontrado.

#### GET /subscription/{prettyName}/ranking/{userId}

*   **Método:** GET
*   **Endpoint:** `/subscription/{prettyName}/ranking/{userId}`
*   **Descrição:** Obtém a posição de um usuário específico no ranking de um evento.
*   **Parâmetros:**

    | Nome        | Tipo   | Descrição                               |
    | ----------- | ------ | ----------------------------------------- |
    | `prettyName` | String | O "pretty name" do evento.              |
    | `userId`     | Integer| O ID do usuário a ser buscado no ranking. |

*   **Resposta:**

    ```json
    {
        "item": {
            "subscribers": 7,
            "userId": 4,
            "name": "Usuário 4"
        },
        "position": 2
    }
    ```

    *   `item`: (SubscriptionRankingItem) As informações do usuário no ranking.
    *   `position`: (Integer) A posição do usuário no ranking.

*   **Códigos de Status:**
    *   `200 OK`: Sucesso. Retorna a posição do usuário no ranking.
    *   `404 Not Found`: Evento não encontrado ou usuário não encontrado no ranking.


## Configuração do Ambiente com Docker Compose

Configuração do ambiente de desenvolvimento com Docker Compose utilizando o banco de dados MySQL e a interface de gerenciamento Adminer.

**Pré-requisitos:**

*   [Docker](https://www.docker.com/get-started/) instalado em sua máquina.
*   [Docker Compose](https://docs.docker.com/compose/install/) instalado em sua máquina.

**Passos:**

1.  **Crie o arquivo `docker-compose.yml`:** Crie um arquivo chamado `docker-compose.yml` na raiz do seu projeto e copie o seguinte conteúdo:

    ```yaml
    version: "3.9"
    services:
      mysql:
        image: mysql:8.4
        restart: always
        container_name: mysql-nlw
        environment:
          MYSQL_ROOT_PASSWORD: mysql
        ports:
          - "3306:3306"
        networks:
          - nlw-network
      adminer:
        image: adminer
        restart: always
        container_name: adminer-nlw
        ports:
          - "8081:8080"
        networks:
          - nlw-network
    networks:
      nlw-network:
        driver: bridge
    ```

2.  **Execute o Docker Compose:** Na raiz do seu projeto (onde o arquivo `docker-compose.yml` está localizado), execute o seguinte comando no terminal:

    ```bash
    docker-compose up
    ```

3.  **Acesse o Adminer:**  Acessível em `http://localhost:8081`. Utilize as credenciais `root` e `mysql` para acessar o MySQL.

4.  **Execute o Script SQL:** Execute o seguinte script SQL para criar a estrutura.

    ```sql
    SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
    SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
    SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

    -- -----------------------------------------------------
    -- Schema db_events
    -- -----------------------------------------------------
    CREATE SCHEMA IF NOT EXISTS `db_events` DEFAULT CHARACTER SET utf8mb3 ;
    USE `db_events` ;

    -- -----------------------------------------------------
    -- Table `db_events`.`tbl_event`
    -- -----------------------------------------------------
    CREATE TABLE IF NOT EXISTS `db_events`.`tbl_event` (
      `event_id` INT NOT NULL AUTO_INCREMENT,
      `title` VARCHAR(255) NOT NULL,
      `pretty_name` VARCHAR(50) NOT NULL,
      `location` VARCHAR(255) NOT NULL,
      `price` DOUBLE NOT NULL,
      `start_date` DATE NULL DEFAULT NULL,
      `end_date` DATE NULL DEFAULT NULL,
      `start_time` TIME NULL DEFAULT NULL,
      `end_time` TIME NULL DEFAULT NULL,
      PRIMARY KEY (`event_id`),
      UNIQUE INDEX `pretty_name_UNIQUE` (`pretty_name` ASC) VISIBLE)
    ENGINE = InnoDB
    AUTO_INCREMENT = 5
    DEFAULT CHARACTER SET = utf8mb3;


    -- -----------------------------------------------------
    -- Table `db_events`.`tbl_user`
    -- -----------------------------------------------------
    CREATE TABLE IF NOT EXISTS `db_events`.`tbl_user` (
      `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
      `user_name` VARCHAR(255) NULL DEFAULT NULL,
      `user_email` VARCHAR(255) NULL DEFAULT NULL,
      PRIMARY KEY (`user_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


    -- -----------------------------------------------------
    -- Table `db_events`.`tbl_subscription`
    -- -----------------------------------------------------
    CREATE TABLE IF NOT EXISTS `db_events`.`tbl_subscription` (
      `subscription_number` INT UNSIGNED NOT NULL AUTO_INCREMENT,
      `subscribed_user_id` INT UNSIGNED NOT NULL,
      `indication_user_id` INT UNSIGNED NULL DEFAULT NULL,
      `event_id` INT NOT NULL,
      PRIMARY KEY (`subscription_number`),
      INDEX `fk_tbl_subscription_tbl_user_idx` (`subscribed_user_id` ASC) VISIBLE,
      INDEX `fk_tbl_subscription_tbl_user1_idx` (`indication_user_id` ASC) VISIBLE,
      INDEX `fk_tbl_subscription_tbl_event1_idx` (`event_id` ASC) VISIBLE,
      CONSTRAINT `fk_tbl_subscription_tbl_event1`
        FOREIGN KEY (`event_id`)
        REFERENCES `db_events`.`tbl_event` (`event_id`),
      CONSTRAINT `fk_tbl_subscription_tbl_user`
        FOREIGN KEY (`subscribed_user_id`)
        REFERENCES `db_events`.`tbl_user` (`user_id`),
      CONSTRAINT `fk_tbl_subscription_tbl_user1`
        FOREIGN KEY (`indication_user_id`)
        REFERENCES `db_events`.`tbl_user` (`user_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb3;


    SET SQL_MODE=@OLD_SQL_MODE;
    SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
    SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
    ```

## Como Executar a Aplicação

1.  Clone o repositório:

    ```bash
    git clone git@github.com:gabriel-mkv/nlw-connect.git
    ```

2.  Navegue até a pasta do projeto:

    ```bash
    cd nlw-connect
    ```

3.  Execute a aplicação utilizando o Maven:

    ```bash
    ./mvnw spring-boot:run
    ```

A API estará disponível em `http://localhost:8080`.