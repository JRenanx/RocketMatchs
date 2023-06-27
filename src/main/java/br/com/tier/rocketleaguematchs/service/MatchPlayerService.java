package br.com.tier.rocketleaguematchs.service;

import java.util.List;

import br.com.tier.rocketleaguematchs.models.Match;
import br.com.tier.rocketleaguematchs.models.MatchPlayer;
import br.com.tier.rocketleaguematchs.models.Player;

public interface MatchPlayerService {

    MatchPlayer findById(Integer id);

    MatchPlayer insert(MatchPlayer matchPlayer);

    MatchPlayer update(MatchPlayer matchPlayer);

    void delete(Integer id);

    List<MatchPlayer> listAll();

    List<MatchPlayer> findByPlayerOrderByGoals(Player player);

    List<MatchPlayer> findByMatchOrderByGoals(Match match);

}
