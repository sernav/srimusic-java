package es.uclm.sri.clustering.weka;

import weka.core.Instance;

public class WekaSRIInstance {

	Instance instance = null;

	protected Double SINGER = Double.NaN;
	protected Double RAP = Double.NaN;
	protected Double AMBIENT = Double.NaN;
	protected Double INDIE = Double.NaN;
	protected Double BLUES = Double.NaN;
	protected Double REGGAE = Double.NaN;
	protected Double PUNK = Double.NaN;
	protected Double HEAVY = Double.NaN;
	protected Double ALTERNATIVE = Double.NaN;
	protected Double CLASSIC = Double.NaN;
	protected Double ELECTRONIC = Double.NaN;
	protected Double ROCK = Double.NaN;
	protected Double POP = Double.NaN;
	protected Double BRIT = Double.NaN;
	protected Double FOLK = Double.NaN;
	protected Double FUNK = Double.NaN;
	protected Double INSTRUMENTAL = Double.NaN;
	protected Double GRUNGE = Double.NaN;

	public WekaSRIInstance() {
		super();
		instance = new Instance(1.0, addAttValues());
	}

	public WekaSRIInstance(double dSinger, double dRap, double dAmbient,
			double dIndie, double dBlues, double dReggae, double dPunk,
			double dHeavy, double dAlternative, double dClassic,
			double dElectronic, double dRock, double dPop, double dBrit,
			double dFolk, double dFunk, double dInstrumental, double dGrunge) {
		super();
		double[] attValues = attInitValues(dSinger, dRap, dAmbient, dIndie,
				dBlues, dReggae, dPunk, dHeavy, dAlternative, dClassic,
				dElectronic, dRock, dPop, dBrit, dFolk, dFunk, dInstrumental,
				dGrunge);
		instance = new Instance(1.0, attValues);
	}

	public WekaSRIInstance(double weight, double[] attValues) {
		super();
		attValues = attInitValues(attValues);
		instance = new Instance(weight, attValues);
	}

	protected double[] addAttValues() {
		double[] attValues = new double[18];

		attValues[0] = this.SINGER.doubleValue();
		attValues[1] = this.RAP.doubleValue();
		attValues[2] = this.AMBIENT.doubleValue();
		attValues[3] = this.INDIE.doubleValue();
		attValues[4] = this.BLUES.doubleValue();
		attValues[5] = this.REGGAE.doubleValue();
		attValues[6] = this.PUNK.doubleValue();
		attValues[7] = this.HEAVY.doubleValue();
		attValues[8] = this.ALTERNATIVE.doubleValue();
		attValues[9] = this.CLASSIC.doubleValue();
		attValues[10] = this.ELECTRONIC.doubleValue();
		attValues[11] = this.ROCK.doubleValue();
		attValues[12] = this.POP.doubleValue();
		attValues[13] = this.BRIT.doubleValue();
		attValues[14] = this.FOLK.doubleValue();
		attValues[15] = this.FUNK.doubleValue();
		attValues[16] = this.INSTRUMENTAL.doubleValue();
		attValues[17] = this.GRUNGE.doubleValue();

		return attValues;
	}

	protected double[] attInitValues(double dSinger, double dRap,
			double dAmbient, double dIndie, double dBlues, double dReggae,
			double dPunk, double dHeavy, double dAlternative, double dClassic,
			double dElectronic, double dRock, double dPop, double dBrit,
			double dFolk, double dFunk, double dInstrumental, double dGrunge) {

		if (dSinger != 0.0)
			this.SINGER = dSinger;
		if (dRap != 0.0)
			this.RAP = dRap;
		if (dAmbient != 0.0)
			this.AMBIENT = dAmbient;
		if (dIndie != 0.0)
			this.INDIE = dIndie;
		if (dBlues != 0.0)
			this.BLUES = dBlues;
		if (dReggae != 0.0)
			this.REGGAE = dReggae;
		if (dPunk != 0.0)
			this.PUNK = dPunk;
		if (dHeavy != 0.0)
			this.HEAVY = dHeavy;
		if (dAlternative != 0.0)
			this.ALTERNATIVE = dAlternative;
		if (dClassic != 0.0)
			this.CLASSIC = dClassic;
		if (dElectronic != 0.0)
			this.ELECTRONIC = dElectronic;
		if (dRock != 0.0)
			this.ROCK = dRock;
		if (dPop != 0.0)
			this.POP = dPop;
		if (dBrit != 0.0)
			this.BRIT = dBrit;
		if (dFolk != 0.0)
			this.FOLK = dFolk;
		if (dFunk != 0.0)
			this.FUNK = dFunk;
		if (dInstrumental != 0.0)
			this.INSTRUMENTAL = dInstrumental;
		if (dGrunge != 0.0)
			this.GRUNGE = dGrunge;

		double[] attValues = addAttValues();
		return attValues;
	}

	protected double[] attInitValues(double[] attValues) {

		if (attValues[0] != 0.0)
			this.SINGER = attValues[0];
		if (attValues[1] != 0.0)
			this.RAP = attValues[1];
		if (attValues[2] != 0.0)
			this.AMBIENT = attValues[2];
		if (attValues[3] != 0.0)
			this.INDIE = attValues[3];
		if (attValues[4] != 0.0)
			this.BLUES = attValues[4];
		if (attValues[5] != 0.0)
			this.REGGAE = attValues[5];
		if (attValues[6] != 0.0)
			this.PUNK = attValues[6];
		if (attValues[7] != 0.0)
			this.HEAVY = attValues[7];
		if (attValues[8] != 0.0)
			this.ALTERNATIVE = attValues[8];
		if (attValues[9] != 0.0)
			this.CLASSIC = attValues[9];
		if (attValues[10] != 0.0)
			this.ELECTRONIC = attValues[10];
		if (attValues[11] != 0.0)
			this.ROCK = attValues[11];
		if (attValues[12] != 0.0)
			this.POP = attValues[12];
		if (attValues[13] != 0.0)
			this.BRIT = attValues[13];
		if (attValues[14] != 0.0)
			this.FOLK = attValues[14];
		if (attValues[15] != 0.0)
			this.FUNK = attValues[15];
		if (attValues[16] != 0.0)
			this.INSTRUMENTAL = attValues[16];
		if (attValues[17] != 0.0)
			this.GRUNGE = attValues[17];

		return addAttValues();
	}

	public Instance getInstance() {
		return this.instance;
	}

	public Double getSINGER() {
		return SINGER;
	}

	public void setSINGER(Double sINGER) {
		SINGER = sINGER;
	}

	public Double getRAP() {
		return RAP;
	}

	public void setRAP(Double rAP) {
		RAP = rAP;
	}

	public Double getAMBIENT() {
		return AMBIENT;
	}

	public void setAMBIENT(Double aMBIENT) {
		AMBIENT = aMBIENT;
	}

	public Double getINDIE() {
		return INDIE;
	}

	public void setINDIE(Double iNDIE) {
		INDIE = iNDIE;
	}

	public Double getBLUES() {
		return BLUES;
	}

	public void setBLUES(Double bLUES) {
		BLUES = bLUES;
	}

	public Double getREGGAE() {
		return REGGAE;
	}

	public void setREGGAE(Double rEGGAE) {
		REGGAE = rEGGAE;
	}

	public Double getPUNK() {
		return PUNK;
	}

	public void setPUNK(Double pUNK) {
		PUNK = pUNK;
	}

	public Double getHEAVY() {
		return HEAVY;
	}

	public void setHEAVY(Double hEAVY) {
		HEAVY = hEAVY;
	}

	public Double getALTERNATIVE() {
		return ALTERNATIVE;
	}

	public void setALTERNATIVE(Double aLTERNATIVE) {
		ALTERNATIVE = aLTERNATIVE;
	}

	public Double getCLASSIC() {
		return CLASSIC;
	}

	public void setCLASSIC(Double cLASSIC) {
		CLASSIC = cLASSIC;
	}

	public Double getELECTRONIC() {
		return ELECTRONIC;
	}

	public void setELECTRONIC(Double eLECTRONIC) {
		ELECTRONIC = eLECTRONIC;
	}

	public Double getROCK() {
		return ROCK;
	}

	public void setROCK(Double rOCK) {
		ROCK = rOCK;
	}

	public Double getPOP() {
		return POP;
	}

	public void setPOP(Double pOP) {
		POP = pOP;
	}

	public Double getBRIT() {
		return BRIT;
	}

	public void setBRIT(Double bRIT) {
		BRIT = bRIT;
	}

	public Double getFOLK() {
		return FOLK;
	}

	public void setFOLK(Double fOLK) {
		FOLK = fOLK;
	}

	public Double getFUNK() {
		return FUNK;
	}

	public void setFUNK(Double fUNK) {
		FUNK = fUNK;
	}

	public Double getINSTRUMENTAL() {
		return INSTRUMENTAL;
	}

	public void setINSTRUMENTAL(Double iNSTRUMENTAL) {
		INSTRUMENTAL = iNSTRUMENTAL;
	}

	public Double getGRUNGE() {
		return GRUNGE;
	}

	public void setGRUNGE(Double gRUNGE) {
		GRUNGE = gRUNGE;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}
	
	public String toString() {
		String cadena = "Pesos nuevo disco \n" +
				"SINGER: " + SINGER.doubleValue() + "\n" +
				"RAP: " + RAP.doubleValue() + "\n" +
				"AMBIENT: " + AMBIENT.doubleValue() + "\n" +
				"INDIE: " + INDIE.doubleValue() + "\n" +
				"BLUES: " + BLUES.doubleValue() + "\n" +
				"REGGAE: " + REGGAE.doubleValue() + "\n" +
				"PUNK: " + PUNK.doubleValue() + "\n" +
				"HEAVY: " + HEAVY.doubleValue() + "\n" +
				"ALTERNATIVE: " + ALTERNATIVE.doubleValue() + "\n" +
				"CLASSIC: " + CLASSIC.doubleValue() + "\n" +
				"ELECTRONIC: " + ELECTRONIC.doubleValue() + "\n" +
				"ROCK: " + ROCK.doubleValue() + "\n" +
				"POP: " + POP.doubleValue() + "\n" +
				"BRIT: " + BRIT.doubleValue() + "\n" +
				"FOLK: " + FOLK.doubleValue() + "\n" +
				"FUNK: " + FUNK.doubleValue() + "\n" +
				"INSTRUMENTAL: " + INSTRUMENTAL.doubleValue() + "\n" +
				"GRUNGE: " + GRUNGE.doubleValue() + "\n";
		return cadena;
	}

}
