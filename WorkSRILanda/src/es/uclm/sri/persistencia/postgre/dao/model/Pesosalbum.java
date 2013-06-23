package es.uclm.sri.persistencia.postgre.dao.model;

public class Pesosalbum {
	
	//private Integer ID_PESOSALBUM;
	private String ALBUM;
	private String ARTISTA;
	
	private Double ROCK;
	private Double INDIE;
	private Double ALTERNATIVE;
	private Double POP;
	private Double ELECTRONIC;
	private Double BRIT;
	private Double FUNK;
	private Double RAP;
	private Double FOLK;
	private Double SINGLE;
	private Double HEAVY;
	private Double BLUES;
	private Double GRUNGE;
	private Double CLASSIC;
	private Double PUNK;
	private Double INSTRUMENTAL;
	private Double AMBIENT;
	private Double REGGAE;
	
//	public Integer getID_PESOSALBUM() {
//		return ID_PESOSALBUM;
//	}
//	
//	public void setID_PESOSALBUM(Integer iD_PESOSALBUM) {
//		ID_PESOSALBUM = iD_PESOSALBUM;
//	}
	
	public String getALBUM() {
		return ALBUM;
	}
	
	public void setALBUM(String aLBUM) {
		ALBUM = aLBUM;
	}
	
	public String getARTISTA() {
		return ARTISTA;
	}
	
	public void setARTISTA(String aRTISTA) {
		ARTISTA = aRTISTA;
	}
	
	public Double getROCK() {
		return ROCK;
	}
	
	public void setROCK(Double rOCK) {
		ROCK = rOCK;
	}
	
	public Double getINDIE() {
		return INDIE;
	}
	
	public void setINDIE(Double iNDIE) {
		INDIE = iNDIE;
	}
	
	public Double getALTERNATIVE() {
		return ALTERNATIVE;
	}
	
	public void setALTERNATIVE(Double aLTERNATIVE) {
		ALTERNATIVE = aLTERNATIVE;
	}
	
	public Double getPOP() {
		return POP;
	}
	
	public void setPOP(Double pOP) {
		POP = pOP;
	}
	
	public Double getELECTRONIC() {
		return ELECTRONIC;
	}
	
	public void setELECTRONIC(Double eLECTRONIC) {
		ELECTRONIC = eLECTRONIC;
	}
	
	public Double getBRIT() {
		return BRIT;
	}
	
	public void setBRIT(Double bRIT) {
		BRIT = bRIT;
	}
	
	public Double getFUNK() {
		return FUNK;
	}
	
	public void setFUNK(Double fUNK) {
		FUNK = fUNK;
	}
	
	public Double getRAP() {
		return RAP;
	}
	
	public void setRAP(Double rAP) {
		RAP = rAP;
	}
	
	public Double getFOLK() {
		return FOLK;
	}
	
	public void setFOLK(Double fOLK) {
		FOLK = fOLK;
	}
	
	public Double getSINGLE() {
		return SINGLE;
	}
	
	public void setSINGLE(Double sINGLE) {
		SINGLE = sINGLE;
	}
	
	public Double getHEAVY() {
		return HEAVY;
	}
	
	public void setHEAVY(Double hEAVY) {
		HEAVY = hEAVY;
	}
	
	public Double getBLUES() {
		return BLUES;
	}
	
	public void setBLUES(Double bLUES) {
		BLUES = bLUES;
	}
	
	public Double getGRUNGE() {
		return GRUNGE;
	}
	
	public void setGRUNGE(Double gRUNGE) {
		GRUNGE = gRUNGE;
	}
	
	public Double getCLASSIC() {
		return CLASSIC;
	}
	
	public void setCLASSIC(Double cLASSIC) {
		CLASSIC = cLASSIC;
	}
	
	public Double getPUNK() {
		return PUNK;
	}
	
	public void setPUNK(Double pUNK) {
		PUNK = pUNK;
	}
	
	public Double getINSTRUMENTAL() {
		return INSTRUMENTAL;
	}
	
	public void setINSTRUMENTAL(Double iNSTRUMENTAL) {
		INSTRUMENTAL = iNSTRUMENTAL;
	}
	
	public Double getAMBIENT() {
		return AMBIENT;
	}
	
	public void setAMBIENT(Double aMBIENT) {
		AMBIENT = aMBIENT;
	}
	
	public Double getREGGAE() {
		return REGGAE;
	}
	
	public void setREGGAE(Double rEGGAE) {
		REGGAE = rEGGAE;
	}
	
	public String toString() {
		return getALBUM() + " " + getARTISTA() + " rock " + getROCK() + " indie " + getINDIE() + " electronic " + getELECTRONIC() +
				" alternative " + getALTERNATIVE() + " pop " + getPOP() + " brit " + getBRIT() + " funk " + getFUNK() + " rap " +
				getRAP() + " folk " + getFOLK() + " single " + getSINGLE() + " heavy " + getHEAVY() + " blues " + getBLUES() + " grunge " +
				getGRUNGE() + " punk " + getPUNK() + " classic " + getCLASSIC() + " instrumental " + getINSTRUMENTAL() + " ambient " +
				getAMBIENT() + " reggae " + getREGGAE();
	}
	
}
