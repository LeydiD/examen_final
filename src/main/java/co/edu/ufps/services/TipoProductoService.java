package co.edu.ufps.services;

import co.edu.ufps.entities.TipoProducto;
import co.edu.ufps.repositories.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoProductoService {

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    public List<TipoProducto> list() {
        return tipoProductoRepository.findAll();
    }

    public TipoProducto create(TipoProducto tipoProducto) {
        return tipoProductoRepository.save(tipoProducto);
    }

    public Optional<TipoProducto> update(Integer id, TipoProducto tipoProductoDetails) {
        Optional<TipoProducto> optionalProducto = tipoProductoRepository.findById(id);
        if (!optionalProducto.isPresent()) {
            return Optional.empty();
        }

        TipoProducto tipoProducto = optionalProducto.get();
        tipoProducto.setNombre(tipoProductoDetails.getNombre());

        return Optional.of(tipoProductoRepository.save(tipoProducto));
    }

    public boolean delete(Integer id) {
        Optional<TipoProducto> optionalProducto = tipoProductoRepository.findById(id);
        if (!optionalProducto.isPresent()) {
            return false;
        }
        tipoProductoRepository.delete(optionalProducto.get());
        return true;
    }
}
