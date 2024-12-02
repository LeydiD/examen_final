package co.edu.ufps.services;

import co.edu.ufps.entities.TipoPago;
import co.edu.ufps.repositories.TipoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoPagoService {

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    public List<TipoPago> list() {
        return tipoPagoRepository.findAll();
    }

    public TipoPago create(TipoPago tipoPago) {
        return tipoPagoRepository.save(tipoPago);
    }

    public Optional<TipoPago> update(Integer id, TipoPago tipoPagoDetails) {
        Optional<TipoPago> optionalPago = tipoPagoRepository.findById(id);
        if (!optionalPago.isPresent()) {
            return Optional.empty();
        }

        TipoPago tipoPago = optionalPago.get();
        tipoPago.setNombre(tipoPagoDetails.getNombre());

        return Optional.of(tipoPagoRepository.save(tipoPago));
    }

    public boolean delete(Integer id) {
        Optional<TipoPago> optionalPago = tipoPagoRepository.findById(id);
        if (!optionalPago.isPresent()) {
            return false;
        }
        tipoPagoRepository.delete(optionalPago.get());
        return true;
    }
}
