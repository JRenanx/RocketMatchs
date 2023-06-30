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
import br.com.tier.rocketleaguematchs.models.Map;
import br.com.tier.rocketleaguematchs.service.CountryService;
import br.com.tier.rocketleaguematchs.service.MapService;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/country.sql")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/map.sql")
public class MapServiceTest extends BaseTests {

    @Autowired
    MapService service;

    @Autowired
    CountryService countryService;

    @Test
    @DisplayName("Buscar por ID válido")
    void findIdTest() {
        Map map = service.findById(1);
        assertNotNull(map);
        assertEquals(1, map.getId());
        assertEquals("ESTADIO VIDA", map.getName());
    }

    @Test
    @DisplayName("Buscar por ID inválido")
    void findIdInvalidTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findById(99));
        assertEquals("Mapa 99 nao encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste insere novo mapa")
    @Sql({ "classpath:/resources/sqls/clear_tables.sql" })
    @Sql({ "classpath:/resources/sqls/country.sql" })
    void inserTest() {
        Map map = new Map(null, "Internacional", countryService.findById(1));
        service.insert(map);
        assertEquals(1, service.listAll().size());
        assertEquals(1, map.getId());
        assertEquals("Internacional", map.getName());
    }

    @Test
    @DisplayName("Teste buscar todos")
    void searchAllTest() {
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste buscar todos com nenhum cadastro")
    @Sql({ "classpath:/resources/sqls/clear_tables.sql" })
    void searchAllWithNoMapTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum mapa cadastrado.", ex.getMessage());
    }
    
    @Test
    @DisplayName("Teste update pista")
    void updateTest() {
        Map map = new Map(1, "Internacional", countryService.findById(1));
        service.update(map);
        assertEquals(3, service.listAll().size());
        assertEquals(1, map.getId());
        assertEquals("Internacional", map.getName());
    }
    
    @Test
    @DisplayName("Teste deletar Mapa")
    void deleteTest() {
        Map map = service.findById(1);
        assertNotNull(map);
        assertEquals(1, map.getId());
        assertEquals("ESTADIO VIDA", map.getName());
        service.delete(1);
        var ex = assertThrows(ObjectNotFound.class, () -> service.findById(1));
        assertEquals("Mapa 1 nao encontrado.", ex.getMessage());
    }
    
    @Test
    @DisplayName("Teste pegar mapa aleatorio")
    void getMapRandomTest() {
        service.delete(1); 
        service.delete(2); 
        service.delete(3); 
        var ex = assertThrows(ObjectNotFound.class, () -> service.getRandom());
        assertEquals("Mapa não cadastrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste pegar mapa não encontrado")
    void getMapRandomNotFoundedTest() {
        Map map = service.getRandom();
        assertNotNull(map);
        assertEquals(3, map.getId());
    }

    
}
