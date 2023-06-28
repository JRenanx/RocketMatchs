package br.com.tier.rocketleaguematchs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tier.rocketleaguematchs.models.Map;

@Repository
public interface MapRepository extends JpaRepository<Map, Integer> {

}
