package br.com.tier.rocketleaguematchs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tier.rocketleaguematchs.models.Season;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer> {

    List<Season> findByYear(Integer year);
    
    List<Season> findByYearBetween(Integer start, Integer end);
    
    List<Season> findByYearBetweenAndDescription(Integer firstYear, Integer lastYear, String description);
}
