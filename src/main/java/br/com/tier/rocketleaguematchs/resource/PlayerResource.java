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
import br.com.tier.rocketleaguematchs.models.dto.PlayerDTO;
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
    public ResponseEntity<PlayerDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> insert(@RequestBody PlayerDTO playerDTO) {
        Player player = new Player(playerDTO, teamService.findById(playerDTO.getTeamId()), countryService.findById(playerDTO.getCountryId())); 
        return ResponseEntity.ok(service.insert(player).toDTO());
        }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> update(@PathVariable Integer id, @RequestBody PlayerDTO playerDTO) {
        Player player = new Player (playerDTO, teamService.findById(playerDTO.getTeamId()), countryService.findById(playerDTO.getCountryId())); 
        player.setId(id);
        return ResponseEntity.ok(service.update(player).toDTO());
    }
        
    

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    ResponseEntity<List<PlayerDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(player -> player.toDTO()).toList());
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<List<PlayerDTO>> findByNameStartsWithIgnoreCase(@PathVariable String name){
        return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name)
                .stream()
                .map(pilot -> pilot.toDTO())
                .toList());
    }
    
    @GetMapping("/team/{id}")
    public ResponseEntity<List<PlayerDTO>> findByTeamOrderByName(@PathVariable Integer id){
        return ResponseEntity.ok(service.findByTeamOrderByName(teamService.findById(id))
                .stream()
                .map(pilot -> pilot.toDTO())
                .toList());
    }
    
    @GetMapping("/country/{id}")
    public ResponseEntity<List<PlayerDTO>> findByCountryOrderByName(@PathVariable Integer id){
        return ResponseEntity.ok(service.findByCountryOrderByName(countryService.findById(id))
                .stream()
                .map(pilot -> pilot.toDTO())
                .toList());
    }

}
