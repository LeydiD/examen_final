package co.edu.ufps.services;

import co.edu.ufps.entities.Compra;
import co.edu.ufps.repositories.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    public List<Compra> list() {
        return compraRepository.findAll();
    }

    public Compra create(Compra compra) {
        return compraRepository.save(compra);
    }

    public Optional<Compra> update(Integer id, Compra compraDetails) {
        Optional<Compra> optionalCompra = compraRepository.findById(id);
        if (!optionalCompra.isPresent()) {
            return Optional.empty();
        }

        Compra compra = optionalCompra.get();
        compra.setFecha(compraDetails.getFecha());
        compra.setImpuestos(compraDetails.getImpuestos());
        compra.setCliente(compraDetails.getCliente());

        return Optional.of(compraRepository.save(compra));
    }

    public boolean delete(Integer id) {
        Optional<Compra> optionalCompra = compraRepository.findById(id);
        if (!optionalCompra.isPresent()) {
            return false;
        }
        compraRepository.delete(optionalCompra.get());
        return true;
    }
}
