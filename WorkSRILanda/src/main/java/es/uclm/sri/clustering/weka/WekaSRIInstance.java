package es.uclm.sri.clustering.weka;

import es.uclm.sri.sis.utilidades.Utils;
import weka.core.Instance;

/**
 * Entidad de una instancia del sistema para Weka. Extiende de Instance.
 * Los atributos se corresponden con los tags del sistema para identificar discos.
 * 
 * @author Sergio Navarro
 * @version 1.0
 * */
public final class WekaSRIInstance extends Instance {

	Instance instance = null;
	
	protected String titulo;
	protected String artista;
	protected int idPesosAlbum;
	
	protected Double singer = Double.NaN;
	protected Double rap = Double.NaN;
	protected Double ambient = Double.NaN;
	protected Double indie = Double.NaN;
	protected Double blues = Double.NaN;
	protected Double reggae = Double.NaN;
	protected Double punk = Double.NaN;
	protected Double heavy = Double.NaN;
	protected Double alternative = Double.NaN;
	protected Double classic = Double.NaN;
	protected Double electronic = Double.NaN;
	protected Double rock = Double.NaN;
	protected Double pop = Double.NaN;
	protected Double brit = Double.NaN;
	protected Double folk = Double.NaN;
	protected Double funk = Double.NaN;
	protected Double instrumental = Double.NaN;
	protected Double grunge = Double.NaN;
	
	/**
	 * Constructor vacío
	 * */
	public WekaSRIInstance() {
		super();
		instance = new Instance(1.0, addAttValues());
	}
	
	/**
	 * Constructor con título y artista de album. Atributos vacíos.
	 * */
	public WekaSRIInstance(String titulo, String artista) {
		super();
		this.titulo = titulo;
		this.artista = artista;
		instance = new Instance(1.0, addAttValues());
	}
	
	/**
	 * Constructor con una instancia de Weka
	 * */
	public WekaSRIInstance(Instance instance) {
		super();
		this.instance = instance;
	}
	
	/**
	 * Constructor para una instancia de Weka con título y artista de album
	 * */
	public WekaSRIInstance(Instance instance, String titulo, String artista) {
		super();
		this.titulo = titulo;
		this.artista = artista;
		this.instance = instance;
	}
	
	/**
	 * Constructor con todos los atributos por parámetro
	 * */
	public WekaSRIInstance(double dsinger, double drap, double dambient,
			double dindie, double dblues, double dreggae, double dpunk,
			double dheavy, double dalternative, double dclassic,
			double delectronic, double drock, double dpop, double dbrit,
			double dfolk, double dfunk, double dinstrumental, double dgrunge) {
		super();
		double[] attValues = attInitValues(dsinger, drap, dambient, dindie,
				dblues, dreggae, dpunk, dheavy, dalternative, dclassic,
				delectronic, drock, dpop, dbrit, dfolk, dfunk, dinstrumental,
				dgrunge);
		instance = new Instance(1.0, attValues);
	}
	
	/**
	 * Constructor con los pesos de album para el tipo primitivo <code>double</code>
	 * */
	public WekaSRIInstance(double weight, double[] attValues) {
		super();
		attValues = attInitValues(attValues);
		instance = new Instance(weight, attValues);
	}
	
	/**
	 * Constructor con los pesos de album para el Double
	 * */
	public WekaSRIInstance(double weight, Double[] attValues) {
	    super();
        double[] auxAttValues = attInitValues(Utils.toDoubleArray(attValues));
        instance = new Instance(weight, auxAttValues);
    }
	
	/**
	 * Constructor para un array de pesos junto al título y artista del album
	 * */
    public WekaSRIInstance(double weight, double[] attValues, String titulo, String artista) {
		super();
		this.titulo = titulo;
		this.artista = artista;
		attValues = attInitValues(attValues);
		instance = new Instance(weight, attValues);
	}
    
    /**
     * Constructor para un array de pesos junto al título y artista del album + el identificador de BD del 
     * registro de dicho album en la tabla PesosAlbum.
     * */
    public WekaSRIInstance(double weight, double[] attValues, String titulo, String artista, int idPesosAlbum) {
        super();
        this.titulo = titulo;
        this.artista = artista;
        this.idPesosAlbum = idPesosAlbum;
        attValues = attInitValues(attValues);
        instance = new Instance(weight, attValues);
    }

	protected double[] addAttValues() {
		double[] attValues = new double[18];

		attValues[0] = this.singer.doubleValue();
		attValues[1] = this.rap.doubleValue();
		attValues[2] = this.ambient.doubleValue();
		attValues[3] = this.indie.doubleValue();
		attValues[4] = this.blues.doubleValue();
		attValues[5] = this.reggae.doubleValue();
		attValues[6] = this.punk.doubleValue();
		attValues[7] = this.heavy.doubleValue();
		attValues[8] = this.alternative.doubleValue();
		attValues[9] = this.classic.doubleValue();
		attValues[10] = this.electronic.doubleValue();
		attValues[11] = this.rock.doubleValue();
		attValues[12] = this.pop.doubleValue();
		attValues[13] = this.brit.doubleValue();
		attValues[14] = this.folk.doubleValue();
		attValues[15] = this.funk.doubleValue();
		attValues[16] = this.instrumental.doubleValue();
		attValues[17] = this.grunge.doubleValue();

		return attValues;
	}

	protected double[] attInitValues(double dsinger, double drap,
			double dambient, double dindie, double dblues, double dreggae,
			double dpunk, double dheavy, double dalternative, double dclassic,
			double delectronic, double drock, double dpop, double dbrit,
			double dfolk, double dfunk, double dinstrumental, double dgrunge) {

		if (dsinger != 0.0)
			this.singer = dsinger;
		if (drap != 0.0)
			this.rap = drap;
		if (dambient != 0.0)
			this.ambient = dambient;
		if (dindie != 0.0)
			this.indie = dindie;
		if (dblues != 0.0)
			this.blues = dblues;
		if (dreggae != 0.0)
			this.reggae = dreggae;
		if (dpunk != 0.0)
			this.punk = dpunk;
		if (dheavy != 0.0)
			this.heavy = dheavy;
		if (dalternative != 0.0)
			this.alternative = dalternative;
		if (dclassic != 0.0)
			this.classic = dclassic;
		if (delectronic != 0.0)
			this.electronic = delectronic;
		if (drock != 0.0)
			this.rock = drock;
		if (dpop != 0.0)
			this.pop = dpop;
		if (dbrit != 0.0)
			this.brit = dbrit;
		if (dfolk != 0.0)
			this.folk = dfolk;
		if (dfunk != 0.0)
			this.funk = dfunk;
		if (dinstrumental != 0.0)
			this.instrumental = dinstrumental;
		if (dgrunge != 0.0)
			this.grunge = dgrunge;

		double[] attValues = addAttValues();
		return attValues;
	}

	protected double[] attInitValues(double[] attValues) {

		if (attValues[0] != 0.0)
			this.singer = attValues[0];
		if (attValues[1] != 0.0)
			this.rap = attValues[1];
		if (attValues[2] != 0.0)
			this.ambient = attValues[2];
		if (attValues[3] != 0.0)
			this.indie = attValues[3];
		if (attValues[4] != 0.0)
			this.blues = attValues[4];
		if (attValues[5] != 0.0)
			this.reggae = attValues[5];
		if (attValues[6] != 0.0)
			this.punk = attValues[6];
		if (attValues[7] != 0.0)
			this.heavy = attValues[7];
		if (attValues[8] != 0.0)
			this.alternative = attValues[8];
		if (attValues[9] != 0.0)
			this.classic = attValues[9];
		if (attValues[10] != 0.0)
			this.electronic = attValues[10];
		if (attValues[11] != 0.0)
			this.rock = attValues[11];
		if (attValues[12] != 0.0)
			this.pop = attValues[12];
		if (attValues[13] != 0.0)
			this.brit = attValues[13];
		if (attValues[14] != 0.0)
			this.folk = attValues[14];
		if (attValues[15] != 0.0)
			this.funk = attValues[15];
		if (attValues[16] != 0.0)
			this.instrumental = attValues[16];
		if (attValues[17] != 0.0)
			this.grunge = attValues[17];

		return addAttValues();
	}
	
	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getArtita() {
		return artista;
	}

	public void setArtita(String artita) {
		this.artista = artita;
	}
	
	public int getIdPesosAlbum() {
	    return idPesosAlbum;
	}
	
	public void setIdPesosAlbum(int idPesosAlbum) {
	    this.idPesosAlbum = idPesosAlbum;
	}

	public Double getSinger() {
		return singer;
	}

	public void setSinger(Double singer) {
		this.singer = singer;
	}

	public Double getRap() {
		return rap;
	}

	public void setRap(Double rap) {
		this.rap = rap;
	}

	public Double getAmbient() {
		return ambient;
	}

	public void setAmbient(Double ambient) {
		this.ambient = ambient;
	}

	public Double getIndie() {
		return indie;
	}

	public void setIndie(Double indie) {
		this.indie = indie;
	}

	public Double getBlues() {
		return blues;
	}

	public void setBlues(Double blues) {
		this.blues = blues;
	}

	public Double getReggae() {
		return reggae;
	}

	public void setReggae(Double reggae) {
		this.reggae = reggae;
	}

	public Double getPunk() {
		return punk;
	}

	public void setPunk(Double punk) {
		this.punk = punk;
	}

	public Double getHeavy() {
		return heavy;
	}

	public void setHeavy(Double heavy) {
		this.heavy = heavy;
	}

	public Double getAlternative() {
		return alternative;
	}

	public void setAlternative(Double alternative) {
		this.alternative = alternative;
	}

	public Double getClassic() {
		return classic;
	}

	public void setClassic(Double classic) {
		this.classic = classic;
	}

	public Double getElectronic() {
		return electronic;
	}

	public void setElectronic(Double electronic) {
		this.electronic = electronic;
	}

	public Double getRock() {
		return rock;
	}

	public void setRock(Double rock) {
		this.rock = rock;
	}

	public Double getPop() {
		return pop;
	}

	public void setPop(Double pop) {
		this.pop = pop;
	}

	public Double getBrit() {
		return brit;
	}

	public void setBrit(Double brit) {
		this.brit = brit;
	}

	public Double getFolk() {
		return folk;
	}

	public void setFolk(Double folk) {
		this.folk = folk;
	}

	public Double getFunk() {
		return funk;
	}

	public void setFunk(Double funk) {
		this.funk = funk;
	}

	public Double getInstrumental() {
		return instrumental;
	}

	public void setInstrumental(Double instrumental) {
		this.instrumental = instrumental;
	}

	public Double getGrunge() {
		return grunge;
	}

	public void setGrunge(Double grunge) {
		this.grunge = grunge;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Título: " + titulo + "\n");
		strBuilder.append("Artista: " + artista + "\n");
		if (singer.doubleValue() > 0)
			strBuilder.append("singer       " + singer.doubleValue() + "\n");
		if (rap.doubleValue() > 0)
			strBuilder.append("rap          " + rap.doubleValue() + "\n");
		if (ambient.doubleValue() > 0)
			strBuilder.append("ambient      " + ambient.doubleValue() + "\n");
		if (indie.doubleValue() > 0)
			strBuilder.append("indie        " + indie.doubleValue() + "\n");
		if (blues.doubleValue() > 0)
			strBuilder.append("blues        " + blues.doubleValue() + "\n");
		if (reggae.doubleValue() > 0)
			strBuilder.append("reggae       " + reggae.doubleValue() + "\n");
		if (punk.doubleValue() > 0)
			strBuilder.append("punk         " + punk.doubleValue() + "\n");
		if (heavy.doubleValue() > 0)
			strBuilder.append("heavy        " + heavy.doubleValue() + "\n");
		if (alternative.doubleValue() > 0)
			strBuilder.append("alternative  " + alternative.doubleValue() + "\n");
		if (classic.doubleValue() > 0)
			strBuilder.append("classic      " + classic.doubleValue() + "\n");
		if (electronic.doubleValue() > 0)
			strBuilder.append("electronic   " + electronic.doubleValue() + "\n");
		if (rock.doubleValue() > 0)
			strBuilder.append("rock          " + rock.doubleValue() + "\n");
		if (pop.doubleValue() > 0)
			strBuilder.append("pop           " + pop.doubleValue() + "\n");
		if (brit.doubleValue() > 0)
			strBuilder.append("brit          " + brit.doubleValue() + "\n");
		if (folk.doubleValue() > 0)
			strBuilder.append("folk          " + folk.doubleValue() + "\n");
		if (funk.doubleValue() > 0)
			strBuilder.append("funk          " + funk.doubleValue() + "\n");
		if (instrumental.doubleValue() > 0)
			strBuilder.append("instrumental  " + instrumental.doubleValue() + "\n");
		if (grunge.doubleValue() > 0)
			strBuilder.append("grunge        " + grunge.doubleValue() + "\n");
		
		return strBuilder.toString();
	}

}
