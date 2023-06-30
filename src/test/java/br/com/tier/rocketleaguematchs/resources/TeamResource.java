package br.com.tier.rocketleaguematchs.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.tier.rocketleaguematchs.RocketleaguematchsApplication;
import br.com.tier.rocketleaguematchs.config.jwt.LoginDTO;
import br.com.tier.rocketleaguematchs.models.Team;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/clear_tables.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/team.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/country.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/player.sql")
@SpringBootTest(classes = RocketleaguematchsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeamResource {

    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<Team> getTeam(String url, HttpHeaders headers) {
        return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Team.class);
    }

    private ResponseEntity<List<Team>> getTeams(String url, HttpHeaders headers) {
        return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {
        });
    }

    @DisplayName("Teste obter Token")
    private HttpHeaders getHeaders(String email, String password) {
        LoginDTO loginDTO = new LoginDTO(email, password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
        ResponseEntity<String> responseEntity = rest.exchange("/auth/token", HttpMethod.POST, requestEntity,
                String.class);
        String token = responseEntity.getBody();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return headers;
    }

    @Test
    @DisplayName("Teste buscar por id")
    public void findIdTest() {
        ResponseEntity<Team> response = getTeam("/teams/1", getHeaders("email1@gmail.com", "senha1"));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Team team = response.getBody();
        assertEquals("FURIA", team.getName());
    }

    @Test
    @DisplayName("Teste buscar por id inexistente")
    public void findIdNotFoundTest() {
        ResponseEntity<Team> response = getTeam("/teams/99", getHeaders("email1@gmail.com", "senha1"));
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Teste cadastrar equipe")
    @Sql({ "classpath:/resources/sqls/clear_tables.sql" })
    @Sql({ "classpath:/resources/sqls/user.sql" })
    public void insertTeamTest() {
        Team team = new Team(null, "Equipe Nova");
        HttpEntity<Team> requestEntity = new HttpEntity<Team>(team, getHeaders("email1@gmail.com", "senha1"));
        ResponseEntity<Team> responseEntity = rest.exchange("/teams", HttpMethod.POST, requestEntity, Team.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Team createdTeam = responseEntity.getBody();
        assertEquals("Equipe Nova", createdTeam.getName());
    }
    
    @Test
    @DisplayName("Teste update equipe")
    public void updateTeamTest() {
        Team team = new Team(1, "Equipe Atualizado");
        HttpEntity<Team> requestEntity = new HttpEntity<Team>(team, getHeaders("email1@gmail.com", "senha1"));
        ResponseEntity<Team> responseEntity = getTeam("/teams/1", getHeaders("email1@gmail.com", "senha1"));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Team updatedTeam = responseEntity.getBody();
        assertEquals("Equipe Atualizado", updatedTeam.getName());
    }
    
    @Test

    @DisplayName("Teste excluir equipe")
    public void deleteTeamTest() {
        HttpHeaders headers = getHeaders("email1@gmail.com", "senha1");
        ResponseEntity<Void> responseEntity = rest.exchange("/teams/1", HttpMethod.DELETE, new HttpEntity<>(headers), Void.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }
    
    @Test
    @DisplayName("Teste listar todos as equipes")
    public void testListAllTest() {
        ResponseEntity<List<Team>> responseEntity = getTeams("/teams", getHeaders("email1@gmail.com", "senha1"));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<Team> teams = responseEntity.getBody();
        assertEquals(3, teams.size());
    }
    
    @Test
    @DisplayName("Teste buscar equipe por nome iniciando com")
    public void findTeamsByNameStartsWithIgnoreCaseTest() {
        ResponseEntity<List<Team>> responseEntity = getTeams("/teams/name/FURIA", getHeaders("email1@gmail.com", "senha1"));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<Team> teams = responseEntity.getBody();
        assertEquals(3, teams.size());
    }
    
    @Test
    @DisplayName("Listar todos os times ordenados por nome")
    public void listAllTeamsOrderByNameTest() {
        ResponseEntity<List<Team>> responseEntity = getTeams("/teams/name", getHeaders("email1@gmail.com", "senha1"));
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<Team> teams = responseEntity.getBody();
        assertEquals(3, teams.size());
    }

}
