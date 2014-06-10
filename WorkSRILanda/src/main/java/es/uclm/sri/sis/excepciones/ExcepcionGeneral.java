package main.java.es.uclm.sri.sis.excepciones;

import main.java.es.uclm.sri.sis.log.Log;

/**
 * Personalización de <code>Exception</code>
 * 
 * @author Sergio Navarro
 * */
public class ExcepcionGeneral extends Exception {

    private static final long serialVersionUID = 1L;

    private Throwable exception;
    private String codigoError;
    private String mensajeError;
    private String mensajeHtmlError;

    public ExcepcionGeneral(String codigoError, String mensajeError) {
        super();
        setCodigoError(codigoError);
        setMensajeError(mensajeError);
    }

    public ExcepcionGeneral(Throwable e) {
        super();
        this.codigoError = e.toString();
        this.mensajeError = e.getMessage();
        this.exception = e;
        Log.log(e, this.mensajeError);
    }
    
    public ExcepcionGeneral(Throwable e, String mensaje) {
        super();
        this.codigoError = e.toString();
        this.mensajeError = mensaje;
        this.exception = e;
        Log.log(e, this.mensajeError);
    }

    public ExcepcionGeneral(ExcepcionGeneral e) {
        this.codigoError = e.getCodigoError();
        this.mensajeError = e.getMensajeError();
        this.exception = e.getException();
        Log.log(e, this.mensajeError);
    }

    public ExcepcionGeneral(Exception e) {
        this.codigoError = e.toString();
        this.mensajeError = e.getMessage();
        this.exception = e;
        Log.log(e);
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getMensajeHtmlError() {
        return mensajeHtmlError;
    }

    public void setMensajeHtmlError(String mensajeHtmlError) {
        this.mensajeHtmlError = mensajeHtmlError;
    }

    public String toString() {
        return getCodigoError() + ": " + getMensajeError();
    }

}
