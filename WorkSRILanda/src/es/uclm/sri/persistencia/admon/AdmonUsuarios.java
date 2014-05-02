package es.uclm.sri.persistencia.admon;

import java.sql.SQLException;
import java.util.Calendar;

import org.apache.ibatis.exceptions.IbatisException;

import de.umass.lastfm.User;
import es.uclm.sri.persistencia.postgre.dao.DusuariosMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Dusuarios;

/**
 * 
 * @author Sergio Navarro
 * @date Dic, 2013
 * */
public class AdmonUsuarios extends AbstractAdmon {

    private DusuariosMapper mapper = null;

    public AdmonUsuarios() {
        super();
        this.mapper = session.getMapper(DusuariosMapper.class);
    }

    public Dusuarios devolverUsuario(String nick) throws SQLException {
        try {
            return mapper.selectByNick(nick);
        } catch (IbatisException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Dusuarios devolverUsuario(User user) throws SQLException {
        try {
            return mapper.selectByNick(user.getName());
        } catch (IbatisException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertarUsuario(Dusuarios record) throws SQLException {
        try {
            mapper.insert(record);
        } catch (IbatisException e) {
            e.printStackTrace();
        }

    }

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
