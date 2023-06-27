package br.com.tier.rocketleaguematchs.service;

import java.util.List;

import br.com.tier.rocketleaguematchs.models.Server;

public interface ServerService {

    Server findById(Integer id);

    Server insert(Server server);

    Server update(Server server);
    
    void delete(Integer id);

    List<Server> listAll();

    List<Server> findByNameStartsWithIgnoreCase(String name);
}
