package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.ufps.entities.TipoProducto;


@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto,Integer>{
	
}
