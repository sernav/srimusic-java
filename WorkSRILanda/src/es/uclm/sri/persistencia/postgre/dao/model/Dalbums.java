package es.uclm.sri.persistencia.postgre.dao.model;

public class Dalbums {
    
    private Integer ID_DALBUM;
    private String TITUALBM;
    private String AUTALBM;
    private String GENRALBM;
    private Integer NUMEPIST;
    private Integer ANYIOPUB;

    public Integer getID_DALBUM() {
        return ID_DALBUM;
    }

    public void setID_DALBUM(Integer ID_DALBUM) {
        this.ID_DALBUM = ID_DALBUM;
    }

    public String getTITUALBM() {
        return TITUALBM;
    }

    public void setTITUALBM(String TITUALBM) {
        this.TITUALBM = TITUALBM;
    }

    public String getAUTALBM() {
        return AUTALBM;
    }

    public void setAUTALBM(String AUTALBM) {
        this.AUTALBM = AUTALBM;
    }

    public String getGENRALBM() {
        return GENRALBM;
    }

    public void setGENRALBM(String GENRALBM) {
        this.GENRALBM = GENRALBM;
    }

    public Integer getNUMEPIST() {
        return NUMEPIST;
    }

    public void setNUMEPIST(Integer NUMEPIST) {
        this.NUMEPIST = NUMEPIST;
    }

    public Integer getANYIOPUB() {
        return ANYIOPUB;
    }

    public void setANYIOPUB(Integer ANYIOPUB) {
        this.ANYIOPUB = ANYIOPUB;
    }
}