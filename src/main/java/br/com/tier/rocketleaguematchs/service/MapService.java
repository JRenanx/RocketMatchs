package br.com.tier.rocketleaguematchs.service;

import java.util.List;

import br.com.tier.rocketleaguematchs.models.Map;

public interface MapService {

    Map findById(Integer id);

    Map insert(Map map);

    Map update(Map map);

    void delete(Integer id);

    List<Map> listAll();
    
    Map getRandom();

}
