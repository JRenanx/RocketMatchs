package br.com.tier.rocketleaguematchs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tier.rocketleaguematchs.models.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country findByName(String name);

    List<Country> findAllByOrderByName();

    List<Country> findByNameStartsWithIgnoreCase(String name);

}
