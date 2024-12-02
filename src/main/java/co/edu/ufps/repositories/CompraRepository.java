
package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.ufps.entities.Compra;


@Repository
public interface CompraRepository extends JpaRepository<Compra,Integer>{
	
}
