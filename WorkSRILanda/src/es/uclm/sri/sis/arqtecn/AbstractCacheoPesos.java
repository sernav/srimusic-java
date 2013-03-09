package es.uclm.sri.sis.arqtecn;

import net.sf.ehcache.Cache;
import es.uclm.sri.cache.EhcacheFactory;
import es.uclm.sri.persistencia.ConnectionFactory;

public abstract class AbstractCacheoPesos {
	
	EhcacheFactory cacheFactory;
	ConnectionFactory connectionSql;
	
	public abstract void getElementosTablaById(Integer identificador);
	
	public abstract void loadCache();
	
	public abstract int loadElementosDCache();
	
	public abstract Cache getCacheCompleta();
	
	public abstract Object getElementoKey (String key);

}
