package es.uclm.sri.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class EhcacheFactory {
	
	private static CacheManager manager = new CacheManager();
    private static Cache cache;
    
    static{
        try{
        	String cacheVpesos = "VPESOSUSER";
        	manager.getCache(cacheVpesos);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Cache getCache(){
        return cache;
    }
	
}
