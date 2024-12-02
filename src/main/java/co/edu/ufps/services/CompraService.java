package co.edu.ufps.services;

import co.edu.ufps.dto.*;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.Vendedor;
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
import co.edu.ufps.repositories.VendedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    
    
    public CompraResponseDTO crearCompra(String tiendaId, CompraRequestDTO compraRequest) {
        // Validar que los datos de la compra sean correctos
        validarEntrada(compraRequest);

        // Obtener la tienda
        Tienda tienda = tiendaRepository.findByUuid(tiendaId)
                .orElseThrow(() -> new RuntimeException("La tienda no existe"));

        // Validar y registrar el cliente
        Cliente cliente = clienteRepository.findByDocumentoAndTipoDocumento(
                compraRequest.getCliente().getDocumento(),
                compraRequest.getCliente().getTipoDocumento()
        ).orElseGet(() -> registrarNuevoCliente(compraRequest.getCliente()));

        // Validar vendedor
        Vendedor vendedor = vendedorRepository.findByDocumento(compraRequest.getVendedor().getDocumento())
                .orElseThrow(() -> new RuntimeException("El vendedor no existe en la tienda"));

        // Validar cajero
        Cajero cajero = cajeroRepository.findByToken(compraRequest.getCajero().getToken(), tiendaId)
                .orElseThrow(() -> new RuntimeException("El token no corresponde a ningún cajero en la tienda"));

        // Crear la compra
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setTienda(tienda);
        compra.setVendedor(vendedor);
        compra.setCajero(cajero);
        compra.setFecha(LocalDate.now());
        
        // Agregar productos a la compra
        List<Producto> productos = validarYObtenerProductos(compraRequest.getProductos());
        
        // Guardar los detalles de la compra
        List<DetallesCompra> detallesCompra = crearDetallesCompra(compra, productos);
        compra.setDetallesCompra(detallesCompra);
        
        // Calcular total de la compra
        Double total = calcularTotal(detallesCompra);
        compra.setTotal(total);
        
        // Calcular impuestos
        Double impuestos = calcularImpuestos(total);
        compra.setImpuestos(impuestos.intValue());

        // Guardar la compra en la base de datos
        compraRepository.save(compra);

        // Registrar pagos
        registrarPagos(compraRequest.getMediosPago(), compra);

        // Retornar la respuesta de la compra
        return crearRespuesta(compra);
    }

    private Cliente registrarNuevoCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setDocumento(clienteDTO.getDocumento());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setTipoDocumento(clienteDTO.getTipoDocumento());
        return clienteRepository.save(cliente);
    }

    private void validarEntrada(CompraRequestDTO compraRequest) {
        if (compraRequest.getCliente() == null) {
            throw new RuntimeException("No hay información del cliente");
        }
        if (compraRequest.getProductos().isEmpty()) {
            throw new RuntimeException("No hay productos asignados para esta compra");
        }
        if (compraRequest.getMediosPago().isEmpty()) {
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
                    .orElseThrow(() -> new RuntimeException("La referencia del producto " + productoDTO.getReferencia() + " no existe, por favor revisar los datos"));
            // Validar que la cantidad no supere el stock disponible
            if (productoDTO.getCantidad() > producto.getCantidad()) {
                throw new RuntimeException("La cantidad a comprar supera el máximo del producto en tienda");
            }
            productos.add(producto);
        }
        return productos;
    }

    private List<DetallesCompra> crearDetallesCompra(Compra compra, List<Producto> productos) {
        List<DetallesCompra> detallesCompra = new ArrayList<>();
        for (Producto producto : productos) {
            DetallesCompra detalle = new DetallesCompra();
            detalle.setCompra(compra);
            detalle.setProducto(producto);
            detalle.setCantidad(1); // Asumiendo que por ahora es cantidad 1 por producto
            detallesCompra.add(detalle);
        }
        detallesCompraRepository.saveAll(detallesCompra);
        return detallesCompra;
    }

    private Double calcularTotal(List<DetallesCompra> detallesCompra) {
        double total = 0;
        for (DetallesCompra detalle : detallesCompra) {
            total += detalle.getProducto().getPrecio() * detalle.getCantidad();
        }
        return total;
    }

    private Double calcularImpuestos(Double total) {
        return total * 0.19; // Asumiendo un impuesto del 19%
    }

    private void registrarPagos(List<MedioPagoDTO> mediosPago, Compra compra) {
        for (MedioPagoDTO pagoDTO : mediosPago) {
            TipoPago tipoPago = tipoPagoRepository.findByNombre(pagoDTO.getTipoPago())
                    .orElseThrow(() -> new RuntimeException("Tipo de pago no permitido en la tienda"));
            Pago pago = new Pago();
            pago.setTipoPago(tipoPago);
            pago.setValor(pagoDTO.getValor());
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

}
