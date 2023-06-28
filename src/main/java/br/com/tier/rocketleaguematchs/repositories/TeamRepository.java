package br.com.tier.rocketleaguematchs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tier.rocketleaguematchs.models.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    Team findByName(String name);

    List<Team> findAllByOrderByName();

    List<Team> findByNameStartsWithIgnoreCase(String name);

}
