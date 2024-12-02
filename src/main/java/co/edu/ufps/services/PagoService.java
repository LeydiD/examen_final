package co.edu.ufps.services;

import co.edu.ufps.entities.Pago;
import co.edu.ufps.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> list() {
        return pagoRepository.findAll();
    }

    public Pago create(Pago pago) {
        return pagoRepository.save(pago);
    }

    public Optional<Pago> update(Integer id, Pago pagoDetails) {
        Optional<Pago> optionalPago = pagoRepository.findById(id);
        if (!optionalPago.isPresent()) {
            return Optional.empty();
        }

        Pago pago = optionalPago.get();
        pago.setValor(pagoDetails.getValor());
        pago.setTipoPago(pagoDetails.getTipoPago());
        pago.setTarjetaTipo(pagoDetails.getTarjetaTipo());
        pago.setCuotas(pagoDetails.getCuotas());

        return Optional.of(pagoRepository.save(pago));
    }

    public boolean delete(Integer id) {
        Optional<Pago> optionalPago = pagoRepository.findById(id);
        if (!optionalPago.isPresent()) {
            return false;
        }
        pagoRepository.delete(optionalPago.get());
        return true;
    }
}
