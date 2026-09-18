# CRUD de Padaria

Projeto desenvolvido em Java com Spring Boot para realizar um CRUD simples de padarias.

## Campos

- id
- nome
- endereco

## Endpoints

- GET /padarias - 201
- POST /padarias - 200
- GET /padarias/{id} - 200
- DELETE /padarias/{id} - 204
- PUT /padarias/{id} - 201

## Requisitos

Para executar o projeto é necessário ter instalado:

- Java 17
- Maven

Para conferir se o Java está instalado:

```bash
java -version
```

Para conferir se o Maven está instalado:

```bash
mvn -version
```

Caso o comando `mvn` não seja reconhecido, será necessário instalar o Apache Maven e adicioná-lo ao PATH do sistema.

## Executar

Dentro da pasta do projeto, execute:

```bash
mvn spring-boot:run
```

A aplicação será iniciada em:

http://localhost:8080

O console do banco H2 fica disponível em:

http://localhost:8080/h2-console

JDBC URL:

```text
jdbc:h2:mem:padariadb
```

Usuário:

```text
sa
```

Senha: deixar em branco.

## Exemplo de cadastro

```json
{
  "nome": "Padaria Central",
  "endereco": "Avenida Brasil, 100"
}
```
