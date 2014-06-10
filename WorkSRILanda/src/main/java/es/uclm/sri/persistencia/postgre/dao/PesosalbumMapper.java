package main.java.es.uclm.sri.persistencia.postgre.dao;

import org.apache.ibatis.annotations.Param;

import main.java.es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;

public interface PesosalbumMapper {
	
    int deleteByPrimaryKey(Integer ID_PESOSALBUM);

    int insert(Pesosalbum record);

    int insertSelective(Pesosalbum record);

    Pesosalbum selectByPrimaryKey(Integer ID_PESOSALBUM);
    
    Pesosalbum[] selectByAlbum(String ALBUM);
    
    Pesosalbum[] selectByArtista(String ARTISTA);
    
    Pesosalbum[] selectByAlbumYArtista(@Param("ALBUM") String ALBUM, @Param("ARTISTA") String ARTISTA);

    int updateByPrimaryKeySelective(Pesosalbum record);

    int updateByPrimaryKey(Pesosalbum record);
    
}
