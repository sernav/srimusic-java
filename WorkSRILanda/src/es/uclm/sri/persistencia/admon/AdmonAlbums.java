package es.uclm.sri.persistencia.admon;

import es.uclm.sri.persistencia.postgre.dao.AlbumsappMapper;
import es.uclm.sri.persistencia.postgre.dao.model.Albumsapp;
import es.uclm.sri.sis.entidades.Album;

public class AdmonAlbums extends AbstractAdmon {
    
    private AlbumsappMapper mapper = null;
    
    public AdmonAlbums() {
        super();
        this.mapper = session.getMapper(AlbumsappMapper.class);
    }
    
    public void insertarAlbum(Albumsapp album) {
        mapper.insert(album);
    }
    
    public void insertarAlbum(Album album) {
        Albumsapp record = new Albumsapp();
        record.setTITUALBM(album.getTitulo());
        record.setAUTALBM(album.getArtista());
        record.setNUMEPIST(new Integer(album.getNumTemas()));
        record.setANYIOPUB(null);
        record.setGENRALBM(null);
        
        mapper.insert(record);
    }
    
    public void insertarAlbum(de.umass.lastfm.Album album) {
        Albumsapp record = new Albumsapp();
        record.setTITUALBM(album.getName());
        record.setAUTALBM(album.getArtist());
        record.setNUMEPIST(new Integer(album.getTracks().size()));
        record.setANYIOPUB(null);
        record.setGENRALBM(null);
        
        mapper.insert(record);
    }
    
    public Albumsapp[] devolverAlbums(Album album) {
        return mapper.selectByAlbumYArtista(album.getTitulo().trim(), album.getArtista().trim());
    }
    
    public Albumsapp[] devolverAlbums(de.umass.lastfm.Album album) {
        return mapper.selectByAlbumYArtista(album.getName().trim(), album.getArtist().trim());
    }

}
