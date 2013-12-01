package es.uclm.sri.cache;

import java.sql.Connection;

import org.postgresql.core.v2.ConnectionFactoryImpl;

public class AdmonCache {
	
	static final String cacheHistoricoClass = "CacheHistorico";
	static final String cachePesosAlbumClass = "CachePesosAlbum";
	static final String cachePesosUsuarioClass = "CachePesosUsuario";
	
	private AdmonCache() {
		
	}
	
	public static AdmonCache getInstance(String idConnect, String nombClass) throws ClassNotFoundException {
		Class c = Class.forName(nombClass);
		Class[] i = c.getInterfaces();
		return null;
	}
	
	public static AdmonCache getInstance() {
		Connection connection = (Connection) new ConnectionFactoryImpl();
		return null;
	}

}
