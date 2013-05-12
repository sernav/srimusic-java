package es.uclm.sri.objnegocio;

public class GeneroMusical {
	
	private String tipo;
	private int numOcurrencias;
	
	public GeneroMusical() {
		this.tipo = "generic";
		this.numOcurrencias = 1;
	}
	
	public GeneroMusical(String tipo, int numOcurrencias) {
		this.tipo = tipo;
		this.numOcurrencias = numOcurrencias;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getNumOcurrencias() {
		return numOcurrencias;
	}

	public void setNumOcurrencias(int numOcurrencias) {
		this.numOcurrencias = numOcurrencias;
	}

}