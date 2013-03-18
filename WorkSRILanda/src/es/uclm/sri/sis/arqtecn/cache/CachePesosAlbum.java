package es.uclm.sri.sis.arqtecn.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import es.uclm.sri.cache.EhcacheFactory;
import es.uclm.sri.persistencia.ConnectionFactory;
import es.uclm.sri.persistencia.postgre.dao.VpesosalbumMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Vpesosalbum;
import es.uclm.sri.sis.arqtecn.KConstantes;

public class CachePesosAlbum implements ICacheApp {

	private Cache cachePesosAlbum = null;
	SqlSessionFactory sqlMapper = ConnectionFactory.getSession();
	SqlSession session = null;
	Element eVPesosAlbum = null;
	
	public void getElementosTablaById(Integer id) {
		try{
			sqlMapper.openSession();
			VpesosalbumMapper mapper = session.getMapper(VpesosalbumMapper.class);
			Vpesosalbum vPesosAlbum = mapper.selectByPrimaryKey(id);
			eVPesosAlbum = new Element(vPesosAlbum.getID_VPESOSA(), vPesosAlbum);
		} finally {
			session.close();
		}	
		
	}

	public void loadCache() {
		EhcacheFactory.setCache(KConstantes.Cache.cacheVPesosAlbum);
		cachePesosAlbum = EhcacheFactory.getCache();
		
	}

	public int loadElementosDCache() {
		try{
			cachePesosAlbum.put(eVPesosAlbum);
			return 1;
		} catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public Cache getCacheCompleta() {
		return cachePesosAlbum;
	}

	public Object getElementoByKey(String key) {
		return cachePesosAlbum.get(key);
	}

}
