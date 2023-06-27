package br.com.tier.rocketleaguematchs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tier.rocketleaguematchs.models.Car;
import br.com.tier.rocketleaguematchs.models.Player;
import br.com.tier.rocketleaguematchs.models.Server;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Player findByName(Player player);

    List<Player> findByCarOrderByName(Car car);

    List<Player> findByServerOrderByName(Server server);
}
