package co.edu.ufps.dto;

import lombok.Data;

@Data
public class MedioPagoDTO {
    private String tipoPago;
    private String tipoTarjeta;
    private Integer cuotas;
    private String valor;
}
