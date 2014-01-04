package es.uclm.sri.persistencia.postgre.dao.model;

import java.util.Date;

public class Dusuarios {
    
    private Integer ID_DUSUARIO;
    private String NICKUSER;
    private String MAILREGS;
    private Date FECHREGS;
    private String NOMBUSER;
    private String APLLUSER;
    private String ORIGEN;

    public Integer getID_DUSUARIO() {
        return ID_DUSUARIO;
    }

    public void setID_DUSUARIO(Integer ID_DUSUARIO) {
        this.ID_DUSUARIO = ID_DUSUARIO;
    }

    public String getNICKUSER() {
        return NICKUSER;
    }

    public void setNICKUSER(String NICKUSER) {
        this.NICKUSER = NICKUSER;
    }

    public String getMAILREGS() {
        return MAILREGS;
    }

    public void setMAILREGS(String MAILREGS) {
        this.MAILREGS = MAILREGS;
    }

    public Date getFECHREGS() {
        return FECHREGS;
    }

    public void setFECHREGS(Date FECHREGS) {
        this.FECHREGS = FECHREGS;
    }

    public String getNOMBUSER() {
        return NOMBUSER;
    }

    public void setNOMBUSER(String NOMBUSER) {
        this.NOMBUSER = NOMBUSER;
    }

    public String getAPLLUSER() {
        return APLLUSER;
    }

    public void setAPLLUSER(String APLLUSER) {
        this.APLLUSER = APLLUSER;
    }
    
    public String getORIGEN() {
        return ORIGEN;
    }

    public void setORIGEN(String ORIGEN) {
        this.ORIGEN = ORIGEN;
    }
}