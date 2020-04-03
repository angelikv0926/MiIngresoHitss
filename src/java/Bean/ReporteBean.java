package Bean;

import Dao.CompraDAO;
import Modelo.Reporte;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @author Viviana
 */

@ManagedBean
@ViewScoped
public class ReporteBean {
    
    private Date fechaI;
    private Date fechaF;
    private List<Reporte> listaReporte;

    public Date getFechaI() {
        return fechaI;
    }

    public void setFechaI(Date fechaI) {
        this.fechaI = fechaI;
    }

    public Date getFechaF() {
        return fechaF;
    }

    public void setFechaF(Date fechaF) {
        this.fechaF = fechaF;
    }

    public List<Reporte> getListaReporte() {
        return listaReporte;
    }

    public void setListaReporte(List<Reporte> listaReporte) {
        this.listaReporte = listaReporte;
    }
    
    public void consultarCompras() throws Exception{
        CompraDAO compDao;
        
        try {
            compDao = new CompraDAO();
            listaReporte = compDao.listarReporte(fechaI, fechaF);
        } catch (Exception e) {
            throw e;
        }
    }
    
}
