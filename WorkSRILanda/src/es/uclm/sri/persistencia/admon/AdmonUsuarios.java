package es.uclm.sri.persistencia.admon;

import java.util.Calendar;

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
    
    public Dusuarios devolverUsuario(String nick) {
        return mapper.selectByNick(nick);
    }
    
    public Dusuarios devolverUsuario(User user) {
        return mapper.selectByNick(user.getName());
    }
    
    public void insertarUsuario(Dusuarios record) {
        mapper.insert(record);
    }
    
    public void insertarUsuario(User user) {
        Dusuarios record = new Dusuarios();
        record.setNICKUSER(user.getName());
        record.setNOMBUSER(user.getRealname());
        record.setAPLLUSER("");
        record.setMAILREGS("");
        record.setFECHREGS(Calendar.getInstance().getTime());
        record.setORIGEN("Lastfm");
        
        mapper.insert(record);
    }

}
