package br.com.tier.rocketleaguematchs.service;

import java.util.List;

import br.com.tier.rocketleaguematchs.models.Map;

public interface MapService {
    Map findById(Integer id);

    Map insert(Map map);
    
    Map update(Map map);
    
    void delte(Integer id);
    
    List<Map> listAll();

    List<Map> findByNameStartsWithIgnoreCase(String name);
}
