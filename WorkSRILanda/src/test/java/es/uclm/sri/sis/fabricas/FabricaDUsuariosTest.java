package es.uclm.sri.sis.fabricas;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import de.umass.lastfm.User;
import es.uclm.sri.persistencia.admon.AdmonPesosUsuario;
import es.uclm.sri.persistencia.admon.AdmonUsuarios;
import es.uclm.sri.persistencia.postgre.dao.model.Dusuarios;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosusuario;

public class FabricaDUsuariosTest {
	
	private static final String API_KEY_LASTFM = "58169c7f645f6ad54529f3012548fc3a";
	
	@Test
	public void testUsuarioLastfm() {
		User userLastfm = User.getInfo("djAguadilla", API_KEY_LASTFM);
		assertNotNull("Usuario de lastfm cargado", userLastfm);
	}

	@Test
	public void testBuscarUsuario() {
		User userLastfm = User.getInfo("djAguadilla", API_KEY_LASTFM);
		AdmonUsuarios admonUsuario = new AdmonUsuarios();
		try {
			Dusuarios user = admonUsuario.devolverUsuario(userLastfm);
			assertNotNull("Usuario de BD", user);
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testPesosUsuario() {
		AdmonPesosUsuario admonPesos = new AdmonPesosUsuario();
		AdmonUsuarios admonUsuario = new AdmonUsuarios();
		
		User userLastfm = User.getInfo("djAguadilla", API_KEY_LASTFM);
		Dusuarios user;
		Pesosusuario historico;
		try {
			user = admonUsuario.devolverUsuario(userLastfm);
			historico = admonPesos.devolverPesosUsuario(user.getID_DUSUARIO())[0];
			assertNotNull("Pesos de usuario", historico);
		} catch (SQLException e) {
			fail(e.getMessage());
		}
		
	}

}
