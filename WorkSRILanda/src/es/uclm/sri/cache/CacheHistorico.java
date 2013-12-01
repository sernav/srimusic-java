package es.uclm.sri.cache;

import net.sf.ehcache.Element;
import es.uclm.sri.persistencia.postgre.dao.HistuserMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Histuser;
import es.uclm.sri.sis.reflexion.KCtesReflexion;

public class CacheHistorico extends AbstractCache {
	
	private static CacheHistorico instance = null;
	
	private CacheHistorico() {
		nombreCache = KCtesReflexion.Cache.cacheHistUser;
		tablaDCache = KCtesReflexion.Tablas.historico;
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
		String keyOldElement = "ID_USER" + "ID_ALBUM";
		Element oldElement = cache.get(keyOldElement);
		cache.replace(oldElement, element);
	}

	public Element getElementosDTablaById(Integer id) {
		try{
			sqlMapper.openSession();
			HistuserMapper mapper = session.getMapper(HistuserMapper.class);
			Histuser histuser = mapper.selectByPrimaryKey(id);
			element = new Element(histuser.getID_HISTUSER(), histuser);
			return element;
		} finally {
			session.close();
		}	
	}
	
	public Element[] getElementosDTablaByUsuario(Integer idUsuario){
		try{
			sqlMapper.openSession();
			HistuserMapper mapper = session.getMapper(HistuserMapper.class);
			Histuser[] histusers = mapper.selectByIdUser(idUsuario);
			Element[] elementos = new Element[histusers.length];
			for (int i = 0; i < histusers.length; i++) {
				elementos[i] = new Element(histusers[i].getID_HISTUSER(), histusers[i]);
			}
			return elementos;
		} finally {
			session.close();
		}	
	}
}
