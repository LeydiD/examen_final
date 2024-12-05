package co.edu.ufps.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private String documento;
    private String nombre;
    private String tipo_documento;
    
    public ClienteDTO(String documento,String nombre, String tipo_documento ) {
    	this.documento=documento;
    	this.nombre=nombre;
    	this.tipo_documento=tipo_documento;
    }
}
