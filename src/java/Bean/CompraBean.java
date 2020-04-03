package Bean;

import Dao.CompraDAO;
import Modelo.Compra;
import Modelo.DetalleCompra;
import Modelo.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * @author Viviana
 */

@ManagedBean
@ViewScoped
public class CompraBean {
    
    private Compra compra = new Compra();
    private Producto producto = new Producto();
    private int cantidad;
    private List<DetalleCompra> listaCompraD = new ArrayList();

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<DetalleCompra> getListaCompraD() {
        return listaCompraD;
    }

    public void setListaCompraD(List<DetalleCompra> listaCompraD) {
        this.listaCompraD = listaCompraD;
    }
    
    public void agregarDetalle(){
        DetalleCompra detComp = new DetalleCompra();
        
        detComp.setCantidad(cantidad);
        detComp.setId_producto(producto);
        this.listaCompraD.add(detComp);
    }
    
    public void registrarCompra() throws Exception{
        CompraDAO comDao;
        
        double valorCompra = 0;
        try {
            for(DetalleCompra detalle : listaCompraD) {
                valorCompra += detalle.getId_producto().getPrecio() * detalle.getCantidad();
            }
            comDao = new CompraDAO();
            
            compra.setValor(valorCompra);
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sDate = new java.sql.Date(utilDate.getTime());
            compra.setFecha(sDate);
            
            comDao.registrar(compra, listaCompraD);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrar", "Compra Registrada con Exito"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registar", "Error"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
}
