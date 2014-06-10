package main.java.es.uclm.sri.persistencia.postgre.dao;

import main.java.es.uclm.sri.persistencia.postgre.dao.model.Historico;

public interface HistoricoMapper {
    
    int deleteByPrimaryKey(Integer ID_HISTUSER);

    int insert(Historico record);

    int insertSelective(Historico record);

    Historico selectByPrimaryKey(Integer ID_HISTUSER);
    
    Historico[] selectByIdUser(Integer ID_DUSUARIO_FK);

    int updateByPrimaryKeySelective(Historico record);

    int updateByPrimaryKey(Historico record);
}