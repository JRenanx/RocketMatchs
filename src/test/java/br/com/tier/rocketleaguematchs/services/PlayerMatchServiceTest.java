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
import br.com.tier.rocketleaguematchs.models.Match;
import br.com.tier.rocketleaguematchs.models.Player;
import br.com.tier.rocketleaguematchs.models.PlayerMatch;
import br.com.tier.rocketleaguematchs.service.MatchService;
import br.com.tier.rocketleaguematchs.service.PlayerMatchService;
import br.com.tier.rocketleaguematchs.service.PlayerService;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/team.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/country.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/player.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/map.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/season.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/match.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/playermatch.sql")
public class PlayerMatchServiceTest extends BaseTests {

    @Autowired
    PlayerMatchService service;

    @Autowired
    PlayerService playerService;

    @Autowired
    MatchService matchService;

    @Test
    @DisplayName("Teste buscar por ID válido")
    void findByIdTest() {
        PlayerMatch playerM = service.findById(1);
        assertNotNull(playerM);
        assertEquals(1, playerM.getId());
        assertEquals(5, playerM.getGoals());
        assertEquals(1, playerM.getPlayer().getId());
        assertEquals(1, playerM.getMatch().getId());
    }

    @Test
    @DisplayName("Teste buscar por ID inválido")
    void findByIdInvalidTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findById(10));
        assertEquals("Jogador/Partida 1 não encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Insert novo jogador/partida")
    @Sql({ "classpath:/resources/sqls/clear_tables.sql" })
    @Sql({ "classpath:/resources/sqls/country.sql" })
    @Sql({ "classpath:/resources/sqls/team.sql" })
    @Sql({ "classpath:/resources/sqls/player.sql" })
    @Sql({ "classpath:/resources/sqls/map.sql" })
    @Sql({ "classpath:/resources/sqls/season.sql" })
    @Sql({ "classpath:/resources/sqls/match.sql" })
    void insert() {
        PlayerMatch playerM = new PlayerMatch(null, 1, playerService.findById(1), matchService.findById(1));
        service.insert(playerM);
        assertEquals(1, service.listAll().size());
        assertEquals(1, playerM.getId());
        assertEquals(1, playerM.getGoals());
    }

    @Test
    @DisplayName("Teste buscar todos")
    void findAllTest() {
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste buscar todos com nenhum cadastro")
    @Sql({ "classpath:/resources/sqls/clear_tables.sql" })
    void sfindAllWithNoPlayerMatchTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum Jogador/Partida cadastrado", ex.getMessage());
    }

    @Test
    @DisplayName("Teste insere update jogador/partida")
    @Sql({ "classpath:/resources/sqls/clear_tables.sql" })
    @Sql({ "classpath:/resources/sqls/country.sql" })
    @Sql({ "classpath:/resources/sqls/team.sql" })
    @Sql({ "classpath:/resources/sqls/player.sql" })
    @Sql({ "classpath:/resources/sqls/map.sql" })
    @Sql({ "classpath:/resources/sqls/season.sql" })
    @Sql({ "classpath:/resources/sqls/match.sql" })
    void updateTest() {
        PlayerMatch playerM = service.findById(1);
        PlayerMatch updatePlayer = new PlayerMatch(1, 10, playerM.getPlayer(), playerM.getMatch());
        service.update(updatePlayer);
        assertEquals(10, service.findById(1).getGoals());
    }

    @Test
    @DisplayName("Teste deletar Jogador/partida")
    void deleteTest() {
        PlayerMatch playerM = service.findById(1);
        assertNotNull(playerM);
        assertEquals(1, playerM.getId());
        service.delete(1);
        var ex = assertThrows(ObjectNotFound.class, () -> service.findById(1));
        assertEquals("Jogador/Partida 1 não encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste buscar por odem de gol")
    void findOrderByGoalsTeste() {
        Match match = matchService.findById(1);
        List<PlayerMatch> lista = service.findByMatchOrderByGoals(match);
        assertEquals(1, lista.get(0).getGoals());
        assertEquals(5, lista.get(1).getGoals());
    }
    

    @Test
    @DisplayName("Teste buscar jogador por odem de gol")
    void findOrderPlayerByGoalsTeste() {
        Player player = playerService.findById(1);
        List<PlayerMatch> lista = service.findByPlayerOrderByGoals(player);
        assertEquals(3, lista.get(0).getGoals());
        assertEquals(5, lista.get(1).getGoals());
    }

}
