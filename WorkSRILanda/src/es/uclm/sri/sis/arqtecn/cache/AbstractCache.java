package es.uclm.sri.sis.arqtecn.cache;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import es.uclm.sri.cache.EhcacheFactory;
import es.uclm.sri.persistencia.ConnectionFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public abstract class AbstractCache {

	public String nombreCache;
	public String tablaDCache;
	public Cache cache;
	
	SqlSessionFactory sqlMapper = ConnectionFactory.getSession();
	SqlSession session = null;
	Element element = null;

	public String getNombreCache() {
		return nombreCache;
	}

	public String getTablaDCache() {
		return tablaDCache;
	}

	public Cache getCache() {
		return cache;
	}
	
	public Element getElementoDCacheByKey(String key) {
		return cache.get(key);
	}
	
	public int loadElementoEnCache() {
		try{
			cache.put(element);
			return 1;
		} catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	public int loadElementoEnCache(Element element) {
		try{
			cache.put(element);
			return 1;
		} catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	public void loadCache() {
		EhcacheFactory.setCache(getNombreCache());
		cache = EhcacheFactory.getCache();
	}

	public abstract Element[] getElementosDCacheById(String id);
	
	public abstract void actualizarElementoDCache(Element element);

}