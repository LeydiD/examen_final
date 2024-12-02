package co.edu.ufps.services;

import co.edu.ufps.entities.Cajero;
import co.edu.ufps.repositories.CajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CajeroService {

    @Autowired
    private CajeroRepository cajeroRepository;

    public List<Cajero> list() {
        return cajeroRepository.findAll();
    }

    public Cajero create(Cajero cajero) {
        return cajeroRepository.save(cajero);
    }

    public Optional<Cajero> update(Integer id, Cajero cajeroDetails) {
        Optional<Cajero> optionalCajero = cajeroRepository.findById(id);
        if (!optionalCajero.isPresent()) {
            return Optional.empty();
        }

        Cajero cajero = optionalCajero.get();
        cajero.setNombre(cajeroDetails.getNombre());
        cajero.setDocumento(cajeroDetails.getDocumento());
        cajero.setEmail(cajeroDetails.getEmail());
        cajero.setToken(cajeroDetails.getToken());
        cajero.setTienda(cajeroDetails.getTienda());

        return Optional.of(cajeroRepository.save(cajero));
    }

    public boolean delete(Integer id) {
        Optional<Cajero> optionalCajero = cajeroRepository.findById(id);
        if (!optionalCajero.isPresent()) {
            return false;
        }
        cajeroRepository.delete(optionalCajero.get());
        return true;
    }
}
