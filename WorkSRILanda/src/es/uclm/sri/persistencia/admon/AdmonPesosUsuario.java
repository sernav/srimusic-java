package es.uclm.sri.persistencia.admon;

import es.uclm.sri.persistencia.postgre.dao.PesosusuarioMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Pesosusuario;

/**
 * 
 * @author Sergio Navarro
 * @date Feb, 2013
 * */
public class AdmonPesosUsuario extends AbstractAdmon {
    
    private PesosusuarioMapper mapper = null;
    
    public AdmonPesosUsuario() {
        super();
        this.mapper = session.getMapper(PesosusuarioMapper.class);
    }
    
    public Pesosusuario[] devolverPesosUsuario(int id_usuario) {
        return mapper.selectByUsuario(id_usuario);
    }
    
    public Pesosusuario devolverPesosUsuarioById(int id_pesosusuario) {
        return mapper.selectByPrimaryKey(id_pesosusuario);
    }
    
    public int insertarPesosUsuario(Pesosusuario record) {
        return mapper.insertSelective(record);
    }
    
    public int actualizarPesosUsuario(Pesosusuario record) {
        return mapper.updateByPrimaryKey(record);
    }
    
}
