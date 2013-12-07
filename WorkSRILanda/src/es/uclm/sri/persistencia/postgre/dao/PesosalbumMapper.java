package es.uclm.sri.persistencia.postgre.dao;

import es.uclm.sri.persistencia.postgre.dao.model.Pesosalbum;

public interface PesosalbumMapper {
	
    int deleteByPrimaryKey(Integer ID_PESOSALBUM);

    int insert(Pesosalbum record);

    int insertSelective(Pesosalbum record);

    Pesosalbum selectByPrimaryKey(Integer ID_PESOSALBUM);
    
    Pesosalbum[] selectByAlbum(String ALBUM);
    
    Pesosalbum[] selectByArtista(String ARTISTA);
    
    Pesosalbum[] selectByAlbumYArtista(String ALBUM, String ARTISTA);

    int updateByPrimaryKeySelective(Pesosalbum record);

    int updateByPrimaryKey(Pesosalbum record);
    
}
