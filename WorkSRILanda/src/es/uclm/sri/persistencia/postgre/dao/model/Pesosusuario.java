package es.uclm.sri.persistencia.postgre.dao.model;

import java.util.Date;

public class Pesosusuario {
    
    private Integer ID_PESOSUSUARIO;
    private Integer ID_DUSUARIO_FK;
    private Date FECHSESI;
    private Double SINGER;
    private Double RAP;
    private Double AMBIENT;
    private Double INDIE;
    private Double BLUES;
    private Double REGGAE;
    private Double PUNK;
    private Double HEAVY;
    private Double ALTERNATIVE;
    private Double CLASSIC;
    private Double ELECTRONIC;
    private Double ROCK;
    private Double POP;
    private Double BRIT;
    private Double FOLK;
    private Double FUNK;
    private Double INSTRUMENTAL;
    private Double GRUNGE;

    public Integer getID_PESOSUSUARIO() {
        return ID_PESOSUSUARIO;
    }

    public void setID_PESOSUSUARIO(Integer ID_PESOSUSUARIO) {
        this.ID_PESOSUSUARIO = ID_PESOSUSUARIO;
    }

    public Integer getID_DUSUARIO_FK() {
        return ID_DUSUARIO_FK;
    }

    public void setID_DUSUARIO_FK(Integer ID_DUSUARIO_FK) {
        this.ID_DUSUARIO_FK = ID_DUSUARIO_FK;
    }

    public Date getFECHSESI() {
        return FECHSESI;
    }

    public void setFECHSESI(Date FECHSESI) {
        this.FECHSESI = FECHSESI;
    }

    public Double getSINGER() {
        return SINGER;
    }

    public void setSINGER(Double SINGER) {
        this.SINGER = SINGER;
    }

    public Double getRAP() {
        return RAP;
    }

    public void setRAP(Double RAP) {
        this.RAP = RAP;
    }

    public Double getAMBIENT() {
        return AMBIENT;
    }

    public void setAMBIENT(Double AMBIENT) {
        this.AMBIENT = AMBIENT;
    }

    public Double getINDIE() {
        return INDIE;
    }

    public void setINDIE(Double INDIE) {
        this.INDIE = INDIE;
    }

    public Double getBLUES() {
        return BLUES;
    }

    public void setBLUES(Double BLUES) {
        this.BLUES = BLUES;
    }

    public Double getREGGAE() {
        return REGGAE;
    }

    public void setREGGAE(Double REGGAE) {
        this.REGGAE = REGGAE;
    }

    public Double getPUNK() {
        return PUNK;
    }

    public void setPUNK(Double PUNK) {
        this.PUNK = PUNK;
    }

    public Double getHEAVY() {
        return HEAVY;
    }

    public void setHEAVY(Double HEAVY) {
        this.HEAVY = HEAVY;
    }

    public Double getALTERNATIVE() {
        return ALTERNATIVE;
    }

    public void setALTERNATIVE(Double ALTERNATIVE) {
        this.ALTERNATIVE = ALTERNATIVE;
    }

    public Double getCLASSIC() {
        return CLASSIC;
    }

    public void setCLASSIC(Double CLASSIC) {
        this.CLASSIC = CLASSIC;
    }

    public Double getELECTRONIC() {
        return ELECTRONIC;
    }

    public void setELECTRONIC(Double ELECTRONIC) {
        this.ELECTRONIC = ELECTRONIC;
    }

    public Double getROCK() {
        return ROCK;
    }

    public void setROCK(Double ROCK) {
        this.ROCK = ROCK;
    }

    public Double getPOP() {
        return POP;
    }

    public void setPOP(Double POP) {
        this.POP = POP;
    }

    public Double getBRIT() {
        return BRIT;
    }

    public void setBRIT(Double BRIT) {
        this.BRIT = BRIT;
    }

    public Double getFOLK() {
        return FOLK;
    }

    public void setFOLK(Double FOLK) {
        this.FOLK = FOLK;
    }

    public Double getFUNK() {
        return FUNK;
    }

    public void setFUNK(Double FUNK) {
        this.FUNK = FUNK;
    }

    public Double getINSTRUMENTAL() {
        return INSTRUMENTAL;
    }

    public void setINSTRUMENTAL(Double INSTRUMENTAL) {
        this.INSTRUMENTAL = INSTRUMENTAL;
    }

    public Double getGRUNGE() {
        return GRUNGE;
    }

    public void setGRUNGE(Double GRUNGE) {
        this.GRUNGE = GRUNGE;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" Singer=" + getSINGER());
        sb.append(" | Rap=" + getRAP());
        sb.append(" | Ambient=" + getAMBIENT());
        sb.append(" | Indie=" + getINDIE());
        sb.append(" | Blues=" + getBLUES());
        sb.append(" | Reggae=" + getREGGAE());
        sb.append(" | Punk=" + getPUNK());
        sb.append(" | Heavy=" + getHEAVY());
        sb.append(" | Alternative=" + getALTERNATIVE());
        sb.append(" | Classic=" + getCLASSIC());
        sb.append(" | Electronic=" + getELECTRONIC());
        sb.append(" | Rock=" + getROCK());
        sb.append(" | Pop=" + getPOP());
        sb.append(" | Brit=" + getBRIT());
        sb.append(" | Folk=" + getFOLK());
        sb.append(" | Funk=" + getFUNK());
        sb.append(" | Instrumental=" + getINSTRUMENTAL());
        sb.append(" | Grunge=" + getGRUNGE());
        
        return sb.toString();
    }

    
}