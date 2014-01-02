package es.uclm.sri.persistencia.postgre.dao.model;

import java.util.Date;

public class Usersapp {
    
    private Integer ID_USERAPP;
    private String NICKUSER;
    private String MAILREGS;
    private Date FECHREGS;
    private String NOMBUSER;
    private String APLLUSER;
    private String ORIGEN;

    public Integer getID_USERAPP() {
        return ID_USERAPP;
    }

    public void setID_USERAPP(Integer ID_USERAPP) {
        this.ID_USERAPP = ID_USERAPP;
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

    public void setORIGEN(String oRIGEN) {
        ORIGEN = oRIGEN;
    }
}