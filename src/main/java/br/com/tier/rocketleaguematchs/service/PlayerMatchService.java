package br.com.tier.rocketleaguematchs.service;

import java.util.List;

import br.com.tier.rocketleaguematchs.models.Match;
import br.com.tier.rocketleaguematchs.models.Player;
import br.com.tier.rocketleaguematchs.models.PlayerMatch;

public interface PlayerMatchService {

    PlayerMatch findById(Integer id);

    PlayerMatch insert(PlayerMatch playerMatch);

    PlayerMatch update(PlayerMatch playerMatch);

    void delete(Integer id);

    List<PlayerMatch> listAll();

    List<PlayerMatch> findByPlayerOrderByGoals(Player player);

    List<PlayerMatch> findByMatchOrderByGoals(Match match);


}
