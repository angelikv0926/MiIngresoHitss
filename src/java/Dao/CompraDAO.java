package Dao;

import Utils.Conexion;
import Modelo.Compra;
import Modelo.DetalleCompra;
import Modelo.Reporte;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Viviana
 */
public class CompraDAO extends Conexion{
    
    public void registrar(Compra compra, List<DetalleCompra> listaDetalle) throws Exception{
        try {
            this.conectar();
            this.getCon().setAutoCommit(false);
            
            String sql = "INSERT INTO compra (id_persona, fecha, valor_compra) VALUES (?, ?, ?)";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            st.setInt(1, compra.getId_persona().getId_persona());
            st.setDate(2, compra.getFecha());
            st.setDouble(3, compra.getValor());
            st.executeUpdate();
            st.close();
            
            PreparedStatement st2 = this.getCon().prepareStatement("SELECT MAX(id_compra) FROM compra");
            int idCompra = 0;
            ResultSet rs;
            rs = st2.executeQuery();
            while (rs.next()) {
                idCompra = rs.getInt(1);
            }
            rs.close();
            
            for (DetalleCompra detalle : listaDetalle) {
                String sql2 = "INSERT INTO detalle_compra (id_compra, id_producto, cantidad) VALUES (?, ?, ?)";
                PreparedStatement st3 = this.getCon().prepareStatement(sql2);

                st3.setInt(1, idCompra);
                st3.setInt(2, detalle.getId_producto().getId_producto());
                st3.setInt(3, detalle.getCantidad());
                st3.executeUpdate();
                st3.close();
            }
            
            this.getCon().commit();
        } catch (Exception e) {
            this.getCon().rollback();
            throw e;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<Reporte> listarReporte(Date fechaI, Date fechaF) throws Exception{
        List<Reporte> listaReporte;
        ResultSet rs;
        
        try {
            this.conectar();
            String sql = "SELECT CONCAT(pe.nombres, ' ', pe.apellidos) AS nombreCliente, p.nombre, dc.cantidad, c.valor_compra, c.fecha FROM compra c\n" +
                            "INNER JOIN detalle_compra dc ON c.id_compra = dc.id_compra\n" +
                            "INNER JOIN producto p ON p.id_producto = dc.id_producto\n" +
                            "INNER JOIN persona pe ON pe.id_persona = c.id_persona\n" +
                            "WHERE c.fecha BETWEEN ? AND ?\n" +
                            "AND pe.estado = 1;";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            java.sql.Date fechaInicio = new java.sql.Date(fechaI.getTime());
            java.sql.Date fechaFinal = new java.sql.Date(fechaF.getTime());
            
            st.setDate(1, fechaInicio);
            st.setDate(2, fechaFinal);
            
            rs = st.executeQuery();
            listaReporte = new ArrayList();
            while(rs.next()){
                Reporte reporte = new Reporte();
                reporte.setNombreCliente(rs.getString("nombreCliente"));
                reporte.setNombreProducto(rs.getString("nombre"));
                reporte.setCantidad(rs.getInt("cantidad"));
                reporte.setValorTotal(rs.getInt("valor_compra"));
                reporte.setFechaCompra(rs.getDate("fecha"));
                
                listaReporte.add(reporte);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return listaReporte;
    }
    
}
