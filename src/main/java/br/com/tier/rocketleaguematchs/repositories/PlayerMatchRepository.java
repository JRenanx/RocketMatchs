package br.com.tier.rocketleaguematchs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tier.rocketleaguematchs.models.Match;
import br.com.tier.rocketleaguematchs.models.Player;
import br.com.tier.rocketleaguematchs.models.PlayerMatch;

@Repository
public interface PlayerMatchRepository extends JpaRepository<PlayerMatch, Integer> {

    List<PlayerMatch> findByPlayerOrderByGoals (Player player);
    
    List<PlayerMatch> findByMatchOrderByGoals(Match match);
    
}
