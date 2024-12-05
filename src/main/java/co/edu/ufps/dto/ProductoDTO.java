package co.edu.ufps.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private String referencia;
    private String nombre;
    private Integer cantidad;
    private double precio;
    private double descuento;
    private double subtotal;
    
    public ProductoDTO(String referencia, String nombre, Integer cantidad,
    		double precio, double descuento, double subtotal) {
    	this.referencia=referencia;
    	this.nombre=nombre;
    	this.cantidad=cantidad;
    	this.precio=precio;
    	this.descuento=descuento;
    	this.subtotal=subtotal;
    }
    
    
    
}
