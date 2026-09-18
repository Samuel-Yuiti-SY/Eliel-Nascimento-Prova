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

## Executar

```bash
mvn spring-boot:run
```

A aplicação será iniciada em:

http://localhost:8080

## Exemplo de cadastro

```json
{
  "nome": "Padaria Central",
  "endereco": "Avenida Brasil, 100"
}
```
