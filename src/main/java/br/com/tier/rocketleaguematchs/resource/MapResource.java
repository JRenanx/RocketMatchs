package br.com.tier.rocketleaguematchs.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tier.rocketleaguematchs.models.Map;
import br.com.tier.rocketleaguematchs.service.MapService;

@RestController
@RequestMapping("/maps")
public class MapResource {

    @Autowired
    private MapService service;

    @Secured({ "ROLE_USER" })
    @GetMapping("/{id}")
    public ResponseEntity<Map> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Secured({ "ROLE_ADMIN" })
    @PostMapping
    public ResponseEntity<Map> insert(@RequestBody Map map) {
        return ResponseEntity.ok(service.insert(map));
    }

    @Secured({ "ROLE_ADMIN" })
    @PutMapping("/{id}")
    public ResponseEntity<Map> update(@PathVariable Integer id, @RequestBody Map map) {
        map.setId(id);
        return ResponseEntity.ok(service.update(map));
    }

    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @Secured({ "ROLE_USER" })
    @GetMapping
    ResponseEntity<List<Map>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }
}
