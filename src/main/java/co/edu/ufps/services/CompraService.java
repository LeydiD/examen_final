package co.edu.ufps.services;

import co.edu.ufps.dto.*;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.TipoDocumento;
import co.edu.ufps.entities.TipoPago;
import co.edu.ufps.entities.Vendedor;
import co.edu.ufps.entities.Cajero;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Producto;
import co.edu.ufps.entities.Pago;
import co.edu.ufps.entities.DetallesCompra;
import co.edu.ufps.repositories.CajeroRepository;
import co.edu.ufps.repositories.ClienteRepository;
import co.edu.ufps.repositories.CompraRepository;
import co.edu.ufps.repositories.DetallesCompraRepository;
import co.edu.ufps.repositories.PagoRepository;
import co.edu.ufps.repositories.ProductoRepository;
import co.edu.ufps.repositories.TiendaRepository;
import co.edu.ufps.repositories.TipoDocumentoRepository;
import co.edu.ufps.repositories.TipoPagoRepository;
import co.edu.ufps.repositories.VendedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private DetallesCompraRepository detallesCompraRepository;

	@Autowired
	private PagoRepository pagoRepository;

	@Autowired
	private TiendaRepository tiendaRepository;

	@Autowired
	private VendedorRepository vendedorRepository;

	@Autowired
	private CajeroRepository cajeroRepository;

	@Autowired
	private TipoPagoRepository tipoPagoRepository;

	@Autowired
	private TipoDocumentoRepository tipoDocumentoRepository;

	public List<Compra> list() {
		return compraRepository.findAll();
	}

	public Compra create(Compra compra) {
		return compraRepository.save(compra);
	}

	public Optional<Compra> update(Integer id, Compra compraDetails) {
		Optional<Compra> optionalCompra = compraRepository.findById(id);
		if (!optionalCompra.isPresent()) {
			return Optional.empty();
		}

		Compra compra = optionalCompra.get();
		compra.setFecha(compraDetails.getFecha());
		compra.setImpuestos(compraDetails.getImpuestos());
		compra.setCliente(compraDetails.getCliente());

		return Optional.of(compraRepository.save(compra));
	}

	public boolean delete(Integer id) {
		Optional<Compra> optionalCompra = compraRepository.findById(id);
		if (!optionalCompra.isPresent()) {
			return false;
		}
		compraRepository.delete(optionalCompra.get());
		return true;
	}

	public CompraResponseDTO crearCompra(String tiendaUuid, CompraRequestDTO compraRequest) {
		// Validar que los datos de la compra sean correctos
		validarEntrada(compraRequest);

		// Obtener la tienda
		Tienda tienda = tiendaRepository.findByUuid(tiendaUuid)
				.orElseThrow(() -> new RuntimeException("La tienda no existe"));

		// Validar y registrar el cliente
		TipoDocumento tipoDocumentoCliente = tipoDocumentoRepository
				.findByNombre(compraRequest.getCliente().getTipo_documento())
				.orElseThrow(() -> new RuntimeException("No se encontró el tipo de documento"));

		Cliente cliente = clienteRepository
				.findByDocumentoAndTipoDocumento(compraRequest.getCliente().getDocumento(), tipoDocumentoCliente)
				.orElseGet(() -> registrarNuevoCliente(compraRequest.getCliente()));

		// Validar vendedor
		Vendedor vendedor = vendedorRepository.findByDocumento(compraRequest.getVendedor().getDocumento())
				.orElseThrow(() -> new RuntimeException("El vendedor no existe en la tienda"));

		// Validar cajero
		Cajero cajero = cajeroRepository.findByToken(compraRequest.getCajero().getToken())
				.orElseThrow(() -> new RuntimeException("El token no corresponde a ningún cajero en la tienda"));

		if (!cajero.getTienda().getId().equals(tienda.getId())) {
			throw new RuntimeException("El cajero no está asignado a esta tienda");
		}

		// Crear la compra
		Compra compra = new Compra();

		compra.setImpuestos(compraRequest.getImpuesto());
		compra.setCliente(cliente);
		compra.setTienda(tienda);
		compra.setVendedor(vendedor);
		compra.setCajero(cajero);
		compra.setFecha(LocalDate.now());
		List<Producto> productos = validarYObtenerProductos(compraRequest.getProductos());

		compra = compraRepository.save(compra);
		List<DetallesCompra> detallesCompra = crearDetallesCompra(compra, compraRequest.getProductos());
		compra.setDetallesCompra(detallesCompra);

		Double totalPagos = calcularTotalMediosPago(compraRequest.getMedios_pago());
		Double totalFactura = calcularTotalFactura(detallesCompra, compraRequest.getImpuesto());

		if (!totalPagos.equals(totalFactura)) {
			throw new RuntimeException("El valor de la factura no coincide con el valor total de los pagos");
		}

		registrarPagos(compraRequest.getMedios_pago(), compra);
		compra.setTotal(totalFactura);

		compraRepository.save(compra);
		return crearRespuesta(compra);
	}

	private Cliente registrarNuevoCliente(ClienteDTO clienteDTO) {
		Cliente cliente = new Cliente();
		cliente.setDocumento(clienteDTO.getDocumento());
		cliente.setNombre(clienteDTO.getNombre());
		TipoDocumento tipoDocumento = tipoDocumentoRepository.findByNombre(clienteDTO.getTipo_documento())
				.orElseThrow(() -> new RuntimeException("El tipo de documento no existe"));
		cliente.setTipoDocumento(tipoDocumento);
		return clienteRepository.save(cliente);
	}

	private void validarEntrada(CompraRequestDTO compraRequest) {
		if (compraRequest.getCliente() == null) {
			throw new RuntimeException("No hay información del cliente");
		}
		if (compraRequest.getProductos().isEmpty()) {
			throw new RuntimeException("No hay productos asignados para esta compra");
		}
		if (compraRequest.getMedios_pago().isEmpty()) {
			throw new RuntimeException("No hay medios de pagos asignados para esta compra");
		}
		if (compraRequest.getVendedor() == null) {
			throw new RuntimeException("No hay información del vendedor");
		}
		if (compraRequest.getCajero() == null) {
			throw new RuntimeException("No hay información del cajero");
		}
	}

	private List<Producto> validarYObtenerProductos(List<ProductoDTO> productosDTO) {
		List<Producto> productos = new ArrayList<>();
		for (ProductoDTO productoDTO : productosDTO) {
			Producto producto = productoRepository.findByReferencia(productoDTO.getReferencia())
					.orElseThrow(() -> new RuntimeException("La referencia del producto " + productoDTO.getReferencia()
							+ " no existe, por favor revisar los datos"));
			if (productoDTO.getCantidad() > producto.getCantidad()) {
				throw new RuntimeException("La cantidad a comprar supera el máximo del producto en tienda");
			}
			productos.add(producto);
		}
		return productos;
	}

	private List<DetallesCompra> crearDetallesCompra(Compra compra, List<ProductoDTO> productosDTO) {
		List<DetallesCompra> detallesCompra = new ArrayList<>();
		for (ProductoDTO productoDTO : productosDTO) {
			Producto producto = productoRepository.findByReferencia(productoDTO.getReferencia())
					.orElseThrow(() -> new RuntimeException("La referencia del producto " + productoDTO.getReferencia()
							+ " no existe, por favor revisar los datos"));
			DetallesCompra detalle = new DetallesCompra();
			detalle.setCompra(compra);
			detalle.setProducto(producto);
			detalle.setCantidad(productoDTO.getCantidad());
			detalle.setDescuento(productoDTO.getDescuento());
			detallesCompra.add(detalle);
		}
		detallesCompraRepository.saveAll(detallesCompra);
		return detallesCompra;

	}

	private Double calcularTotalFactura(List<DetallesCompra> detallesCompra, Double impuesto) {
		double total = 0;
		for (DetallesCompra detalle : detallesCompra) {
			double precio = detalle.getProducto().getPrecio();
			int cantidad = detalle.getCantidad();
			double descuento = detalle.getDescuento();
			total += precio * cantidad * (1 - descuento / 100);
		}
		Double totalImpuesto = total * (impuesto / 100);
		return total + totalImpuesto;
	}

	private Double calcularTotalMediosPago(List<MedioPagoDTO> mediosPago) {
		double total = 0;
		for (MedioPagoDTO pago : mediosPago) {
			total += Double.parseDouble(pago.getValor());
		}
		return total;
	}

	private void registrarPagos(List<MedioPagoDTO> mediosPago, Compra compra) {
		for (MedioPagoDTO pagoDTO : mediosPago) {
			TipoPago tipoPago = tipoPagoRepository.findByNombre(pagoDTO.getTipo_pago())
					.orElseThrow(() -> new RuntimeException("Tipo de pago no permitido en la tienda"));
			Pago pago = new Pago();
			pago.setTipoPago(tipoPago);
			pago.setCuotas(pagoDTO.getCuotas());
			pago.setTarjetaTipo(pagoDTO.getTipo_tarjeta());
			pago.setValor(Double.parseDouble(pagoDTO.getValor()));
			pago.setCompra(compra);
			pagoRepository.save(pago);
		}
	}

	private CompraResponseDTO crearRespuesta(Compra compra) {
		CompraResponseDTO response = new CompraResponseDTO();
		response.setStatus("success");
		response.setMessage("La compra se ha creado correctamente con el número: " + compra.getId());

		CompraDataDTO data = new CompraDataDTO();
		data.setNumero(compra.getId().toString());
		data.setTotal(String.valueOf(compra.getTotal()));
		data.setFecha(compra.getFecha().toString());
		response.setData(data);

		return response;
	}

	public FacturaResponseDTO consultarFactura(String tiendaUuid, ConsultaFacturaRequestDTO consultaRequest) {
		Tienda tienda = tiendaRepository.findByUuid(tiendaUuid)
				.orElseThrow(() -> new RuntimeException("La tienda no existe"));
		
		Cajero cajero = cajeroRepository.findByToken(consultaRequest.getToken())
				.orElseThrow(() -> new RuntimeException("Cajero no encontrado"));
		
		if(!cajero.getTienda().equals(tienda)) {
			System.out.println("Cajero sin permiso");
			throw new RuntimeException("El cajero no pertence a esa tienda");
		}

		Compra compra = compraRepository.findById(consultaRequest.getFactura())
				.orElseThrow(() -> new RuntimeException("Factura no encontrada"));

		if (!compra.getCajero().equals(cajero)) {
			System.out.println("Cajero sin permiso");
			throw new RuntimeException("El cajero no tiene permiso para consultar esta factura");
		}

		// Validar que el cliente esté asociado a la factura
		Cliente cliente = clienteRepository.findByDocumentoAndTipoDocumento(consultaRequest.getCliente(), compra.getCliente().getTipoDocumento())
				.orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

		if (!compra.getCliente().equals(cliente)) {
			throw new RuntimeException("El cliente no está asociado con esta factura");
		}
		return new FacturaResponseDTO(compra);
		
	}
}
