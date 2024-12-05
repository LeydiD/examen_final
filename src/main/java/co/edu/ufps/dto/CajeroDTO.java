package co.edu.ufps.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CajeroDTO {
    private String token;
    private String documento;
    private String nombre;
    
    public CajeroDTO(String documento, String nombre) {
    	this.documento=documento;
    	this.nombre=nombre;
    }
}
