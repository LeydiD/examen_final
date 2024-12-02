package co.edu.ufps.services;
import co.edu.ufps.entities.DetallesCompra;
import co.edu.ufps.repositories.DetallesCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetallesCompraService {

    @Autowired
    private DetallesCompraRepository detallesCompraRepository;

    public List<DetallesCompra> list() {
        return detallesCompraRepository.findAll();
    }

    public DetallesCompra create(DetallesCompra detallesCompra) {
        return detallesCompraRepository.save(detallesCompra);
    }

    public Optional<DetallesCompra> update(Integer id, DetallesCompra detallesCompraDetails) {
        Optional<DetallesCompra> optionalDetalles = detallesCompraRepository.findById(id);
        if (!optionalDetalles.isPresent()) {
            return Optional.empty();
        }

        DetallesCompra detallesCompra = optionalDetalles.get();
        detallesCompra.setCantidad(detallesCompraDetails.getCantidad());
        detallesCompra.setPrecio(detallesCompraDetails.getPrecio());
        detallesCompra.setDescuento(detallesCompraDetails.getDescuento());
        detallesCompra.setProducto(detallesCompraDetails.getProducto());

        return Optional.of(detallesCompraRepository.save(detallesCompra));
    }

    public boolean delete(Integer id) {
        Optional<DetallesCompra> optionalDetalles = detallesCompraRepository.findById(id);
        if (!optionalDetalles.isPresent()) {
            return false;
        }
        detallesCompraRepository.delete(optionalDetalles.get());
        return true;
    }
}
