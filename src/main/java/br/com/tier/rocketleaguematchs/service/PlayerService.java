package br.com.tier.rocketleaguematchs.service;

import java.util.List;

import br.com.tier.rocketleaguematchs.models.Car;
import br.com.tier.rocketleaguematchs.models.Player;
import br.com.tier.rocketleaguematchs.models.Server;

public interface PlayerService {

    Player findById(Integer id);

    Player insert(Player player);

    Player update(Player player);

    void delete(Integer id);

    List<Player> listAll();

    List<Player> findByCarOrderByName(Car car);

    List<Player> findByServerOrderByName(Server server);
}
