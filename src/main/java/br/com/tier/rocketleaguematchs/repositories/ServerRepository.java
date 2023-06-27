package br.com.tier.rocketleaguematchs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tier.rocketleaguematchs.models.Server;

@Repository
public interface ServerRepository extends JpaRepository<Server, Integer>{

    List<Server> findByNameStartsWithIgnoreCase(String name);
}
