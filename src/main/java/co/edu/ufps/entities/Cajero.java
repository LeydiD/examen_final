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
@Table(name="cajero")
@Data
public class Cajero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="documento")
	private String documento;
	
	@Column(name="email")
	private String email;
	
	@Column(name="token")
	private String token;
	
	@ManyToOne
	@JoinColumn(name="tienda_id")
	private Tienda tienda;
	
	@OneToMany(mappedBy = "cajero", cascade= CascadeType.ALL)
	@JsonIgnore
	private List<Compra> compra;
}