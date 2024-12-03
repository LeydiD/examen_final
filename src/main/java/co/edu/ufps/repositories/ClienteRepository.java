package co.edu.ufps.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.TipoDocumento;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer>{
	
	Optional<Cliente> findByDocumentoAndTipoDocumento(String documento, TipoDocumento tipoDocumento);
	Optional<Cliente> findByDocumento(String documento);
}
