package es.uclm.sri.persistencia.postgre.dao.model;

import java.util.Date;

public class Vpesosuser {
    private Integer ID_VPESOSU;

    private Integer ID_USERAPP_FK;

    private Date FECHSESI;

    private Short VPESO1;

    private Short VPESO2;

    private Short VPESO3;

    private Short VPESO4;

    private Short VPESO5;

    private Short VPESO6;

    private Short VPESO7;

    private Short VPESO8;

    public Integer getID_VPESOSU() {
        return ID_VPESOSU;
    }

    public void setID_VPESOSU(Integer ID_VPESOSU) {
        this.ID_VPESOSU = ID_VPESOSU;
    }

    public Integer getID_USERAPP_FK() {
        return ID_USERAPP_FK;
    }

    public void setID_USERAPP_FK(Integer ID_USERAPP_FK) {
        this.ID_USERAPP_FK = ID_USERAPP_FK;
    }

    public Date getFECHSESI() {
        return FECHSESI;
    }

    public void setFECHSESI(Date FECHSESI) {
        this.FECHSESI = FECHSESI;
    }

    public Short getVPESO1() {
        return VPESO1;
    }

    public void setVPESO1(Short VPESO1) {
        this.VPESO1 = VPESO1;
    }

    public Short getVPESO2() {
        return VPESO2;
    }

    public void setVPESO2(Short VPESO2) {
        this.VPESO2 = VPESO2;
    }

    public Short getVPESO3() {
        return VPESO3;
    }

    public void setVPESO3(Short VPESO3) {
        this.VPESO3 = VPESO3;
    }

    public Short getVPESO4() {
        return VPESO4;
    }

    public void setVPESO4(Short VPESO4) {
        this.VPESO4 = VPESO4;
    }

    public Short getVPESO5() {
        return VPESO5;
    }

    public void setVPESO5(Short VPESO5) {
        this.VPESO5 = VPESO5;
    }

    public Short getVPESO6() {
        return VPESO6;
    }

    public void setVPESO6(Short VPESO6) {
        this.VPESO6 = VPESO6;
    }

    public Short getVPESO7() {
        return VPESO7;
    }

    public void setVPESO7(Short VPESO7) {
        this.VPESO7 = VPESO7;
    }

    public Short getVPESO8() {
        return VPESO8;
    }

    public void setVPESO8(Short VPESO8) {
        this.VPESO8 = VPESO8;
    }
}