package co.edu.ufps.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.ufps.entities.Tienda;


@Repository
public interface TiendaRepository extends JpaRepository<Tienda,Integer>{
	Optional<Tienda> findByUuid(String uuid);
}
