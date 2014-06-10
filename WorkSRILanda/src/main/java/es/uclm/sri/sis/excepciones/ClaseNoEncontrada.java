package main.java.es.uclm.sri.sis.excepciones;

/**
 * Excepción de "Clase no encontrada". Extiende de <code>ExcepcionGeneral</code>
 * 
 * @author Sergio Navarro
 * */
public class ClaseNoEncontrada extends ExcepcionGeneral {
    
    private static final long serialVersionUID = 1L;

    public ClaseNoEncontrada(String codigoError, String mensaje) {
        super(codigoError, mensaje);
    }

    public ClaseNoEncontrada(String mensaje) {
        super(new String(ClaseNoEncontrada.class.getName()), mensaje);
    }
    
    public ClaseNoEncontrada(Throwable e) {
        super(e);
    }

}
