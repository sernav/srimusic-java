package es.uclm.sri.persistencia.postgre.dao.model;

public class Pesosalbum {
	
	private Integer ID_PESOSALBUM;
    private String ALBUM;
	private String ARTISTA;
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
	private Integer ID_DALBUM_FK;

    public Integer getID_PESOSALBUM() {
        return ID_PESOSALBUM;
    }
    
    public void setID_PESOSALBUM(Integer ID_PESOSALBUM) {
        this.ID_PESOSALBUM = ID_PESOSALBUM;
    }
    
    public String getALBUM() {
        return ALBUM;
    }
    
    public void setALBUM(String ALBUM) {
        this.ALBUM = ALBUM;
    }
    
    public String getARTISTA() {
        return ARTISTA;
    }
    
    public void setARTISTA(String ARTISTA) {
        this.ARTISTA = ARTISTA;
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
    
    public Integer getID_DALBUM_FK() {
        return ID_DALBUM_FK;
    }

    public void setID_DALBUM_FK(Integer ID_DALBUM_FK) {
        this.ID_DALBUM_FK = ID_DALBUM_FK;
    }
    
    public boolean tienePesosValidos() {
        if (getSINGER() != null && !getSINGER().isNaN() && getSINGER().doubleValue() > 0.0)
            return true;
        if (getRAP() != null && !getRAP().isNaN() && getRAP().doubleValue() > 0.0)
            return true;
        if (getAMBIENT() != null && !getAMBIENT().isNaN() && getAMBIENT().doubleValue() > 0.0)
            return true;
        if (getINDIE() != null && !getINDIE().isNaN() && getINDIE().doubleValue() > 0.0)
            return true;
        if (getBLUES() != null && !getBLUES().isNaN() && getBLUES().doubleValue() > 0.0)
            return true;
        if (getREGGAE() != null && !getREGGAE().isNaN() && getREGGAE().doubleValue() > 0.0)
            return true;
        if (getPUNK() != null && !getPUNK().isNaN() && getPUNK().doubleValue() > 0.0)
            return true;
        if (getHEAVY() != null && !getHEAVY().isNaN() && getHEAVY().doubleValue() > 0.0)
            return true;
        if (getALTERNATIVE() != null && !getALTERNATIVE().isNaN() && getALTERNATIVE().doubleValue() > 0.0)
            return true;
        if (getCLASSIC() != null && !getCLASSIC().isNaN() && getCLASSIC().doubleValue() > 0.0)
            return true;
        if (getELECTRONIC() != null && !getELECTRONIC().isNaN() && getELECTRONIC().doubleValue() > 0.0)
            return true;
        if (getROCK() != null && !getROCK().isNaN() && getROCK().doubleValue() > 0.0)
            return true;
        if (getPOP() != null && !getPOP().isNaN() && getPOP().doubleValue() > 0.0)
            return true;
        if (getBRIT() != null && !getBRIT().isNaN() && getBRIT().doubleValue() > 0.0)
            return true;
        if (getFOLK() != null && !getFOLK().isNaN() && getFOLK().doubleValue() > 0.0)
            return true;
        if (getFUNK() != null && !getFUNK().isNaN() && getFUNK().doubleValue() > 0.0)
            return true;
        if (getINSTRUMENTAL() != null && !getINSTRUMENTAL().isNaN() && getINSTRUMENTAL().doubleValue() > 0.0)
            return true;
        if (getGRUNGE() != null && !getGRUNGE().isNaN() && getGRUNGE().doubleValue() > 0.0)
            return true;
        return false;
    }
	
}
