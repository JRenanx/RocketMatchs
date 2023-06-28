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

import br.com.tier.rocketleaguematchs.models.Player;
import br.com.tier.rocketleaguematchs.service.CountryService;
import br.com.tier.rocketleaguematchs.service.PlayerService;
import br.com.tier.rocketleaguematchs.service.TeamService;

@RestController
@RequestMapping("/players")
public class PlayerResource {

    @Autowired
    private PlayerService service;

    @Autowired
    private CountryService countryService;

    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public ResponseEntity<Player> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    ResponseEntity<Player> insert(@RequestBody Player player) {
        countryService.findById(player.getCountry().getId());
        teamService.findById(player.getCountry().getId());
        return ResponseEntity.ok(service.insert(player));
    }

    @PutMapping("/{id}")
    ResponseEntity<Player> update(@PathVariable Integer id, @RequestBody Player player) {
        countryService.findById(player.getCountry().getId());
        teamService.findById(player.getCountry().getId());
        player.setId(id);
        return ResponseEntity.ok(service.update(player));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<List<Player>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

}
