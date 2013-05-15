package es.uclm.sri.sis.preframework.cache;

import net.sf.ehcache.Cache;

public interface ICacheApp {
	
	public void getElementosTablaById(Integer identificador);
	
	public void loadCache();
	
	public int loadElementosDCache();
	
	public Cache getCacheCompleta();
	
	public Object getElementoByKey (String key);

}
