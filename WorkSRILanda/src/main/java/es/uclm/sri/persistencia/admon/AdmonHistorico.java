package es.uclm.sri.persistencia.admon;

import es.uclm.sri.persistencia.postgre.dao.HistoricoMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Historico;

/**
 * Admon para atacar las operaciones de la tabla de base de datos HISTORICO.
 * Utiliza XML de mapeo postgre.dao.sqlmap.HistoricoMapper.xml
 * 
 * @author Sergio Navarro
 * */
public class AdmonHistorico extends AbstractAdmon {

    private HistoricoMapper mapper = null;

    public AdmonHistorico() {
        super();
        this.mapper = session.getMapper(HistoricoMapper.class);
    }
    
    /**
     * Devuelve array de objetos Historico de un usuario
     * 
     * @param identificador de usuario: int
     * @return Historico[]
     * */
    public Historico[] devolverHistoricoDUsuario(int id_usuario) {
        return mapper.selectByIdUser(id_usuario);
    }
    
    /**
     * Devuelve un objeto Historico de un identificador
     * 
     * @param identificador de histórico: int
     * @return Historico
     * */
    public Historico devolverHistorico(int id_historico) {
        return mapper.selectByPrimaryKey(id_historico);
    }
    
    /**
     * Inserta un registro de histórico de un usuario.
     * 
     * @param Historio
     * @return id del registro insertado
     * */
    public int insertarHistoricoDUsuario(Historico record) {
        return mapper.insert(record);
    }
    
    /**
     * Elimina un registro de la tabla
     * 
     * @param Id de registro
     * */
    public int elimitarRegistroHistorico(int id_historico) {
        return mapper.deleteByPrimaryKey(id_historico);
    }
    
    /**
     * Actualiza registro.
     * 
     * @param Historico
     * */
    public int actualizarRegistroHistorico(Historico record) {
        return mapper.updateByPrimaryKey(record);
    }

}
