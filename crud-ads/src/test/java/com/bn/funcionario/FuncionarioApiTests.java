package com.bn.funcionario;

import com.bn.funcionario.models.FuncionarioModel;
import com.bn.funcionario.repositories.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {"spring.security.user.name=aluno", "spring.security.user.password=teste123"})
class FuncionarioApiTests {
    @LocalServerPort int port;
    @Autowired FuncionarioRepository repository;
    @Autowired ObjectMapper mapper;
    private final HttpClient client = HttpClient.newHttpClient();
    private static final String DADOS = "{\"nome\":\"Ana\",\"cpf\":\"00000000000\",\"rua\":\"Rua A\"}";

    @BeforeEach
    void limparBanco() {
        repository.deleteAll();
    }

    private HttpResponse<String> enviar(String metodo, String caminho, String corpo, String credenciais) throws Exception {
        HttpRequest.Builder request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + caminho))
                .header("Content-Type", "application/json");
        if (credenciais != null) {
            request.header("Authorization", "Basic " + Base64.getEncoder()
                    .encodeToString(credenciais.getBytes(StandardCharsets.UTF_8)));
        }
        request.method(metodo, corpo == null ? HttpRequest.BodyPublishers.noBody()
                : HttpRequest.BodyPublishers.ofString(corpo));
        return client.send(request.build(), HttpResponse.BodyHandlers.ofString());
    }

    private long cadastrar() throws Exception {
        var resposta = enviar("POST", "/api/funcionarios", DADOS, null);
        assertThat(resposta.statusCode()).isEqualTo(201);
        return mapper.readTree(resposta.body()).get("id").asLong();
    }

    @Test void listarSemLogin() throws Exception {
        var resposta = enviar("GET", "/api/funcionarios", null, null);
        assertThat(resposta.statusCode()).isEqualTo(200);
        assertThat(resposta.body()).isEqualTo("[]");
    }

    @Test void criarEBuscarSemLogin() throws Exception {
        long id = cadastrar();
        var resposta = enviar("GET", "/api/funcionarios/" + id, null, null);
        assertThat(resposta.statusCode()).isEqualTo(200);
        assertThat(mapper.readTree(resposta.body()).get("nome").asText()).isEqualTo("Ana");
    }

    @Test void putSemLoginRetorna401ENaoAltera() throws Exception {
        long id = cadastrar();
        var resposta = enviar("PUT", "/api/funcionarios/" + id, DADOS.replace("Ana", "Bia"), null);
        assertThat(resposta.statusCode()).isEqualTo(401);
        assertThat(resposta.headers().firstValue("WWW-Authenticate")).isPresent();
        assertThat(repository.findById(id).orElseThrow().getNome()).isEqualTo("Ana");
    }

    @Test void deleteSemLoginRetorna401ENaoExclui() throws Exception {
        long id = cadastrar();
        assertThat(enviar("DELETE", "/api/funcionarios/" + id, null, null).statusCode()).isEqualTo(401);
        assertThat(repository.existsById(id)).isTrue();
    }

    @Test void senhaIncorretaRetorna401() throws Exception {
        long id = cadastrar();
        assertThat(enviar("PUT", "/api/funcionarios/" + id, DADOS, "aluno:errada").statusCode()).isEqualTo(401);
        assertThat(enviar("DELETE", "/api/funcionarios/" + id, null, "aluno:errada").statusCode()).isEqualTo(401);
        assertThat(repository.existsById(id)).isTrue();
    }

    @Test void atualizarComHttpBasic() throws Exception {
        long id = cadastrar();
        String novosDados = DADOS.replace("Ana", "Bia").replace("Rua A", "Rua B");
        assertThat(enviar("PUT", "/api/funcionarios/" + id, novosDados, "aluno:teste123").statusCode()).isEqualTo(200);
        FuncionarioModel salvo = repository.findById(id).orElseThrow();
        assertThat(salvo.getNome()).isEqualTo("Bia");
        assertThat(salvo.getRua()).isEqualTo("Rua B");
    }

    @Test void excluirComHttpBasic() throws Exception {
        long id = cadastrar();
        assertThat(enviar("DELETE", "/api/funcionarios/" + id, null, "aluno:teste123").statusCode()).isEqualTo(204);
        assertThat(repository.existsById(id)).isFalse();
    }

    @Test void idsInexistentesRetornam404() throws Exception {
        assertThat(enviar("GET", "/api/funcionarios/999999", null, null).statusCode()).isEqualTo(404);
        assertThat(enviar("PUT", "/api/funcionarios/999999", DADOS, "aluno:teste123").statusCode()).isEqualTo(404);
        assertThat(enviar("DELETE", "/api/funcionarios/999999", null, "aluno:teste123").statusCode()).isEqualTo(404);
    }

    @Test void postIgnoraIdEnviado() throws Exception {
        long id = cadastrar();
        String corpo = "{\"id\":" + id + ",\"nome\":\"Bia\"}";
        assertThat(enviar("POST", "/api/funcionarios", corpo, null).statusCode()).isEqualTo(201);
        assertThat(repository.count()).isEqualTo(2);
        assertThat(repository.findById(id).orElseThrow().getNome()).isEqualTo("Ana");
    }
}
