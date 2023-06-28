package br.com.tier.rocketleaguematchs.service;

import java.util.List;

import br.com.tier.rocketleaguematchs.models.Country;
import br.com.tier.rocketleaguematchs.models.Player;
import br.com.tier.rocketleaguematchs.models.Team;

public interface PlayerService {

    Player findById(Integer id);

    Player insert(Player player);

    Player update(Player player);

    void delete(Integer id);

    List<Player> listAll();

    List<Player> findByNameStartsWithIgnoreCase(String name);

    List<Player> findByTeamOrderByName(Team Team);

    List<Player> findByCountryOrderByName(Country country);

}
