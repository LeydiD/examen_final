
package co.edu.ufps.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.Cliente;


@Repository
public interface CompraRepository extends JpaRepository<Compra,Integer>{
	 
}
