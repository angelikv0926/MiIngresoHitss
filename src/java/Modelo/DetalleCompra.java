package Modelo;

/**
 * @author Viviana
 */
public class DetalleCompra {
    
    private Compra id_compra;
    private Producto id_producto;
    private int cantidad;

    public Compra getId_compra() {
        return id_compra;
    }

    public void setId_compra(Compra id_compra) {
        this.id_compra = id_compra;
    }

    public Producto getId_producto() {
        return id_producto;
    }

    public void setId_producto(Producto id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
