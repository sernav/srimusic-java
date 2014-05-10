package es.uclm.sri.persistencia.postgre.dao.model;

import java.util.Date;

public class Historico {
    
    private Integer ID_HISTORICO;
    private Integer ID_DUSUARIO_FK;
    private Integer ID_DALBUM_FK;
    private Integer NUMREPRD;
    private Date FULTREPR;
    private Integer ID_PESOSALBUM_FK;

    public Integer getID_HISTORICO() {
        return ID_HISTORICO;
    }

    public void setID_HISTORICO(Integer ID_HISTORICO) {
        this.ID_HISTORICO = ID_HISTORICO;
    }

    public Integer getID_DUSUARIO_FK() {
        return ID_DUSUARIO_FK;
    }

    public void setID_DUSUARIO_FK(Integer ID_DUSUARIO_FK) {
        this.ID_DUSUARIO_FK = ID_DUSUARIO_FK;
    }

    public Integer getID_DALBUM_FK() {
        return ID_DALBUM_FK;
    }

    public void setID_DALBUM_FK(Integer ID_DALBUM_FK) {
        this.ID_DALBUM_FK = ID_DALBUM_FK;
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
    
    public Integer getID_PESOSALBUM_FK() {
        return ID_PESOSALBUM_FK;
    }

    public void setID_PESOSALBUM_FK(Integer ID_PESOSALBUM_FK) {
        this.ID_PESOSALBUM_FK = ID_PESOSALBUM_FK;
    }
}