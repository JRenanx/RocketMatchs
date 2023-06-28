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

import br.com.tier.rocketleaguematchs.models.Season;
import br.com.tier.rocketleaguematchs.service.SeasonService;

@RestController
@RequestMapping("/seasons")
public class SeasonResource {

    @Autowired
    private SeasonService service;

    @GetMapping("/{id}")
    public ResponseEntity<Season> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Season> insert(@RequestBody Season season) {
        return ResponseEntity.ok(service.insert(season));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Season> update(@PathVariable Integer id, @RequestBody Season season) {
        season.setId(id);
        return ResponseEntity.ok(service.update(season));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Season>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }
}
