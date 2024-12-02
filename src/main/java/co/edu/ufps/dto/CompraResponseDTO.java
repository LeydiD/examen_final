package co.edu.ufps.dto;

import lombok.Data;

@Data
public class CompraResponseDTO {

    private String status;
    private String message;
    private CompraDataDTO data;
    public CompraResponseDTO() {
    }
    
    public CompraResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }
    // Getters and Setters
}
