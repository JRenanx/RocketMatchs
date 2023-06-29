package br.com.tier.rocketleaguematchs.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void searchIdValidTest() {
        Player player = service.findById(1);
        assertNotNull(player);
        assertEquals(1, player.getId());
        assertEquals("Yanxnz", player.getName());
    }

    @Test
    @DisplayName("Teste buscar por ID inválido")
    void searchIdInvalidTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findById(10));
        assertEquals("Jogador 10 nao encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste buscar todos")
    void searchAllTest() {
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste buscar todos com nenhum cadastro")
    @Sql({ "classpath:/resources/sqls/clear_tables.sql" })
    void searchAllWithNoPlayerTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum jogador cadastrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Insert novo player")
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
    @DisplayName("Teste update player")
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

}
