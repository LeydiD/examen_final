package co.edu.ufps.entities;
 
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="detalles_compra")
@Data
public class DetallesCompra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="compra_id")
	private Compra compra;
	
	@ManyToOne
	@JoinColumn(name="producto_id")
	private Producto producto;
	
	@Column(name="cantidad")
	private Integer cantidad;
	
	@Column(name="precio")
	private Integer precio;
	
	@Column(name="descuento")
	private Integer descuento;
	
}