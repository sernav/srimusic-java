package es.uclm.sri.cache;

import net.sf.ehcache.Cache;

public interface ICache {
	
	public void getElementosTablaById(Integer identificador);
	
	public void loadCache();
	
	public int loadElementosDCache();
	
	public Cache getCacheCompleta();
	
	public Object getElementoByKey (String key);

}
