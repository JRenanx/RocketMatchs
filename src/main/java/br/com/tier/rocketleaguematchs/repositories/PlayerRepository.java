package br.com.tier.rocketleaguematchs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tier.rocketleaguematchs.models.Country;
import br.com.tier.rocketleaguematchs.models.Player;
import br.com.tier.rocketleaguematchs.models.Team;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {


    List<Player> findByNameStartsWithIgnoreCase(String name);

    List<Player> findByTeamOrderByName(Team team);

    List<Player> findByCountryOrderByName(Country country);

}
