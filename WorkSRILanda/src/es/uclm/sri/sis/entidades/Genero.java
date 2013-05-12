package es.uclm.sri.sis.entidades;

public class Genero {
	
	private String tipo;
	private int numOcurrencias;
	
	public Genero() {
		this.tipo = "generic";
		this.numOcurrencias = 1;
	}
	
	public Genero(String tipo, int numOcurrencias) {
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
	
	public String toString() {
		return getTipo() + " - " + getNumOcurrencias();
	}

}
