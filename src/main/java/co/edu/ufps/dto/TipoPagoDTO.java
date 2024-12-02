package co.edu.ufps.dto;

import lombok.Data;

@Data
public class TipoPagoDTO {
    private Integer tipo_pago;
    private String tipo_tarjeta;
    private String cuotas;
    private String valor;

}
