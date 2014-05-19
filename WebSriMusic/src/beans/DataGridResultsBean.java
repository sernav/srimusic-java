package beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import es.uclm.sri.sis.entidades.Recomendacion;

@ManagedBean
@ViewScoped
public class DataGridResultsBean {
    
    private List<Recomendacion> recomendaciones;
    
    private Recomendacion recomendacion;
     
//    @ManagedProperty("#{carService}")
//    private CarService service;
     
//    @PostConstruct
//    public void init() {
//        cars = service.createCars(48);
//    }
 
    public List<Recomendacion> getRecomendaciones() {
        return recomendaciones;
    }

 
    public Recomendacion getSelectedRecomendacion() {
        return recomendacion;
    }
 
    public void setSelectedCar(Recomendacion recomendacion) {
        this.recomendacion = recomendacion;
    }

}
