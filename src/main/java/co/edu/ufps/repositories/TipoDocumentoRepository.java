package co.edu.ufps.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.TipoDocumento;


@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento,Integer>{
	Optional<TipoDocumento> findByNombre(String doc);
}
