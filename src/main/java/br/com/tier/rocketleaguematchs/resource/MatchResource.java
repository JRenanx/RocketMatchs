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
import org.springframework.web.bind.annotation.RestController;

import br.com.tier.rocketleaguematchs.models.Match;
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
    public ResponseEntity<Match> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    ResponseEntity<Match> insert(@RequestBody Match match) {
        seasonService.findById(match.getSeason().getId());
        mapService.findById(match.getMap().getId());
        return ResponseEntity.ok(service.insert(match));
    }

    @PutMapping("/{id}")
    ResponseEntity<Match> update(@PathVariable Integer id, @RequestBody Match match) {
        seasonService.findById(match.getSeason().getId());
        mapService.findById(match.getMap().getId());
        match.setId(id);
        return ResponseEntity.ok(service.update(match));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<List<Match>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }
    
    

}
