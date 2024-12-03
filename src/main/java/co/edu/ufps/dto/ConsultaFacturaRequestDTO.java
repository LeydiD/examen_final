package co.edu.ufps.dto;

import lombok.Data;

@Data
public class ConsultaFacturaRequestDTO {
	private String token;
    private String cliente;
    private Integer factura;
}
