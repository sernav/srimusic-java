package es.uclm.sri.cache.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class EhcacheFactory {
	
	private static CacheManager manager = new CacheManager();
    private static Cache cache;
    
    static{
        try{
        	manager.getCache(cache.getName());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void setCache(String cacheName){
    	cache = manager.getCache(cacheName);
    }

    public static Cache getCache(){
        return cache;
    }
	
}
