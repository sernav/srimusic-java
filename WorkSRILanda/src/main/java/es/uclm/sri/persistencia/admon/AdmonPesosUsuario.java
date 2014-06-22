package es.uclm.sri.persistencia.admon;

import es.uclm.sri.persistencia.postgre.dao.PesosusuarioMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosusuario;

/**
 * Admon para atacar las operaciones de la tabla de base de datos PESOSUSUARIO
 * Utiliza XML de mapeo postgre.dao.sqlmap.PesosusuarioMapper.xml
 * 
 * @author Sergio Navarro
 * */
public class AdmonPesosUsuario extends AbstractAdmon {
    
    private PesosusuarioMapper mapper = null;
    
    public AdmonPesosUsuario() {
        super();
        this.mapper = session.getMapper(PesosusuarioMapper.class);
    }
    
    /**
     * Devuelve pesos de usuario por Id de usuario
     * 
     * @param Id: int
     * */
    public Pesosusuario[] devolverPesosUsuario(int id_usuario) {
        return mapper.selectByUsuario(id_usuario);
    }
    
    /**
     * Devuelve pesos de usuario por Id de registro
     * 
     * @param Id: int
     * */
    public Pesosusuario devolverPesosUsuarioById(int id_pesosusuario) {
        return mapper.selectByPrimaryKey(id_pesosusuario);
    }
    
    /**
     * Inserta pesos de usuario por su entidad
     * 
     * @param Pesosusuario
     * */
    public int insertarPesosUsuario(Pesosusuario record) {
        return mapper.insert(record);
    }
    
    /**
     * Inserta pesos de usuario por su entidad
     * 
     * @param Pesosusuario
     * */
    public int insertarPesosUsuarioSelective(Pesosusuario record) {
        return mapper.insertSelective(record);
    }
    
    /**
     * Actualiza pesos de usuario por su entidad
     * 
     * @param Pesosusuario
     * */
    public int actualizarPesosUsuario(Pesosusuario record) {
        return mapper.updateByPrimaryKey(record);
    }
    
}
