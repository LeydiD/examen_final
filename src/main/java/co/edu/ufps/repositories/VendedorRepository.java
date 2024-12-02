package co.edu.ufps.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.ufps.entities.Vendedor;


@Repository
public interface VendedorRepository extends JpaRepository<Vendedor,Integer>{
	 Optional<Vendedor> findByDocumento(String documento);
}
