package co.edu.ufps.services;

import co.edu.ufps.entities.Tienda;
import co.edu.ufps.repositories.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TiendaService {

    @Autowired
    private TiendaRepository tiendaRepository;

    public List<Tienda> list() {
        return tiendaRepository.findAll();
    }

    public Tienda create(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    public Optional<Tienda> update(Integer id, Tienda tiendaDetails) {
        Optional<Tienda> optionalTienda = tiendaRepository.findById(id);
        if (!optionalTienda.isPresent()) {
            return Optional.empty();
        }

        Tienda tienda = optionalTienda.get();
        tienda.setNombre(tiendaDetails.getNombre());
        tienda.setDireccion(tiendaDetails.getDireccion());
        tienda.setUuid(tiendaDetails.getUuid());

        return Optional.of(tiendaRepository.save(tienda));
    }

    public boolean delete(Integer id) {
        Optional<Tienda> optionalTienda = tiendaRepository.findById(id);
        if (!optionalTienda.isPresent()) {
            return false;
        }
        tiendaRepository.delete(optionalTienda.get());
        return true;
    }
}
