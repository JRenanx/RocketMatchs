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
import br.com.tier.rocketleaguematchs.models.Map;
import br.com.tier.rocketleaguematchs.models.Match;
import br.com.tier.rocketleaguematchs.models.Season;
import br.com.tier.rocketleaguematchs.service.MapService;
import br.com.tier.rocketleaguematchs.service.MatchService;
import br.com.tier.rocketleaguematchs.service.SeasonService;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;
import br.com.tier.rocketleaguematchs.utils.DateUtils;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/country.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/map.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/season.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/match.sql")
public class MatchServiceTest extends BaseTests {

    @Autowired
    MatchService service;

    @Autowired
    SeasonService seasonService;

    @Autowired
    MapService mapService;



    @Test
    @DisplayName("Teste buscar partida por ID")
    void findByIdTest() {
        var corrida = service.findById(1);
        assertNotNull(corrida);
        assertEquals(1, corrida.getId());
    }

    @Test
    @DisplayName("Teste buscar partida por ID inexistente")
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> service.findById(99));
        assertEquals("Partida 99 nao encontrada.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste Buscar todos")
    void searchAllTest() {
        assertEquals(3, service.listAll().size());
    }
    
    @Test
    @DisplayName("Teste buscar todos com nenhum cadastro")
    @Sql({"classpath:/resources/sqls/clear_tables.sql"})
    void searchAllWithNoMatchTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhuma partida cadastrada", ex.getMessage());
    }

    @Test
    @DisplayName("Teste inserir nova partida")
    @Sql({"classpath:/resources/sqls/clear_tables.sql"})
    @Sql({"classpath:/resources/sqls/country.sql"})
    @Sql({"classpath:/resources/sqls/map.sql"})
    @Sql({"classpath:/resources/sqls/season.sql"})
    void insertTest() {
        Match match = new Match(null, DateUtils.dateBrToZoneDate("01/10/2020"), mapService.findById(2), seasonService.findById(1) );
        service.insert(match);
        assertEquals(1, service.listAll().size());
        assertEquals(1, match.getId());
        assertEquals(2020, match.getDate().getYear());
    }
    
    @Test
    @DisplayName("Teste update partida")
    void updateTest() {
        Match match = new Match (1, DateUtils.dateBrToZoneDate("01/10/2021"), mapService.findById(2), seasonService.findById(1) );
        service.update(match);
        assertEquals(3, service.listAll().size());
        assertEquals(1, match.getId());
        assertEquals(2021, match.getDate().getYear());
    }
    
    @Test
    @DisplayName("Teste deleta partida")
    void deleteTeste() {
        Match match = service.findById(1);
        assertNotNull(match);
        assertEquals(1, match.getId());
        assertEquals(2018, match.getDate().getYear());
        service.delete(1);
        var ex = assertThrows(ObjectNotFound.class, () -> service.findById(1));
        assertEquals("Partida 1 nao encontrada.", ex.getMessage());
    }
    
    @Test
    @DisplayName("Teste delete partida não existente")
    void deleteInvalidTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.delete(99));
        assertEquals("Partida 99 nao encontrada.", ex.getMessage());
    }
    
    @Test
    @DisplayName("Encontra por data")
    void findByDateTest() {
        List<Match> lista = service.findByDate("21/06/2018");
        assertEquals(1, lista.size());          
    }
    
    @Test
    @DisplayName("Teste encontra por data não existe")
    void findByDateNonExistTest() {
        List<Match> lista = service.findByDate("21/06/2018");
        assertEquals(1, lista.size());  
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByDate("21/06/2025"));
        assertEquals("Nenhuma partida cadastrada na data 21/06/2025", ex.getMessage());
    }
    
    @Test
    @DisplayName("Teste encontra por temporada ordenado por data")
    void findBySeasonOrderByDateTest() {
        List<Match> lista = service.findBySeasonOderByDate(seasonService.findById(1));
        assertEquals(1, lista.size());
    }
    
    @Test
    @DisplayName("Teste encontra por temporada ordenado por data não existe")
    void findBySeasonOrderByDateNonExistTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findBySeasonOderByDate(seasonService.findById(3)));
        assertEquals("Nenhuma partida cadastrada na temporada Temporada 2022", ex.getMessage());
    }
    

    @Test
    @DisplayName("Teste encontra por data entre")
    void findByDateBetweenTest() {
        List<Match> lista = service.findByDateBetween("01/10/2018", "01/10/2020");
        assertEquals(2, lista.size());  
    }
    
    @Test
    @DisplayName("Teste encontra por data entre não existe")
    void findByDateBetweenNonExistTest() {  
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByDateBetween("01/10/2000", "01/10/2001"));
        assertEquals("Nenhuma partida cadastrada nas data entre 01/10/2000 e 01/10/2001", ex.getMessage());
    }


}
