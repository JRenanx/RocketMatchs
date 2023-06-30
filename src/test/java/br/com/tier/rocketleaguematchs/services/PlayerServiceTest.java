package br.com.tier.rocketleaguematchs.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.tier.rocketleaguematchs.BaseTests;
import br.com.tier.rocketleaguematchs.models.Player;
import br.com.tier.rocketleaguematchs.service.CountryService;
import br.com.tier.rocketleaguematchs.service.PlayerService;
import br.com.tier.rocketleaguematchs.service.TeamService;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/team.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/country.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/player.sql")
@Transactional
public class PlayerServiceTest extends BaseTests {

    @Autowired
    PlayerService service;

    @Autowired
    CountryService countryService;

    @Autowired
    TeamService teamService;

    @Test
    @DisplayName("Teste buscar por ID")
    void findIdTest() {
        Player player = service.findById(1);
        assertNotNull(player);
        assertEquals(1, player.getId());
        assertEquals("Yanxnz", player.getName());
    }

    @Test
    @DisplayName("Teste buscar por ID inválido")
    void findIdInvalidTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findById(10));
        assertEquals("Jogador 10 nao encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste insere novo jogador")
    @Sql({ "classpath:/resources/sqls/clear_tables.sql" })
    @Sql({ "classpath:/resources/sqls/country.sql" })
    @Sql({ "classpath:/resources/sqls/team.sql" })
    void insertTest() {
        Player player = new Player(null, "Ciber", teamService.findById(1), countryService.findById(1));
        service.insert(player);
        assertEquals(1, service.listAll().size());
        assertEquals(1, player.getId());
        assertEquals("Ciber", player.getName());
    }

    @Test
    @DisplayName("Teste update jogador")
    void updateTest() {
        Player player = service.findById(1);
        assertNotNull(player);
        assertEquals(1, player.getId());
        assertEquals("Yanxnz", player.getName());
        player = new Player(1, "Updated Player", teamService.findById(1), countryService.findById(1));
        service.update(player);
        assertEquals(3, service.listAll().size());
        assertEquals(1, player.getId());
        assertEquals("Updated Player", player.getName());
    }

    @Test
    @DisplayName("Teste delete jogador")
    void deleteTest() {
        Player player = service.findById(1);
        assertNotNull(player);
        assertEquals(1, player.getId());
        assertEquals("Yanxnz", player.getName());
        service.delete(1);
        var ex = assertThrows(ObjectNotFound.class, () -> service.findById(1));
        assertEquals("Jogador 1 nao encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste buscar todos")
    void findAllTest() {
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste buscar todos com nenhum cadastro")
    @Sql({ "classpath:/resources/sqls/clear_tables.sql" })
    void findAllWithNoPlayerTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum jogador cadastrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste encontra jogador por nome")
    void findByNameStartsWithIgnoreCaseTest() {
        List<Player> lista = service.findByNameStartsWithIgnoreCase("Y");
        assertEquals(1, lista.size());
    }

    @Test
    @DisplayName("Teste encontra jogador por nome sem nomes iguais")
    void findByNameStartsWithIgnoreCaseInvalidTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByNameStartsWithIgnoreCase("H"));
        assertEquals("Nenhum jogador cadastrado com nome: H", ex.getMessage());
    }

    @Test
    @DisplayName("Teste encontra pilotos por time")
    void findByTeamOrderByName() {
        List<Player> lista = service.findByTeamOrderByName(teamService.findById(1));
        assertEquals(3, lista.size());
    }

    @Test
    @DisplayName("Teste encontra jogador por time sem times encontrados")
    @Sql({ "classpath:/resources/sqls/clear_tables.sql" })
    @Sql({ "classpath:/resources/sqls/team.sql" })
    void findByTeamOrderByNameInvalid() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByTeamOrderByName(teamService.findById(1)));
        assertEquals("Nenhum jogador cadastrado na equipe: FURIA", ex.getMessage());
    }

    @Test
    @DisplayName("Teste encontra jogador por país")
    void findByCountryOrderByNameTest() {
        List<Player> lista = service.findByCountryOrderByName(countryService.findById(1));
        assertEquals(1, lista.size());
    }

    @Test
    @DisplayName("Encontra jogador por país sem nenhum com este país")
    @Sql({ "classpath:/resources/sqls/clear_tables.sql" })
    @Sql({ "classpath:/resources/sqls/country.sql" })
    void findByCountryOrderByNameInvalidTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByCountryOrderByName(countryService.findById(1)));
        assertEquals("Nenhum jogador cadastrado no pais: Brasil", ex.getMessage());
    }

}
