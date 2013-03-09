package es.uclm.sri.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class EhcacheFactory {
	
	private static CacheManager manager = new CacheManager();
    private static Cache cache;
    
    private static String VPESOSUSER = "VPESOSUSER";
    private static String VPESOSALBUM = "VPESOSALBUM";
    private static String HISTUSER = "HISTUSER";
    
    static{
        try{
        	manager.getCache(VPESOSUSER);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static Cache setCache(String cacheName){
    	cache = manager.getCache(cacheName);
    	return cache;
    }

    public static Cache getCache(){
        return cache;
    }
	
}
