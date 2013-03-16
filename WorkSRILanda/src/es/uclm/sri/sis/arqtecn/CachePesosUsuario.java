package es.uclm.sri.sis.arqtecn;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import es.uclm.sri.cache.EhcacheFactory;
import es.uclm.sri.persistencia.ConnectionFactory;
import es.uclm.sri.persistencia.postgre.dao.VpesosuserMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Vpesosuser;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class CachePesosUsuario extends AbstractCacheoPesos {

	private Cache cachePesosUser = null;
	SqlSessionFactory sqlMapper = ConnectionFactory.getSession();
	SqlSession session = null;
	Element eVPesosUser = null;
	
	public void getElementosTablaById(Integer id) {
		try{
			sqlMapper.openSession();
			VpesosuserMapper mapper = session.getMapper(VpesosuserMapper.class);
			Vpesosuser vPesosUser = mapper.selectByPrimaryKey(id);
			eVPesosUser = new Element(vPesosUser.getID_VPESOSU(), vPesosUser);
		} finally {
			session.close();
		}	
		
	}

	public void loadCache() {
		EhcacheFactory.setCache(KConstantes.Cache.cacheVPesosAlbum);
		cachePesosUser = EhcacheFactory.getCache();
		
	}

	public int loadElementosDCache() {
		try{
			cachePesosUser.put(eVPesosUser);
			return 1;
		} catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public Cache getCacheCompleta() {
		return cachePesosUser;
	}

	public Object getElementoByKey(String key) {
		return cachePesosUser.get(key);
	}

}
