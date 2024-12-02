package co.edu.ufps.dto;

import java.util.List;

import lombok.Data;

@Data
public class FacturaRequestDTO {
    private Integer impuesto;
    private ClienteDTO cliente;
    private List<ProductoDTO> productos;
    private List<MedioPagoDTO> mediosPago;
    private VendedorDTO vendedor;
    private CajeroDTO cajero;
}