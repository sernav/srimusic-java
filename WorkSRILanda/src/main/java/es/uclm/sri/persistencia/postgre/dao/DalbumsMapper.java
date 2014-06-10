package main.java.es.uclm.sri.persistencia.postgre.dao;

import org.apache.ibatis.annotations.Param;

import main.java.es.uclm.sri.persistencia.postgre.dao.model.Dalbums;

public interface DalbumsMapper {
    
    int deleteByPrimaryKey(Integer ID_DALBUM);

    int insert(Dalbums record);

    int insertSelective(Dalbums record);

    Dalbums selectByPrimaryKey(Integer ID_DALBUM);
    
    Dalbums[] selectByAlbum(String TITUALBM);
    
    Dalbums[] selectByArtista(String AUTALBM);
    
    Dalbums[] selectByAlbumYArtista(@Param("TITUALBM") String TITUALBM, @Param("AUTALBM") String AUTALBM);

    int updateByPrimaryKeySelective(Dalbums record);

    int updateByPrimaryKey(Dalbums record);
}