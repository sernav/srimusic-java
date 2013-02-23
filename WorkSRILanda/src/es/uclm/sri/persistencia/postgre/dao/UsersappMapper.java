package es.uclm.sri.persistencia.postgre.dao;

import es.uclm.sri.persistencia.postgre.dao.model.Usersapp;

public interface UsersappMapper {
    int deleteByPrimaryKey(Integer ID_USERAPP);

    int insert(Usersapp record);

    int insertSelective(Usersapp record);

    Usersapp selectByPrimaryKey(Integer ID_USERAPP);

    int updateByPrimaryKeySelective(Usersapp record);

    int updateByPrimaryKey(Usersapp record);
}