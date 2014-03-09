package es.uclm.sri.sis.entidades;

import java.util.Date;

import de.umass.lastfm.User;

public class Usuario {

    private String nick;

    private String nombreCompleto;

    private String mailRegistro;

    private Date fechaRegistro;

    private String inductor;

    public Usuario(String nick, String nombreCompleto, String mailRegistro, Date fechaRegistro, String inductor) {
        this.nick = nick;
        this.nombreCompleto = nombreCompleto;
        this.mailRegistro = mailRegistro;
        this.fechaRegistro = fechaRegistro;
        this.inductor = inductor;
    }

    public Usuario(User userLastFm) {

    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getMailRegistro() {
        return mailRegistro;
    }

    public void setMailRegistro(String mailRegistro) {
        this.mailRegistro = mailRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getInductor() {
        return inductor;
    }

    public void setInductor(String inductor) {
        this.inductor = inductor;
    }

}
