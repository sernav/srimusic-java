package test.java.es.uclm.sri.persistencia.admon;

import static org.junit.Assert.assertNotNull;

import java.net.ConnectException;

import main.java.es.uclm.sri.persistencia.ConnectionFactory;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

public class AdmonsTest {
	
	private SqlSessionFactory sqlMapper;
    private SqlSession session;


	@Test
	public void testEstablecerConexion() {
		sqlMapper = ConnectionFactory.getSession();
		assertNotNull("Conexión establecida con éxito", sqlMapper);
	}
	
	@Test
	public void testAbrirSesion() {
		testEstablecerConexion();
		session = sqlMapper.openSession();

		assertNotNull("Conexión establecida con éxito", session);
	}
	
	@Test(expected = ConnectException.class)
	public void testConexionFallida() {
		sqlMapper = ConnectionFactory.getSession();
	}

}
