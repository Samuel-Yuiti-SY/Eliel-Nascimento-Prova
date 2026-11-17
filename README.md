# CRUD de Padaria - Eliel Nascimento

Projeto desenvolvido em Java com Spring Boot, JPA e banco H2 (critérios ensinado na disciplina de Análise e Desenvolvimento de sistemas)
Informações abaixo caso tenha problemas em rodar o código.

## Requisito

Para executar o projeto é necessário apenas ter o Java 17 instalado.

Para conferir:

```bash
java -version
```

Não é necessário instalar o Maven manualmente. O projeto possui Maven Wrapper e baixa o Maven automaticamente na primeira execução.

## Executar no Windows

A forma mais simples é abrir o arquivo:

```text
executar.bat
```

Também é possível executar pelo Prompt de Comando dentro da pasta do projeto:

```bat
mvnw.cmd spring-boot:run
```

Na primeira execução pode ser necessário acesso à internet para baixar o Maven e as dependências do projeto.

Quando a aplicação iniciar, ela ficará disponível em:

```text
http://localhost:8080
```

## Banco H2

Console:

```text
http://localhost:8080/h2-console
```

JDBC URL:

```text
jdbc:h2:mem:padariadb
```

Usuário:

```text
sa
```

Senha: deixar em branco.

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

## Exemplo de cadastro

```json
{
  "nome": "Padaria Central",
  "endereco": "Avenida Brasil, 100"
}
```


PS: Deu trabalho professor, acredito que o mínimo é uma notinha no mesmo nível 😂.
