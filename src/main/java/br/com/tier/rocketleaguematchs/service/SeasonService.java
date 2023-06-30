package br.com.tier.rocketleaguematchs.service;

import java.util.List;

import br.com.tier.rocketleaguematchs.models.Season;

public interface SeasonService {

    Season findById(Integer id);

    Season insert(Season season);

    Season update(Season season);

    void delete(Integer id);

    List<Season> listAll();

    List<Season> findByYear(Integer year);

    List<Season> findByYearBetween(Integer start, Integer end);

    List<Season> findByDescriptionContainsIgnoreCase(String description);

}
