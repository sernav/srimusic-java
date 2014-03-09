package es.uclm.sri.sis.excepciones;

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
