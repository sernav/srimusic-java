package es.uclm.sri.persistencia;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConnectionFactoryTest {

	private SqlSessionFactory sqlMapper;
	private Reader reader;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
		sqlMapper = null;
		reader = null;
	}

	@Test
	public void testConnection() {
		try {
			reader = Resources.getResourceAsReader("sqlMapConfig.xml");
			assertNotNull("Fichero XML cargado", reader);
			
			sqlMapper = new SqlSessionFactoryBuilder().build(reader);
			assertNotNull("Conectado", sqlMapper);
		} catch (IOException e) {

		}
	}

	@Test(expected = IOException.class)
	public void testExcepcionES() throws IOException {
		reader = Resources.getResourceAsReader("sqlMapConfig_EXCEPTION_IO.xml");

	}

}
