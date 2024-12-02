package co.edu.ufps.dto;

import lombok.Data;

@Data
public class CompraResponseDTO {

    private String status;
    private String message;
    private CompraDataDTO data;

    // Getters and Setters
}
