package br.com.tier.rocketleaguematchs.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.tier.rocketleaguematchs.models.PlayerMatch;
import br.com.tier.rocketleaguematchs.models.dto.PlayerMatchDTO;
import br.com.tier.rocketleaguematchs.service.MatchService;
import br.com.tier.rocketleaguematchs.service.PlayerMatchService;
import br.com.tier.rocketleaguematchs.service.PlayerService;

@RestController
@RequestMapping("/players/matchs")
public class PlayerMatchResource {

    @Autowired
    private PlayerMatchService service;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchService matchService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerMatchDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id).toDTO());
    }

    @PostMapping
    public ResponseEntity<PlayerMatchDTO> insert(@RequestBody PlayerMatchDTO playerMatchDTO) {
        PlayerMatch playerM = new PlayerMatch(playerMatchDTO, playerService.findById(playerMatchDTO.getPlayerId()),
                matchService.findById(playerMatchDTO.getMatchId()));
        return ResponseEntity.ok(service.insert(playerM).toDTO());
    }

    @PostMapping("/{id}")
    public ResponseEntity<PlayerMatchDTO> update(@RequestBody PlayerMatchDTO playerMatchDTO, @PathVariable Integer id) {
        PlayerMatch playerM = new PlayerMatch(playerMatchDTO, playerService.findById(playerMatchDTO.getPlayerId()),
                matchService.findById(playerMatchDTO.getMatchId()));
        playerM.setId(id);
        return ResponseEntity.ok(service.update(playerM).toDTO());

    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PlayerMatchDTO>> listAll() {
        return ResponseEntity.ok(service.listAll().stream().map(playerM -> playerM.toDTO()).toList());
    }
    



}
