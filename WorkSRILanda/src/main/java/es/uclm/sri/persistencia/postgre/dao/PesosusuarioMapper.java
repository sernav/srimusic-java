package es.uclm.sri.persistencia.postgre.dao;

import es.uclm.sri.persistencia.postgre.dao.model.Pesosusuario;

public interface PesosusuarioMapper {
	
    int deleteByPrimaryKey(Integer ID_PESOSALBUM);

    int insert(Pesosusuario record);

    int insertSelective(Pesosusuario record);

    Pesosusuario selectByPrimaryKey(Integer ID_PESOSALBUM);
    
    Pesosusuario[] selectByUsuario(Integer ID_DUSUARIO_FK);

    int updateByPrimaryKeySelective(Pesosusuario record);

    int updateByPrimaryKey(Pesosusuario record);
}