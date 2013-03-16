package es.uclm.sri.persistencia.postgre.dao;

import es.uclm.sri.persistencia.postgre.dao.model.Histuser;

public interface HistuserMapper {
    int deleteByPrimaryKey(Integer ID_HISTUSER);

    int insert(Histuser record);

    int insertSelective(Histuser record);

    Histuser selectByPrimaryKey(Integer ID_HISTUSER);
    
    Histuser[] selectByIdUser(Integer ID_USERAPP_FK);

    int updateByPrimaryKeySelective(Histuser record);

    int updateByPrimaryKey(Histuser record);
}