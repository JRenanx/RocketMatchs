package br.com.tier.rocketleaguematchs.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.tier.rocketleaguematchs.BaseTests;
import br.com.tier.rocketleaguematchs.models.Country;
import br.com.tier.rocketleaguematchs.service.CountryService;
import br.com.tier.rocketleaguematchs.service.exception.IntegrityViolation;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class CountryServiceTest extends BaseTests {

    @Autowired
    CountryService service;

    @Test
    @DisplayName("Teste busca por ID")
    @Sql({ "classpath:/resources/sqls/country.sql" })
    void searchIdValidTest() {
        Country country = service.findById(1);
        assertNotNull(country);
        assertEquals(1, country.getId());
        assertEquals("Brasil", country.getName());
    }

    @Test
    @DisplayName("Tteste buscar por ID inválido")
    @Sql({ "classpath:/resources/sqls/country.sql" })
    void searchIdInvalidTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.findById(10));
        assertEquals("País de id 10 não encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Test buscar todos")
    @Sql({ "classpath:/resources/sqls/country.sql" })
    void searchAllTest() {
        assertEquals(3, service.listAll().size());
    }

    @Test
    @DisplayName("Teste buscar todos com nenhum cadastro")
    void searchAllWithNoCountryTest() {
        var ex = assertThrows(ObjectNotFound.class, () -> service.listAll());
        assertEquals("Nenhum país cadastrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste insere novo pais")
    void insertCountry() {
        Country country = new Country(null, "insert");
        service.insert(country);
        assertEquals(1, service.listAll().size());
        assertEquals(1, country.getId());
        assertEquals("insert", country.getName());
    }

    @Test
    @DisplayName("Teste insere novo pais com mesmo nome")
    @Sql({ "classpath:/resources/sqls/country.sql" })
    void insertDuplicatedNameTest() {
        Country country = new Country(null, "Brasil");
        var ex = assertThrows(IntegrityViolation.class, () -> service.insert(country));
        assertEquals("Nome já cadastrado: Brasil.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste update pais")
    @Sql({ "classpath:/resources/sqls/country.sql" })
    void updateTest() {
        Country pais = service.findById(1);
        assertNotNull(pais);
        assertEquals(1, pais.getId());
        assertEquals("Brasil", pais.getName());
        pais = new Country(1, "update");
        service.update(pais);
        assertEquals(3, service.listAll().size());
        assertEquals(1, pais.getId());
        assertEquals("update", pais.getName());
    }

    @Test
    @DisplayName("Test update pais inexistente")
    @Sql({ "classpath:/resources/sqls/country.sql" })
    void updateInvalidTest() {
        Country country = new Country(10, "ABC");
        var ex = assertThrows(ObjectNotFound.class, () -> service.update(country));
        assertEquals("País de id 10 não encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Teste deleta pais")
    @Sql({ "classpath:/resources/sqls/country.sql" })
    void deleteCountryTest() {
        assertEquals(3, service.listAll().size());
        service.delete(1);
        assertEquals(2, service.listAll().size());
        assertEquals(2, service.listAll().get(0).getId());
    }

    @Test
    @DisplayName("Teste deleta pais inexistente")
    @Sql({ "classpath:/resources/sqls/country.sql" })
    void deleteCountryNoExistTest() {
        assertEquals(3, service.listAll().size());
        var ex = assertThrows(ObjectNotFound.class, () -> service.delete(10));
        assertEquals("País de id 10 não encontrado.", ex.getMessage());
        assertEquals(3, service.listAll().size());
        assertEquals(1, service.listAll().get(0).getId());
    }
    
    @Test
    @DisplayName("Teste Procura por nome")
    @Sql({"classpath:/resources/sqls/country.sql"})
    void findByNameTest() {   
        assertEquals(1, service.findByNameStartsWithIgnoreCase("br").size());
        assertEquals(1, service.findByNameStartsWithIgnoreCase("fr").size());
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByNameStartsWithIgnoreCase("x"));
        assertEquals("Nenhum país inicia com x.", ex.getMessage());
    }
    
    @Test
    @DisplayName("Teste Procura por nome que nao existe")
    @Sql({"classpath:/resources/sqls/country.sql"})
    void findByNameNonExistTest() {   
        assertEquals(1, service.findByNameStartsWithIgnoreCase("br").size());
        var ex = assertThrows(ObjectNotFound.class, () -> service.findByNameStartsWithIgnoreCase("x"));
        assertEquals("Nenhum país inicia com x.", ex.getMessage());
        
    }
    @Test
    @DisplayName("Ordena por nome")
    @Sql({"classpath:/resources/sqls/country.sql"})
    void searchAllAndOrderByName() {    
        assertEquals(1, service.findAllByOrderByName().get(0).getId());
        assertEquals(2, service.findAllByOrderByName().get(1).getId());
        assertEquals(3, service.findAllByOrderByName().get(2).getId());
    }
    

}