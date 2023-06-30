package br.com.tier.rocketleaguematchs.service.impl;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tier.rocketleaguematchs.models.Match;
import br.com.tier.rocketleaguematchs.models.Season;
import br.com.tier.rocketleaguematchs.repositories.MatchRepository;
import br.com.tier.rocketleaguematchs.service.MapService;
import br.com.tier.rocketleaguematchs.service.MatchService;
import br.com.tier.rocketleaguematchs.service.exception.ObjectNotFound;
import br.com.tier.rocketleaguematchs.utils.DateUtils;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    MatchRepository repository;
    
    @Autowired
    MapService mapService;

    @Override
    public Match findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFound("Partida %s nao encontrada.".formatted(id)));
    }

    @Override
    public Match insert(Match match) {
        mapService.getRandom();
        return repository.save(match);
    }

    @Override
    public Match update(Match match) {
        findById(match.getId());
        return repository.save(match);
    }

    @Override
    public void delete(Integer id) {
        Match match = findById(id);
        if (match != null) {
            repository.delete(match);
        }

    }

    @Override
    public List<Match> listAll() {
        List<Match> lista = repository.findAll();
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma partida cadastrada");
        }
        return lista;
    }

    @Override
    public List<Match> findByDate(String date) {
        ZonedDateTime zonedDate = DateUtils.dateBrToZoneDate(date);
        List<Match> lista = repository.findByDate(zonedDate);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma partida cadastrada na data %s".formatted(date));
        }
        return lista;
    }

    @Override
    public List<Match> findByDateBetween(String dateIn, String dateFin) {
        ZonedDateTime zonedDate1 = DateUtils.dateBrToZoneDate(dateIn);
        ZonedDateTime zonedDate2 = DateUtils.dateBrToZoneDate(dateFin);
        List<Match> lista = repository.findByDateBetween(zonedDate1, zonedDate2);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma partida cadastrada nas data entre %s e %s".formatted(dateIn, dateFin));
        }
        return lista;
    }

    @Override
    public List<Match> findBySeasonOrderByDate(Season season) {
        List<Match> lista = repository.findBySeasonOrderByDate(season);
        if (lista.isEmpty()) {
            throw new ObjectNotFound("Nenhuma partida cadastrada na temporada %s".formatted(season.getDescription()));
        }
        return lista;
    }

}
