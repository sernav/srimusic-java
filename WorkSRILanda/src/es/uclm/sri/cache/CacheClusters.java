package es.uclm.sri.cache;

import es.uclm.sri.sis.KSistema;
import net.sf.ehcache.Element;

/**
 * <code>CacheClusters</code> almacena los elementos de cada uno de los
 * clusters creados
 * 
 * @author Sergio Navarro
 * */
public class CacheClusters extends AbstractCache {
    
    private static CacheClusters instance = null;
    
    private CacheClusters() {
        nombreCache = KSistema.Cache.cacheClusters;
        cache = getCache();
    }
    
    private static void createInstance() {
        if (instance == null) {
            synchronized (CacheClusters.class) {
                if (instance == null)
                    instance = new CacheClusters();
            }
        }
    }
    
    public static CacheClusters getInstance() {
        createInstance();
        return instance;
    }

    public Element[] getElementosDCacheById(String id) {
        Element[] elements = new Element[1];
        elements[1] = getElementoDCacheByKey(id);
        return elements;
    }

    public void actualizarElementoDCache(Element element) {
        String keyOldElement = "CLUTER";
        Element oldElement = cache.get(keyOldElement);
        cache.replace(oldElement, element);
    }

}
