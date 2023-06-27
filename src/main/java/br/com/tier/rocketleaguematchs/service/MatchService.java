package br.com.tier.rocketleaguematchs.service;

import java.time.ZonedDateTime;
import java.util.List;

import br.com.tier.rocketleaguematchs.models.Map;
import br.com.tier.rocketleaguematchs.models.Match;
import br.com.tier.rocketleaguematchs.models.Season;

public interface MatchService {

    Match findById(Integer id);

    Match insert(Match match);

    Match update(Match match);

    void delete(Integer id);

    List<Match> listAll();

    List<Match> findByDateBetween(ZonedDateTime dateIn, ZonedDateTime dateFin);

    List<Match> findByDate(ZonedDateTime date);

    List<Match> findByMapOrderByDate(Map map);

    List<Match> findBySeasonOrderByDate(Season Season);
}
