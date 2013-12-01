package es.uclm.sri.clustering;

/**
 * Extensi—n de la clase Exception pesonalizada
 * para el cluster

 * @author sernav
 * @version 0.1
 * @since   0.1
 *
 */
public class ClusterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2353858209996638736L;

	/**
	 * 
	 */
	public ClusterException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ClusterException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ClusterException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ClusterException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}