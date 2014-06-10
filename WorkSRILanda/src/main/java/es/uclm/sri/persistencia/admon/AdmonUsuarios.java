package main.java.es.uclm.sri.persistencia.admon;

import java.sql.SQLException;
import java.util.Calendar;

import org.apache.ibatis.exceptions.IbatisException;

import de.umass.lastfm.User;
import main.java.es.uclm.sri.persistencia.postgre.dao.DusuariosMapper;
import main.java.es.uclm.sri.persistencia.postgre.dao.model.Dusuarios;

/**
 * Admon para atacar las operaciones de la tabla de base de datos DUSUARIO
 * Utiliza XML de mapeo postgre.dao.sqlmap.PesosusuarioMapper.xml
 * 
 * @author Sergio Navarro
 * */
public class AdmonUsuarios extends AbstractAdmon {

    private DusuariosMapper mapper = null;

    public AdmonUsuarios() {
        super();
        this.mapper = session.getMapper(DusuariosMapper.class);
    }

    /**
     * Devuelver usuario por su nick
     * 
     * @param nick: String
     * @return Dusuarios
     * */
    public Dusuarios devolverUsuario(String nick) throws SQLException {
        try {
            return mapper.selectByNick(nick);
        } catch (IbatisException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Devuelver usuario por entidad User
     * 
     * @param user: User
     * @return Dusuarios
     * */
    public Dusuarios devolverUsuario(User user) throws SQLException {
        try {
            return mapper.selectByNick(user.getName());
        } catch (IbatisException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Inserta usuario por entidad Dusuarios
     * 
     * @param Dusuarios
     * */
    public void insertarUsuario(Dusuarios record) throws SQLException {
        try {
            mapper.insert(record);
        } catch (IbatisException e) {
            e.printStackTrace();
        }

    }

    /**
     * Inserta usuario por entidad User
     * 
     * @param user: User
     * */
    public int insertarUsuario(User user) throws SQLException {
        Dusuarios record = new Dusuarios();
        record.setNICKUSER(user.getName());
        record.setNOMBUSER(user.getRealname());
        record.setAPLLUSER("");
        record.setMAILREGS("");
        record.setFECHREGS(Calendar.getInstance().getTime());
        record.setORIGEN("Lastfm");
        try {
            return mapper.insert(record);
        } catch (IbatisException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
