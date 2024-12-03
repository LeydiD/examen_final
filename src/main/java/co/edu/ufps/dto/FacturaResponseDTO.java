package co.edu.ufps.dto;

import java.util.List;

import lombok.Data;

@Data
public class FacturaResponseDTO {
	private Double total;
    private Double impuestos;
    private ClienteDTO cliente;
    private List<ProductoDTO> productos;
    private CajeroDTO cajero;
}
