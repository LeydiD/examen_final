package co.edu.ufps.entities;
 
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name="compra")
@Data
public class Compra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="total")
	private Double total;
	
	@Column(name="impuestos")
	private Integer impuestos;
	
	@Column(name="fecha")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fecha;
	
	@Column(name="observaciones")
	private String observaciones;
	
	@OneToMany(mappedBy = "compra", cascade= CascadeType.ALL)
	@JsonIgnore
	private List<Pago> pagos;
	
	@OneToMany(mappedBy = "compra", cascade= CascadeType.ALL)
	@JsonIgnore
	private List<DetallesCompra> detallesCompra;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="tienda_id")
	private Tienda tienda;
	
	@ManyToOne
	@JoinColumn(name="vendedor_id")
	private Vendedor vendedor;
	
	@ManyToOne
	@JoinColumn(name="cajero_id")
	private Cajero cajero;
	
	
}