package es.uclm.sri.cache;

import net.sf.ehcache.Element;
import es.uclm.sri.persistencia.postgre.dao.HistoricoMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Historico;
import es.uclm.sri.sis.KSistema;

/**
 * Crea y recupera instancias de <code>CacheHistorico</code>. Utiliza el patrón
 * Singleton. Recoge elementos de la caché y actualiza la misma.
 * Si el elemento no está, se recupera de la tabla de base de datos.
 * 
 * @author Sergio Navarro
 * */
public class CacheHistorico extends AbstractCache {
	
	private static CacheHistorico instance = null;
	
	private CacheHistorico() {
		nombreCache = KSistema.Cache.cacheHistUser;
		tablaDCache = KSistema.Tablas.historico;
		cache = getCache();
	}
	
	private static void createInstance() {
		if (instance == null) {
			synchronized (CacheHistorico.class) {
				if (instance == null)
					instance = new CacheHistorico();
			}
		}
	}
	
	public static CacheHistorico getInstance() {
		createInstance();
		return instance;
	}
	
	public Element[] getElementosDCacheById(String id) {
		Element[] elements = new Element[1];
		elements[1] = getElementoDCacheByKey(id);
		return elements;
	}
	
	public void actualizarElementoDCache(Element element) {
		String keyOldElement = "ID_USER" + "ID_PESOSALBUM";
		Element oldElement = cache.get(keyOldElement);
		cache.replace(oldElement, element);
	}

	public Element getElementosDTablaById(Integer id) {
		try{
			sqlMapper.openSession();
			HistoricoMapper mapper = session.getMapper(HistoricoMapper.class);
			Historico histuser = mapper.selectByPrimaryKey(id);
			element = new Element(histuser.getID_HISTORICO(), histuser);
			return element;
		} finally {
			session.close();
		}	
	}
	
	public Element[] getElementosDTablaByUsuario(Integer idUsuario){
		try{
			sqlMapper.openSession();
			HistoricoMapper mapper = session.getMapper(HistoricoMapper.class);
			Historico[] histusers = mapper.selectByIdUser(idUsuario);
			Element[] elementos = new Element[histusers.length];
			for (int i = 0; i < histusers.length; i++) {
				elementos[i] = new Element(histusers[i].getID_HISTORICO(), histusers[i]);
			}
			return elementos;
		} finally {
			session.close();
		}	
	}
}
