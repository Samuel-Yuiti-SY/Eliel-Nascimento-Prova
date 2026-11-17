# API de funcionários

Adaptação do [CRUD disponibilizado pelo professor](https://github.com/ElielNascimento/ca-a-ao-tesouro), com Spring Security e autenticação HTTP Basic.

O projeto fica nesta pasta. O CRUD de padaria, na raiz do repositório, é uma atividade anterior.

## Executar

É necessário Java 21 e Maven. Dentro da pasta `crud-ads`, execute:

```sh
mvn spring-boot:run
```

A API estará em `http://localhost:8080/api/funcionarios`. O banco H2 fica em memória e é apagado ao encerrar a aplicação.

| Método | Rota | Acesso |
| --- | --- | --- |
| GET | /api/funcionarios | Livre |
| GET | /api/funcionarios/{id} | Livre |
| POST | /api/funcionarios | Livre |
| PUT | /api/funcionarios/{id} | HTTP Basic |
| DELETE | /api/funcionarios/{id} | HTTP Basic |

Para os testes locais, o usuário é `aluno` e a senha é `teste123`. São credenciais de demonstração; podem ser substituídas pelas variáveis `API_USER` e `API_PASSWORD`.

## Testar no Postman

1. Faça um POST em `http://localhost:8080/api/funcionarios`, com Body > raw > JSON:

```json
{
  "nome": "Ana",
  "cpf": "00000000000",
  "rua": "Rua A"
}
```

2. Consulte a lista com GET e copie o ID retornado no cadastro.
3. Faça PUT na rota com esse ID. Sem autenticação, a resposta deve ser 401. Em Authorization > Basic Auth, informe as credenciais acima e envie o JSON atualizado.
4. Repita com DELETE. Com autenticação correta, a resposta é 204.

O cadastro retorna 201 e o endereço do novo registro no cabeçalho Location. Consulta e atualização retornam 200. Um ID inexistente retorna 404 quando a requisição tem o acesso necessário.

## Testes automatizados

```sh
mvn clean verify
```

Os testes iniciam a aplicação em uma porta livre e fazem requisições HTTP reais, cobrindo GET e POST públicos, PUT e DELETE sem login, senha incorreta, alterações autenticadas e IDs inexistentes. O GitHub Actions executa esses testes a cada alteração nesta pasta.

## Ajustes no projeto original

As classes foram reunidas dentro de `com.bn.funcionario` para serem encontradas pelo Spring. Foram mantidos os campos id, nome, cpf e rua, a separação entre controller, service e repository e o banco H2.

A atualização também salva a rua. A busca de um funcionário inexistente passou a retornar 404. O POST gera um novo ID, mesmo que o cliente envie um no corpo.

O Maven usa Spring Boot 3.5.5, mesma versão do projeto já presente neste repositório, com Java 21. A segurança está em `config/SecurityConfig.java`, usando SecurityFilterChain, CSRF desativado conforme o enunciado e HTTP Basic.
