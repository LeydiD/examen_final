package co.edu.ufps.dto;

import java.util.List;

import lombok.Data;

@Data
public class FacturaDTO {
	 private double total;
	    private double impuestos;
	    private ClienteDTO cliente;
	    private List<ProductoDTO> productos;
	    private CajeroDTO cajero;
}
