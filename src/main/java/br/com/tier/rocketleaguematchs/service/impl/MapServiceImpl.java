package br.com.tier.rocketleaguematchs.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tier.rocketleaguematchs.models.Map;
import br.com.tier.rocketleaguematchs.repositories.MapRepository;
import br.com.tier.rocketleaguematchs.service.MapService;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;

@Service
public class MapServiceImpl implements MapService {

    @Autowired
    MapRepository repository;

    @Override
    public Map findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Mapa %s nao encontrado.".formatted(id)));
    }

    @Override
    public Map insert(Map map) {
        return repository.save(map);
    }

    @Override
    public Map update(Map map) {
        findById(map.getId());
        return repository.save(map);
    }

    @Override
    public void delete(Integer id) {
        Map map = findById(id);
        if (map != null) {
            repository.delete(map);
        }
    }

    @Override
    public List<Map> listAll() {
        List<Map> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum mapa cadastrado.");
        }
        return lista;
    }

    public Map getRandomMap() {
        List<Map> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Mapa não cadastrado.");
        }
        Random random = new Random();
        int index = random.nextInt(lista.size());
        return lista.get(index);
    }
}
