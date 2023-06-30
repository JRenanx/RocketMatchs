package br.com.tier.rocketleaguematchs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tier.rocketleaguematchs.models.Match;
import br.com.tier.rocketleaguematchs.models.Player;
import br.com.tier.rocketleaguematchs.models.PlayerMatch;
import br.com.tier.rocketleaguematchs.repositories.PlayerMatchRepository;
import br.com.tier.rocketleaguematchs.service.PlayerMatchService;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;

@Service
public class PlayerMatchServiceImpl implements PlayerMatchService {

    @Autowired
    PlayerMatchRepository repository;

    @Override
    public PlayerMatch findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFound("Jogador/Partida %s não encontrado.".formatted(id)));
    }

    @Override
    public PlayerMatch insert(PlayerMatch playerMatch) {
        return repository.save(playerMatch);
    }

    @Override
    public PlayerMatch update(PlayerMatch playerMatch) {
        findById(playerMatch.getId());
        return repository.save(playerMatch);
    }

    @Override
    public void delete(Integer id) {
        PlayerMatch playerM = findById(id);
        if (playerM != null) {
            repository.delete(playerM);
        }
    }

    @Override
    public List<PlayerMatch> listAll() {
        List<PlayerMatch> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhum Jogador/Partida cadastrado");
        }
        return lista;
    }

    @Override
    public List<PlayerMatch> findByPlayerOrderByGoals(Player player) {
        List<PlayerMatch> lista = repository.findByPlayerOrderByGoals(player);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Jogador não cadastrado: %s".formatted(player.getName()));
        }
        return lista;
    }

    @Override
    public List<PlayerMatch> findByMatchOrderByGoals(Match match) {
        List<PlayerMatch> lista = repository.findByMatchOrderByGoals(match);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Jogador não associado com a partida: %s".formatted(match.getId()));
        }
        return lista;
    }

}
