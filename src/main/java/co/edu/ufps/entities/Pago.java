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
@Table(name="pago")
@Data
public class Pago {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name="tipo_pago_id")
	private TipoPago tipoPago;
	
	@ManyToOne
	@JoinColumn(name="compra_id")
	private Compra compra;
	
	@Column(name="tarjeta_tipo")
	private String tarjetaTipo;
	
	
	@Column(name="cuotas")
	private Integer cuotas;
	
	@Column(name="valor")
	private Double valor;
	
	
	@Column(name="cantidad")
	private Integer cantidad;
	

}