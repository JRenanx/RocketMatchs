package br.com.tier.rocketleaguematchs.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tier.rocketleaguematchs.models.Match;
import br.com.tier.rocketleaguematchs.models.dto.MatchDTO;
import br.com.tier.rocketleaguematchs.service.MapService;
import br.com.tier.rocketleaguematchs.service.MatchService;
import br.com.tier.rocketleaguematchs.service.SeasonService;

@RestController
@RequestMapping("/matchs")
public class MatchResource {

    @Autowired
    private MatchService service;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private MapService mapService;

    @GetMapping("/{id}")
    public ResponseEntity<MatchDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @PostMapping
    public ResponseEntity<MatchDTO> insert(@RequestBody MatchDTO matchDTO) {
        Match match = new Match(matchDTO, mapService.findById(matchDTO.getId()),
                seasonService.findById(matchDTO.getSeasonId()));
        return ResponseEntity.ok(service.insert(match).toDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDTO> update(@PathVariable Integer id, @RequestBody MatchDTO matchDTO) {
        Match match = new Match(matchDTO, mapService.findById(matchDTO.getSeasonId()),
                seasonService.findById(matchDTO.getSeasonId()));
        match.setId(id);
        return ResponseEntity.ok(service.update(match).toDTO());
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<List<MatchDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(race -> race.toDTO()).toList());
    }

    @GetMapping("/date")
    public ResponseEntity<List<MatchDTO>> findByDate(@RequestParam String date) {
        return ResponseEntity.ok(service.findByDate(date).stream().map(race -> race.toDTO()).toList());
    }

    @GetMapping("/date/between")
    public ResponseEntity<List<MatchDTO>> findByDateBetween(@RequestParam String date1, @RequestParam String date2) {
        return ResponseEntity.ok(service.findByDateBetween(date1, date2).stream().map(race -> race.toDTO()).toList());
    }

    @GetMapping("/season/{id}")
    public ResponseEntity<List<MatchDTO>> findBySeasonOrderByDate(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findBySeasonOrderByDate(seasonService.findById(id)).stream()
                .map(race -> race.toDTO()).toList());
    }

}
