package br.com.tier.rocketleaguematchs.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tier.rocketleaguematchs.models.Map;
import br.com.tier.rocketleaguematchs.models.Match;
import br.com.tier.rocketleaguematchs.models.Season;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer>{

    List<Match> findByDateBetween(ZonedDateTime dateIn, ZonedDateTime dateFin);

    List<Match> findByDate(ZonedDateTime date);

    List<Match> findByMapOrderByDate(Map map);

    List<Match> findBySeasonOrderByDate(Season Season);
}
