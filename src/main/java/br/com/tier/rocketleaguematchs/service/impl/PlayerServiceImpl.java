package br.com.tier.rocketleaguematchs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tier.rocketleaguematchs.models.Country;
import br.com.tier.rocketleaguematchs.models.Player;
import br.com.tier.rocketleaguematchs.models.Team;
import br.com.tier.rocketleaguematchs.repositories.PlayerRepository;
import br.com.tier.rocketleaguematchs.service.PlayerService;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository repository;

    @Override
    public Player findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFound("Jogador %s nao encontrado.".formatted(id)));
    }

    @Override
    public Player insert(Player player) {
        return repository.save(player);
    }

    @Override
    public Player update(Player player) {
        findById(player.getId());
        return repository.save(player);
    }

    @Override
    public void delete(Integer id) {
        Player player = findById(id);
        repository.delete(player);
    }

    @Override
    public List<Player> listAll() {
        List<Player> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum jogador cadastrado.");
        }
        return lista;
    }
    
    @Override
    public List<Player> findByNameStartsWithIgnoreCase(String name) {
        List<Player> lista = repository.findByNameStartsWithIgnoreCase(name);
        if(lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum jogador cadastrado com nome: %s".formatted(name));
        }
        return lista;
    }


    @Override
    public List<Player> findByTeamOrderByName(Team team) {
        List<Player> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum jogador cadastrado na equipe: %s".formatted(team.getName()));
        }
        return lista;
    }

    @Override
    public List<Player> findByCountryOrderByName(Country country) {
        List<Player> lista = repository.findByCountryOrderByName(country);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum jogador cadastrado no pais: %s".formatted(country.getName()));
        }
        return lista;
    }
}
