package br.com.tier.rocketleaguematchs.service;

import java.util.List;

import br.com.tier.rocketleaguematchs.models.Match;
import br.com.tier.rocketleaguematchs.models.Season;

public interface MatchService {

    Match findById(Integer id);

    Match insert(Match match);

    Match update(Match match);

    void delete(Integer id);

    List<Match> listAll();

    List<Match> findByDate(String date);

    List<Match> findByDateBetween(String dateIn, String dateFin);

    List<Match> findBySeasonOrderByDate(Season season);



}
