package co.edu.ufps.services;

import co.edu.ufps.entities.Producto;
import co.edu.ufps.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> list() {
        return productoRepository.findAll();
    }

    public Producto create(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> update(Integer id, Producto productoDetails) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (!optionalProducto.isPresent()) {
            return Optional.empty();
        }

        Producto producto = optionalProducto.get();
        producto.setNombre(productoDetails.getNombre());
        producto.setPrecio(productoDetails.getPrecio());
//        producto.setStock(productoDetails.getStock());

        return Optional.of(productoRepository.save(producto));
    }

    public boolean delete(Integer id) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (!optionalProducto.isPresent()) {
            return false;
        }
        productoRepository.delete(optionalProducto.get());
        return true;
    }
}
