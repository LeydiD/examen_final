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
@Table(name="tienda")
@Data
public class Tienda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="direccion")
	private String direccion;
	
	@Column(name="uuid")
	private String uuid;
	
	@OneToMany(mappedBy = "tienda", cascade= CascadeType.ALL)
	@JsonIgnore
	private List<Cajero> cajero;
	
	@OneToMany(mappedBy = "tienda", cascade= CascadeType.ALL)
	@JsonIgnore
	private List<Compra> compras;
}