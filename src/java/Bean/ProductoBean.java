package Bean;

import Dao.ProductoDAO;
import Modelo.Producto;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Viviana
 */
@ManagedBean
@ViewScoped
public class ProductoBean {
    
    private Producto producto = new Producto();
    private List<Producto> listaProducto;
    private String accion;
    
    public ProductoBean() throws Exception {
        listarProducto();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.limpiarCampos();
        this.accion = accion;
    }
    
    public void evaluarAccion() throws Exception{
        switch (accion){
            case "Registrar":
                this.registrarProducto();
                this.limpiarCampos();
                break;
            case "Modificar":
                this.modificarProducto();
                this.limpiarCampos();
                break;
        }
    }
    
    public void limpiarCampos(){
        this.producto.setId_producto(0);
        this.producto.setNombre("");
        this.producto.setPrecio(0);
    }
    
    private void registrarProducto() throws Exception{
        ProductoDAO prodDao;
        
        try {
            prodDao = new ProductoDAO();
            prodDao.registrar(producto);
            this.listarProducto();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void listarProducto() throws Exception{
        ProductoDAO prodDao;
        
        try {
            prodDao = new ProductoDAO();
            listaProducto = prodDao.listar();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void consultarById(Producto prod) throws Exception{
        ProductoDAO prodDao;
        Producto temp;
        
        try {
            prodDao = new ProductoDAO();
            temp = prodDao.consultarById(prod);
            if (temp != null){
                this.producto = temp;
                this.accion = "Modificar";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void modificarProducto() throws Exception{
        ProductoDAO prodDao;
        
        try {
            prodDao = new ProductoDAO();
            prodDao.modificar(producto);
            this.listarProducto();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void eliminarProducto(Producto prod) throws Exception{
        ProductoDAO prodDao;
        
        try {
            prodDao = new ProductoDAO();
            prodDao.eliminar(prod);
            this.listarProducto();
        } catch (Exception e) {
            throw e;
        }
    }
    
}
