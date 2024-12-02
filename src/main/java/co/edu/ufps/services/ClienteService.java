package co.edu.ufps.services;

import co.edu.ufps.entities.Cliente;
import co.edu.ufps.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> list() {
        return clienteRepository.findAll();
    }

    public Cliente create(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> update(Integer id, Cliente clienteDetails) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        if (!optionalCliente.isPresent()) {
            return Optional.empty();
        }

        Cliente cliente = optionalCliente.get();
        cliente.setNombre(clienteDetails.getNombre());
        cliente.setTipoDocumento(clienteDetails.getTipoDocumento());

        return Optional.of(clienteRepository.save(cliente));
    }

    public boolean delete(Integer id) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        if (!optionalCliente.isPresent()) {
            return false;
        }
        clienteRepository.delete(optionalCliente.get());
        return true;
    }
}
