package br.com.tier.rocketleaguematchs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tier.rocketleaguematchs.models.Team;
import br.com.tier.rocketleaguematchs.repositories.TeamRepository;
import br.com.tier.rocketleaguematchs.service.TeamService;
import br.com.tier.rocketleaguematchs.service.exception.IntegrityViolation;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRepository repository;

    @Override
    public Team findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Equipe %s nao encontrado.".formatted(id)));
    }

    private void findByName(Team team) {
        Team check = repository.findByName(team.getName());
        if (check != null && !check.getId().equals(team.getId())) {
            throw new IntegrityViolation("Nome %s já cadastrado.".formatted(team.getName()));
        }
    }

    @Override
    public Team insert(Team team) {
        findByName(team);
        return repository.save(team);
    }

    @Override
    public Team update(Team team) {
        findById(team.getId());
        findByName(team);
        return repository.save(team);
    }

    @Override
    public void delete(Integer id) {
        Team team = findById(id);
        if (team != null) {
            repository.delete(team);
        }
    }

    @Override
    public List<Team> listAll() {
        return repository.findAll();
    }

    @Override
    public List<Team> findAllByOrderByName() {
        repository.findAll();
        return repository.findAllByOrderByName();
    }

    @Override
    public List<Team> findByNameStartsWithIgnoreCase(String name) {
        List<Team> lista = repository.findByNameStartsWithIgnoreCase(name);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma equipe inicia com %s".formatted(name));
        }
        return lista;
    }
}
