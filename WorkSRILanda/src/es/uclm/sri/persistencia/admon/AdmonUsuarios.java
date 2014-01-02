package es.uclm.sri.persistencia.admon;

import java.util.Calendar;

import de.umass.lastfm.User;
import es.uclm.sri.persistencia.postgre.dao.UsersappMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Usersapp;

/**
 * 
 * @author Sergio Navarro
 * @date Dic, 2013
 * */
public class AdmonUsuarios extends AbstractAdmon {
    
    private UsersappMapper mapper = null;
    
    public AdmonUsuarios() {
        super();
        this.mapper = session.getMapper(UsersappMapper.class);
    }
    
    public Usersapp devolverUsuario(String nick) {
        return mapper.selectByNick(nick);
    }
    
    public Usersapp devolverUsuario(User user) {
        return mapper.selectByNick(user.getName());
    }
    
    public void insertarUsuario(Usersapp record) {
        mapper.insert(record);
    }
    
    public void insertarUsuario(User user) {
        Usersapp record = new Usersapp();
        record.setNICKUSER(user.getName());
        record.setNOMBUSER(user.getRealname());
        record.setAPLLUSER("");
        record.setMAILREGS("");
        record.setFECHREGS(Calendar.getInstance().getTime());
        record.setORIGEN("Lastfm");
        
        mapper.insert(record);
    }

}
