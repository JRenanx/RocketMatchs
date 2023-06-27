package br.com.tier.rocketleaguematchs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tier.rocketleaguematchs.models.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findByNameStartsWithIgnoreCase(String name);
}
