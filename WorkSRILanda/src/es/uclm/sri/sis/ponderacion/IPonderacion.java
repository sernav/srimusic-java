package es.uclm.sri.sis.ponderacion;

import java.util.ArrayList;

import es.uclm.sri.sis.entidades.Genero;

public interface IPonderacion {
    
    public void procesar();
    
    public void calcularPesos();
    
    public ArrayList<Genero> convertirTags();
    
    public void ordenar(String tipoOrden);

}
