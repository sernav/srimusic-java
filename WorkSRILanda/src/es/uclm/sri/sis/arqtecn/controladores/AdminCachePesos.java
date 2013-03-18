package es.uclm.sri.sis.arqtecn.controladores;

import java.sql.Connection;

import org.postgresql.core.v2.ConnectionFactoryImpl;

public class AdminCachePesos {
	
	public AdminCachePesos() {
		
	}
	
	public static AdminCachePesos getInstance(String idConnection) {
		return null;
	}
	
	public static AdminCachePesos getInstance() {
		Connection connection = (Connection) new ConnectionFactoryImpl();
		return null;
	}

}
