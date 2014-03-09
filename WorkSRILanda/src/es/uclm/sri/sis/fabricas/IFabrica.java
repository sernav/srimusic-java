package es.uclm.sri.sis.fabricas;

import java.util.HashMap;

public interface IFabrica {
    
    public void run();
    
    public void aplicarSistemaDReglas();
    
    public HashMap<Integer, String> getAvisosDSistema();

}
