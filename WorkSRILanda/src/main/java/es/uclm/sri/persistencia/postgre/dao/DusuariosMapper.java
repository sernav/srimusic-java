package main.java.es.uclm.sri.persistencia.postgre.dao;

import org.apache.ibatis.annotations.Param;

import main.java.es.uclm.sri.persistencia.postgre.dao.model.Dusuarios;

public interface DusuariosMapper {
    
    int deleteByPrimaryKey(Integer ID_DUSUARIO);

    int insert(Dusuarios record);

    int insertSelective(Dusuarios record);

    Dusuarios selectByPrimaryKey(Integer ID_DUSUARIO);
    
    Dusuarios selectByNick(@Param("NICKUSER") String NICKUSER);

    int updateByPrimaryKeySelective(Dusuarios record);

    int updateByPrimaryKey(Dusuarios record);
}