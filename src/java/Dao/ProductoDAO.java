package Dao;

import Utils.Conexion;
import Modelo.Producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Viviana
 */
public class ProductoDAO extends Conexion{
    
    public void registrar(Producto producto) throws Exception{
        try {
            this.conectar();
            String sql = "INSERT INTO producto (nombre, precio) VALUES (?, ?)";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            st.setString(1, producto.getNombre());
            st.setDouble(2, producto.getPrecio());
            
            st.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<Producto> listar() throws Exception{
        List<Producto> listaProducto;
        ResultSet rs;
        
        try {
            this.conectar();
            String sql = "SELECT id_producto, nombre, precio FROM producto";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            rs = st.executeQuery();
            listaProducto = new ArrayList();
            while(rs.next()){
                Producto producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                
                listaProducto.add(producto);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return listaProducto;
    }
    
    public Producto consultarById(Producto prod) throws Exception{
        Producto producto = null;
        ResultSet rs;
        
        try {
            this.conectar();
            String sql = "SELECT id_producto, nombre, precio FROM producto WHERE id_producto = ?";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            st.setInt(1, prod.getId_producto());
            rs = st.executeQuery();
            while(rs.next()){
                producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return producto;
    }
    
    public void modificar(Producto producto) throws Exception{
        try {
            this.conectar();
            String sql = "UPDATE producto SET nombre = ?, precio = ? WHERE id_producto = ?";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            st.setString(1, producto.getNombre());
            st.setDouble(2, producto.getPrecio());
            st.setInt(3, producto.getId_producto());
            
            st.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void eliminar(Producto producto) throws Exception{
        try {
            this.conectar();
            String sql = "DELETE FROM producto WHERE id_producto = ?";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            st.setInt(1, producto.getId_producto());
            
            st.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrarConexion();
        }
    }
}
