package co.edu.ufps.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class CompraRequestDTO {

    private Double impuesto;
    private ClienteDTO cliente;
    private List<ProductoDTO> productos;
    private List<MedioPagoDTO> medios_pago;
    private VendedorDTO vendedor;
    private CajeroDTO cajero;
    
    public CompraRequestDTO() {
        this.medios_pago = new ArrayList<>();  
    }
}
