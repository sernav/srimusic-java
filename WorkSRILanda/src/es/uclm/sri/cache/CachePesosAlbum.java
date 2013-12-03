package es.uclm.sri.cache;

import net.sf.ehcache.Element;
import es.uclm.sri.persistencia.postgre.dao.VpesosalbumMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Vpesosalbum;
import es.uclm.sri.sis.KSistema;

/**
 * Crea y recupera instancias de <code>CachePesosAlbum</code>. Utiliza el patrón
 * Singleton. Recoge elementos de la caché y actualiza la misma.
 * Si el elemento no está, se recupera de la tabla de base de datos.
 * 
 * @author Sergio Navarro
 * */
public class CachePesosAlbum extends AbstractCache {
	
	private static CachePesosAlbum instance = null;
	public Element[] elements;
	
	private CachePesosAlbum() {
		nombreCache = KSistema.Cache.cacheVPesosAlbum;
		tablaDCache = KSistema.Tablas.albums;
		cache = getCache();
	}
	
	private static void createInstance() {
		if (instance == null) {
			synchronized (CachePesosAlbum.class) {
				if (instance == null)
					instance = new CachePesosAlbum();
			}
		}
	}
	
	public static CachePesosAlbum getInstance() {
		createInstance();
		return instance;
	}
	
	public Element[] getElementosDCacheById(String id) {
		Element[] elements = null;
		// calcularElementosDPesosAlbum(Usuario user);
		for (Element element : elements) {
			loadElementoEnCache(element);
		}
		return elements;
	}
	
	public void actualizarElementoDCache(Element element) {
		String keyOldElement = "ID_ALBUM";
		Element oldElement = cache.get(keyOldElement);
		cache.replace(oldElement, element);
	}
	
	public Element getElementosDTablaById(Integer id) {
		try{
			sqlMapper.openSession();
			VpesosalbumMapper mapper = session.getMapper(VpesosalbumMapper.class);
			Vpesosalbum vPesosAlbum = mapper.selectByPrimaryKey(id);
			element = new Element(vPesosAlbum.getID_VPESOSA(), vPesosAlbum);
			return element;
		} finally {
			session.close();
		}	
		
	}
}
