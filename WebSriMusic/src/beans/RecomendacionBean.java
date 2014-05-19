package beans;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import logica.RecomendacionesByLastfm;

@ManagedBean(name = "recomendacionesBean")
@ApplicationScoped
public class RecomendacionBean implements Serializable {
    
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public void analizarUserLastfm() {
        
    }
    
    public void generarRecomendaciones() {
        RecomendacionesByLastfm.generarRecomendacionesByLastfm(getUserName());
    }

}
