/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.ClienteDAO;
import DAO.FacturaDAO;
import DAO.ProductoDAO;
import DAO.TipoLimpiezaDAO;
import Model.ClienteDTO;
import Model.DetalleFacturaDTO;
import Model.EmpleadoDTO;
import Model.FacturaDTO;
import Model.ProductoDTO;
import Model.TipoLimpiezaDTO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author aguilarje
 */
@Named(value = "homeMB")
@ViewScoped
public class HomeMB implements Serializable{
    
    private final HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    private EmpleadoDTO empleado;
    private static final long serialVersionUID = 1L;

    private String email;
    private ClienteDTO cliente;
    private ClienteDTO clienteNuevo;
    private boolean clienteExiste;
    
    private int tipoLimpiezaSeleccionado;
    private List<Integer> productosSeleccionados;

    private List<FacturaDTO> facturas;
    private List<TipoLimpiezaDTO> tiposDeLimpieza;
    private List<ProductoDTO> productos;
    
    private List<DetalleFacturaDTO> detalles;
    private DetalleFacturaDTO detalleActual;
    private DetalleFacturaDTO detalleSeleccionado;
    private FacturaDTO factura;
    
    private List<Integer> cantidadMax;

    private ClienteDAO clienteDAO;
    private FacturaDAO facturaDAO;
    private TipoLimpiezaDAO tipoLimpiezaDAO;
    private ProductoDAO productoDAO;
    
    private static final String SESSION_EMAIL_CLIENTE_VERIFICAR = "emailClienteVerificar";
    
    //@PostConstruct
    public void init() {
        clienteDAO = new ClienteDAO();
        facturaDAO = new FacturaDAO();
        tipoLimpiezaDAO = new TipoLimpiezaDAO();
        productoDAO = new ProductoDAO();
        detalles = new ArrayList<>();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        //HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) {
            empleado = (EmpleadoDTO) session.getAttribute("empleado");
            if (empleado == null) {
                try {
                    facesContext.getExternalContext().redirect("login.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            email = (String) session.getAttribute(SESSION_EMAIL_CLIENTE_VERIFICAR);
            if(email == null){
                email = "";
            }
        }
        try{
            tiposDeLimpieza = tipoLimpiezaDAO.obtenerTiposDeLimpieza();
            productos = productoDAO.obtenerProductos();
            facturas = facturaDAO.obtenerFacturas();
            cantidadMax = new ArrayList<>();
            for (int i = 1; i <= 30; i++) {
                cantidadMax.add(i);
            }
        } catch (Exception ex){
            
        }
    }
    
    public void verificarCliente() throws ClassNotFoundException, SQLException {
        clienteExiste = clienteDAO.verificarClientePorEmail(email);
        if (clienteExiste) {
            cliente = clienteDAO.obtenerClientePorEmail(email);
            factura = new FacturaDTO(0,cliente.getClienteId(), empleado.getEmpleadoId(), new Date(), 0.0);
            prepararNuevoDetalle();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente encontrado", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Cliente no encontrado, registre un nuevo cliente", null));
        }
    }

    public void registrarCliente() throws ClassNotFoundException {
        clienteDAO.registrarCliente(clienteNuevo);
        cliente = clienteNuevo;
        clienteExiste = true;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente registrado exitosamente", null));
    }

    public void generarFactura() throws ClassNotFoundException {
        FacturaDTO factura = new FacturaDTO(0, cliente.getClienteId(), empleado.getEmpleadoId(), new Date(),0.0);
        
        //facturaDAO.crearFactura(factura);
        facturas.add(factura);
        
        
    }
    
    // Preparar un nuevo detalle para el modal
    public void prepararNuevoDetalle() {
        detalleActual = new DetalleFacturaDTO(); // Resetear el detalle actual
        detalleSeleccionado = null; // Resetear la selección
    }

    // Preparar un detalle para editar
    public void prepararEdicionDetalle(DetalleFacturaDTO detalle) {
        detalleSeleccionado = detalle;
        detalleActual = detalle; // Copiar el detalle seleccionado para editar
        RequestContext.getCurrentInstance().execute("PF('detalleDialog').show();");
    }

    // Agregar o actualizar el detalle en la lista
    public void agregarOActualizarDetalle() {
        if (detalleSeleccionado == null) {
            detalles.add(detalleActual); // Agregar nuevo detalle
        } else {
            int index = detalles.indexOf(detalleSeleccionado);
            detalles.set(index, detalleActual); // Actualizar detalle existente
        }
        detalleActual = new DetalleFacturaDTO(); // Resetear el detalle actual
        detalleSeleccionado = null; // Resetear la selección
        //RequestContext.getCurrentInstance().execute("PF('detalleDialog').hide();"); // Cerrar el modal
    }

    // Eliminar un detalle de la lista
    public void eliminarDetalle(DetalleFacturaDTO detalle) {
        detalles.remove(detalle);
    }

    // Crear la factura con sus detalles
    public void crearFactura() throws ClassNotFoundException {
        int facturaId = facturaDAO.crearFactura(factura, detalles);
        if (facturaId != -1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Factura generada exitosamente", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un error al generar la factura", null));
        }
    }

    public void validarFactura(FacturaDTO factura) throws ClassNotFoundException {
        facturaDAO.validarFactura(factura.getFacturaId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Factura validada", null));
    }
    
    public void guardaEmailClienteVerificar(){
        agregarElmentoSession(SESSION_EMAIL_CLIENTE_VERIFICAR);
    }
    
    private void agregarElmentoSession(String variableSession){
        if (session != null){
            switch(variableSession){
                case SESSION_EMAIL_CLIENTE_VERIFICAR:
                    if(email != null || !email.equals("")){
                        session.setAttribute(SESSION_EMAIL_CLIENTE_VERIFICAR, email);
                    }
                break;
                default:
                break;
            }
        }
    }
    
    public void calcularSubtotal(){
        double precio = obtenerPrecioProducto(detalleActual.getProductoId());
        double cantidad = detalleActual.getCantidad();
        detalleActual.setSubtotal(precio * cantidad);
    }
    
    private Double obtenerPrecioProducto(int idProducto){
        double precio = 0;
        for(ProductoDTO p : productos){
            if(p.getProductoId() == idProducto){
                precio = p.getPrecio();
            }
        }
        return precio;
    }
    
    public void calcularTotal(){
        double total = 0;
        for(DetalleFacturaDTO d : detalles){
            total = total + d.getSubtotal();
        }
        factura.setTotal(total);
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public ClienteDTO getClienteNuevo() {
        return clienteNuevo;
    }

    public void setClienteNuevo(ClienteDTO clienteNuevo) {
        this.clienteNuevo = clienteNuevo;
    }

    public boolean isClienteExiste() {
        return clienteExiste;
    }

    public void setClienteExiste(boolean clienteExiste) {
        this.clienteExiste = clienteExiste;
    }

    public int getTipoLimpiezaSeleccionado() {
        return tipoLimpiezaSeleccionado;
    }

    public void setTipoLimpiezaSeleccionado(int tipoLimpiezaSeleccionado) {
        this.tipoLimpiezaSeleccionado = tipoLimpiezaSeleccionado;
    }

    public List<Integer> getProductosSeleccionados() {
        return productosSeleccionados;
    }

    public void setProductosSeleccionados(List<Integer> productosSeleccionados) {
        this.productosSeleccionados = productosSeleccionados;
    }

    public List<FacturaDTO> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturaDTO> facturas) {
        this.facturas = facturas;
    }

    public List<TipoLimpiezaDTO> getTiposDeLimpieza() {
        return tiposDeLimpieza;
    }

    public void setTiposDeLimpieza(List<TipoLimpiezaDTO> tiposDeLimpieza) {
        this.tiposDeLimpieza = tiposDeLimpieza;
    }

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    public List<DetalleFacturaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFacturaDTO> detalles) {
        this.detalles = detalles;
    }

    public DetalleFacturaDTO getDetalleActual() {
        return detalleActual;
    }

    public void setDetalleActual(DetalleFacturaDTO detalleActual) {
        this.detalleActual = detalleActual;
    }

    public DetalleFacturaDTO getDetalleSeleccionado() {
        return detalleSeleccionado;
    }

    public void setDetalleSeleccionado(DetalleFacturaDTO detalleSeleccionado) {
        this.detalleSeleccionado = detalleSeleccionado;
    }

    public FacturaDTO getFactura() {
        return factura;
    }

    public void setFactura(FacturaDTO factura) {
        this.factura = factura;
    }

    public List<Integer> getCantidadMax() {
        return cantidadMax;
    }

    public void setCantidadMax(List<Integer> cantidadMax) {
        this.cantidadMax = cantidadMax;
    }
    
}
