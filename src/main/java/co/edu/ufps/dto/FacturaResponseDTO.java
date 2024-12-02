package co.edu.ufps.dto;

import lombok.Data;

@Data
public class FacturaResponseDTO {
    private String status;
    private String message;
    private FacturaDataDTO data;
}
