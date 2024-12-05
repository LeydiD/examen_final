package co.edu.ufps.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.ufps.entities.*;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.Producto;
import lombok.Data;

@Data
public class FacturaResponseDTO {
	private double total;
	private double impuestos;
	private ClienteDTO cliente;
	private List<ProductoDTO> productos;
	private CajeroDTO cajero;

	public FacturaResponseDTO(Compra compra) {
		  	this.total = compra.getTotal();
		    this.impuestos = compra.getImpuestos();
		    Cliente c= compra.getCliente();
		    this.cliente = new ClienteDTO(c.getDocumento(), c.getNombre(), c.getTipoDocumento().getNombre());
		    this.productos = new ArrayList<>(); // Inicializar la lista

		    for (DetallesCompra detalle : compra.getDetallesCompra()) {
		        Producto producto = detalle.getProducto();
		        ProductoDTO productoDTO = new ProductoDTO(
		            producto.getReferencia(),
		            producto.getNombre(),
		            detalle.getCantidad(),
		            producto.getPrecio(),
		            detalle.getDescuento(),
		            detalle.getCantidad() * producto.getPrecio() - detalle.getDescuento()
		        );
		        this.productos.add(productoDTO);
		    }
		    Cajero ca= compra.getCajero();
		    this.cajero = new CajeroDTO(ca.getDocumento(), ca.getNombre());
		}
}
