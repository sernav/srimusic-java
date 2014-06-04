package es.uclm.sri.clustering;

/**
 * Módulo clustering: Clase Exception pesonalizada para el cluster
 * 
 * @author Sergio Navarro
 * @version 0.1
 */
public class ClusterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2353858209996638736L;

	/**
	 * Constructor vacío
	 */
	public ClusterException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con argumento
	 * 
	 * @param arg0: String
	 */
	public ClusterException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con argumentos de excepción Throwable.
	 * 
	 * @param arg0: String
	 * @param arg1: Throwable
	 */
	public ClusterException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con argumento Throwable
	 * 
	 * @param arg0: Throwable
	 */
	public ClusterException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}