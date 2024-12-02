package co.edu.ufps.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.entities.Pago;
import co.edu.ufps.entities.TipoPago;


@Repository
public interface TipoPagoRepository extends JpaRepository<TipoPago,Integer>{
	 Optional<TipoPago> findByNombre(String nombre);
}
