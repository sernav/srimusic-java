package es.uclm.sri.persistencia.admon;

import es.uclm.sri.persistencia.postgre.dao.DalbumsMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Dalbums;
import es.uclm.sri.sis.entidades.Album;

public class AdmonAlbums extends AbstractAdmon {
    
    private DalbumsMapper mapper = null;
    
    public AdmonAlbums() {
        super();
        this.mapper = session.getMapper(DalbumsMapper.class);
    }
    
    public void insertarAlbum(Dalbums album) {
        mapper.insert(album);
    }
    
    public void insertarAlbum(Album album) {
        Dalbums record = new Dalbums();
        record.setTITUALBM(album.getTitulo());
        record.setAUTALBM(album.getArtista());
        record.setNUMEPIST(new Integer(album.getNumTemas()));
        record.setANYIOPUB(null);
        record.setGENRALBM(null);
        
        mapper.insert(record);
    }
    
    public void insertarAlbum(de.umass.lastfm.Album album) {
        Dalbums record = new Dalbums();
        record.setTITUALBM(album.getName());
        record.setAUTALBM(album.getArtist());
        record.setNUMEPIST(new Integer(album.getTracks().size()));
        record.setANYIOPUB(new Integer(1900));
        record.setGENRALBM(null);
        
        mapper.insert(record);
    }
    
    public Dalbums[] devolverAlbums(Album album) {
        return mapper.selectByAlbumYArtista(album.getTitulo().trim(), album.getArtista().trim());
    }
    
    public Dalbums[] devolverAlbums(de.umass.lastfm.Album album) {
        return mapper.selectByAlbumYArtista(album.getName().trim(), album.getArtist().trim());
    }

}
