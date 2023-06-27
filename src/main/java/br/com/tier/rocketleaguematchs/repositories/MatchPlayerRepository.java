package br.com.tier.rocketleaguematchs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tier.rocketleaguematchs.models.Match;
import br.com.tier.rocketleaguematchs.models.MatchPlayer;
import br.com.tier.rocketleaguematchs.models.Player;

@Repository
public interface MatchPlayerRepository extends JpaRepository<MatchPlayer, Integer> {

    List<MatchPlayer> findByPlayerOrderByGoals (Player player);
    
    List<MatchPlayer> findByMatchOrderByGoals(Match match);
    
}
