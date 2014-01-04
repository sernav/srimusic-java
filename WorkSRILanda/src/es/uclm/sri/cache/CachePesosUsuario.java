package es.uclm.sri.cache;

import net.sf.ehcache.Element;
import es.uclm.sri.persistencia.postgre.dao.PesosusuarioMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosusuario;
import es.uclm.sri.sis.KSistema;

/**
 * Crea y recupera instancias de <code>CachePesosUsuario</code>. Utiliza el patrón
 * Singleton. Recoge elementos de la caché y actualiza la misma.
 * Si el elemento no está, se recupera de la tabla de base de datos.
 * 
 * @author Sergio Navarro
 * */
public class CachePesosUsuario extends AbstractCache {
	
	private static CachePesosUsuario instance = null;
	
	private CachePesosUsuario() {
		nombreCache = KSistema.Cache.cacheVPesosAlbum;
		tablaDCache = KSistema.Tablas.albums;
		cache = getCache();
	}
	
	private static void createInstance() {
		if (instance == null) {
			synchronized (CachePesosUsuario.class) {
				if (instance == null)
					instance = new CachePesosUsuario();
			}
		}
	}
	
	public static CachePesosUsuario getInstance() {
		createInstance();
		return instance;
	}
	
	public Element[] getElementosDCacheById(String id) {
		Element[] elements = new Element[1];
		elements[1] = getElementoDCacheByKey(id);
		return elements;
	}
	
	public void actualizarElementoDCache(Element element) {
		String keyOldElement = "ID_USER";
		Element oldElement = cache.get(keyOldElement);
		cache.replace(oldElement, element);
	}
	
	public Element getElementosDTablaById(Integer id) {
		try{
			sqlMapper.openSession();
			PesosusuarioMapper mapper = session.getMapper(PesosusuarioMapper.class);
			Pesosusuario vPesosUser = mapper.selectByPrimaryKey(id);
			element = new Element(vPesosUser.getID_PESOSALBUM(), vPesosUser);
			return element;
		} finally {
			session.close();
		}	
		
	}
	
	public Element[] getElementosDTablaByUsuario(Integer idUsuario){
		try{
			sqlMapper.openSession();
			PesosusuarioMapper mapper = session.getMapper(PesosusuarioMapper.class);
			Pesosusuario[] vpesosuser = mapper.selectByIdUser(idUsuario);
			Element[] elementos = new Element[vpesosuser.length];
			for (int i = 0; i < vpesosuser.length; i++) {
				elementos[i] = new Element(vpesosuser[i].getID_PESOSALBUM(), vpesosuser[i]);
			}
			return elementos;
		} finally {
			session.close();
		}	
	}

}
