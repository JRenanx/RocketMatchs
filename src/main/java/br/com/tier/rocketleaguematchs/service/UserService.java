package br.com.tier.rocketleaguematchs.service;

import java.util.List;

import br.com.tier.rocketleaguematchs.models.User;

public interface UserService {

    User findById(Integer id);

    User insert(User user);

    List<User> listAll();

    User update(User user);

    void delete(Integer id);
    
    List<User> findByNameStartsWithIgnoreCase(String name);

}