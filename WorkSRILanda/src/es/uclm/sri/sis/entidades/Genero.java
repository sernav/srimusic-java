package es.uclm.sri.sis.entidades;

public class Genero {
	
	private String tipo;
	private int numOcurrencias;
	private double valorPonderado;
	
	public Genero() {
		this.tipo = "generic";
		this.numOcurrencias = 0;
		this.valorPonderado = 0;
	}
	
	public Genero(String tipo, int numOcurrencias, double valorPonderado) {
		this.tipo = tipo;
		this.numOcurrencias = numOcurrencias;
		this.valorPonderado = valorPonderado;
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
	
	public double getValorPonderado() {
		return valorPonderado;
	}

	public void setValorPonderado(double valorPonderado) {
		this.valorPonderado = valorPonderado;
	}
	
	public String toString() {
		return getTipo() + " - " + getNumOcurrencias();
	}

}
