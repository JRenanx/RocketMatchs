package br.com.tier.rocketleaguematchs.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tier.rocketleaguematchs.models.Season;
import br.com.tier.rocketleaguematchs.repositories.SeasonRepository;
import br.com.tier.rocketleaguematchs.service.SeasonService;
import br.com.tier.rocketleaguematchs.service.exception.IntegrityViolation;
import br.com.trier.springvespertino.service.exception.ObjectNotFound;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private SeasonRepository repository;

    private void validateSeason(Integer year) {
        if (year == null) {
            throw new IntegrityViolation("O ano não pode ser nulo!");
        }
        if (year < 2015 || year > LocalDate.now().plusYears(1).getYear()) {
            throw new IntegrityViolation(
                    "Temporada deve estar ente 2015 e %s".formatted(LocalDate.now().plusYears(1).getYear()));
        }
    }

    @Override
    public Season findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFound("Temporada %s não encontrada.".formatted(id)));
    }

    @Override
    public Season insert(Season season) {
        validateSeason(season.getYear());
        return repository.save(season);
    }

    @Override
    public Season update(Season season) {
        findById(season.getId());
        validateSeason(season.getYear());
        return repository.save(season);
    }

    @Override
    public void delete(Integer id) {
        Season season = findById(id);
        if (season != null) {
            repository.delete(season);
        }
    }

    @Override
    public List<Season> listAll() {
        return repository.findAll();
    }

    @Override
    public List<Season> findByYear(Integer year) {
        List<Season> lista = repository.findByYear(year);
        validateSeason(year);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma temporada em %s".formatted(year));
        }
        return repository.findByYear(year);
    }

    @Override
    public List<Season> findByYearBetween(Integer start, Integer end) {
        List<Season> lista = repository.findByYearBetween(Math.min(start, end), Math.max(start, end));
        validateSeason(start);
        validateSeason(end);
        if (lista.isEmpty()) {
            throw new ObjectNotFound(
                    "Nenhuma temporada entre %s e %s".formatted(Math.min(start, end), Math.max(start, end)));
        }
        return lista;
    }

    @Override
    public List<Season> findByYearBetweenAndDescription(Integer start, Integer end, String description) {
        // TODO Auto-generated method stub
        return null;
    }
}
