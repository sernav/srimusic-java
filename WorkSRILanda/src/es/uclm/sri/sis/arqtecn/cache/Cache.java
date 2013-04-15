package es.uclm.sri.sis.arqtecn.cache;

import org.w3c.dom.Element;

public class Cache {

	private static Cache instance = null;
	
	public String nombreCache;
	public String tablaDCache;
	public Element[] elementosDCache;

	private Cache() {
		
	}

	private static void createInstance() {
		if (instance == null) {
			synchronized (Cache.class) {
				if (instance == null)
					instance = new Cache();
			}
		}
	}

	public static Cache getInstance() {
		createInstance();
		return instance;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

}
