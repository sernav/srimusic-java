package main.java.es.uclm.sri.cache;

import main.java.es.uclm.sri.sis.KSistema;
import net.sf.ehcache.Element;

/**
 * Crea y recupera instancias de <code>CacheClusters</code>. Utiliza el patrón
 * Singleton. Recoge elementos de la caché y actualiza la misma.
 * 
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
