package es.uclm.sri.persistencia.postgre.dao;

import org.apache.ibatis.annotations.Param;

import es.uclm.sri.persistencia.postgre.dao.model.Albumsapp;

public interface AlbumsappMapper {
    
    int deleteByPrimaryKey(Integer ID_ALBUMAPP);

    int insert(Albumsapp record);

    int insertSelective(Albumsapp record);

    Albumsapp selectByPrimaryKey(Integer ID_ALBUMAPP);
    
    Albumsapp[] selectByAlbum(String TITUALBM);
    
    Albumsapp[] selectByArtista(String AUTALBM);
    
    Albumsapp[] selectByAlbumYArtista(@Param("TITUALBM") String TITUALBM, @Param("AUTALBM") String AUTALBM);

    int updateByPrimaryKeySelective(Albumsapp record);

    int updateByPrimaryKey(Albumsapp record);
}