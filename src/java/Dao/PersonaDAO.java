package Dao;

import Utils.Conexion;
import Modelo.Persona;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Viviana
 */
public class PersonaDAO extends Conexion{
    
    public void registrar(Persona persona) throws Exception{
        try {
            this.conectar();
            this.getCon().setAutoCommit(false);
            
            String sql = "INSERT INTO persona (nombres, apellidos, identificacion, sexo, estado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            st.setString(1, persona.getNombres());
            st.setString(2, persona.getApellidos());
            st.setString(3, persona.getIdentificacion());
            st.setString(4, persona.getSexo());
            st.setString(5, "1");
            st.executeUpdate();
            st.close();
            
            PreparedStatement st2 = this.getCon().prepareStatement("SELECT MAX(id_persona) FROM persona");
            int idPersona = 0;
            ResultSet rs;
            rs = st2.executeQuery();
            while (rs.next()) {
                idPersona = rs.getInt(1);
            }
            rs.close();
            
            String sql2 = "INSERT INTO usuario (id_persona, usuario, clave, id_rol, estado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st3 = this.getCon().prepareStatement(sql2);
            
            st3.setInt(1, idPersona);
            st3.setString(2, persona.getUsuario());
            st3.setString(3, persona.getClave());
            st3.setInt(4, 2);
            st3.setString(5, "1");
            st3.executeUpdate();
            st3.close();
            
            this.getCon().commit();
        } catch (Exception e) {
            this.getCon().rollback();
            throw e;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public List<Persona> listar() throws Exception{
        List<Persona> listaPersona;
        ResultSet rs;
        
        try {
            this.conectar();
            String sql = "SELECT p.id_persona, identificacion, nombres, apellidos, sexo, usuario, clave\n"
                            + "FROM persona p INNER JOIN usuario u ON p.id_persona = u.id_persona\n"
                            + "WHERE p.estado = 1 AND id_rol = 2";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            rs = st.executeQuery();
            listaPersona = new ArrayList();
            while(rs.next()){
                Persona persona = new Persona();
                persona.setId_persona(rs.getInt("id_persona"));
                persona.setIdentificacion(rs.getString("identificacion"));
                persona.setNombres(rs.getString("nombres"));
                persona.setApellidos(rs.getString("apellidos"));
                persona.setSexo(rs.getString("sexo"));
                persona.setUsuario(rs.getString("usuario"));
                
                listaPersona.add(persona);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return listaPersona;
    }
    
    public Persona consultarById(Persona pers) throws Exception{
        Persona persona = null;
        ResultSet rs;
        
        try {
            this.conectar();
            String sql = "SELECT p.id_persona, identificacion, nombres, apellidos, sexo, usuario, clave\n"
                            + "FROM persona p INNER JOIN usuario u ON p.id_persona = u.id_persona\n"
                            + "WHERE p.id_persona = ?";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            st.setInt(1, pers.getId_persona());
            rs = st.executeQuery();
            while(rs.next()){
                persona = new Persona();
                persona.setId_persona(rs.getInt("id_persona"));
                persona.setIdentificacion(rs.getString("identificacion"));
                persona.setNombres(rs.getString("nombres"));
                persona.setApellidos(rs.getString("apellidos"));
                persona.setSexo(rs.getString("sexo"));
                persona.setUsuario(rs.getString("usuario"));
                persona.setClave(rs.getString("clave"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return persona;
    }
    
    public void modificar(Persona persona) throws Exception{
        try {
            this.conectar();
            this.getCon().setAutoCommit(false);
            
            String sql = "UPDATE persona SET nombres = ?, apellidos = ? WHERE id_persona = ?";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            st.setString(1, persona.getNombres());
            st.setString(2, persona.getApellidos());
            st.setInt(3, persona.getId_persona());
            st.executeUpdate();
            st.close();
            
            String sql2 = "UPDATE usuario SET usuario = ?, clave = ? WHERE id_persona = ?";
            PreparedStatement st2 = this.getCon().prepareStatement(sql2);
            
            st2.setString(1, persona.getUsuario());
            st2.setString(2, persona.getClave());
            st2.setInt(3, persona.getId_persona());
            st2.executeUpdate();
            st2.close();
            
            this.getCon().commit();
        } catch (Exception e) {
            this.getCon().rollback();
            throw e;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public void eliminar(Persona persona) throws Exception{
        try {
            this.conectar();
            this.getCon().setAutoCommit(false);
            
            String sql = "UPDATE persona SET estado = 2 WHERE id_persona = ?";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            st.setInt(1, persona.getId_persona());
            st.executeUpdate();
            st.close();
            
            String sql2 = "UPDATE usuario SET estado = 2 WHERE id_persona = ?";
            PreparedStatement st2 = this.getCon().prepareStatement(sql2);
            
            st2.setInt(1, persona.getId_persona());
            st2.executeUpdate();
            st2.close();
            
            this.getCon().commit();
        } catch (Exception e) {
            this.getCon().rollback();
            throw e;
        } finally {
            this.cerrarConexion();
        }
    }
    
    public Persona iniciarSesion(Persona pers) throws Exception{
        Persona persona = null;
        ResultSet rs;
        
        try {
            this.conectar();
            String sql = "SELECT p.id_persona, id_rol FROM persona p INNER JOIN usuario u ON p.id_persona = u.id_persona\n"
                            + "WHERE usuario = ? AND clave = ? AND p.estado = 1 AND u.estado = 1";
            PreparedStatement st = this.getCon().prepareStatement(sql);
            
            st.setString(1, pers.getUsuario());
            st.setString(2, pers.getClave());
            
            rs = st.executeQuery();
            while(rs.next()){
                persona = new Persona();
                persona.setId_persona(rs.getInt("id_persona"));
                persona.setRol(rs.getInt("id_rol"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrarConexion();
        }
        return persona;
    }    
    
}
