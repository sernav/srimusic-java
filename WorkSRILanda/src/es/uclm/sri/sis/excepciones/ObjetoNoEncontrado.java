package es.uclm.sri.sis.excepciones;

public class ObjetoNoEncontrado extends ExcepcionGeneral {

    private static final long serialVersionUID = 1L;

    public ObjetoNoEncontrado(String codigoError, String mensaje) {
        super(codigoError, mensaje);
    }

    public ObjetoNoEncontrado(String mensaje) {
        super(new String(ObjetoNoEncontrado.class.getName()), mensaje);
    }

    public ObjetoNoEncontrado(Throwable e) {
        super(e);
    }

}
