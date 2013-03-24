package es.uclm.sri.sis.arqtecn.cache;

import net.sf.ehcache.Element;
import es.uclm.sri.persistencia.postgre.dao.VpesosuserMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Vpesosuser;
import es.uclm.sri.sis.arqtecn.KConstantes;

public class CachePesosUsuario extends AbstractCache {
	
	private static CachePesosUsuario instance = null;
	
	private CachePesosUsuario() {
		nombreCache = KConstantes.Cache.cacheVPesosAlbum;
		tablaDCache = KConstantes.Tablas.albums;
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
			VpesosuserMapper mapper = session.getMapper(VpesosuserMapper.class);
			Vpesosuser vPesosUser = mapper.selectByPrimaryKey(id);
			element = new Element(vPesosUser.getID_VPESOSU(), vPesosUser);
			return element;
		} finally {
			session.close();
		}	
		
	}
	
	public Element[] getElementosDTablaByUsuario(Integer idUsuario){
		try{
			sqlMapper.openSession();
			VpesosuserMapper mapper = session.getMapper(VpesosuserMapper.class);
			Vpesosuser[] vpesosuser = mapper.selectByIdUser(idUsuario);
			Element[] elementos = new Element[vpesosuser.length];
			for (int i = 0; i < vpesosuser.length; i++) {
				elementos[i] = new Element(vpesosuser[i].getID_VPESOSU(), vpesosuser[i]);
			}
			return elementos;
		} finally {
			session.close();
		}	
	}

}
