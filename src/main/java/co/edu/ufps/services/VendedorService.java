package co.edu.ufps.services;

import co.edu.ufps.entities.Vendedor;
import co.edu.ufps.repositories.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> list() {
        return vendedorRepository.findAll();
    }

    public Vendedor create(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    public Optional<Vendedor> update(Integer id, Vendedor vendedorDetails) {
        Optional<Vendedor> optionalVendedor = vendedorRepository.findById(id);
        if (!optionalVendedor.isPresent()) {
            return Optional.empty();
        }

        Vendedor vendedor = optionalVendedor.get();
        vendedor.setNombre(vendedorDetails.getNombre());
        vendedor.setDocumento(vendedorDetails.getDocumento());
        vendedor.setEmail(vendedorDetails.getEmail());

        return Optional.of(vendedorRepository.save(vendedor));
    }

    public boolean delete(Integer id) {
        Optional<Vendedor> optionalVendedor = vendedorRepository.findById(id);
        if (!optionalVendedor.isPresent()) {
            return false;
        }
        vendedorRepository.delete(optionalVendedor.get());
        return true;
    }
}
