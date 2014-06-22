package es.uclm.sri.persistencia.admon;

import es.uclm.sri.persistencia.postgre.dao.DalbumsMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Dalbums;
import es.uclm.sri.sis.entidades.Album;

/**
 * Admon para atacar las operaciones de la tabla de base de datos DALBUMS.
 * Utiliza XML de mapeo postgre.dao.sqlmap.DalbumsMapper.xml
 * 
 * @author Sergio Navarro
 * */
public class AdmonAlbums extends AbstractAdmon {
    
    private DalbumsMapper mapper = null;
    
    public AdmonAlbums() {
        super();
        this.mapper = session.getMapper(DalbumsMapper.class);
    }
    
    /**
     * Inserta objeto de entidad Dalbums
     * 
     * @param Dalbums
     * */
    public void insertarAlbum(Dalbums album) {
        mapper.insert(album);
    }
    
    /**
     * Inserta objeto de entidad Album
     * 
     * @param Album
     * */
    public void insertarAlbum(Album album) {
        Dalbums record = new Dalbums();
        record.setTITUALBM(album.getTitulo());
        record.setAUTALBM(album.getArtista());
        record.setNUMEPIST(new Integer(album.getNumTemas()));
        record.setANYIOPUB(null);
        record.setGENRALBM(null);
        
        mapper.insert(record);
    }
    
    /**
     * Inserta objeto de entidad Album de LastFm
     * 
     * @param de.umass.lastfm.Album
     * */
    public void insertarAlbum(de.umass.lastfm.Album album) {
        Dalbums record = new Dalbums();
        record.setTITUALBM(album.getName());
        record.setAUTALBM(album.getArtist());
        //record.setNUMEPIST(new Integer(album.getTracks().size()));
        record.setNUMEPIST(0);
        record.setANYIOPUB(new Integer(1900));
        record.setGENRALBM(null);
        
        mapper.insert(record);
    }
    
    /**
     * Devuelve albums de título y artista
     * 
     * @param titulo: String
     * @param artista: String
     * @return Dalbums[]
     * */
    public Dalbums[] devolverAlbums(String titulo, String artista) {
        return mapper.selectByAlbumYArtista(titulo.trim(), artista.trim());
    }
    
    /**
     * Devuelve albums por el objeto Album
     * 
     * @param Album
     * @return Dalbums[]
     * */
    public Dalbums[] devolverAlbums(Album album) {
        return mapper.selectByAlbumYArtista(album.getTitulo().trim(), album.getArtista().trim());
    }
    /**
     * Devuelve albums por el objeto Album de lastfm
     * 
     * @param de.umass.lastfm.Album
     * @return Dalbums[]
     * */
    public Dalbums[] devolverAlbums(de.umass.lastfm.Album album) {
        return mapper.selectByAlbumYArtista(album.getName().trim(), album.getArtist().trim());
    }

}
