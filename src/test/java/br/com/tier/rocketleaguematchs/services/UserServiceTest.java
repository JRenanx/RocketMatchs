package br.com.tier.rocketleaguematchs.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.tier.rocketleaguematchs.BaseTests;
import br.com.tier.rocketleaguematchs.models.User;
import br.com.tier.rocketleaguematchs.service.UserService;
import br.com.tier.rocketleaguematchs.service.exception.IntegrityViolation;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
class UserServiceTest extends BaseTests {
    
    @Autowired
    UserService userService;
    
    

    @Test
    @DisplayName("Teste buscar usuário por ID")
    @Sql({ "classpath:/resources/sqls/user.sql" })
    void findByIdTest() {
        var usuario = userService.findById(4);
        assertNotNull(usuario);
        assertEquals(4, usuario.getId());
        assertEquals("User 1", usuario.getName());
        assertEquals("email1@gmail.com", usuario.getEmail());
        assertEquals("senha1", usuario.getPassword());
    }

    @Test
    @DisplayName("Teste buscar usuário por ID inexistente")
    @Sql({ "classpath:/resources/sqls/user.sql" })
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> userService.findById(10));
        assertEquals("O usuário 10 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste listar todos")
    @Sql({ "classpath:/resources/sqls/user.sql" })
    void listAllUsersTest() {
        List<User> lista = userService.listAll();
        assertEquals(3, lista.size());
    }

    @Test
    @DisplayName("Teste inserir usuário")
    void insertUserTest() {
        User usuario = new User(null, "insert", "insert", "insert", "ADMIN");
        userService.insert(usuario);
        usuario = userService.findById(1);
        assertEquals(1, usuario.getId());
        assertEquals("insert", usuario.getName());
        assertEquals("insert", usuario.getEmail());
        assertEquals("insert", usuario.getPassword());
    }

    @Test
    @DisplayName("Teste remover usuário")
    @Sql({ "classpath:/resources/sqls/user.sql" })
    void removeUserTest() {
        userService.delete(4);
        List<User> lista = userService.listAll();
        assertEquals(2, lista.size());
        assertEquals(5, lista.get(0).getId());
    }

    @Test
    @DisplayName("Teste remover usuário inexistente")
    @Sql({ "classpath:/resources/sqls/user.sql" })
    void removeUserNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> userService.delete(10));
        assertEquals("O usuário 10 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Teste listar todos sem possuir usuários cadastrados")
    void listAllUsersEmptyTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> userService.listAll());
        assertEquals("Nenhum usuário cadastrado", exception.getMessage());
    }

    @Test
    @DisplayName("Teste alterar usuário")
    @Sql({ "classpath:/resources/sqls/user.sql" })
    void updateUsersTest() {
        var usuario = userService.findById(4);
        assertEquals("User 1", usuario.getName());
        var usuarioAltera = new User(4, "altera", "altera", "altera", "ADMIN");
        userService.update(usuarioAltera);
        usuario = userService.findById(4);
        assertEquals("altera", usuario.getName());
    }

    @Test
    @DisplayName("Teste alterar usuário inexistente")
    void updateUsersNonExistsTest() {
        User user = new User(1, "User80", "User80", "123", "ADMIN");
        var ex = assertThrows(ObjectNotFound.class, () -> userService.update(user));
        assertEquals("O usuário 1 não existe", ex.getMessage());

    }

    @Test
    @DisplayName("Teste inserir usuário com email duplicado")
    @Sql({ "classpath:/resources/sqls/user.sql" })
    void insertEmailDuplicatedTest() {
        User user = new User(null, "User80", "email1@gmail.com", "123","ADMIN");
        var exception = assertThrows(IntegrityViolation.class, () -> userService.insert(user));
        assertEquals("Email já existente: email1@gmail.com", exception.getMessage());
    }

    @Test
    @DisplayName("Test altera usuario com email duplicado")
    @Sql({ "classpath:/resources/sqls/user.sql" })
    void updateEmailDuplicatedTest() {
        User user = new User(5, "User2", "email1@gmail.com", "123", "ADMIN");
        var exception = assertThrows(IntegrityViolation.class, () -> userService.update(user));
        assertEquals("Email já existente: email1@gmail.com", exception.getMessage());

    }

    @Test
    @DisplayName("Teste buscar usuario por nome")
    @Sql({ "classpath:/resources/sqls/user.sql" })
    void findByNameTest() {
        var usuario = userService.findByNameStartsWithIgnoreCase("User 1");
        assertNotNull(usuario);
        assertEquals(1, usuario.size());
        var usuario1 = userService.findByNameStartsWithIgnoreCase("User 2");
        assertEquals(1, usuario1.size());
    }

    @Test
    @DisplayName("Teste buscar usuario por nome errado")
    @Sql({ "classpath:/resources/sqls/user.sql" })
    void findByNameInvalidTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> userService.findByNameStartsWithIgnoreCase("w"));
        assertEquals("Nenhum usário inicia com w", exception.getMessage());

    }

}