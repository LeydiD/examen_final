package co.edu.ufps.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private String referencia;
    private Integer cantidad;
    private double descuento;
    
    private String nombre;
    private double precio;
    private double subtotal;
    
    
}
