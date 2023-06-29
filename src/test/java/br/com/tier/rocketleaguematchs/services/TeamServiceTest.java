package br.com.tier.rocketleaguematchs.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.tier.rocketleaguematchs.BaseTests;
import br.com.tier.rocketleaguematchs.models.Team;
import br.com.tier.rocketleaguematchs.service.TeamService;
import br.com.tier.rocketleaguematchs.service.exception.IntegrityViolation;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class TeamServiceTest extends BaseTests {

    @Autowired
    TeamService timeService;

    @Test
    @DisplayName("Teste buscar por ID válido")
    @Sql({ "classpath:/resources/sqls/team.sql" })
    void findIdValidTest() {
        Team team = timeService.findById(1);
        assertNotNull(team);
        assertEquals(1, team.getId());
        assertEquals("FURIA", team.getName());
    }

    @Test
    @DisplayName("Teste buscar por ID inválido")
    @Sql({ "classpath:/resources/sqls/team.sql" })
    void findIdInvalidTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> timeService.findById(99));
        assertEquals("Equipe 99 nao encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste buscar todos")
    @Sql({ "classpath:/resources/sqls/team.sql" })
    void findhAllTest() {
        assertEquals(3, timeService.listAll().size());
    }

    @Test
    @DisplayName("Teste buscar todos com nenhum cadastro")
    void searchAllWithNoTeamTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> timeService.listAll());
        assertEquals("Nenhuma equipe cadastrada", ex.getMessage());
    }

    @Test
    @DisplayName("Teste inser nova equipe")
    void insertTest() {
        Team team = new Team(null, "insert");
        timeService.insert(team);
        assertEquals(1, timeService.listAll().size());
        assertEquals(1, team.getId());
        assertEquals("insert", team.getName());
    }

    @Test
    @DisplayName("Teste insere nova equipe com nome duplicado")
    @Sql({ "classpath:/resources/sqls/team.sql" })
    void insertWithSameNameTest() {
        Team team = new Team(null, "FURIA");
        var ex = assertThrows(IntegrityViolation.class, () -> timeService.insert(team));
        assertEquals("Nome FURIA já cadastrado.", ex.getMessage());

    }
    
    @Test
    @DisplayName("Update time")
    @Sql({"classpath:/resources/sqls/team.sql"})
    void update() { 
        Team team = timeService.findById(1);
        assertNotNull(team);
        assertEquals(1, team.getId());
        assertEquals("FURIA", team.getName());    
        team = new Team(1, "update");
        timeService.update(team);
        assertEquals(3, timeService.listAll().size());
        assertEquals(1, team.getId());
        assertEquals("update", team.getName()); 
    }

    @Test
    @DisplayName("Delete usuário")
    @Sql({"classpath:/resources/sqls/team.sql"})
    void deleteTeam() { 
        assertEquals(3, timeService.listAll().size());
        timeService.delete(1);
        assertEquals(2, timeService.listAll().size());
        assertEquals(2, timeService.listAll().get(0).getId());
    }
    
    @Test
    @DisplayName("Procura por nome")
    @Sql({"classpath:/resources/sqls/team.sql"})
    void searchByName() {   
        assertEquals(1, timeService.findByNameStartsWithIgnoreCase("f").size());
        assertEquals(2, timeService.findByNameStartsWithIgnoreCase("v").size());
        var ex = assertThrows(ObjectNotFound.class, () ->
        timeService.findByNameStartsWithIgnoreCase("A"));
        assertEquals("Nenhuma equipe inicia com A", ex.getMessage());
    }
    
    @Test
    @DisplayName("Ordena por nome")
    @Sql({"classpath:/resources/sqls/team.sql"})
    void searchAllAndOrderByName() {    
        assertEquals(1, timeService.findAllByOrderByName().get(0).getId());
        assertEquals(2, timeService.findAllByOrderByName().get(1).getId());
        assertEquals(3, timeService.findAllByOrderByName().get(2).getId());
    }
}
