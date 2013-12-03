package es.uclm.sri.persistencia.postgre.dao.model;

import java.util.Date;

public class Histuser {
    
    private Integer ID_HISTUSER;
    private Integer ID_USERAPP_FK;
    private Integer ID_ALBUMAPP_FK;
    private Integer NUMREPRD;
    private Date FULTREPR;

    public Integer getID_HISTUSER() {
        return ID_HISTUSER;
    }

    public void setID_HISTUSER(Integer ID_HISTUSER) {
        this.ID_HISTUSER = ID_HISTUSER;
    }

    public Integer getID_USERAPP_FK() {
        return ID_USERAPP_FK;
    }

    public void setID_USERAPP_FK(Integer ID_USERAPP_FK) {
        this.ID_USERAPP_FK = ID_USERAPP_FK;
    }

    public Integer getID_ALBUMAPP_FK() {
        return ID_ALBUMAPP_FK;
    }

    public void setID_ALBUMAPP_FK(Integer ID_ALBUMAPP_FK) {
        this.ID_ALBUMAPP_FK = ID_ALBUMAPP_FK;
    }

    public Integer getNUMREPRD() {
        return NUMREPRD;
    }

    public void setNUMREPRD(Integer NUMREPRD) {
        this.NUMREPRD = NUMREPRD;
    }

    public Date getFULTREPR() {
        return FULTREPR;
    }

    public void setFULTREPR(Date FULTREPR) {
        this.FULTREPR = FULTREPR;
    }
}