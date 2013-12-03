package es.uclm.sri.persistencia.postgre.dao.model;

public class Albumsapp {
    
    private Integer ID_ALBUMAPP;
    private String TITUALBM;
    private String AUTALBM;
    private String GENRALBM;
    private Integer NUMEPIST;
    private Integer ANYIOPUB;

    public Integer getID_ALBUMAPP() {
        return ID_ALBUMAPP;
    }

    public void setID_ALBUMAPP(Integer ID_ALBUMAPP) {
        this.ID_ALBUMAPP = ID_ALBUMAPP;
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