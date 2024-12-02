package co.edu.ufps.services;

import co.edu.ufps.entities.TipoDocumento;
import co.edu.ufps.repositories.TipoDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoDocumentoService {

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    public List<TipoDocumento> list() {
        return tipoDocumentoRepository.findAll();
    }

    public TipoDocumento create(TipoDocumento tipoDocumento) {
        return tipoDocumentoRepository.save(tipoDocumento);
    }

    public Optional<TipoDocumento> update(Integer id, TipoDocumento tipoDocumentoDetails) {
        Optional<TipoDocumento> optionalTipo = tipoDocumentoRepository.findById(id);
        if (!optionalTipo.isPresent()) {
            return Optional.empty();
        }

        TipoDocumento tipoDocumento = optionalTipo.get();
        tipoDocumento.setNombre(tipoDocumentoDetails.getNombre());

        return Optional.of(tipoDocumentoRepository.save(tipoDocumento));
    }

    public boolean delete(Integer id) {
        Optional<TipoDocumento> optionalTipo = tipoDocumentoRepository.findById(id);
        if (!optionalTipo.isPresent()) {
            return false;
        }
        tipoDocumentoRepository.delete(optionalTipo.get());
        return true;
    }
}
