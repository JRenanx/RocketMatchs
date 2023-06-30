package br.com.tier.rocketleaguematchs.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.tier.rocketleaguematchs.BaseTests;
import br.com.tier.rocketleaguematchs.models.Season;
import br.com.tier.rocketleaguematchs.service.SeasonService;
import br.com.tier.rocketleaguematchs.service.exception.IntegrityViolation;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/season.sql")
@Transactional
public class SeasonServiceTest extends BaseTests {

    @Autowired
    SeasonService service;

    @Test
    @Sql(scripts = "classpath:/resources/sqls/season.sql")
    @DisplayName("Teste buscar por id")
    void findByIdTest() {
        Season season = service.findById(1);
        assertNotNull(season);
        assertEquals(1, season.getId());
        assertEquals("Temporada 2020", season.getDescription());
    }

    @Test
    @DisplayName("Teste buscar por id invalido")
    void findByIdInvalidTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findById(99));
        assertEquals("Temporada 99 não encontrada.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste inserir temporada")
    @Sql({ "classpath:/resources/sqls/clear_tables.sql" })
    void insertSeasonTest() {
        Season season = new Season(null, "insert", 2024);
        service.insert(season);
        assertEquals(1, service.listAll().size());
        assertEquals(1, season.getId());
        assertEquals("insert", season.getDescription());
        assertEquals(2024, season.getYear());
    }

    @Test
    @DisplayName("Teste inserir temporada invalida")
    void insertSeasonInvalidYearTest() {
        Season season = new Season(null, "insert", 2000);
        var exception = assertThrows(IntegrityViolation.class, () -> service.insert(season));
        assertEquals("Temporada deve estar ente 2015 e 2024.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste atualizar Season")
    void updateSeasonTest() {
        Season season = service.findById(1);
        assertNotNull(season);
        assertEquals(1, season.getId());
        assertEquals("Temporada 2020", season.getDescription());
        season = new Season(1, "Temporada 2024", 2024);
        service.update(season);
        assertEquals(3, service.listAll().size());
        assertEquals(1, season.getId());
        assertEquals("Temporada 2024", season.getDescription());
        assertEquals(2024, season.getYear());
    }

    @Test
    @DisplayName("Teste deleta season")
    void deleteSeasonTest() {
        assertEquals(3, service.listAll().size());
        service.delete(1);
        assertEquals(2, service.listAll().size());
    }

    @Test
    @DisplayName("Teste delete season que não existe")
    void deleteSeasonIdNoExistTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.delete(99));
        assertEquals("Temporada 99 não encontrada.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste listar todos")
    void listAllSeasonTest() {
        List<Season> lista = service.listAll();
        assertEquals(3, lista.size());
    }



    @Test
    @DisplayName("Teste procura por ano")
    @Sql({ "classpath:/resources/sqls/season.sql" })
    void searchByYearTest() {
        assertEquals(1, service.findByYear(2020).size());
        assertEquals(1, service.findByYear(2021).size());
        assertEquals(1, service.findByYear(2022).size());

    }

    @Test
    @DisplayName("Teste procura por ano não existente")
    @Sql({ "classpath:/resources/sqls/season.sql" })
    void searchByYearNonExistTest() {
        var ex = assertThrows(ObjectNotFound.class, () ->
        service.findByYear(2024));
        assertEquals("Nenhuma temporada em 2024.", ex.getMessage());

    }
    
    @Test
    @DisplayName("Teste procura por ano não existente")
    @Sql({ "classpath:/resources/sqls/season.sql" })
    void searchByYearNullTest() {
        var ex = assertThrows(ObjectNotFound.class, () ->
        service.findByYear(null));
        assertEquals("Ano não pode ser nulo.", ex.getMessage());

    }

    @Test
    @DisplayName("Teste procura por descrição")
    @Sql({ "classpath:/resources/sqls/season.sql" })
    void findByDescriptionTest() {
        assertEquals(3, service.findByDescriptionContainsIgnoreCase("T").size());
        assertEquals(3, service.findByDescriptionContainsIgnoreCase("2").size());
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByDescriptionContainsIgnoreCase("Z"));
        assertEquals("Nenhuma temporada contem Z.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste procura por ano entre")
    @Sql({ "classpath:/resources/sqls/season.sql" })
    void findByYearBetweenTest() {
        assertEquals(3, service.findByYearBetween(2020, 2022).size());
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByYearBetween(2000, 2010));
        assertEquals("Temporada deve estar ente 2015 e 2024.", ex.getMessage());
    }
    
    @Test
    @DisplayName("Teste procura errado por ano entre")
    @Sql({ "classpath:/resources/sqls/season.sql" })
    void findByWrongYearBetweenTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByYearBetween(2015, 2018));
        assertEquals("Nenhuma temporada entre 2015 e 2018.", ex.getMessage());
    }    
    

    @Test
    @DisplayName("Teste procura por ano entre ano <2015 ou ano>ano atual + 1")
    @Sql({ "classpath:/resources/sqls/season.sql" })
    void findByYearBetweenViolationTest() {
        var ex = assertThrows(IntegrityViolation.class, () -> service.findByYearBetween(1980, 2015));
        assertEquals("Temporada deve estar ente 2015 e 2024.".formatted(LocalDate.now().plusYears(1).getYear()),
                ex.getMessage());

        ex = assertThrows(IntegrityViolation.class, () -> service.findByYearBetween(2015, 2990));
        assertEquals("Temporada deve estar ente 2015 e 2024.".formatted(LocalDate.now().plusYears(1).getYear()),
                ex.getMessage());

    }

}
