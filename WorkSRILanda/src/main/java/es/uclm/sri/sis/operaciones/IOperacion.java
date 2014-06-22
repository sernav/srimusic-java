package es.uclm.sri.sis.operaciones;

import java.util.HashMap;

public interface IOperacion {
    
    public Object procesar();
    
    public void ordenar(String tipoOrden);
    
    public HashMap<Integer, String> getAvisosDSistema();

}
