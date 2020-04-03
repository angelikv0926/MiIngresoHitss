package Bean;

import Dao.PersonaDAO;
import Modelo.Persona;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Viviana
 */
@ManagedBean
@SessionScoped
public class PersonaBean {
    
    private Persona persona = new Persona();
    private List<Persona> listaPersona;
    private String accion;

    public PersonaBean() throws Exception {
        listarPersona();
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Persona> getListaPersona() {
        return listaPersona;
    }

    public void setListaPersona(List<Persona> listaPersona) {
        this.listaPersona = listaPersona;
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
                this.registrarPersona();
                this.limpiarCampos();
                break;
            case "Modificar":
                this.modificarPersona();
                this.limpiarCampos();
                break;
        }
    }
    
    public void limpiarCampos(){
        this.persona.setId_persona(0);
        this.persona.setNombres("");
        this.persona.setApellidos("");
        this.persona.setIdentificacion("");
        this.persona.setSexo("");
        this.persona.setUsuario("");
        this.persona.setClave("");
    }
    
    private void registrarPersona() throws Exception{
        PersonaDAO perDao;
        
        try {
            perDao = new PersonaDAO();
            perDao.registrar(persona);
            this.listarPersona();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void listarPersona() throws Exception{
        PersonaDAO perDao;
        
        try {
            perDao = new PersonaDAO();
            listaPersona = perDao.listar();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void consultarById(Persona pers) throws Exception{
        PersonaDAO perDao;
        Persona temp;
        
        try {
            perDao = new PersonaDAO();
            temp = perDao.consultarById(pers);
            if (temp != null){
                this.persona = temp;
                this.accion = "Modificar";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void modificarPersona() throws Exception{
        PersonaDAO perDao;
        
        try {
            perDao = new PersonaDAO();
            perDao.modificar(persona);
            this.listarPersona();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void eliminarPersona(Persona pers) throws Exception{
        PersonaDAO perDao;
        
        try {
            perDao = new PersonaDAO();
            perDao.eliminar(pers);
            this.listarPersona();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public String iniciarSesion() throws Exception{
        PersonaDAO perDao;
        Persona temp;
        String resultado;
        
        try {
            perDao = new PersonaDAO();
            temp = perDao.iniciarSesion(persona);
            if (temp != null){
                this.persona.setRol(temp.getRol());
                resultado = "Inicio?faces-redirect=true";
            } else {
                resultado = "index?faces-redirect=true";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inicio Sesion", "Usuario o Clave Incorrectos"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
        return resultado;
    }
}
