package es.uclm.sri.persistencia.postgre.dao;

import es.uclm.sri.persistencia.postgre.dao.model.Albumsapp;

public interface AlbumsappMapper {
    int deleteByPrimaryKey(Integer ID_ALBUMAPP);

    int insert(Albumsapp record);

    int insertSelective(Albumsapp record);

    Albumsapp selectByPrimaryKey(Integer ID_ALBUMAPP);

    int updateByPrimaryKeySelective(Albumsapp record);

    int updateByPrimaryKey(Albumsapp record);
}