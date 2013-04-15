package es.uclm.sri.sis.arqtecn.controladores;

import java.sql.Connection;

import org.postgresql.core.v2.ConnectionFactoryImpl;

public class AdminCachePesos {
	
	final String cacheHistoricoClass = "CacheHistorico";
	
	public AdminCachePesos() {
		
	}
	
	public static AdminCachePesos getInstance(String idConnect, String nombClass) throws ClassNotFoundException {
		Class c = Class.forName(nombClass);
		Class[] i = c.getInterfaces();
		return null;
	}
	
	public static AdminCachePesos getInstance() {
		Connection connection = (Connection) new ConnectionFactoryImpl();
		return null;
	}

}
