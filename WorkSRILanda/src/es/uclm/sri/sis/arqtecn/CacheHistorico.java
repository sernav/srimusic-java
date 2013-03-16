package es.uclm.sri.sis.arqtecn;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import es.uclm.sri.cache.EhcacheFactory;
import es.uclm.sri.persistencia.ConnectionFactory;
import es.uclm.sri.persistencia.postgre.dao.HistuserMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Histuser;

public class CacheHistorico extends AbstractCachePesos {
	
	private Cache cacheHistuser = null;
	SqlSessionFactory sqlMapper = ConnectionFactory.getSession();
	SqlSession session = null;
	Element eHistuser = null;

	public void getElementosTablaById(Integer id) {
		try{
			sqlMapper.openSession();
			HistuserMapper mapper = session.getMapper(HistuserMapper.class);
			Histuser histuser = mapper.selectByPrimaryKey(id);
			eHistuser = new Element(histuser.getID_HISTUSER(), histuser);
		} finally {
			session.close();
		}	
	}
	
	public Element[] getElementosTablaByUsuario(Integer idUsuario){
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

	public void loadCache() {
		EhcacheFactory.setCache(KConstantes.Cache.cacheHistUser);
		cacheHistuser = EhcacheFactory.getCache();
	}

	public int loadElementosDCache() {
		try{
			cacheHistuser.put(eHistuser);
			return 1;
		} catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public Cache getCacheCompleta() {
		return cacheHistuser;
	}

	public Element getElementoByKey(String key) {
		return cacheHistuser.get(key);
	}

}
