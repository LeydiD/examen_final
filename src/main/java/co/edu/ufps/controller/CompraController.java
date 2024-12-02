package co.edu.ufps.controller;

import co.edu.ufps.entities.Compra;
import co.edu.ufps.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public List<Compra> list() {
        return compraService.list();
    }

    @PostMapping
    public Compra create(@RequestBody Compra compra) {
        return compraService.create(compra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> update(@PathVariable Integer id, @RequestBody Compra compraDetails) {
        Optional<Compra> updatedCompra = compraService.update(id, compraDetails);
        return updatedCompra.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (compraService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
