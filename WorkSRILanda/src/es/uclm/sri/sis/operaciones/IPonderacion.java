package es.uclm.sri.sis.operaciones;

import java.util.ArrayList;

import es.uclm.sri.sis.entidades.AlbumPonderado;
import es.uclm.sri.sis.entidades.Genero;

public interface IPonderacion {
    
    public AlbumPonderado procesar();
    
    public void calcularPesos();
    
    public ArrayList<Genero> convertirTags();

}
